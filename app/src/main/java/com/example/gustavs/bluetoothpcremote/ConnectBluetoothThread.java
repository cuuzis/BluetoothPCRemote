package com.example.gustavs.bluetoothpcremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class ConnectBluetoothThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    private final OutputStream mmOutStream;

    // Constants used in server and client
    private static UUID GUID = UUID.fromString("d07c0736-07b9-4ec5-b876-53647c4d047b");


    public ConnectBluetoothThread(BluetoothDevice device, BluetoothAdapter btAdapter) {
        BluetoothSocket tmp = null;
        mmDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(GUID);
        } catch (IOException e) { }
        mmSocket = tmp;

        OutputStream tmpOut = null;
        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) { }
        mmOutStream = tmpOut;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        //mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }
    }

    public void write(String message) {
        byte[] msg = message.getBytes();
        try {
            mmOutStream.write(msg);
        } catch (IOException e) {
            System.out.println("Write error");
            e.printStackTrace();
        }
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
