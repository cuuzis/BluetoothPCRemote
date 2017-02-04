package com.example.gustavs.remotepccontroller;

import android.content.Intent;
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
import com.example.gustavs.remotepccontroller.model.Profile;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import static com.example.gustavs.remotepccontroller.MainActivity.PROFILE_ID;
import static com.example.gustavs.remotepccontroller.barcodereader.BarcodeActivity.BarcodeObject;


public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = AbstractConnectActivity.class.getSimpleName();
    private static final int RC_BARCODE_CAPTURE = 9001;

    public static final int EMPTY_ID = -1;
    private int profileId;
    private Profile mProfile;

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
        profileId = getIntent().getIntExtra(PROFILE_ID, EMPTY_ID);

        if (profileId == EMPTY_ID) {
            mProfile = new Profile();
        } else {
            mProfile = new Profile(this, profileId);
            ((EditText)findViewById(R.id.et_wlanname)).setText(mProfile.getWlanName());
            ((EditText)findViewById(R.id.et_wlanport)).setText(mProfile.getWlanPort());
            ((EditText)findViewById(R.id.et_blutoothname)).setText(mProfile.getBlutoothName());
            ((RadioGroup)findViewById(R.id.rg_first_priority)).check(mProfile.getFirstPriority());
            ((RadioGroup)findViewById(R.id.rg_second_priority)).check(mProfile.getSecondPriority());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
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

    public void connect(View view) {
        saveProfile();
        mProfile.connect(this);
    }

    private void saveProfile() {                // TODO: button should only be enabled if input is valid
        mProfile.setWlanName(((EditText)findViewById(R.id.et_wlanname)).getText().toString());
        mProfile.setWlanPort(((EditText)findViewById(R.id.et_wlanport)).getText().toString());
        mProfile.setBlutoothName(((EditText)findViewById(R.id.et_blutoothname)).getText().toString());
        mProfile.setFirstPriority(((RadioGroup)findViewById(R.id.rg_first_priority)).getCheckedRadioButtonId());
        mProfile.setSecondPriority(((RadioGroup)findViewById(R.id.rg_second_priority)).getCheckedRadioButtonId());
        mProfile.saveToDatabase(this);
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
