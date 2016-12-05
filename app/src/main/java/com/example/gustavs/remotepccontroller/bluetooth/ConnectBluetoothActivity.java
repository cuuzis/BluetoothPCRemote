package com.example.gustavs.remotepccontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.ConnectActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ConnectBluetoothActivity extends ConnectActivity {

    private static final String TAG = ConnectBluetoothActivity.class.getSimpleName();
    private final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mmDevice;
    //private ConnectBluetoothThread connectBluetoothThread;

    private ConnectBluetoothTask connectBluetoothTask;
    private BluetoothSocket mmSocket;
    private OutputStream mmOutStream;
    private final static UUID GUID = UUID.fromString("d07c0736-07b9-4ec5-b876-53647c4d047b"); // used in server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check bluetooth status
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            promptToEnableBluetooth();
        }
        else {
            connectToWindows();
        }
    }

    @Override
    protected void sendCommand(String command) {
        //connectBluetoothThread.write(command);
        connectBluetoothTask.write(command);
    }

    private void promptToEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void connectToWindows() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.i(TAG, device.getName() + " @ " + device.getAddress());
                // TODO: remove hardcoded device name and scan for devices
                if (device.getName().equals("CUZIS-PC")) {
                    mmDevice = device;
                    break;
                }
            }
        }
        if (mmDevice != null) {
            Log.i(TAG, "Connecting to " + "CUZIS-PC");
            connectBluetoothTask = new ConnectBluetoothTask();
            connectBluetoothTask.execute();
            //connectBluetoothThread = new ConnectBluetoothThread(mmDevice, mBluetoothAdapter);
            //connectBluetoothThread.run();
            //setIsConnected(true);
        }
        else {
            Toast.makeText(this, "Device not found", Toast.LENGTH_SHORT).show();
            setIsConnected(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            connectToWindows();
        }
        else {
            setIsConnected(false);
        }
    }

    private class ConnectBluetoothTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setIsConnected(mmOutStream != null);
        }

        public void write(String message) {
            byte[] msg = message.getBytes();
            try {
                mmOutStream.write(msg);
            } catch (IOException e) {
                Log.e(TAG, "BT write error", e);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(GUID);
                mmSocket.connect(); // This will block until it succeeds or throws an exception
                mmOutStream = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "BT socket connecting error", e);
                cancel();
            }
            return null;
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "BT socket close error", e);
            }
        }
    }

}
