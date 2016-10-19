package com.example.gustavs.bluetoothpcremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;
//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mmDevice;
    private ConnectThread connectThread;

    // Constants used in server and client
    private final String UP      = "UP";
    private final String DOWN    = "DOWN";
    private final String LEFT    = "LEFT";
    private final String RIGHT   = "RIGHT";
    private final String SPACE   = "SPACE";
    private final String F5      = "F5";
    private final String CTRL_F5 = "CTRL_F5";
    private final String CTRL_L  = "CTRL_L";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check bluetooth status
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            System.out.println("Device does not support Bluetooth");
            ((TextView) findViewById(R.id.btInfo) ).setText("Bluetooth not supported");
        }
        else if (!mBluetoothAdapter.isEnabled()) {
            promptToEnableBluetooth();
        }
        else {
            connectToWindows();
        }
    }

    private void promptToEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void connectToWindows() {
        System.out.println("Connecting to PC...");
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
            connectThread = new ConnectThread(mmDevice, mBluetoothAdapter);
            connectThread.run();
            System.out.println("Established connection");
            ((TextView) findViewById(R.id.btInfo) ).setText("Established connection");
            enableButtons();
        }
        else {
            System.out.println("Device not found");
            ((TextView) findViewById(R.id.btInfo) ).setText("Device not found");
        }
    }

    private void enableButtons() {
        Button button = (Button) findViewById(R.id.down);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //while (button.clicked)
                connectThread.write(DOWN);
            }
        });
        button = (Button) findViewById(R.id.up);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(UP);
            }
        });
        button = (Button) findViewById(R.id.left);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(LEFT);
            }
        });
        button = (Button) findViewById(R.id.right);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(RIGHT);
            }
        });
        button = (Button) findViewById(R.id.space);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(SPACE);
            }
        });
        button = (Button) findViewById(R.id.f5);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(F5);
            }
        });
        button = (Button) findViewById(R.id.ctrl_f5);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(CTRL_F5);
            }
        });
        button = (Button) findViewById(R.id.ctrl_l);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectThread.write(CTRL_L);
            }
        });
    }

    // Volume keys are mapped to arrows:
    //   volume down -> left arrow
    //   volume up   -> right arrow
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            connectThread.write("LEFT");
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            connectThread.write("RIGHT");
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    // Disables volume key sound
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP) || (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            connectToWindows();
        }
        else {
            ((TextView) findViewById(R.id.btInfo) ).setText("Bluetooth not enabled");
        }
    }
}
