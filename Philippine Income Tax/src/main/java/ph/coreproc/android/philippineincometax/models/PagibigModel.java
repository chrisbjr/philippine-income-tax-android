package ph.coreproc.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import ph.coreproc.android.philippineincometax.db.DatabaseHelper;
import ph.coreproc.android.philippineincometax.db.DatabaseManager;
import ph.coreproc.android.philippineincometax.objects.Pagibig;
import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class PagibigModel {

	private DatabaseHelper db;
	Dao<Pagibig, Integer> pagibigDao;

	public PagibigModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			pagibigDao = db.getPagibigDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int create(Pagibig pagibig) {
		try {
			return pagibigDao.create(pagibig);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(Pagibig pagibig) {
		try {
			return pagibigDao.update(pagibig);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(Pagibig pagibig) {
		try {
			return pagibigDao.delete(pagibig);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Pagibig> getAll() {
		try {
			return pagibigDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Pagibig getPagibig(double salaryRate, int monthlyFrequency) {
		salaryRate = salaryRate * monthlyFrequency;
		QueryBuilder<Pagibig, Integer> qb = pagibigDao.queryBuilder();
		try {
			PreparedQuery<Pagibig> preparedQuery = qb.where()
					.le("salaryRateFrom", salaryRate).and()
					.ge("salaryRateTo", salaryRate).prepare();
			Pagibig pagibig = pagibigDao.queryForFirst(preparedQuery);
			pagibig.setSalaryRate(salaryRate, monthlyFrequency);
			return pagibig;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
