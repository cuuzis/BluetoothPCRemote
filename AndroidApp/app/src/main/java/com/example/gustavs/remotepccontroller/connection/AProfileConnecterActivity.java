package com.example.gustavs.remotepccontroller.connection;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.R;
import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothTask;
import com.example.gustavs.remotepccontroller.profile.Profile;
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
        if ((mConnectionAttempt == 1) && (mProfile.getFirstPriority() == R.id.rb_first_priority_wlan))
            connectWlan(mProfile);
        else if ((mConnectionAttempt == 1) && (mProfile.getFirstPriority() == R.id.rb_first_priority_btooth))
            connectBluetooth(mProfile);
        else if ((mConnectionAttempt == 2) && (mProfile.getSecondPriority() == R.id.rb_second_priority_wlan))
            connectWlan(mProfile);
        else if ((mConnectionAttempt == 2) && (mProfile.getSecondPriority() == R.id.rb_second_priority_btooth))
            connectBluetooth(mProfile);
        else {
            Log.e(TAG, "Could not connect");
            Toast.makeText(this, R.string.could_not_connect, Toast.LENGTH_SHORT).show();
            if (this instanceof ConnectionActivity)
                finish();
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
            mConnectionAttempt++;
            connect();
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
