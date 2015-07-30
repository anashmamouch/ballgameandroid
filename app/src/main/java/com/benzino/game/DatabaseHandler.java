package com.benzino.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anas on 30/7/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    //Database version
    private static final int DATABASE_VERSION = 2;

    //Database name
    private static final String DATABASE_NAME = "game";

    //Test table name
    private static final String TABLE_TESTS = "tests";

    //Column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_BALL_TOUCHED = "ball_touched";
    private static final String KEY_TOTAL_TOUCHES = "total_touches";
    //private static final String KEY_FIRST_TIME = "first_time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TESTS_TABLE = "CREATE TABLE " + TABLE_TESTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BALL_TOUCHED + " INTEGER, "
                + KEY_TOTAL_TOUCHES + " INTEGER, "
                + KEY_USERNAME + " TEXT, "
                + KEY_CREATED_AT + " DATETIME" +")";

        db.execSQL(CREATE_TESTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);

        // Create tables again
        onCreate(db);
    }

    //Create a test according to a user
    public void createTest(Test test){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        //Test ball touched
        values.put(KEY_BALL_TOUCHED, test.getBallTouched());
        //Test total touches
        values.put(KEY_TOTAL_TOUCHES, test.getTotalTouches());
        //Test username
        values.put(KEY_USERNAME, test.getUsername());
        //Test created at
        values.put(KEY_CREATED_AT, getDateTime());

        //Inserting row in the test table
        db.insert(TABLE_TESTS, null, values);

        //Close database connection
        db.close();
    }


    //Find all tests
    public List<Test> findAllTests(){
        SQLiteDatabase db = this.getWritableDatabase();

        List<Test> tests = new ArrayList<Test>();

        String query = "SELECT * FROM "+TABLE_TESTS;
        Cursor cursor = db.rawQuery(query, null);

        //Looping through the table and add all the tests
        if(cursor.moveToFirst()){
            do{
                Test test = new Test();
                test.setId(Integer.parseInt(cursor.getString(0)));
                test.setBallTouched(Integer.parseInt(cursor.getString(1)));
                test.setTotalTouches(Integer.parseInt(cursor.getString(2)));
                test.setUsername(cursor.getString(3));
                test.setCreatedAt(cursor.getString(4));

                //Adding test to the list
                tests.add(test);
            }while(cursor.moveToNext());
        }

        return tests;
    }


    //Getting users count
    public int getTestsCount(){
        String query = "SELECT * FROM "+TABLE_TESTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //Created_at value current datetime
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
