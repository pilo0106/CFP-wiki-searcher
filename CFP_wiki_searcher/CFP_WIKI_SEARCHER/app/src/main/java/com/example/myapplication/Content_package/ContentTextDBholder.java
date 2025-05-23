package com.example.myapplication.Content_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.Content_package.ContentText;
import com.example.myapplication.KeyWord_package.KeyWordSet;

import java.util.ArrayList;
import java.util.List;

public class ContentTextDBholder extends SQLiteOpenHelper {
    public static final String CONTENTTEXT_TABLE = "CONTENTTEXT_TABLE";
    public static final String COLUMN_CONTENTTEXT_ID = "ID";
    public static final String COLUMN_CONTENTTEXT_NAME = "CONTENTTEXT_NAME";
    public static final String COLUMN_CONTENTTEXT_NAMELIST = "CONTENTTEXT_NAMELIST";
    public static final String COLUMN_CONTENTTEXT_URL = "CONTENTTEXT_URL";
    public static final String COLUMN_CONTENTTEXT_NOTIDUE = "CONTENTTEXT_NOTIDUE";
    public static final String COLUMN_CONTENTTEXT_FVDUE = "CONTENTTEXT_FVDUE";
    public static final String COLUMN_CONTENTTEXT_CONTENT = "CONTENTTEXT_CONTENT";

    public ContentTextDBholder(@Nullable Context context) {
        super(context, "content.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CONTENTTEXT_TABLE + " (" +
                COLUMN_CONTENTTEXT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTENTTEXT_NAME + " TEXT, " +
                COLUMN_CONTENTTEXT_NAMELIST + " TEXT, " +
                COLUMN_CONTENTTEXT_URL + " TEXT, " +
                COLUMN_CONTENTTEXT_NOTIDUE + " INT, " +
                COLUMN_CONTENTTEXT_FVDUE + " INT, " +
                COLUMN_CONTENTTEXT_CONTENT + " TEXT)";
        db.execSQL(createTableStatement);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(ContentText contentText){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTENTTEXT_NAME, contentText.getName());
        cv.put(COLUMN_CONTENTTEXT_NAMELIST, contentText.getNameList());
        cv.put(COLUMN_CONTENTTEXT_URL, contentText.geturl());
        cv.put(COLUMN_CONTENTTEXT_NOTIDUE, contentText.getNotiDue());
        cv.put(COLUMN_CONTENTTEXT_FVDUE, contentText.getFvDue());
        cv.put(COLUMN_CONTENTTEXT_CONTENT, contentText.getContent());

        long insert = db.insert(CONTENTTEXT_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteOne(ContentText contentText){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CONTENTTEXT_TABLE + " WHERE " + COLUMN_CONTENTTEXT_ID + " = " + contentText.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){       //find the object
            return true;
        }
        else{
            return false;
        }
    }
    public List<ContentText> getEveryone(){
        List<ContentText> returnList = new ArrayList<>();
        //get dat from database
        String queryString = "SELECT * FROM " + CONTENTTEXT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);   //cursor: result set
        if(cursor.moveToFirst()){
            do{
                int contentTextId = cursor.getInt(0);
                String contentTextName = cursor.getString(1);
                String contentTextNameList = cursor.getString(2);
                String contentTexturl = cursor.getString(3);
                int contentTextnotiDue = cursor.getInt(4);
                int contentTexfvDue = cursor.getInt(5);
                String contentTextcontent = cursor.getString(6);

                ContentText newContent = new ContentText(contentTextId, contentTextName, contentTextNameList, contentTexturl, contentTextnotiDue, contentTexfvDue, contentTextcontent);
                returnList.add(newContent);
            }while(cursor.moveToNext());
        }
        else{
            //failed. don't add anything
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public ContentText getContent(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentText contentText = null;
        String query = "SELECT * FROM " + CONTENTTEXT_TABLE + " WHERE " + COLUMN_CONTENTTEXT_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor.moveToFirst()) {
            int contentTextId = cursor.getInt(0);
            String contentTextName = cursor.getString(1);
            String contentTextNameList = cursor.getString(2);
            String contentTexturl = cursor.getString(3);
            int contentTextnotiDue = cursor.getInt(4);
            int contentTexfvDue = cursor.getInt(5);
            String contentTextcontent = cursor.getString(6);

            contentText = new ContentText(contentTextId, contentTextName, contentTextNameList, contentTexturl, contentTextnotiDue, contentTexfvDue, contentTextcontent);
        }

        cursor.close();
        db.close();

        return contentText;
    }
}
