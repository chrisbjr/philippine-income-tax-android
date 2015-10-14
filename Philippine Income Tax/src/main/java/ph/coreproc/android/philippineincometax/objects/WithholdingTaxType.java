package ph.coreproc.android.philippineincometax.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "withholding_tax_type")
public class WithholdingTaxType {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String description;

	public WithholdingTaxType() {
		// ORMLite needs this
	}

	public WithholdingTaxType(String[] csvLine) {
		this.id = Integer.parseInt(csvLine[0]);
		this.name = csvLine[1];
		this.description = csvLine[2];
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
