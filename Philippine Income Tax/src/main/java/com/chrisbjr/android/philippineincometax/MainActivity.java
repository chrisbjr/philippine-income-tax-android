package com.chrisbjr.android.philippineincometax;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.chrisbjr.android.philippineincometax.adapters.IncomeAdapter;
import com.chrisbjr.android.philippineincometax.models.IncomeTaxCalculationModel;
import com.chrisbjr.android.philippineincometax.models.NonTaxableIncomeModel;
import com.chrisbjr.android.philippineincometax.models.TaxableIncomeModel;
import com.chrisbjr.android.philippineincometax.objects.IncomeTaxCalculation;
import com.chrisbjr.android.philippineincometax.objects.NonTaxableIncome;
import com.chrisbjr.android.philippineincometax.objects.TaxableIncome;

import java.text.DecimalFormat;

public class MainActivity extends SherlockActivity {

    private IncomeTaxCalculation mIncomeTaxCalculation;
    private IncomeTaxCalculationModel mIncomeTaxCalculationModel;
    private Context mContext;
    private CheckBox mSssCheckBox;
    private CheckBox mPhilhealthCheckBox;
    private CheckBox mPagibigCheckBox;
    private EditText mSalaryEditText;
    private TextView mNetIncomeTextView;
    private LinearLayout mMoreOptionsLinearLayout;
    private TextView mMoreOptionsTextView;
    private TextView mDependentsTextView;
    private SeekBar mDependentsSeekBar;
    private Spinner mWithholdingTaxTypeSpinner;
    private TaxableIncomeModel mTaxableIncomeModel;
    private NonTaxableIncomeModel mNonTaxableIncomeModel;
    private LinearLayout mTaxableIncomeLinearLayout;
    private LinearLayout mNonTaxableIncomeLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mContext = this;

        mIncomeTaxCalculation = new IncomeTaxCalculation(0);
        mIncomeTaxCalculationModel = new IncomeTaxCalculationModel(mContext);
        mIncomeTaxCalculationModel.create(mIncomeTaxCalculation);

        mTaxableIncomeModel = new TaxableIncomeModel(mContext);
        mNonTaxableIncomeModel = new NonTaxableIncomeModel(mContext);

        mTaxableIncomeLinearLayout = (LinearLayout) findViewById(R.id.taxableIncomeLinearLayout);
        mNonTaxableIncomeLinearLayout = (LinearLayout) findViewById(R.id.nonTaxableIncomeLinearLayout);

