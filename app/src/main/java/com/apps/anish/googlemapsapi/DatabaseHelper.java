package com.apps.anish.googlemapsapi;

/**
 * Created by anish on 12/27/18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FIXIT";
    private static final int DATABASE_VERSION = 1;
    private static final String createReportSQL = "create table report (" +
            "reportid numeric," +
            "type text(100)," +
            "description text(300)," +
            "reportingdate date," +
            "customerid numeric," +
            "location text(100)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createReportSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
