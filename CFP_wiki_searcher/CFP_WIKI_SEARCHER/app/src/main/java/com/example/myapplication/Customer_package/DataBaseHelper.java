package com.example.myapplication.Customer_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_EVENT_NAME = "EVENT_NAME";
    public static final String COLUMN_EVENT_WHEN = "EVENT_WHEN";
    public static final String COLUMN_EVENT_WHERE = "EVENT_WHERE";
    public static final String COLUMN_EVENT_DEADLINE = "EVENT_DEADLINE";
    public static final String COLUMN_EVENT_DESCRIPTION = "EVENT_DESCRIPTION";
    public static final String COLUMN_EVENT_CONTENT = "EVENT_CONTENT";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    //this is calledd the first time the db is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_EVENT_WHEN + " TEXT, " +
                COLUMN_EVENT_WHERE + " TEXT, " +
                COLUMN_EVENT_DEADLINE + "TEXT)";
        db.execSQL(createTableStatement);
    }
    //this is called if the db version number changes. It prevents previous users app from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENT_NAME, customerModel.getName());
        cv.put(COLUMN_EVENT_WHEN, customerModel.getAge());
        cv.put(COLUMN_EVENT_WHERE, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + customerModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){       //find the object
            return true;
        }
        else{
            return false;
        }
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();
        //get dat from database
        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);   //cursor: result set
        if(cursor.moveToFirst()){
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true:false;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);
            }while(cursor.moveToNext());
        }
        else{
            //failed. don't add anything
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
