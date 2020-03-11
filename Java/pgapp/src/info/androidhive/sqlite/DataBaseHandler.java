package info.androidhive.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "gpapp_database";
	
	private static final int DATABASE_VERSION = 1;
	// Contacts table name
	private static final String TABLE_USER = "gpapp_user";
	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USER = "username";
	private static final String KEY_USER_PHONE = "userphone";
	private static final String KEY_IMAGE_URL = "imageUrl";
	private static final String KEY_DESC01 = "description01";
	private static final String KEY_DESC02 = "description02";
	private static final String KEY_DESC03 = "description03";
	
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_GPAPP_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER + " TEXT,"
				+ KEY_USER_PHONE + " TEXT, " + KEY_IMAGE_URL +" TEXT, "+
				KEY_DESC01 +" TEXT, "+ KEY_DESC02 +" TEXT, "+ KEY_DESC03 +
				" TEXT)";
		db.execSQL(CREATE_GPAPP_TABLE);
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public DBUsers getContact(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		/* http://android-pro.blogspot.com.br/2010/10/using-sqlite-database-with-android.html
	     * 	db.query has the folowing parameters:
			1.String Table Name: the name of the table to run the query against.
			2.String [ ] columns: the projection of the query i.e the columns to retrieve.
			3.String WHERE clause: where clause, if none pass null.
			4.String [ ] selection args: the parameters of the WHERE clause.
			5.String Group by: a string specifying group by clause.
			6.String Having: a string specifying HAVING clause.
			7.String Order By by: a string Order By by clause.

	     * */
	    Cursor cursor = db.query(TABLE_USER,new String[] { KEY_ID,KEY_USER, KEY_USER_PHONE, KEY_DESC01, KEY_DESC02, KEY_DESC03 },
	    						KEY_USER_PHONE + "=?",new String[] { String.valueOf(id) },null, null,null);
	    if (cursor != null)	cursor.moveToFirst();
	    DBUsers dbusers = new DBUsers(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
	    db.close();
	    return dbusers;
	}

	public void addUserFromWebService(DBUsers dbusers){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER, dbusers.getName()); // WebService UserName
		values.put(KEY_USER_PHONE, dbusers.getUserPhone()); // WebService User Phone
		values.put(KEY_IMAGE_URL, dbusers.getImageUrl()); // WebService User Image Url

		// Inserting Row
		db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection
	}
	
	public ArrayList<String> getAllUsername(){
		ArrayList<String> usernameList = new ArrayList<String>();
		// Select All Query
		String selectQuery = "SELECT  " + KEY_USER + " FROM " + TABLE_USER;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DBUsers dbusers = new DBUsers();
				//dbusers.setID(Integer.parseInt(cursor.getString(0)));
				dbusers.setName(cursor.getString(cursor.getColumnIndex(KEY_USER)));
				//dbusers.setName(cursor.getColumnIndex(KEY_NAME));
				//dbusers.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				//usernameList.add(dbusers);
				usernameList.add(cursor.getString(cursor.getColumnIndex(KEY_USER)));
				
			} while (cursor.moveToNext());
		}
		// http://stackoverflow.com/questions/21201415/how-can-i-convert-arraylistobject-to-arrayliststring-or-arraylisttimestamp
		/*
		ArrayList<String> usernameListAdapter = new ArrayList<String>();
		for (DBUsers object : usernameList) {
	        usernameListAdapter.add(object != null ? object.toString() : null); // if object != null {.toString();} else {null;} another way to write if else statement, String.valueOf()
	    }
	    */
		return usernameList;
	}
	
	public ArrayList<String> getAllUserPhone(){
		//ArrayList<DBUsers> userphoneList = new ArrayList<DBUsers>();
		ArrayList<String> userphoneList = new ArrayList<String>();
		// Select All Query
				String selectQuery = "SELECT  " + KEY_USER_PHONE + " FROM " + TABLE_USER;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DBUsers dbusers = new DBUsers();
						//dbusers.setID(Integer.parseInt(cursor.getString(0)));
						//dbusers.setName(cursor.getString(1));
						dbusers.setUserPhone(cursor.getString(cursor.getColumnIndex(KEY_USER_PHONE)));
						// Adding contact to list
						//userphoneList.add(dbusers);
						userphoneList.add(cursor.getString(cursor.getColumnIndex(KEY_USER_PHONE)));
					} while (cursor.moveToNext());
				}
				/*
				ArrayList<String> userphoneListAdapter = new ArrayList<String>();
				for (DBUsers object : userphoneList) {
			        userphoneListAdapter.add(object.toString());
			    }
			    */
		return userphoneList;
	}
	public ArrayList<String> getAllUserImageUrl(){
		//ArrayList<DBUsers> userImageUrlList = new ArrayList<DBUsers>();
		ArrayList<String> userImageUrlList = new ArrayList<String>();
		// Select All Query
				String selectQuery = "SELECT  " + KEY_IMAGE_URL + " FROM " + TABLE_USER;
				SQLiteDatabase db = this.getWritableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				// looping through all rows and adding to list
				if (cursor.moveToFirst()) {
					do {
						DBUsers dbusers = new DBUsers();
						//dbusers.setID(Integer.parseInt(cursor.getString(0)));
						//dbusers.setName(cursor.getString(1));
						//dbusers.setPhoneNumber(cursor.getString(2));
						dbusers.setImageUrl(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)));
						// Adding contact to list
						//userImageUrlList.add(dbusers);
						userImageUrlList.add(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)));
					} while (cursor.moveToNext());
				}
				/*
				ArrayList<String> userImageUrlListAdapter = new ArrayList<String>();
				for (DBUsers object : userImageUrlList) {
			        userImageUrlListAdapter.add(object.toString());
			    }
			    */
		//return userImageUrlListAdapter;
				return userImageUrlList;
	}

	public int getContactsCount() {
        String countQuery = "SELECT  "+KEY_USER_PHONE+" FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int valueCounted = cursor.getCount(); 
        cursor.close();
        // return count
        return valueCounted;
    }
	
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER,null,null);
		//db.execSQL("delete from "+ TABLE_USER);
		db.close();
	}
}