package com.example.gustavs.remotepccontroller.profile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.gustavs.remotepccontroller.profile.ProfileActivity.EMPTY_ID;
import static com.example.gustavs.remotepccontroller.R.id.rb_first_priority_wlan;
import static com.example.gustavs.remotepccontroller.R.id.rb_second_priority_btooth;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry._ID;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.ALL_COLUMNS;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_BLUETOOTHNAME;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_FIRST_PRIORITY;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_SECOND_PRIORITY;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.TABLE_NAME;

public class Profile {

    private static final int DEFAULT_PORT = 8001;

    private int id;
    private String wlanName;
    private int wlanPort;
    private String bluetoothName;
    private int firstPriority;
    private int secondPriority;

    public Profile(Context context, int profileId) {
        ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, _ID+"="+profileId, null, null, null, null);
        cursor.moveToFirst();
        this.id = profileId;
        this.wlanName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANNAME));
        this.wlanPort = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANPORT));
        this.bluetoothName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BLUETOOTHNAME));
        this.firstPriority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_FIRST_PRIORITY));
        this.secondPriority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_SECOND_PRIORITY));
        cursor.close();
    }

    public Profile() {
        this.id = EMPTY_ID;
        this.wlanName = "";
        this.wlanPort = DEFAULT_PORT;
        this.bluetoothName = "";
        this.firstPriority = rb_first_priority_wlan;
        this.secondPriority = rb_second_priority_btooth;
    }

    public void saveToDatabase(Context context) {
        ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_WLANNAME, wlanName);
        values.put(COLUMN_NAME_WLANPORT, wlanPort);
        values.put(COLUMN_NAME_BLUETOOTHNAME, bluetoothName);
        values.put(COLUMN_NAME_FIRST_PRIORITY, firstPriority);
        values.put(COLUMN_NAME_SECOND_PRIORITY, secondPriority);
        if (this.id == EMPTY_ID) {
            db.insert(TABLE_NAME, null, values);
        } else {
            this.id = db.update(TABLE_NAME, values, _ID + " = " + this.id, null);
        }
    }

    public String getWlanName() {
        return wlanName;
    }

    public void setWlanName(String wlanName) {
        this.wlanName = wlanName;
    }

    public int getWlanPort() {
        return wlanPort;
    }

    public void setWlanPort(String wlanPort) {
        if (wlanPort.isEmpty())
            this.wlanPort = DEFAULT_PORT;
        else
            this.wlanPort = Integer.valueOf(wlanPort);
    }

    public String getBlutoothName() {
        return bluetoothName;
    }

    public void setBlutoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public int getFirstPriority() {
        return firstPriority;
    }

    public void setFirstPriority(int firstPriority) {
        this.firstPriority = firstPriority;
    }

    public int getSecondPriority() {
        return secondPriority;
    }

    public void setSecondPriority(int secondPriority) {
        this.secondPriority = secondPriority;
    }
}
