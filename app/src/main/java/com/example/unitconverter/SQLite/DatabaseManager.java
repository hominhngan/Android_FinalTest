package com.example.unitconverter.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DBHelper(context);
    }
    public void open(){
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        database.close();
    }

    //Them mot dong moi vao database
    public void addData(double result) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_RESULT, result);
        database.insert(DBHelper.TABLE_NAME, null, values);
    }
    //Lay tat ca cac dong trong database
    public List<Result> getAllData() {
        List<Result> list = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID));
            double result = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_RESULT));

            list.add(new Result(id, result));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


}
