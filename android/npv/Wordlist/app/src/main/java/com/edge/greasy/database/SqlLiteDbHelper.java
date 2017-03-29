package com.edge.greasy.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.edge.greasy.dbo.Word;
import com.edge.greasy.utility.Constants;

public class SqlLiteDbHelper extends SQLiteOpenHelper {

	private static String TAG = "grewordlist";

	static Context mContext;
	private static SqlLiteDbHelper instance;
	private SQLiteDatabase db;

	public static synchronized SqlLiteDbHelper getHelper(Context context) {
		if (instance == null)
			instance = new SqlLiteDbHelper(context);
		return instance;
	}

	public SqlLiteDbHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
		mContext = context;
		initialize();
	}

	private void initialize() {
		if (databaseExists()) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
			int dbVersion = prefs.getInt(Constants.SP_KEY_DB_VER, 1);
			if (Constants.DATABASE_VERSION != dbVersion) {
				File dbFile = mContext.getDatabasePath(Constants.DATABASE_NAME);
				if (!dbFile.delete()) {
					Log.w(TAG, "Unable to update database");
				}
			}
		}
		if (!databaseExists()) {
			createDatabase();
		}

	}

	private boolean databaseExists() {
		File dbFile = mContext.getDatabasePath(Constants.DATABASE_NAME);
		return dbFile.exists();
	}

	private void createDatabase() {
		String parentPath = mContext.getDatabasePath(Constants.DATABASE_NAME).getParent();
		String path = mContext.getDatabasePath(Constants.DATABASE_NAME).getPath();

		File file = new File(parentPath);
		if (!file.exists()) {
			if (!file.mkdir()) {
				Log.w(TAG, "Unable to create database directory");
				return;
			}
		}

		InputStream is = null;
		OutputStream os = null;
		try {
			is = mContext.getAssets().open(Constants.DATABASE_NAME);
			os = new FileOutputStream(path);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(mContext);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(Constants.SP_KEY_DB_VER, Constants.DATABASE_VERSION);
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void copyDBfromAssets() throws IOException {
		InputStream inputStream = mContext.getAssets().open(
				Constants.DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = getDatabasePath();

		// if the path doesn't exist first, create it
		File file = new File(mContext.getApplicationInfo().dataDir
				+ Constants.DB_PATH_SUFFIX);
		if (!file.exists()) {
			file.mkdir();
		}

		// Open the empty db as the output stream
		OutputStream outputStream = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}

		// Close the streams
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}

	private String getDatabasePath() {
		// TODO Auto-generated method stub
		return mContext.getApplicationInfo().dataDir + Constants.DB_PATH_SUFFIX
				+ Constants.DATABASE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public SQLiteDatabase openDataBase() {

		File dbfile = mContext.getDatabasePath(Constants.DATABASE_NAME);
		if (!dbfile.exists()) {
			try {
				copyDBfromAssets();
				Log.i(TAG, "Copying success from assest folder");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Error creating source database", e);
			}
		}

		return SQLiteDatabase.openDatabase(dbfile.getPath(), null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS
				| SQLiteDatabase.CREATE_IF_NECESSARY);

	}

	// Getting single contact
	public Word Get_WordDetails() {
		db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM "
				+ Constants.DATABASE_TABLE_WORD, null);
		if (cursor != null && cursor.moveToFirst()) {
			Word word = new Word(cursor.getInt(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
			// return word
			cursor.close();
			db.close();
			return word;
		}
		return null;
	}

}
