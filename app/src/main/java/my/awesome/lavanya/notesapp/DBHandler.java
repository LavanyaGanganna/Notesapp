package my.awesome.lavanya.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavanya on 3/6/17.
 */

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 10678;
    // Database Name
    private static final String DATABASE_NAME = "noteslists.db";
    // Contacts table name
    private static final String TABLE_NAME = "notestables";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "notedate";
    private static final String TAG = DBHandler.class.getSimpleName();

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_BODY + " TEXT," + KEY_DATE + " TEXT" + " );";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNotes(String title, String body, String datessent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_BODY, body);
        values.put(KEY_DATE, datessent);
// Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<notesobject> getAllnotes() {
        SQLiteDatabase db = this.getReadableDatabase();
       notesobject notesobj;
        ArrayList<notesobject> arrayList=new ArrayList<>();
        //  Cursor cursors= db.rawQuery(" SELECT * FROM celebrity LIMIT " + id +","+ " 25",null);
        Cursor cursors= db.rawQuery(" SELECT * FROM notestables " ,null);
        //Cursor cursor = db.rawQuery(" SELECT * FROM celebrity WHERE  " + KEY_ID + "=?;", new String[]{Integer.toString(id)});
        if (cursors != null) {
            cursors.moveToFirst();
            // int i=id;
            while(!cursors.isAfterLast()){
                notesobj = new notesobject(cursors.getString(1), cursors.getString(2),cursors.getString(3));
                arrayList.add(notesobj);
                cursors.moveToNext();
            }

        }
        if(cursors!=null)
            cursors.close();
        //return contact;
        return  arrayList;
    }

    public notesobject getnoteonly(String listtext){
        SQLiteDatabase db = this.getReadableDatabase();
        notesobject noteonlyobj=null;
        Log.d(TAG,"the received text" + listtext);
        Cursor cursor=db.rawQuery("SELECT * FROM notestables WHERE " + KEY_TITLE + "=?", new String[]{listtext});
        if(cursor != null){
            cursor.moveToFirst();
            noteonlyobj=new notesobject(cursor.getString(1),cursor.getString(2),cursor.getString(3));

        }
        return  noteonlyobj;
    }
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void delete(String titless){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE FROM notestable WHERE " + KEY_ID + "=?;", new String[]{Integer.toString(pos)});
       // db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+KEY_TITLE+"='"+titless+"'");
        db.execSQL("DELETE FROM " + TABLE_NAME +" WHERE "+ KEY_TITLE + "=?",new String[]{titless});
        db.close();
    }

    public ArrayList<String> searchdata(String keystring){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<String> arraylist=new ArrayList<>();
      //  String selectdataquery=" SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " =? ";
        String selectdataquery="Select * from notestables where title like " + "'%" + keystring + "%' OR " +  KEY_BODY + " LIKE " + "'%" + keystring + "%'";
        Cursor cursor=db.rawQuery(selectdataquery,null);
        if(cursor !=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String retdata= cursor.getString(1);
                Log.d(TAG,"the retddata" + retdata);
                arraylist.add(retdata);
                cursor.moveToNext();
            }
        }
        if(cursor!=null)
            cursor.close();
        //return contact;
        return  arraylist;
    }
}