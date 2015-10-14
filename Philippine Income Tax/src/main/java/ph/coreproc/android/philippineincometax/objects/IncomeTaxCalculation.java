package ph.coreproc.android.philippineincometax.objects;

import android.content.Context;

import ph.coreproc.android.philippineincometax.models.NonTaxableIncomeModel;
import ph.coreproc.android.philippineincometax.models.PagibigModel;
import ph.coreproc.android.philippineincometax.models.PhilHealthModel;
import ph.coreproc.android.philippineincometax.models.SssModel;
import ph.coreproc.android.philippineincometax.models.TaxableIncomeModel;
import ph.coreproc.android.philippineincometax.models.WithholdingTaxModel;
import com.j256.ormlite.field.DatabaseField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class IncomeTaxCalculation {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private double salaryRate;
    @DatabaseField
    private int salaryFrequency;
    @DatabaseField
    private int withholdingTaxTypeId;
    @DatabaseField
    private int dependents;
    @DatabaseField
    private boolean isSssActive;
    @DatabaseField
    private boolean isPhilhealthActive;
    @DatabaseField
    private boolean isPagibigActive;
    @DatabaseField
    private Date createdOn;

    private Sss mSss = null;
    private Philhealth mPhilhealth = null;
    private Pagibig mPagibig = null;

    public IncomeTaxCalculation() {
        // Nothing here
    }

    public IncomeTaxCalculation(double salaryRate) {
        this.salaryRate = salaryRate;
        this.salaryFrequency = 1;
        this.withholdingTaxTypeId = 2;
        this.dependents = 0;
        this.isSssActive = true;
        this.isPhilhealthActive = true;
        this.isPagibigActive = true;
        this.createdOn = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(double salaryRate, Context ctx) {
        this.salaryRate = salaryRate;
        setSss(ctx);
        setPhilhealth(ctx);
        setPagibig(ctx);
    }

    private void setSss(Context ctx) {
        if (isSssActive()) {
            SssModel sssModel = new SssModel(ctx);
            mSss = sssModel.getSss(getSalaryRate(), getSalaryFrequency());
        } else {
            mSss = null;
        }
    }

    private void setPhilhealth(Context ctx) {
        if (isPhilhealthActive()) {
            PhilHealthModel phModel = new PhilHealthModel(ctx);
            mPhilhealth = phModel.getPhilhealth(getSalaryRate(),
                    getSalaryFrequency());
        } else {
            mPhilhealth = null;
        }
    }

    private void setPagibig(Context ctx) {
        if (isPagibigActive()) {
            PagibigModel pModel = new PagibigModel(ctx);
            mPagibig = pModel
                    .getPagibig(getSalaryRate(), getSalaryFrequency());
        } else {
            mPagibig = null;
        }
    }

    public Sss getSss() {
        return mSss;
    }

    public Philhealth getPhilhealth() {
        return mPhilhealth;
    }

    public Pagibig getPagibig() {
        return mPagibig;
    }

    public int getSalaryFrequency() {
        return salaryFrequency;
    }

    public String getSalaryFrequencyString() {
        switch (getSalaryFrequency()) {
            case 1:
                return "MONTHLY";
            case 2:
                return "SEMI-MONTHLY";
            case 4:
                return "WEEKLY";
            case 20:
                return "DAILY";
            default:
                return "MONTHLY";
        }
    }

    public void setSalaryFrequency(int salaryFrequency) {
        this.salaryFrequency = salaryFrequency;
    }

    public int getWithholdingTaxTypeId() {
        return withholdingTaxTypeId;
    }

    public void setWithholdingTaxTypeId(int withholdingTaxTypeId) {
        this.withholdingTaxTypeId = withholdingTaxTypeId;
    }

    public int getDependents() {
        return dependents;
    }

    public void setDependents(int dependents) {
        this.dependents = dependents;
        if (dependents > 4) {
            this.dependents = 4;
        }
        if (dependents < 0) {
            this.dependents = 0;
        }
    }

    public boolean isSssActive() {
        return isSssActive;
    }

    public void setSssActive(boolean isSssActive) {
        this.isSssActive = isSssActive;
    }

    public boolean isPhilhealthActive() {
        return isPhilhealthActive;
    }

    public void setPhilhealthActive(boolean isPhilhealthActive) {
        this.isPhilhealthActive = isPhilhealthActive;
    }

    public boolean isPagibigActive() {
        return isPagibigActive;
    }

    public void setPagibigActive(boolean isPagibigActive) {
        this.isPagibigActive = isPagibigActive;
    }


    public List<TaxableIncome> getTaxableIncome(Context ctx) {
        TaxableIncomeModel taxableIncomeModel = new TaxableIncomeModel(ctx);
        return taxableIncomeModel.getListFromIncomeTaxCalculation(this);
    }

    public List<NonTaxableIncome> getNonTaxableIncome(Context ctx) {
        NonTaxableIncomeModel nonTaxableIncomeModel = new NonTaxableIncomeModel(
                ctx);
        return nonTaxableIncomeModel.getListFromIncomeTaxCalculation(this);
    }

    public double getTotalTaxableIncomeAmount(Context ctx) {
        double taxableIncome = getSalaryRate();

        if (mSss != null) {
            taxableIncome = taxableIncome - mSss.getEmployeeContribution();
        }
        if (mPhilhealth != null) {
            taxableIncome = taxableIncome
                    - mPhilhealth.getEmployeeContribution();
        }
        if (mPagibig != null) {
            taxableIncome = taxableIncome - mPagibig.getEmployeeContribution();
        }

        double taxableIncomeListAmount = 0;
        List<TaxableIncome> taxableIncomeList = getTaxableIncome(ctx);
        if (taxableIncomeList != null && taxableIncomeList.size() > 0) {
            for (TaxableIncome t : taxableIncomeList) {
                taxableIncomeListAmount = taxableIncomeListAmount
                        + t.getAmount();
            }
        }

        taxableIncome = taxableIncome + taxableIncomeListAmount;
        System.out.println("taxableincomelistamount is: "
                + taxableIncomeListAmount);
        System.out.println("taxable income is: " + taxableIncome);

        return taxableIncome;
    }

    public WithholdingTax getWithholdingTax(Context ctx) {
        WithholdingTaxModel wwModel = new WithholdingTaxModel(ctx);
        WithholdingTax withholdingTax = wwModel.getWithholdingTax(
                getTotalTaxableIncomeAmount(ctx), getDependents(),
                getWithholdingTaxTypeId(), getSalaryFrequencyString());
        return withholdingTax;
    }

    public double getNetIncome(Context ctx) {
        double netIncome = getTotalTaxableIncomeAmount(ctx)
                - getWithholdingTax(ctx).getWithholdingTaxAmount();

        List<NonTaxableIncome> nonTaxableIncomeList = getNonTaxableIncome(ctx);
        double nonTaxableIncomeAmount = 0;
        if (nonTaxableIncomeList != null && nonTaxableIncomeList.size() > 0) {
            for (NonTaxableIncome n : nonTaxableIncomeList) {
                nonTaxableIncomeAmount = nonTaxableIncomeAmount + n.getAmount();
            }
            System.out.println("non taxable income amoun is: "
                    + nonTaxableIncomeAmount);
        }

        netIncome = netIncome + nonTaxableIncomeAmount;

        return round(netIncome, 2);
    }

    private double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return Double.parseDouble(bd.toString());
    }

}
