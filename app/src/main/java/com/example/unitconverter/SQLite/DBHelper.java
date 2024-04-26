package com.example.unitconverter.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "UnitConverter-database";
    private static final int DB_VERSION = 1;


    //Ten bang va cot
    public static final String TABLE_NAME = "SQLiteUnitConverter";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RESULT = "result";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RESULT + " DOUBLE);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xu ly cap nhat database khi can thiet
    }
}
