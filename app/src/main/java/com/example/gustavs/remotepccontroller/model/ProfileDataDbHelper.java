package com.example.gustavs.remotepccontroller.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry._ID;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.ALL_COLUMNS;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_BLUETOOTHNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_FIRST_PRIORITY;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_SECOND_PRIORITY;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.TABLE_NAME;

public class ProfileDataDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ProfileData.db";




    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_WLANNAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_WLANPORT + INT_TYPE + COMMA_SEP +
                    COLUMN_NAME_BLUETOOTHNAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_FIRST_PRIORITY + INT_TYPE + COMMA_SEP +
                    COLUMN_NAME_SECOND_PRIORITY + INT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ProfileDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public Cursor queryAllProfiles() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
    }

    public void deleteProfile(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, _ID+" = "+id, null);
    }
}
