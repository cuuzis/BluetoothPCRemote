package com.example.gustavs.remotepccontroller.wifi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.gustavs.remotepccontroller.AbstractConnectActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;

public class ConnectWlanActivity extends AbstractConnectActivity {

    private static final String TAG = ConnectWlanActivity.class.getSimpleName();
    private ConnectWlanTask connectWlanTask;
    private OutputStream mmOutStream;
    //public final String SERVER_IP = "10.7.153.161";//"192.168.137.1";//"172.24.20.133";
    //private final int SERVER_PORT = 8001;
    //private final String SERVER_NAME = "cuzis-pc";
    public final int TIMEOUT_MILISECONDS = 3000;
    private String mServerName;
    private int mServerPort;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServerName = getIntent().getStringExtra(COLUMN_NAME_WLANNAME);
        mServerPort = getIntent().getIntExtra(COLUMN_NAME_WLANPORT, -1);
        if ((mServerName == null) || (mServerPort == -1)) {
            Log.e(TAG,"Profile information not passed:"+mServerName+";"+mServerPort);
            setIsConnected(false);
        }

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
            Log.i(TAG, "Connecting to " + mServerName + ":" + mServerPort);
            InetAddress serverAddr = null;
            try {
                //serverAddr = InetAddress.getByName(SERVER_IP);
                //Socket socket = new Socket(serverAddr, SERVER_PORT);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress( mServerName, mServerPort), TIMEOUT_MILISECONDS);
                System.out.println("Connected to " + mServerName + ":" + mServerPort);
                mmOutStream = socket.getOutputStream();
            } catch (SocketException e) {
                Log.e(TAG, "Socket Exception", e);
            } catch (IOException e) {
                if (e instanceof UnknownHostException) {
                    Log.e(TAG, "Unknown host: " + mServerName);
                } else {
                    Log.e(TAG, "TCP error", e);
                }
            }
            return null;
        }
    }
}
