package com.example.gustavs.remotepccontroller.model;

import android.provider.BaseColumns;

public final class ProfileData {

    private ProfileData() {}

    public static class ProfileEntry implements BaseColumns {
        public static final String TABLE_NAME = "Profile";
        public static final String COLUMN_NAME_WLANNAME = "WlanName";
        public static final String COLUMN_NAME_WLANPORT = "WlanPort";
        public static final String COLUMN_NAME_BLUETOOTHNAME = "BluetoothName";
        public static final String[] ALL_COLUMNS = {_ID, COLUMN_NAME_WLANNAME, COLUMN_NAME_WLANPORT, COLUMN_NAME_BLUETOOTHNAME};
    }

}
