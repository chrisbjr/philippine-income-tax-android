package com.chrisbjr.android.philippineincometax.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chrisbjr.android.philippineincometax.R;
import com.chrisbjr.android.philippineincometax.objects.NonTaxableIncome;
import com.chrisbjr.android.philippineincometax.objects.TaxableIncome;

import java.text.DecimalFormat;

public class IncomeAdapter {

	private TaxableIncome mTaxableIncome = null;
	private NonTaxableIncome mNonTaxableIncome = null;

	public IncomeAdapter(TaxableIncome ti) {
		mTaxableIncome = ti;
	}

	public IncomeAdapter(NonTaxableIncome ti) {
		mNonTaxableIncome = ti;
	}

	public View getView(Context ctx) {
		View view = LayoutInflater.from(ctx).inflate(R.layout.layout_income,
				null, false);

		TextView incomeNameTextView = (TextView) view
				.findViewById(R.id.incomeNameTextView);
		TextView incomeAmountTextView = (TextView) view
				.findViewById(R.id.incomeAmountTextView);
		TextView incomeLabelTextView = (TextView) view
				.findViewById(R.id.incomeLabelTextView);

		if (mTaxableIncome != null) {
			incomeNameTextView.setText(mTaxableIncome.getName());
			incomeAmountTextView.setText("P"
					+ customFormat("###,###,###,###.##",
							mTaxableIncome.getAmount()));
			incomeLabelTextView.setText("Taxable Income");
		}

		if (mNonTaxableIncome != null) {
			incomeNameTextView.setText(mNonTaxableIncome.getName());
			incomeAmountTextView.setText("P"
					+ customFormat("###,###,###,###.##",
							mNonTaxableIncome.getAmount()));
			incomeLabelTextView.setText("Non-Taxable Income");
		}

		return view;
	}

	private String customFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);

	}

}
