package ph.coreproc.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import ph.coreproc.android.philippineincometax.db.DatabaseHelper;
import ph.coreproc.android.philippineincometax.db.DatabaseManager;
import ph.coreproc.android.philippineincometax.objects.IncomeTaxCalculation;
import ph.coreproc.android.philippineincometax.objects.NonTaxableIncome;
import android.content.Context;

import com.j256.ormlite.dao.Dao;

public class NonTaxableIncomeModel {
	private DatabaseHelper db;
	Dao<NonTaxableIncome, Integer> nonTaxableIncomeDao;

	public NonTaxableIncomeModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			nonTaxableIncomeDao = db.getNonTaxableIncomeDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int create(NonTaxableIncome nonTaxableIncome) {
		try {
			return nonTaxableIncomeDao.create(nonTaxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(NonTaxableIncome nonTaxableIncome) {
		try {
			return nonTaxableIncomeDao.update(nonTaxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(NonTaxableIncome nonTaxableIncome) {
		try {
			return nonTaxableIncomeDao.delete(nonTaxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<NonTaxableIncome> getAll() {
		try {
			return nonTaxableIncomeDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<NonTaxableIncome> getListFromIncomeTaxCalculation(
			IncomeTaxCalculation incomeTaxCalculation) {
		try {
			return nonTaxableIncomeDao.queryForEq("incomeTaxCalculation_id",
					incomeTaxCalculation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
