package com.example.gustavs.remotepccontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.barcodereader.BarcodeCaptureActivity;
import com.example.gustavs.remotepccontroller.barcodereader.BarcodeMainActivity;
import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothActivity;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.google.android.gms.vision.barcode.Barcode;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ConnectActivity.class.getSimpleName();
    private static final int QR_CODE_VALUES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        startActivityForResult(i, QR_CODE_VALUES);
        //startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QR_CODE_VALUES){
            if (data != null) {
                Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                String[] values = decodeValues(barcode.displayValue);
                ((EditText)findViewById(R.id.txt_pc_hostname)).setText(values[0]);
                ((EditText)findViewById(R.id.txt_pc_port)).setText(values[1]);
                ((EditText)findViewById(R.id.txt_pc_blutooth_name)).setText(values[2]);

            } else {
                Log.d(TAG, "No barcode captured, intent data is null");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String[] decodeValues(String displayValue) {
        String[] result = displayValue.split(";");
        if (result.length < 3) {
            Log.d(TAG, "Bad QR Code");
            String[] badresult = {"", "", ""};
            return badresult;
        } else {
            return result;
        }

    }
}
