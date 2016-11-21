package com.example.gustavs.bluetoothpcremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Set;

public class ConnectBluetoothActivity extends ConnectActivity {

    private final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mmDevice;
    private ConnectBluetoothThread connectBluetoothThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check bluetooth status
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            System.out.println("Device does not support Bluetooth");
            ((TextView) findViewById(R.id.connectionInfo) ).setText("Bluetooth not supported");
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            promptToEnableBluetooth();
        }
        else {
            connectToWindows();
        }
    }

    @Override
    void writeToThread(String str) {
        connectBluetoothThread.write(str);
    }

    private void promptToEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void connectToWindows() {
        System.out.println("Connecting to PC via Bluetooth...");
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println(device.getName() + "\n" + device.getAddress() + "\n");
                // hardcoded device name
                if (device.getName().equals("CUZIS-PC")) {
                    mmDevice = device;
                    break;
                }
            }
        }
        if (mmDevice != null) {
            connectBluetoothThread = new ConnectBluetoothThread(mmDevice, mBluetoothAdapter);
            connectBluetoothThread.run();
            System.out.println("Established Bluetooth connection");
            ((TextView) findViewById(R.id.connectionInfo) ).setText("Established Bluetooth connection");
            threadIsConnected = true;
        }
        else {
            System.out.println("Device not found");
            ((TextView) findViewById(R.id.connectionInfo) ).setText("Device not found");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            connectToWindows();
        }
        else {
            ((TextView) findViewById(R.id.connectionInfo) ).setText("Bluetooth not enabled");
        }
    }
}
