package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sss")
public class Sss {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private double salaryRateFrom;
	@DatabaseField
	private double salaryRateTo;
	@DatabaseField
	private double monthlySalaryCredit;
	@DatabaseField
	private double employerContribution;
	@DatabaseField
	private double employeeContribution;
	@DatabaseField
	private double employerEc;
	@DatabaseField
	private double totalPremium;
	@DatabaseField
	private double totalContribution;

	public Sss() {
		// ORMLite needs this
	}

	public Sss(String[] csvLine) {
		this.id = Integer.parseInt(csvLine[0]);
		this.salaryRateFrom = Double.parseDouble(csvLine[1]);
		this.salaryRateTo = Double.parseDouble(csvLine[2]);
		this.monthlySalaryCredit = Double.parseDouble(csvLine[3]);
		this.employerContribution = Double.parseDouble(csvLine[4]);
		this.employeeContribution = Double.parseDouble(csvLine[5]);
		this.employerEc = Double.parseDouble(csvLine[6]);
		this.totalPremium = Double.parseDouble(csvLine[7]);
		this.totalContribution = Double.parseDouble(csvLine[8]);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalaryRateFrom() {
		return salaryRateFrom;
	}

	public void setSalaryRateFrom(double salaryRateFrom) {
		this.salaryRateFrom = salaryRateFrom;
	}

	public double getSalaryRateTo() {
		return salaryRateTo;
	}

	public void setSalaryRateTo(double salaryRateTo) {
		this.salaryRateTo = salaryRateTo;
	}

	public double getMonthlySalaryCredit() {
		return monthlySalaryCredit;
	}

	public void setMonthlySalaryCredit(double monthlySalaryCredit) {
		this.monthlySalaryCredit = monthlySalaryCredit;
	}

	public double getEmployerContribution() {
		return employerContribution;
	}

	public void setEmployerContribution(double employerContribution) {
		this.employerContribution = employerContribution;
	}

	public double getEmployeeContribution() {
		return employeeContribution;
	}

	public void setEmployeeContribution(double employeeContribution) {
		this.employeeContribution = employeeContribution;
	}

	public double getEmployerEc() {
		return employerEc;
	}

	public void setEmployerEc(double employerEc) {
		this.employerEc = employerEc;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public double getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(double totalContribution) {
		this.totalContribution = totalContribution;
	}

}
