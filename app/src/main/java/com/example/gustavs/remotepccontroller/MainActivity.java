package com.example.gustavs.remotepccontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothActivity;
import com.example.gustavs.remotepccontroller.barcodereader.BarcodeGraphic;
import com.example.gustavs.remotepccontroller.barcodereader.BarcodeMainActivity;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.example.gustavs.remotepccontroller.wifi.ScanNetwork;


//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private final String SSID = "REMOTE-CONTROLLER";
    private final String KEY  = "unsafepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ConnectToNetwork(SSID, KEY, this);
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

    public void scanQRCode(View v) {
        Intent i = new Intent(this, BarcodeMainActivity.class);
        startActivity(i);
    }
}
