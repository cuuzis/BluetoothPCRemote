package com.example.gustavs.remotepccontroller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class ConnectBluetoothThread extends Thread {

    private static final String TAG = ConnectBluetoothThread.class.getSimpleName();
    private final BluetoothSocket mmSocket;
    private final OutputStream mmOutStream;
    private final static UUID GUID = UUID.fromString("d07c0736-07b9-4ec5-b876-53647c4d047b"); // used in server

    public ConnectBluetoothThread(BluetoothDevice device, BluetoothAdapter btAdapter) {
        BluetoothSocket tmp = null;
        try {
            tmp = device.createRfcommSocketToServiceRecord(GUID);
        } catch (IOException e) {
            Log.e(TAG, "Error creating BT socket", e);
            //finish();  ??
        }
        mmSocket = tmp;

        OutputStream tmpOut = null;
        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error creating BT stream", e);
        }
        mmOutStream = tmpOut;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        //mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();

        } catch (IOException e) {
            Log.e(TAG, "BT socket connect error", e);
            cancel();
        }
    }

    public void write(String message) {
        byte[] msg = message.getBytes();
        try {
            mmOutStream.write(msg);
        } catch (IOException e) {
            Log.e(TAG, "BT write error", e);
        }
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
