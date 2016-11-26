package com.example.gustavs.bluetoothpcremote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connectViaBluetooth(View v) {
        Intent i = new Intent(this, ConnectBluetoothActivity.class);
        startActivity(i);
    }

    public void connectViaWiFiDirect(View v) {
        Intent i = new Intent(this, ConnectWiFiDirectActivity.class);
        startActivity(i);
    }

    public void connectViaWlan(View v) {
        Intent i = new Intent(this, ConnectWlanActivity.class);
        startActivity(i);
    }
}
