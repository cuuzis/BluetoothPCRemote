package com.example.gustavs.remotepccontroller.wifi;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.gustavs.remotepccontroller.ConnectActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectWlanActivity extends ConnectActivity {

    private ConnectWlanTask connectWlanTask;
    private OutputStream mmOutStream;
    public static final String SERVER_IP = "10.7.153.161";//"192.168.137.1";//"172.24.20.133";
    public static final int SERVER_PORT = 8001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Creating WLAN thread...");

        connectWlanTask = new ConnectWlanTask();
        threadIsConnected = true;
        connectWlanTask.execute();
    }

    @Override
    protected void sendCommand(String command) {
        System.out.println("TCP writing: " + command);
        connectWlanTask.write(command);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mmOutStream.close();
        } catch (IOException e) {
            System.out.println("Error on closing socket");
            e.printStackTrace();
        }
    }

    private class ConnectWlanTask extends AsyncTask<String, Void, Void> {

        public void write(String message) {
            byte[] msg = message.getBytes();
            try {
                mmOutStream.write(msg);
            } catch (IOException e) {
                System.out.println("Write error");
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            System.out.println("Connecting to " + SERVER_IP + ":" + SERVER_PORT);
            InetAddress serverAddr = null;
            try {
                serverAddr = InetAddress.getByName(SERVER_IP);
                Socket socket = new Socket(serverAddr, SERVER_PORT);
                System.out.println("Connected to " + SERVER_IP + ":" + SERVER_PORT);
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
                if (e instanceof UnknownHostException) {
                    System.out.println("Unknown host error");
                } else {
                    System.out.println("TCP error: " + e.getMessage());
                }
                e.printStackTrace();
            }
            return null;
        }
    }
}
