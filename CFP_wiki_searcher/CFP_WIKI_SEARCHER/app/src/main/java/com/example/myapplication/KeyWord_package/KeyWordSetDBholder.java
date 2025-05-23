package com.example.myapplication.KeyWord_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.General_package.General;

import java.util.ArrayList;
import java.util.List;

public class KeyWordSetDBholder extends SQLiteOpenHelper {
    public static final String KEYWORDSET_TABLE = "KEYWORDSET_TABLE";
    public static final String COLUMN_KEYWORDSET_ID = "ID";
    public static final String COLUMN_KEYWORDSET_NAME = "KEYWORDSET_NAME";
    public static final String COLUMN_kEYWORDSET_KEYWORD = "KEYWORDSET_KEYWORD";

    public KeyWordSetDBholder(@Nullable Context context){
        super(context, "keyword.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + KEYWORDSET_TABLE + " (" +
                COLUMN_KEYWORDSET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KEYWORDSET_NAME + " TEXT, " +
                COLUMN_kEYWORDSET_KEYWORD + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(KeyWordSet keywordSet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEYWORDSET_ID, keywordSet.getId());
        cv.put(COLUMN_KEYWORDSET_NAME, keywordSet.getName());
        cv.put(COLUMN_kEYWORDSET_KEYWORD, keywordSet.getKeyword());

        long insert = db.insert(KEYWORDSET_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteOne(KeyWordSet keywordSet){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + KEYWORDSET_TABLE + " WHERE " + COLUMN_KEYWORDSET_ID + " = " + keywordSet.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){       //find the object
            return true;
        }
        else{
            return false;
        }
    }

    public List<KeyWordSet> getEveryone(){
        List<KeyWordSet> returnList = new ArrayList<>();
        //get dat from database
        String queryString = "SELECT * FROM " + KEYWORDSET_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);   //cursor: result set
        if(cursor.moveToFirst()){
            do{
                int keyWordSetId = cursor.getInt(0);
                String keyWordSetName = cursor.getString(1);
                String keyWordSetKeyWord = cursor.getString(2);

                KeyWordSet newCustomer = new KeyWordSet(keyWordSetId, keyWordSetName, keyWordSetKeyWord);
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

    public List<KeyWordSet> getBack(String key) {
        List<KeyWordSet> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 构建 SQL 查询语句，查找包含关键字的数据名
        String queryString = "SELECT DISTINCT " + COLUMN_KEYWORDSET_NAME + " FROM " + KEYWORDSET_TABLE +
                " WHERE " + COLUMN_KEYWORDSET_NAME + " LIKE ? OR " +
                COLUMN_kEYWORDSET_KEYWORD + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + key + "%", "%" + key + "%"};
        Log.d("Key", "key: " + key );
        for (String arg : selectionArgs) {
            Log.d("SelectionArg", "Arg value: " + arg);
        }

        Cursor cursor = db.rawQuery(queryString, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_KEYWORDSET_NAME);
            do {
                String keywordSetName = cursor.getString(columnIndex);
                if (keywordSetName != null) {
                    Log.d("KeywordCheckInFolder", "keywordName: " + keywordSetName );
                    KeyWordSet newCustomer = new KeyWordSet(keywordSetName);
                    returnList.add(newCustomer);
                    Log.d("KeywordCheckReturnList", "returnList: " + returnList );
                }
                else{
                    Log.d("KeywordCheckInFolder", "no");
                }
            } while (cursor.moveToNext());
        } else {
            // 查询失败或无结果，这里添加相应的错误处理逻辑
            Log.e("DatabaseError", "No matching records found for the given keyword: " + key);
            // 可以抛出异常或者记录错误信息到日志等
            // 例如： throw new RuntimeException("No matching records found for the given keyword: " + key);
        }

        /*if (cursor != null) {

        }*/
        cursor.close();
        db.close();
        return returnList;
    }
    public List<String> getContent(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> values = new ArrayList<>();
        String query = "SELECT * FROM " + KEYWORDSET_TABLE + " WHERE " + COLUMN_KEYWORDSET_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});

        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(2);
                values.add(value);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return values;
    }
    /**




    /**
     public boolean deleteOne(KeyWordSet keywordSet) {
     SQLiteDatabase db = this.getWritableDatabase();
     return db.delete(KEYWORDSET_TABLE, COLUMN_KEYWORDSET_ID + " = ?", new String[]{String.valueOf(keywordSet.getId())}) > 0;
     }
     */

}
