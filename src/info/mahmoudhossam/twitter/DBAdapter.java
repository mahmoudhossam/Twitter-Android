package info.mahmoudhossam.twitter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String DB_NAME = "tweets.db";
	private static final String CREATE_TIMELINE_TABLE = "CREATE TABLE timeline(tweet TEXT,"
			+ "contents TEXT);";
	private static final String CREATE_MENTIONS_TABLE = "CREATE TABLE mentions(tweet TEXT,"
			+ "contents TEXT);";

	public DBAdapter(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TIMELINE_TABLE);
		db.execSQL(CREATE_MENTIONS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("db", "Upgrading DB from version " + oldVersion + " to version "
				+ newVersion);
	}

}
