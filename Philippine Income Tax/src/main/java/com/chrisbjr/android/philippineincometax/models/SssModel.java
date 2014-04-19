package com.chrisbjr.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import com.chrisbjr.android.philippineincometax.db.DatabaseHelper;
import com.chrisbjr.android.philippineincometax.db.DatabaseManager;
import com.chrisbjr.android.philippineincometax.objects.Sss;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class SssModel {
	private DatabaseHelper db;
	Dao<Sss, Integer> sssDao;

	public SssModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			sssDao = db.getSssDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}

	}

	public int create(Sss sss) {
		try {
			return sssDao.create(sss);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Sss sss) {
		try {
			return sssDao.update(sss);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Sss sss) {
		try {
			return sssDao.delete(sss);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public List<Sss> getAll() {
		try {
			return sssDao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

	public Sss getSss(double salaryRate, int monthlyFrequency) {
		salaryRate = salaryRate * monthlyFrequency;
		QueryBuilder<Sss, Integer> qb = sssDao.queryBuilder();
		try {
			PreparedQuery<Sss> preparedQuery = qb.where()
					.le("salaryRateFrom", salaryRate).and()
					.ge("salaryRateTo", salaryRate).prepare();
			return sssDao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
