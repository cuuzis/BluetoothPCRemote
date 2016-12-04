package com.example.gustavs.remotepccontroller.wifi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.gustavs.remotepccontroller.ConnectActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectWlanActivity extends ConnectActivity {

    private static final String TAG = ConnectWlanActivity.class.getSimpleName();
    private ConnectWlanTask connectWlanTask;
    private OutputStream mmOutStream;
    public final String SERVER_IP = "10.7.153.161";//"192.168.137.1";//"172.24.20.133";
    public final int SERVER_PORT = 8001;
    public final String SERVER_NAME = "cuzis-pc";
    public final int TIMEOUT_MILISECONDS = 3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectWlanTask = new ConnectWlanTask();
        connectWlanTask.execute();
        // keep doing stuff, while thread is still working...
    }

    @Override
    protected void sendCommand(String command) {
        Log.i(TAG, "TCP writing: " + command);
        connectWlanTask.write(command);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mmOutStream != null) {
            try {
                mmOutStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing stream", e);
            }
        }
    }

    private class ConnectWlanTask extends AsyncTask<String, Void, Void> {

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
                Log.e(TAG, "Stream write error", e);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.i(TAG, "Connecting to " + SERVER_NAME + ":" + SERVER_PORT);
            InetAddress serverAddr = null;
            try {
                //serverAddr = InetAddress.getByName(SERVER_IP);
                //Socket socket = new Socket(serverAddr, SERVER_PORT);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress( SERVER_NAME, SERVER_PORT), TIMEOUT_MILISECONDS);
                System.out.println("Connected to " + SERVER_NAME + ":" + SERVER_PORT);
                mmOutStream = socket.getOutputStream();
            } catch (SocketException e) {
                Log.e(TAG, "Socket Exception", e);
            } catch (IOException e) {
                if (e instanceof UnknownHostException) {
                    Log.e(TAG, "Unknown host: " + SERVER_NAME);
                } else {
                    Log.e(TAG, "TCP error", e);
                }
            }
            return null;
        }
    }
}
