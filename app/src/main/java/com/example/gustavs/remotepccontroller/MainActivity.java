package com.example.gustavs.remotepccontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothActivity;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.example.gustavs.remotepccontroller.wifi.ScanNetwork;

import static com.example.gustavs.remotepccontroller.wifi.ScanNetwork.ConnectToNetwork;

//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private final String SSID = "REMOTE-CONTROLLER";
    private final String KEY  = "unsafepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectToNetwork(SSID, KEY, this);
    }

    public void connectViaBluetooth(View v) {
        Intent i = new Intent(this, ConnectBluetoothActivity.class);
        startActivity(i);
    }

    public void connectViaWiFiDirect(View v) {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
        //ScanNetwork scanNetwork = new ScanNetwork();
        //scanNetwork.doScan(this);
        //Intent i = new Intent(this, ConnectWiFiDirectActivity.class);
        //startActivity(i);
    }

    public void connectViaWlan(View v) {
        Intent i = new Intent(this, ConnectWlanActivity.class);
        startActivity(i);
    }
}
