package com.example.gustavs.remotepccontroller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.barcodereader.BarcodeActivity;
import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothActivity;
import com.example.gustavs.remotepccontroller.model.ProfileDataDbHelper;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import static android.provider.BaseColumns._ID;
import static com.example.gustavs.remotepccontroller.MainActivity.PROFILE_ID;
import static com.example.gustavs.remotepccontroller.barcodereader.BarcodeActivity.BarcodeObject;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.ALL_COLUMNS;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_FIRST_PRIORITY;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_SECOND_PRIORITY;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_BLUETOOTHNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.TABLE_NAME;


public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = AbstractConnectActivity.class.getSimpleName();
    private static final int RC_BARCODE_CAPTURE = 9001;

    private static final int EMPTY_ID = -1;
    private long profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar profileToolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(profileToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        else
            throw new AssertionError("getSupportActionBar returned null");

        // Loads bundle data (stored values)
        profileId = getIntent().getLongExtra(PROFILE_ID, EMPTY_ID);
        if (profileId != EMPTY_ID) {
            ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(this);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, _ID+"="+profileId, null, null, null, null);
            cursor.moveToFirst();
            ((EditText)findViewById(R.id.et_wlanname)).setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANNAME)));
            ((EditText)findViewById(R.id.et_wlanport)).setText(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANPORT))));
            ((EditText)findViewById(R.id.et_blutoothname)).setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BLUETOOTHNAME)));
            ((RadioGroup)findViewById(R.id.rg_first_priority)).check(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_FIRST_PRIORITY)));
            ((RadioGroup)findViewById(R.id.rg_second_priority)).check(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_SECOND_PRIORITY)));
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    public void connect(View view) {
        final String wlanName = ((EditText)findViewById(R.id.et_wlanname)).getText().toString();
        final int wlanPort = Integer.parseInt(((EditText)findViewById(R.id.et_wlanport)).getText().toString());
        final String bluetoothName = ((EditText)findViewById(R.id.et_blutoothname)).getText().toString();
        final int firstPriority = ((RadioGroup)findViewById(R.id.rg_first_priority)).getCheckedRadioButtonId();
        final int secondPriority = ((RadioGroup)findViewById(R.id.rg_second_priority)).getCheckedRadioButtonId();
        Intent intent;
        Bundle bundle = new Bundle();
        if (firstPriority == R.id.rb_first_priority_wlan) {
            intent = new Intent(this, ConnectWlanActivity.class);
            bundle.putString(COLUMN_NAME_WLANNAME, wlanName);
            bundle.putInt(COLUMN_NAME_WLANPORT, wlanPort);
            intent.putExtras(bundle);
        } else {
            intent = new Intent(this, ConnectBluetoothActivity.class);
            bundle.putString(COLUMN_NAME_BLUETOOTHNAME, bluetoothName);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void connectViaBluetooth(View v) {
        Intent i = new Intent(this, ConnectBluetoothActivity.class);
        startActivity(i);
    }

    public void connectViaWiFiDirect(View v) {
        //ScanNetwork scanNetwork = new ScanNetwork();
        //scanNetwork.FindIPsByHostname("cuzis-pc");
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();

        //ScanNetwork scanNetwork = new ScanNetwork();
        //scanNetwork.doScan(this);
        //Intent i = new Intent(this, ConnectWiFiDirectActivity.class);
        //startActivity(i);
    }

    public void connectViaWlan(View v) {
        Intent intent = new Intent(this, ConnectWlanActivity.class);
        /*Bundle bundle = new Bundle();
        bundle.putString("SERVER_NAME", "cuzis-pc");
        intent.putExtras(bundle);*/
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveProfile();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case android.R.id.title:
                //TODO:show popup title editor
                Toast.makeText(this, "Title clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProfile() {                // TODO: button should only be enabled if input is valid
        ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        final String wlanName = ((EditText)findViewById(R.id.et_wlanname)).getText().toString();
        final int wlanPort = Integer.parseInt(((EditText)findViewById(R.id.et_wlanport)).getText().toString());
        final String bluetoothName = ((EditText)findViewById(R.id.et_blutoothname)).getText().toString();
        final int firstPriority = ((RadioGroup)findViewById(R.id.rg_first_priority)).getCheckedRadioButtonId();
        final int secondPriority = ((RadioGroup)findViewById(R.id.rg_second_priority)).getCheckedRadioButtonId();
        values.put(COLUMN_NAME_WLANNAME, wlanName);
        values.put(COLUMN_NAME_WLANPORT, wlanPort);
        values.put(COLUMN_NAME_BLUETOOTHNAME, bluetoothName);
        values.put(COLUMN_NAME_FIRST_PRIORITY, firstPriority);
        values.put(COLUMN_NAME_SECOND_PRIORITY, secondPriority);
        if (profileId == EMPTY_ID) {
            db.insert(TABLE_NAME, null, values);
        } else {
            db.update(TABLE_NAME, values, _ID+" = "+profileId, null);
        }
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }

    //region scan QRcode
    public void scanQRCode(View v) {
        Intent intent = new Intent(this, BarcodeActivity.class);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    String[] values = decodeValues(barcode.displayValue);
                    ((EditText)findViewById(R.id.et_wlanname)).setText(values[0]);
                    ((EditText)findViewById(R.id.et_wlanport)).setText(values[1]);
                    ((EditText)findViewById(R.id.et_blutoothname)).setText(values[2]);

                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Log.e(TAG, "Barcode read error: " + CommonStatusCodes.getStatusCodeString(resultCode));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String[] decodeValues(String displayValue) {
        String[] result = displayValue.split(";");
        if (result.length < 3) {
            Log.e(TAG, "Bad QR Code");
            String[] badresult = {"", "", ""};
            return badresult;
        } else {
            return result;
        }

    }
    //endregion

    // Ensures 1st and 2nd connection priorities are not the same
    public void onPriorityChecked(View view) {
        if (view.getId() == R.id.rb_first_priority_wlan)
            if (((RadioButton)findViewById(R.id.rb_second_priority_wlan)).isChecked())
                ((RadioGroup)findViewById(R.id.rg_second_priority)).check(R.id.rb_second_priority_btooth);
        if (view.getId() == R.id.rb_first_priority_btooth)
            if (((RadioButton)findViewById(R.id.rb_second_priority_btooth)).isChecked())
                ((RadioGroup)findViewById(R.id.rg_second_priority)).check(R.id.rb_second_priority_wlan);
        if (view.getId() == R.id.rb_second_priority_wlan)
            if (((RadioButton)findViewById(R.id.rb_first_priority_wlan)).isChecked())
                ((RadioGroup)findViewById(R.id.rg_first_priority)).check(R.id.rb_first_priority_btooth);
        if (view.getId() == R.id.rb_second_priority_btooth)
            if (((RadioButton)findViewById(R.id.rb_first_priority_btooth)).isChecked())
                ((RadioGroup)findViewById(R.id.rg_first_priority)).check(R.id.rb_first_priority_wlan);
    }

}
