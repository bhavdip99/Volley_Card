package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {

	public static final String KEY_ITEM_PRIMARY = "primary_id";
	public static final String KEY_ITEM_NAME = "name";
	public static final String KEY_ITEM_PRICE = "price";
	public static final String KEY_ITEM_IMAGE = "image";
	public static final String KEY_ITEM_DETAIL = "detail";

	public static final String[] ALL_KEYS = new String[] { KEY_ITEM_NAME,
			KEY_ITEM_PRICE, KEY_ITEM_IMAGE, KEY_ITEM_DETAIL };

	private static final String DATABASE_NAME = "kemchho.db";
	private static final String DATABASE_TABLE_KEMCHHOALLUSER = "kemchoalluser";

	private static final int DATABASE_VERSION = 2;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourdatabase;

	// ////////////////////////////////////

	public void deleteRowname(String string) {
		String where = KEY_ITEM_NAME + "='" + string + "'";

		ourdatabase.delete(DATABASE_TABLE_KEMCHHOALLUSER, where, null);
	}

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String sql_kemchho_alluser = "CREATE TABLE "
					+ DATABASE_TABLE_KEMCHHOALLUSER + " (" + KEY_ITEM_PRIMARY
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ITEM_NAME
					+ " TEXT NOT NULL, " + KEY_ITEM_PRICE + " TEXT NOT NULL, "
					+ KEY_ITEM_IMAGE + " TEXT NOT NULL, " + KEY_ITEM_DETAIL
					+ " TEXT NOT NULL);";

			db.execSQL(sql_kemchho_alluser);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			String sqlupdate = "DROP TABLE IF EXISTS "
					+ DATABASE_TABLE_KEMCHHOALLUSER;
			db.execSQL(sqlupdate);
			onCreate(db);

		}
	}

	public DBHelper(Context c) {
		ourContext = c;
	}

	public DBHelper open() {
		ourHelper = new DbHelper(ourContext);
		ourdatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public void addtocart(String name, String price, String image, String detail) {
		ContentValues cv = new ContentValues();

		cv.put(KEY_ITEM_NAME, name);
		cv.put(KEY_ITEM_PRICE, price);
		cv.put(KEY_ITEM_IMAGE, image);
		cv.put(KEY_ITEM_DETAIL, detail);
		ourdatabase.insert(DATABASE_TABLE_KEMCHHOALLUSER, null, cv);
	}

	public ArrayList<HashMap<String, String>> get_all_Data_cart() {

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
		Cursor c = ourdatabase.query(DATABASE_TABLE_KEMCHHOALLUSER, ALL_KEYS,
				null, null, null, null, null);

		// /////////////////////////////////////
		int KEY_ITEM_NAME_int = c.getColumnIndex(KEY_ITEM_NAME);
		int KEY_ITEM_PRICE_int = c.getColumnIndex(KEY_ITEM_PRICE);
		int KEY_ITEM_IMAGE_int = c.getColumnIndex(KEY_ITEM_IMAGE);
		int KEY_ITEM_DETAIL_int = c.getColumnIndex(KEY_ITEM_DETAIL);

		// ////////////////////////////////////////
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_ITEM_NAME, c.getString(KEY_ITEM_NAME_int));
				map.put(KEY_ITEM_PRICE, c.getString(KEY_ITEM_PRICE_int));
				map.put(KEY_ITEM_IMAGE, c.getString(KEY_ITEM_IMAGE_int));
				map.put(KEY_ITEM_DETAIL, c.getString(KEY_ITEM_DETAIL_int));
				contactList.add(map);
			} while (c.moveToNext());
		}
		return contactList;

	}
}
