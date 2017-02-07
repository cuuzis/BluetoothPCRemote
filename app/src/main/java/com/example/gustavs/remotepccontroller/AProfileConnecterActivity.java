package com.example.gustavs.remotepccontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothTask;
import com.example.gustavs.remotepccontroller.model.Profile;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanTask;

import java.io.OutputStream;

import static com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothTask.REQUEST_ENABLE_BT;

public abstract class AProfileConnecterActivity extends AppCompatActivity {

    private static final String TAG = AProfileConnecterActivity.class.getSimpleName();
    private ConnectBluetoothTask mConnectBluetoothTask = null;
    private ConnectWlanTask mConnectWlanTask = null;
    private Profile mProfile;
    private int mConnectionAttempt;


    protected void connect(Profile profile) {
        mProfile = profile;
        mConnectionAttempt = 1;
        connect();
    }

    private void connect() {
        if (mConnectionAttempt == 1) {
            if (mProfile.getFirstPriority() == R.id.rb_first_priority_wlan)
                connectWlan(mProfile);
            else if (mProfile.getFirstPriority() == R.id.rb_first_priority_btooth)
                connectBluetooth(mProfile);
        } else if (mConnectionAttempt == 2) {
            if (mProfile.getSecondPriority() == R.id.rb_second_priority_wlan)
                connectWlan(mProfile);
            else if (mProfile.getSecondPriority() == R.id.rb_second_priority_btooth)
                connectBluetooth(mProfile);
        } else {
            Log.i(TAG, "Could not connect");
        }
    }

    private void connectWlan(Profile profile) {
        mConnectWlanTask = new ConnectWlanTask(this);
        mConnectWlanTask.execute(profile);
    }

    private void connectBluetooth(Profile profile) {
        mConnectBluetoothTask = new ConnectBluetoothTask(this);
        mConnectBluetoothTask.execute(profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Log.i(TAG, "Bluetooth enabled");
            connect();
        } else if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED) {
            Log.i(TAG, "Bluetooth canceled");
        } else {
            Log.i(TAG, "Unknown activity result");
        }
    }

    public void onReceiveConnection(OutputStream oStream) {
        if (oStream != null) {
            Log.v(TAG, "Asynctask stream received");
            ConnectionActivity.outputStream = oStream;
            ConnectionActivity.currentProfile = mProfile;
            if (!(this instanceof ConnectionActivity)) {
                Intent intent = new Intent(this, ConnectionActivity.class);
                startActivity(intent);
            }
        } else {
            Log.v(TAG, "Asynctask no stream");
            mConnectionAttempt++;
            connect();
        }
    }
}
