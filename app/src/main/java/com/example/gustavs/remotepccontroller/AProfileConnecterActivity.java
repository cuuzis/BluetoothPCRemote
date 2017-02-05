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


    public void connect(Profile profile) {
        mProfile = profile;
        if (mProfile.getFirstPriority() == R.id.rb_first_priority_wlan) {
            mConnectWlanTask = new ConnectWlanTask(this);
            mConnectWlanTask.execute(profile);
        } else {
            mConnectBluetoothTask = new ConnectBluetoothTask(this);
            mConnectBluetoothTask.execute(profile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            Log.i(TAG, "Bluetooth enabled");
            connect(mProfile);
        } else if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED) {
            Log.i(TAG, "Bluetooth canceled");
        } else {
            Log.i(TAG, "Unknown activity result");
        }
    }

    //@Override
    public void onReceiveConnection(OutputStream oStream) {
        Log.i(TAG, "Async task done");
        if (oStream != null) {
            ConnectionActivity.outputStream = oStream;
            Intent intent = new Intent(this, ConnectionActivity.class);
            //intent.putExtra("STREAM", (Parcelable) oStream);
            startActivity(intent);
        }
    }
}
