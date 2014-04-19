package com.chrisbjr.android.philippineincometax.models;

import android.content.Context;

import com.chrisbjr.android.philippineincometax.db.DatabaseHelper;
import com.chrisbjr.android.philippineincometax.db.DatabaseManager;
import com.chrisbjr.android.philippineincometax.objects.IncomeTaxCalculation;
import com.chrisbjr.android.philippineincometax.objects.TaxableIncome;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class TaxableIncomeModel {
	private DatabaseHelper db;
	Dao<TaxableIncome, Integer> taxableIncomeDao;

	public TaxableIncomeModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			taxableIncomeDao = db.getTaxableIncomeDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int create(TaxableIncome taxableIncome) {
		try {
			return taxableIncomeDao.create(taxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(TaxableIncome taxableIncome) {
		try {
			return taxableIncomeDao.update(taxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(TaxableIncome taxableIncome) {
		try {
			return taxableIncomeDao.delete(taxableIncome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<TaxableIncome> getAll() {
		try {
			return taxableIncomeDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<TaxableIncome> getListFromIncomeTaxCalculation(
			IncomeTaxCalculation incomeTaxCalculation) {
		try {
			return taxableIncomeDao.queryForEq("incomeTaxCalculation_id",
					incomeTaxCalculation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
