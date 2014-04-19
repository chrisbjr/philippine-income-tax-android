package com.chrisbjr.android.philippineincometax.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chrisbjr.android.philippineincometax.R;
import com.chrisbjr.android.philippineincometax.objects.IncomeTaxCalculation;
import com.chrisbjr.android.philippineincometax.objects.NonTaxableIncome;
import com.chrisbjr.android.philippineincometax.objects.Pagibig;
import com.chrisbjr.android.philippineincometax.objects.Philhealth;
import com.chrisbjr.android.philippineincometax.objects.Sss;
import com.chrisbjr.android.philippineincometax.objects.TaxableIncome;
import com.chrisbjr.android.philippineincometax.objects.WithholdingTax;
import com.chrisbjr.android.philippineincometax.objects.WithholdingTaxType;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "pitc.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private Dao<IncomeTaxCalculation, Integer> incomeTaxCalculationDao = null;
	private RuntimeExceptionDao<IncomeTaxCalculation, Integer> incomeTaxCalculationRuntimeDao = null;

	private Dao<TaxableIncome, Integer> taxableIncomeDao = null;
	private RuntimeExceptionDao<TaxableIncome, Integer> taxableIncomeRuntimeDao = null;

	private Dao<NonTaxableIncome, Integer> nonTaxableIncomeDao = null;
	private RuntimeExceptionDao<NonTaxableIncome, Integer> nonTaxableIncomeRuntimeDao = null;

	private Dao<Pagibig, Integer> pagibigDao = null;
	private RuntimeExceptionDao<Pagibig, Integer> pagibigRuntimeDao = null;

	private Dao<Philhealth, Integer> philhealthDao = null;
	private RuntimeExceptionDao<Philhealth, Integer> philhealthRuntimeDao = null;

	private Dao<Sss, Integer> sssDao = null;
	private RuntimeExceptionDao<Sss, Integer> sssRuntimeDao = null;

	private Dao<WithholdingTax, Integer> withholdingTaxDao = null;
	private RuntimeExceptionDao<WithholdingTax, Integer> withholdingTaxRuntimeDao = null;

	private Dao<WithholdingTaxType, Integer> withholdingTaxTypeDao = null;
	private RuntimeExceptionDao<WithholdingTaxType, Integer> withholdingTaxTypeRuntimeDao = null;

	private Context mContext;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils
					.createTable(connectionSource, IncomeTaxCalculation.class);
			TableUtils.createTable(connectionSource, TaxableIncome.class);
			TableUtils.createTable(connectionSource, NonTaxableIncome.class);
			TableUtils.createTable(connectionSource, Pagibig.class);
			TableUtils.createTable(connectionSource, Philhealth.class);
			TableUtils.createTable(connectionSource, Sss.class);
			TableUtils.createTable(connectionSource, WithholdingTax.class);
			TableUtils.createTable(connectionSource, WithholdingTaxType.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
		RuntimeExceptionDao<Pagibig, Integer> piDao = getPagibigDataDao();
		RuntimeExceptionDao<Philhealth, Integer> phDao = getPhilhealthDataDao();
		RuntimeExceptionDao<Sss, Integer> sssCreateDao = getSssDataDao();
		RuntimeExceptionDao<WithholdingTax, Integer> wtDao = getWithholdingTaxDataDao();
		RuntimeExceptionDao<WithholdingTaxType, Integer> wtTypeDao = getWithholdingTaxTypeDataDao();

		try {
			ArrayList<String[]> pagibigStringList = readRawCsv(R.raw.pagibig);
			for (String[] s : pagibigStringList) {
				Pagibig pagibig = new Pagibig(s);
				piDao.create(pagibig);
			}
			ArrayList<String[]> philhealthStringList = readRawCsv(R.raw.philhealth);
			for (String[] s : philhealthStringList) {
				Philhealth philhealth = new Philhealth(s);
				phDao.create(philhealth);
			}
			ArrayList<String[]> sssStringList = readRawCsv(R.raw.sss);
			for (String[] s : sssStringList) {
				Sss sss = new Sss(s);
				sssCreateDao.create(sss);
			}
			ArrayList<String[]> withholdingTaxStringList = readRawCsv(R.raw.witholding_tax);
			for (String[] s : withholdingTaxStringList) {
				WithholdingTax withholdingTax = new WithholdingTax(s);
				wtDao.create(withholdingTax);
			}
			ArrayList<String[]> withholdingTaxTypeStringList = readRawCsv(R.raw.witholding_tax);
			for (String[] s : withholdingTaxTypeStringList) {
				WithholdingTaxType w = new WithholdingTaxType(s);
				wtTypeDao.create(w);
			}
		} catch (IOException e) {
			// TODO we need to close off the whole app
			e.printStackTrace();
		}

		Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate");
	}

	public ArrayList<String[]> readRawCsv(int resId) throws IOException {
		ArrayList<String[]> result = new ArrayList<String[]>();
		String line = "";
		String cvsSplitBy = ",";
		InputStream inputStream = mContext.getResources()
				.openRawResource(resId);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		while ((line = buffreader.readLine()) != null) {

			// use comma as separator
			String[] splitString = line.split(cvsSplitBy);
			result.add(splitString);

		}

		return result;
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, WithholdingTax.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It
	 * will create it or just give the cached value.
	 */
	public Dao<Pagibig, Integer> getPagibigDao() throws SQLException {
		if (pagibigDao == null) {
			pagibigDao = getDao(Pagibig.class);
		}
		return pagibigDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our SimpleData class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Pagibig, Integer> getPagibigDataDao() {
		if (pagibigRuntimeDao == null) {
			pagibigRuntimeDao = getRuntimeExceptionDao(Pagibig.class);
		}
		return pagibigRuntimeDao;
	}

	public Dao<Philhealth, Integer> getPhilhealthDao() throws SQLException {
		if (philhealthDao == null) {
			philhealthDao = getDao(Philhealth.class);
		}
		return philhealthDao;
	}

	public RuntimeExceptionDao<Philhealth, Integer> getPhilhealthDataDao() {
		if (philhealthRuntimeDao == null) {
			philhealthRuntimeDao = getRuntimeExceptionDao(Philhealth.class);
		}
		return philhealthRuntimeDao;
	}

	public Dao<Sss, Integer> getSssDao() throws SQLException {
		if (sssDao == null) {
			sssDao = getDao(Sss.class);
		}
		return sssDao;
	}

	public RuntimeExceptionDao<Sss, Integer> getSssDataDao() {
		if (sssRuntimeDao == null) {
			sssRuntimeDao = getRuntimeExceptionDao(Sss.class);
		}
		return sssRuntimeDao;
	}

	public Dao<WithholdingTax, Integer> getWithholdingTaxDao()
			throws SQLException {
		if (withholdingTaxDao == null) {
			withholdingTaxDao = getDao(WithholdingTax.class);
		}
		return withholdingTaxDao;
	}

	public RuntimeExceptionDao<WithholdingTax, Integer> getWithholdingTaxDataDao() {
		if (withholdingTaxRuntimeDao == null) {
			withholdingTaxRuntimeDao = getRuntimeExceptionDao(WithholdingTax.class);
		}
		return withholdingTaxRuntimeDao;
	}

	public Dao<WithholdingTaxType, Integer> getWithholdingTaxTypeDao()
			throws SQLException {
		if (withholdingTaxTypeDao == null) {
			withholdingTaxTypeDao = getDao(WithholdingTaxType.class);
		}
		return withholdingTaxTypeDao;
	}

	public RuntimeExceptionDao<WithholdingTaxType, Integer> getWithholdingTaxTypeDataDao() {
		if (withholdingTaxTypeRuntimeDao == null) {
			withholdingTaxTypeRuntimeDao = getRuntimeExceptionDao(WithholdingTaxType.class);
		}
		return withholdingTaxTypeRuntimeDao;
	}

	public Dao<IncomeTaxCalculation, Integer> getIncomeTaxCalculationDao()
			throws SQLException {
		if (incomeTaxCalculationDao == null) {
			incomeTaxCalculationDao = getDao(IncomeTaxCalculation.class);
		}
		return incomeTaxCalculationDao;
	}

	public RuntimeExceptionDao<IncomeTaxCalculation, Integer> getIncomeTaxCalculationDataDao() {
		if (incomeTaxCalculationRuntimeDao == null) {
			incomeTaxCalculationRuntimeDao = getRuntimeExceptionDao(IncomeTaxCalculation.class);
		}
		return incomeTaxCalculationRuntimeDao;
	}

	public Dao<TaxableIncome, Integer> getTaxableIncomeDao()
			throws SQLException {
		if (taxableIncomeDao == null) {
			taxableIncomeDao = getDao(TaxableIncome.class);
		}
		return taxableIncomeDao;
	}

	public RuntimeExceptionDao<TaxableIncome, Integer> getTaxableIncomeDataDao() {
		if (taxableIncomeRuntimeDao == null) {
			taxableIncomeRuntimeDao = getRuntimeExceptionDao(TaxableIncome.class);
		}
		return taxableIncomeRuntimeDao;
	}

	public Dao<NonTaxableIncome, Integer> getNonTaxableIncomeDao()
			throws SQLException {
		if (nonTaxableIncomeDao == null) {
			nonTaxableIncomeDao = getDao(NonTaxableIncome.class);
		}
		return nonTaxableIncomeDao;
	}

	public RuntimeExceptionDao<NonTaxableIncome, Integer> getNonTaxableIncomeDataDao() {
		if (nonTaxableIncomeRuntimeDao == null) {
			nonTaxableIncomeRuntimeDao = getRuntimeExceptionDao(NonTaxableIncome.class);
		}
		return nonTaxableIncomeRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		withholdingTaxDao = null;
	}

}