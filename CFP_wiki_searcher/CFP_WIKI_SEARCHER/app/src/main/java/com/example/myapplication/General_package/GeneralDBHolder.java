package com.example.myapplication.General_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.General_package.General;
import com.example.myapplication.KeyWord_package.KeyWordSet;
import com.example.myapplication.KeyWord_package.KeyWordSetDBholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GeneralDBHolder extends SQLiteOpenHelper {
    public static final String GENERAL_TABLE = "GENERAL_TABLE";
    public static final String COLUMN_GENERAL_ID = "ID";
    public static final String COLUMN_GENERAL_NAME = "GENERAL_NAME";
    public static final String COLUMN_GENERAL_START = "GENERAL_START";
    public static final String COLUMN_GENERAL_END = "GENERAL_END";
    public static final String COLUMN_GENERAL_WHERE = "GENERAL_WHERE";
    public static final String COLUMN_GENERAL_DEADLINE = "GENERAL_DEADLINE";
    public static final String COLUMN_GENERAL_BRIEF = "GENERAL_BRIEF";

    public GeneralDBHolder(@Nullable Context context) {
        super(context, "general.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + GENERAL_TABLE + " (" +
                COLUMN_GENERAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GENERAL_NAME + " TEXT, " +
                COLUMN_GENERAL_START + " INT, " +
                COLUMN_GENERAL_END + " INT, " +
                COLUMN_GENERAL_WHERE + " TEXT, " +
                COLUMN_GENERAL_DEADLINE + " INT, " +
                COLUMN_GENERAL_BRIEF + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(General general){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GENERAL_NAME, general.getName());
        cv.put(COLUMN_GENERAL_START, general.getStart());
        cv.put(COLUMN_GENERAL_END, general.getEnd());
        cv.put(COLUMN_GENERAL_WHERE, general.getWhere());
        cv.put(COLUMN_GENERAL_DEADLINE, general.getDeadline());
        cv.put(COLUMN_GENERAL_BRIEF, general.getBrief());

        long insert = db.insert(GENERAL_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteOne(General general){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + GENERAL_TABLE + " WHERE " + COLUMN_GENERAL_ID + " = " + general.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){       //find the object
            return true;
        }
        else{
            return false;
        }
    }

    public List<General> getEveryone(){
        List<General> returnList = new ArrayList<>();
        //get dat from database
        String queryString = "SELECT * FROM " + GENERAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);   //cursor: result set
        if(cursor.moveToFirst()){
            do{
                int generalId = cursor.getInt(0);
                String generalName = cursor.getString(1);
                int generalStart = cursor.getInt(2);
                int generalEnd = cursor.getInt(3);
                String generalWhere = cursor.getString(4);
                int generalDeadline = cursor.getInt(5);
                String generalBrief = cursor.getString(6);

                General newGeneral = new General(generalId, generalName, generalStart, generalEnd, generalWhere, generalDeadline, generalBrief);
                returnList.add(newGeneral);
            }while(cursor.moveToNext());
        }
        else{
            //failed. don't add anything
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<General> searchWord(int d1, int d2, int d3, int d0, String search_word, KeyWordSetDBholder key_db, int c) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<General> returnList = new ArrayList<>();
        Log.d("search_wordCheck", "search_word: " + search_word);
        List<KeyWordSet> keyWordNameSet = key_db.getBack(search_word);


        String[] columns = {"*"};
        String selection = COLUMN_GENERAL_START + " >= ? AND "+
                COLUMN_GENERAL_END + " <= ? AND " +
                COLUMN_GENERAL_DEADLINE + " >= ? AND " +
                COLUMN_GENERAL_DEADLINE + " <= ?";

        String[] selectionArgs = new String[]{String.valueOf(d1), String.valueOf(d2), String.valueOf(d0), String.valueOf(d3)};
        Cursor cursor = db.query(GENERAL_TABLE, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //Log.d("CursorRowCount", "Number of rows in cursor: " + cursor.getCount());
            int idIndex = cursor.getColumnIndex(COLUMN_GENERAL_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_GENERAL_NAME);
            int startIndex = cursor.getColumnIndex(COLUMN_GENERAL_START);
            int endIndex = cursor.getColumnIndex(COLUMN_GENERAL_END);
            int whereIndex = cursor.getColumnIndex(COLUMN_GENERAL_WHERE);
            int deadlineIndex = cursor.getColumnIndex(COLUMN_GENERAL_DEADLINE);
            int briefIndex = cursor.getColumnIndex(COLUMN_GENERAL_BRIEF);

            do {
                int generalId = cursor.getInt(idIndex);
                String generalName = cursor.getString(nameIndex);
                int generalStart = cursor.getInt(startIndex);
                int generalEnd = cursor.getInt(endIndex);
                String generalWhere = cursor.getString(whereIndex);
                int generalDeadline = cursor.getInt(deadlineIndex);
                String generalBrief = cursor.getString(briefIndex);

                // 检查 generalName 是否包含关键字2
                if (generalName != null && keyWordNameSet != null && !generalName.isEmpty()) {
                    for (KeyWordSet keyWordSet : keyWordNameSet) {
                        String keyword = keyWordSet.getName();
                        //Log.d("KeywordCheck", "Keyword: " + keyword + ", GeneralName: " + generalName);
                        if (keyword != null && !keyword.isEmpty() && generalName.contains(keyword)) {
                            General newGeneral = new General(generalId, generalName, generalStart, generalEnd, generalWhere, generalDeadline, generalBrief);
                            returnList.add(newGeneral);
                            break; // 如果找到匹配的关键字，则跳出循环
                        }
                    }

                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        if(c == 1) {
            returnList.sort((g1, g2) -> Integer.compare(g1.getStart(), g2.getStart()));
        }
        else if(c == 2){
            returnList.sort(Comparator.comparingInt(General::getStart).reversed());

        }
        else if(c == 3){
            returnList.sort((g1, g2) -> Integer.compare(g1.getDeadline(), g2.getDeadline()));
        }
        else if(c == 4){
            returnList.sort(Comparator.comparingInt(General::getDeadline).reversed());
        }
        else{

        }

        return returnList;
    }

    public General getContent(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        General general = null;
        String query = "SELECT * FROM " + GENERAL_TABLE + " WHERE " + COLUMN_GENERAL_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor.moveToFirst()) {
            int generalId = cursor.getInt(0);
            String generalName = cursor.getString(1);
            int generalStart = cursor.getInt(2);
            int generalEnd = cursor.getInt(3);
            String generalWhere = cursor.getString(4);
            int generalDeadline = cursor.getInt(5);
            String generalBrief = cursor.getString(6);

            general = new General(generalId, generalName, generalStart, generalEnd, generalWhere, generalDeadline, generalBrief);
        }
        cursor.close();
        db.close();

        return general;
    }
    public General getContentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        General general = null;
        String query = "SELECT * FROM " + GENERAL_TABLE + " WHERE " + COLUMN_GENERAL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int generalId = cursor.getInt(0);
            String generalName = cursor.getString(1);
            int generalStart = cursor.getInt(2);
            int generalEnd = cursor.getInt(3);
            String generalWhere = cursor.getString(4);
            int generalDeadline = cursor.getInt(5);
            String generalBrief = cursor.getString(6);

            general = new General(generalId, generalName, generalStart, generalEnd, generalWhere, generalDeadline, generalBrief);
        }
        cursor.close();
        db.close();

        return general;
    }

}


