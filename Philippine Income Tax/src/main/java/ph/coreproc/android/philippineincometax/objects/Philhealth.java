package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "philhealth")
public class Philhealth {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private double salaryRateFrom;
	@DatabaseField
	private double salaryRateTo;
	@DatabaseField
	private double salaryBase;
	@DatabaseField
	private double totalContribution;
	@DatabaseField
	private double employeeContribution;
	@DatabaseField
	private double employerContribution;

	public Philhealth() {
		// ORMLite needs this
	}

	public Philhealth(String[] csvLine) {
		this.id = Integer.parseInt(csvLine[0]);
		this.salaryRateFrom = Double.parseDouble(csvLine[1]);
		this.salaryRateTo = Double.parseDouble(csvLine[2]);
		this.salaryBase = Double.parseDouble(csvLine[3]);
		this.totalContribution = Double.parseDouble(csvLine[4]);
		this.employeeContribution = Double.parseDouble(csvLine[5]);
		this.employerContribution = Double.parseDouble(csvLine[6]);
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

	public double getSalaryBase() {
		return salaryBase;
	}

	public void setSalaryBase(double salaryBase) {
		this.salaryBase = salaryBase;
	}

	public double getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(double totalContribution) {
		this.totalContribution = totalContribution;
	}

	public double getEmployeeContribution() {
		return employeeContribution;
	}

	public void setEmployeeContribution(double employeeContribution) {
		this.employeeContribution = employeeContribution;
	}

	public double getEmployerContribution() {
		return employerContribution;
	}

	public void setEmployerContribution(double employerContribution) {
		this.employerContribution = employerContribution;
	}

}
