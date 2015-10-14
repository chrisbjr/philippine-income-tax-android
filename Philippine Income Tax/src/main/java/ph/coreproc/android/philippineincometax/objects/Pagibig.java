package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pagibig")
public class Pagibig {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private double salaryRateFrom;
	@DatabaseField
	private double salaryRateTo;
	@DatabaseField
	private double employerPercentage;
	@DatabaseField
	private double employeePercentage;
	@DatabaseField
	private double maximum;

	private double salaryRate = 0;

	public Pagibig() {
		// ORMLite needs this
	}

	public Pagibig(String[] csvLine) {
		this.id = Integer.parseInt(csvLine[0]);
		this.salaryRateFrom = Double.parseDouble(csvLine[1]);
		this.salaryRateTo = Double.parseDouble(csvLine[2]);
		this.employerPercentage = Double.parseDouble(csvLine[3]);
		this.employeePercentage = Double.parseDouble(csvLine[4]);
		this.maximum = Double.parseDouble(csvLine[5]);
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

	public double getSalaryRate() {
		return salaryRate;
	}

	public void setSalaryRate(double salaryRate, int frequency) {
		this.salaryRate = salaryRate * frequency;
	}

	public double getEmployerPercentage() {
		return employerPercentage;
	}

	public void setEmployerPercentage(double employerPercentage) {
		this.employerPercentage = employerPercentage;
	}

	public double getEmployeePercentage() {
		return employeePercentage;
	}

	public void setEmployeePercentage(double employeePercentage) {
		this.employeePercentage = employeePercentage;
	}

	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}

	public double getEmployeeContribution() {
		double employeeContribution = getSalaryRate() * getEmployeePercentage();
		if (employeeContribution > getMaximum()) {
			employeeContribution = getMaximum();
		}
		return employeeContribution;
	}

	public double getEmployerContribution() {
		double employerContribution = getSalaryRate() * getEmployerPercentage();
		if (employerContribution > getMaximum()) {
			employerContribution = getMaximum();
		}
		return employerContribution;
	}

}
