package ph.coreproc.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import ph.coreproc.android.philippineincometax.db.DatabaseHelper;
import ph.coreproc.android.philippineincometax.db.DatabaseManager;
import ph.coreproc.android.philippineincometax.objects.WithholdingTax;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class WithholdingTaxModel {
	private DatabaseHelper db;
	Dao<WithholdingTax, Integer> withholdingTaxDao;

	public WithholdingTaxModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			withholdingTaxDao = db.getWithholdingTaxDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}

	}

	public int create(WithholdingTax withholdingTax) {
		try {
			return withholdingTaxDao.create(withholdingTax);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int update(WithholdingTax withholdingTax) {
		try {
			return withholdingTaxDao.update(withholdingTax);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(WithholdingTax withholdingTax) {
		try {
			return withholdingTaxDao.delete(withholdingTax);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public List<WithholdingTax> getAll() {
		try {
			return withholdingTaxDao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

	public WithholdingTax getWithholdingTax(double taxableIncome) {
		return computeWithholdingTax(taxableIncome, 0, 1, "MONTHLY");
	}

	public WithholdingTax getWithholdingTax(double taxableIncome, int dependents) {
		return computeWithholdingTax(taxableIncome, dependents, 1, "MONTHLY");
	}

	public WithholdingTax getWithholdingTax(double taxableIncome,
			int dependents, int withholdingTaxTypeId) {
		return computeWithholdingTax(taxableIncome, dependents,
				withholdingTaxTypeId, "MONTHLY");
	}

	public WithholdingTax getWithholdingTax(double taxableIncome,
			int dependents, int withholdingTaxTypeId,
			String computationFrequency) {
		return computeWithholdingTax(taxableIncome, dependents,
				withholdingTaxTypeId, computationFrequency);
	}

	private WithholdingTax computeWithholdingTax(double taxableIncome,
			int dependents, int withholdingTaxTypeId,
			String computationFrequency) {
		QueryBuilder<WithholdingTax, Integer> qb = withholdingTaxDao
				.queryBuilder();
		try {
			PreparedQuery<WithholdingTax> pq = qb.where()
					.eq("withholdingTaxTypeId", withholdingTaxTypeId).and()
					.eq("dependents", dependents).and()
					.eq("computationFrequency", computationFrequency).and()
					.le("taxableIncomeFrom", taxableIncome).and()
					.ge("taxableIncomeTo", taxableIncome).prepare();
			// Log.i("wt", pq.getStatement());
			WithholdingTax withholdingTax = withholdingTaxDao.queryForFirst(pq);
			withholdingTax.setTaxableIncome(taxableIncome);
			return withholdingTax;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
