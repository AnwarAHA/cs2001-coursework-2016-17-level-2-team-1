package com.example.rishikapadia.connectid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.rishikapadia.connectid.Contract.NewUserInfo.TABLE_NAME;

/**
 * Created by Rishi Kapadia on 18/01/2017.
 */

public class DbHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "UserInfo.db";
    private static final int DATABASE_VERSION = 3;

    private  static final String CREATE_QUERY =
            "CREATE TABLE " + TABLE_NAME
                    + "(" + Contract.NewUserInfo.USER_NAME +" TEXT,"+
                    Contract.NewUserInfo.USER_AGE + " TEXT," +
                    Contract.NewUserInfo.USER_COURSE + " TEXT," +
                    Contract.NewUserInfo.USER_SOCIETIES + " TEXT," +
                    Contract.NewUserInfo.USER_INTERESTS + " TEXT" +
                    ");";



    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS","Database created / opened...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS","Table Created...");

    }

    public void addInformation(String name, String age, String course,String societies,String interests, SQLiteDatabase db){

        db.execSQL("DELETE FROM " + TABLE_NAME +";");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.NewUserInfo.USER_NAME,name);
        contentValues.put(Contract.NewUserInfo.USER_AGE,age);
        contentValues.put(Contract.NewUserInfo.USER_COURSE,course);
        contentValues.put(Contract.NewUserInfo.USER_SOCIETIES,societies);
        contentValues.put(Contract.NewUserInfo.USER_INTERESTS,interests);

        db.insert(TABLE_NAME,null,contentValues);
        Log.e("DATABASE OPERATIONS","One row inserted...");
    }

    public Cursor getInformation(SQLiteDatabase db){

        Cursor cursor;
        String[] projections = {Contract.NewUserInfo.USER_NAME, Contract.NewUserInfo.USER_AGE,
                Contract.NewUserInfo.USER_COURSE, Contract.NewUserInfo.USER_SOCIETIES, Contract.NewUserInfo.USER_INTERESTS};
        cursor=db.query(TABLE_NAME,projections,null,null,null,null,null);
        return cursor;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
