package com.example.placeitmap.database;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.example.placeitmap.dto.Reminder;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class placeitDB {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_remiderText = "remiderText";
	public static final String KEY_latitude = "latitude";
	public static final String KEY_longitude = "longitude";
	public static final String KEY_radius = "radius";
	public static final String KEY_reminderType = "reminderType";
	public static final String KEY_created = "created";
	public static final String KEY_priority = "priority";
	public static final String KEY_reminded = "reminded";
	private static final String DATABASE_NAME = "AdmonitioDB";
	private static final String DATABASE_TABLE = "mainTable";
	private static final int DATABASE_VERSION = 1;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_remiderText
					+ " TEXT NOT NULL, " + KEY_latitude + " REAL NOT NULL, "
					+ KEY_longitude + " REAL NOT NULL, " + KEY_radius
					+ " REAL NOT NULL, " + KEY_reminderType
					+ " INTEGER NOT NULL, " + KEY_created + " TEXT NOT NULL, "
					+ KEY_priority + " INTEGER NOT NULL, " + KEY_reminded
					+ " INTEGER NOT NULL );");
			System.out.print("Created table");
			Log.d("DB", "Created Table");
			// http://www.sqlite.org/datatype3.html
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public placeitDB(Context c) {
		ourContext = c;
	}

	public placeitDB open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(Reminder reminder) {
		// TODO Auto-generated method stub

		ContentValues cv = setContentFromObj(reminder);

		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	private ContentValues setContentFromObj(Reminder reminder) {

		ContentValues cv = new ContentValues();
		cv.put(KEY_remiderText, reminder.getRemiderText());
		cv.put(KEY_latitude, reminder.getLatitude());
		cv.put(KEY_longitude, reminder.getLongitude());
		cv.put(KEY_radius, reminder.getRadius());
		cv.put(KEY_reminderType, reminder.getReminderType());
		cv.put(KEY_created, dateFormat.format(reminder.getCreated()));
		cv.put(KEY_priority, reminder.getPriority());
		cv.put(KEY_reminded, reminder.getisReminded());

		return cv;
	}

	public ArrayList<Reminder> getData() {
		// TODO Date Conversion
		String[] columns = new String[] { KEY_ROWID, KEY_remiderText,
				KEY_latitude, KEY_longitude, KEY_radius, KEY_reminderType,
				KEY_created, KEY_priority, KEY_reminded};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		ArrayList<Reminder> list = new ArrayList<Reminder>();

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iRemiderText = c.getColumnIndex(KEY_remiderText);
		int iLatitude = c.getColumnIndex(KEY_latitude);
		int iLongitude = c.getColumnIndex(KEY_longitude);
		int iRadius = c.getColumnIndex(KEY_radius);
		int iReminderType = c.getColumnIndex(KEY_reminderType);
		int iCreated = c.getColumnIndex(KEY_created);
		int iPriority = c.getColumnIndex(KEY_priority);
		int ireminded = c.getColumnIndex(KEY_reminded);
		Reminder reminder;
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			reminder = new Reminder();
			reminder.setID(c.getInt(iRow));
			reminder.setRemiderText(c.getString(iRemiderText));
			reminder.setLatitude(c.getDouble(iLatitude));
			reminder.setLongitude(c.getDouble(iLongitude));
			reminder.setRadius(c.getFloat(iRadius));
			reminder.setReminderType(c.getInt(iReminderType));

			try {
				reminder.setCreated(dateFormat.parse(c.getString(iCreated)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			reminder.setPriority(c.getInt(iPriority));
			reminder.setisReminded(c.getInt(ireminded));

			list.add(reminder);
		}
		return list;
	}

	public void updateEntry(Reminder reminder) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cvUpdate = setContentFromObj(reminder);
		ourDatabase.update(DATABASE_TABLE, cvUpdate,
				KEY_ROWID + "=" + reminder.getID(), null);
	}

	public void deleteEntry(long lRow1) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
	}
}
