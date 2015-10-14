package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class NonTaxableIncome {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(canBeNull = false, foreign = true)
	private IncomeTaxCalculation incomeTaxCalculation;
	@DatabaseField
	private String name;
	@DatabaseField
	private double amount;
	@DatabaseField
	private Date createdOn;

	public NonTaxableIncome() {
		// Nothing here
	}

	public NonTaxableIncome(IncomeTaxCalculation incomeTaxCalculation,
			String name, double amount) {
		this.incomeTaxCalculation = incomeTaxCalculation;
		this.name = name;
		this.amount = amount;
		this.createdOn = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IncomeTaxCalculation getIncomeTaxCalculation() {
		return incomeTaxCalculation;
	}

	public void setIncomeTaxCalculation(
			IncomeTaxCalculation incomeTaxCalculation) {
		this.incomeTaxCalculation = incomeTaxCalculation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
