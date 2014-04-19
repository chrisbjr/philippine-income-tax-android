package com.chrisbjr.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import com.chrisbjr.android.philippineincometax.db.DatabaseHelper;
import com.chrisbjr.android.philippineincometax.db.DatabaseManager;
import com.chrisbjr.android.philippineincometax.objects.Philhealth;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class PhilHealthModel {
	private DatabaseHelper db;
	Dao<Philhealth, Integer> philhealthDao;

	public PhilHealthModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			philhealthDao = db.getPhilhealthDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}

	}

	public int create(Philhealth philhealth) {
		try {
			return philhealthDao.create(philhealth);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Philhealth philhealth) {
		try {
			return philhealthDao.update(philhealth);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Philhealth philhealth) {
		try {
			return philhealthDao.delete(philhealth);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public List<Philhealth> getAll() {
		try {
			return philhealthDao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

	public Philhealth getPhilhealth(double salaryRate, int monthlyFrequency) {
		salaryRate = salaryRate * monthlyFrequency;
		QueryBuilder<Philhealth, Integer> qb = philhealthDao.queryBuilder();
		try {
			PreparedQuery<Philhealth> preparedQuery = qb.where()
					.le("salaryRateFrom", salaryRate).and()
					.ge("salaryRateTo", salaryRate).prepare();
			return philhealthDao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
