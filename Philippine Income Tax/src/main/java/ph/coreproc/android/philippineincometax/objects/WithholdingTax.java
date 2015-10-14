package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "withholding_tax")
public class WithholdingTax {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int withholdingTaxTypeId;
	@DatabaseField
	private int dependents;
	@DatabaseField
	private String computationFrequency;
	@DatabaseField
	private double taxableIncomeFrom;
	@DatabaseField
	private double taxableIncomeTo;
	@DatabaseField
	private double baseTax;
	@DatabaseField
	private double percentOver;

	private double taxableIncome = 0;

	public WithholdingTax() {
		// ORMLite needs this
	}

	public WithholdingTax(String[] csvLine) {
		this.id = Integer.parseInt(csvLine[0]);
		this.withholdingTaxTypeId = Integer.parseInt(csvLine[1]);
		this.dependents = Integer.parseInt(csvLine[2]);
		this.computationFrequency = csvLine[3];
		this.taxableIncomeFrom = Double.parseDouble(csvLine[4]);
		this.taxableIncomeTo = Double.parseDouble(csvLine[5]);
		this.baseTax = Double.parseDouble(csvLine[6]);
		this.percentOver = Double.parseDouble(csvLine[7]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	}

	public String getComputationFrequency() {
		return computationFrequency;
	}

	public void setComputationFrequency(String computationFrequency) {
		this.computationFrequency = computationFrequency;
	}

	public double getTaxableIncomeFrom() {
		return taxableIncomeFrom;
	}

	public void setTaxableIncomeFrom(double taxableIncomeFrom) {
		this.taxableIncomeFrom = taxableIncomeFrom;
	}

	public double getTaxableIncomeTo() {
		return taxableIncomeTo;
	}

	public void setTaxableIncomeTo(double taxableIncomeTo) {
		this.taxableIncomeTo = taxableIncomeTo;
	}

	public double getBaseTax() {
		return baseTax;
	}

	public void setBaseTax(double baseTax) {
		this.baseTax = baseTax;
	}

	public double getPercentOver() {
		return percentOver;
	}

	public void setPercentOver(double percentOver) {
		this.percentOver = percentOver;
	}

	public double getTaxableIncome() {
		return taxableIncome;
	}

	public void setTaxableIncome(double taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public double getWithholdingTaxAmount() {
		return round(
				((getTaxableIncome() - getTaxableIncomeFrom()) * (getPercentOver() / 100))
						+ getBaseTax(), 2);
	}

	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
		return Double.parseDouble(bd.toString());
	}

}