        mSssCheckBox = (CheckBox) findViewById(R.id.sssCheckBox);
        mSssCheckBox.setChecked(mIncomeTaxCalculation.isSssActive());
        mSssCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });
        mPhilhealthCheckBox = (CheckBox) findViewById(R.id.philhealthCheckBox);
        mPhilhealthCheckBox.setChecked(mIncomeTaxCalculation
                .isPhilhealthActive());
        mPhilhealthCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });
        mPagibigCheckBox = (CheckBox) findViewById(R.id.pagibigCheckBox);
        mPagibigCheckBox.setChecked(mIncomeTaxCalculation.isPagibigActive());
        mPagibigCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });

        mSalaryEditText = (EditText) findViewById(R.id.salaryEditText);
        if (mIncomeTaxCalculation.getSalaryRate() > 0) {
            mSalaryEditText.setText(mIncomeTaxCalculation.getSalaryRate() + "");
        }
        mSalaryEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                compute();
                return false;
            }
        });

        mNetIncomeTextView = (TextView) findViewById(R.id.netIncomeTextView);
        mMoreOptionsLinearLayout = (LinearLayout) findViewById(R.id.moreOptionsLinearLayout);

        mMoreOptionsTextView = (TextView) findViewById(R.id.moreOptionsTextView);
        mMoreOptionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                toggleMoreOptions();
            }
        });

        mDependentsTextView = (TextView) findViewById(R.id.dependentsTextView);
        mDependentsSeekBar = (SeekBar) findViewById(R.id.dependentsSeekBar);
        mDependentsSeekBar.setMax(4);
        mDependentsSeekBar.incrementProgressBy(1);
        mDependentsSeekBar.setProgress(mIncomeTaxCalculation.getDependents());
        mDependentsSeekBar
                .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar arg0) {
                        // Nothing here
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar arg0) {
                        // Nothing here
                    }

                    @Override
                    public void onProgressChanged(SeekBar arg0, int arg1,
                                                  boolean arg2) {
                        if (arg1 == arg0.getMax()) {
                            mDependentsTextView.setText(arg1 + " or more");
                        } else {
                            mDependentsTextView.setText(arg1 + "");
                        }
                        compute();
                    }
                });

        mWithholdingTaxTypeSpinner = (Spinner) findViewById(R.id.withholdingTaxTypeSpinner);
        mWithholdingTaxTypeSpinner.setSelection(mIncomeTaxCalculation
                .getWithholdingTaxTypeId() - 1);
        mWithholdingTaxTypeSpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        if (arg2 == 0) {
                            mDependentsSeekBar.setEnabled(false);
                            mDependentsSeekBar.setProgress(0);
                        } else {
                            mDependentsSeekBar.setEnabled(true);
                        }
                        compute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // Nothing here
                    }
                });

        TextView addTaxableIncomeTextView = (TextView) findViewById(R.id.addTaxableIncomeTextView);
        addTaxableIncomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final View view = LayoutInflater.from(mContext).inflate(
                        R.layout.dialog_add_income, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(
                        mContext).create();
                alertDialog.setTitle("Add A Taxable Income");
                alertDialog.setView(view);
                alertDialog.setCancelable(false);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }
                );
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }
                );
                alertDialog.show();
                Button alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                if (alertDialogButton != null) {
                    alertDialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            assert view != null;
                            EditText incomeNameEditText = (EditText) view
                                    .findViewById(R.id.incomeNameEditText);
                            EditText incomeAmountEditText = (EditText) view
                                    .findViewById(R.id.incomeAmountEditText);
                            Editable incomeNameEditTextText = incomeNameEditText.getText();
                            String incomeName = "";
                            if (incomeNameEditTextText != null) {
                                incomeName = incomeNameEditTextText.toString().trim();
                            }
                            Editable incomeAmountEditTextText = incomeAmountEditText
                                    .getText();
                            String incomeAmountString = "";
                            if (incomeAmountEditTextText != null) {
                                incomeAmountString = incomeAmountEditTextText.toString().trim();
                            }

                            if (incomeAmountString.length() <= 0) {
                                incomeAmountEditText
                                        .setError("Amount needs to be greater than 0");
                                return;
                            }
                            double incomeAmount = Double
                                    .parseDouble(incomeAmountString);
                            if (incomeAmount <= 0) {
                                incomeAmountEditText
                                        .setError("Amount needs to be greater than 0");
                                return;
                            }

                            // Save the income
                            TaxableIncome ti = new TaxableIncome(
                                    mIncomeTaxCalculation, incomeName,
                                    incomeAmount);
                            mTaxableIncomeModel.create(ti);
                            IncomeAdapter tia = new IncomeAdapter(ti);
                            mTaxableIncomeLinearLayout.addView(tia
                                    .getView(mContext));
                            compute();
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });

        TextView addNonTaxableIncomeTextView = (TextView) findViewById(R.id.addNonTaxableIncomeTextView);
        addNonTaxableIncomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                final View view = LayoutInflater.from(mContext).inflate(
                        R.layout.dialog_add_income, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(
                        mContext).create();
                alertDialog.setTitle("Add A Non-Taxable Income");
                alertDialog.setView(view);
                alertDialog.setCancelable(false);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }
                );
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }
                );
                alertDialog.show();
                Button alertDialogButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                if (alertDialogButton != null) {
                    alertDialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            assert view != null;
                            EditText incomeNameEditText = (EditText) view
                                    .findViewById(R.id.incomeNameEditText);
                            EditText incomeAmountEditText = (EditText) view
                                    .findViewById(R.id.incomeAmountEditText);
                            Editable incomeNameEditTextText = incomeNameEditText.getText();
                            String incomeName = "";
                            if (incomeNameEditTextText != null) {
                                incomeName = incomeNameEditTextText.toString().trim();
                            }
                            Editable incomeAmountEditTextText = incomeAmountEditText.getText();
                            String incomeAmountString = "";
                            if (incomeAmountEditTextText != null) {
                                incomeAmountString = incomeAmountEditTextText.toString().trim();
                            }
                            if (incomeAmountString.length() <= 0) {
                                incomeAmountEditText
                                        .setError("Amount needs to be greater than 0");
                                return;
                            }
                            double incomeAmount = Double
                                    .parseDouble(incomeAmountString);
                            if (incomeAmount <= 0) {
                                incomeAmountEditText
                                        .setError("Amount needs to be greater than 0");
                                return;
                            }

                            // Save the income
                            NonTaxableIncome ti = new NonTaxableIncome(
                                    mIncomeTaxCalculation, incomeName,
                                    incomeAmount);
                            mNonTaxableIncomeModel.create(ti);
                            IncomeAdapter ia = new IncomeAdapter(ti);
                            mNonTaxableIncomeLinearLayout.addView(ia
                                    .getView(mContext));
                            compute();
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });

    }

    public void toggleMoreOptions() {
        if (mMoreOptionsLinearLayout.getVisibility() == View.VISIBLE) {
            mMoreOptionsLinearLayout.setVisibility(View.GONE);
            mMoreOptionsTextView.setText("More Income Tax Options");
        } else {
            mMoreOptionsLinearLayout.setVisibility(View.VISIBLE);
            mMoreOptionsTextView.setText("Less Income Tax Options");
        }
    }

    public void compute() {
        Editable salaryEditable = mSalaryEditText.getText();
        String salaryStringValue = "";
        if (salaryEditable != null) {
            salaryStringValue = salaryEditable.toString().trim();
        }
        double salary = 0;
        if (salaryStringValue.length() > 0) {
            salary = Double.parseDouble(mSalaryEditText.getText().toString());
            if (salary >= 100000000) {
                mSalaryEditText.setError("Number is too large");
                return;
            }
        }
        mIncomeTaxCalculation.setSalaryRate(salary);
        mIncomeTaxCalculation.setSalaryFrequency(1);
        mIncomeTaxCalculation
                .setWithholdingTaxTypeId(mWithholdingTaxTypeSpinner
                        .getSelectedItemPosition() + 1);
        mIncomeTaxCalculation.setDependents(mDependentsSeekBar.getProgress());

        mIncomeTaxCalculation.setSssActive(mSssCheckBox.isChecked());
        mIncomeTaxCalculation.setPhilhealthActive(mPhilhealthCheckBox
                .isChecked());
        mIncomeTaxCalculation.setPagibigActive(mPagibigCheckBox.isChecked());

        double netIncome = mIncomeTaxCalculation.getNetIncome(mContext);
        mNetIncomeTextView.setText("P"
                + customFormat("###,###,###,###.##", netIncome));

        mIncomeTaxCalculationModel.update(mIncomeTaxCalculation);
    }

    private String customFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);

    }

}