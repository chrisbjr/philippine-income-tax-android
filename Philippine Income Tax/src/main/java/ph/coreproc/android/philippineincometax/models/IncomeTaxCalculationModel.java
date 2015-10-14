package ph.coreproc.android.philippineincometax.models;

import java.sql.SQLException;
import java.util.List;

import ph.coreproc.android.philippineincometax.db.DatabaseHelper;
import ph.coreproc.android.philippineincometax.db.DatabaseManager;
import ph.coreproc.android.philippineincometax.objects.IncomeTaxCalculation;
import android.content.Context;

import com.j256.ormlite.dao.Dao;

public class IncomeTaxCalculationModel {
	private DatabaseHelper db;
	Dao<IncomeTaxCalculation, Integer> incomeTaxCalculationDao;

	public IncomeTaxCalculationModel(Context ctx) {
		try {
			DatabaseManager dbManager = new DatabaseManager();
			db = dbManager.getHelper(ctx);
			incomeTaxCalculationDao = db.getIncomeTaxCalculationDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int create(IncomeTaxCalculation incomeTaxCalculation) {
		try {
			return incomeTaxCalculationDao.create(incomeTaxCalculation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int update(IncomeTaxCalculation incomeTaxCalculation) {
		try {
			return incomeTaxCalculationDao.update(incomeTaxCalculation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(IncomeTaxCalculation incomeTaxCalculation) {
		try {
			return incomeTaxCalculationDao.delete(incomeTaxCalculation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<IncomeTaxCalculation> getAll() {
		try {
			return incomeTaxCalculationDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
