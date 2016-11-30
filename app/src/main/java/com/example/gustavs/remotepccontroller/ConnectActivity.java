package com.example.gustavs.remotepccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public abstract class ConnectActivity extends AppCompatActivity {

    protected boolean threadIsConnected = false;

    // Constants used in server and client
    protected final String UP      = "UP";
    protected final String DOWN    = "DOWN";
    protected final String LEFT    = "LEFT";
    protected final String RIGHT   = "RIGHT";
    protected final String SPACE   = "SPACE";
    protected final String F5      = "F5";
    protected final String CTRL_F5 = "CTRL_F5";
    protected final String CTRL_L  = "CTRL_L";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }


    // Buttons are mapped to command strings by their IDs
    protected void onButtonDown(View v) {
        if (!threadIsConnected) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.up:
                sendCommand(UP);
                break;
            case R.id.down:
                sendCommand(DOWN);
                break;
            case R.id.left:
                sendCommand(LEFT);
                break;
            case R.id.right:
                sendCommand(RIGHT);
                break;
            case R.id.space:
                sendCommand(SPACE);
                break;
            case R.id.f5:
                sendCommand(F5);
                break;
            case R.id.ctrl_f5:
                sendCommand(CTRL_F5);
                break;
            case R.id.ctrl_l:
                sendCommand(CTRL_L);
                break;
            default:
                System.out.println("Error:invalid button");
                break;
        }
    }

    // Volume keys are mapped to arrows:
    //   volume down -> left arrow
    //   volume up   -> right arrow
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            sendCommand("LEFT");
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendCommand("RIGHT");
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

    protected abstract void sendCommand(String command);
}
