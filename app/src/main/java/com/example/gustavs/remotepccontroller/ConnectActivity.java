package com.example.gustavs.remotepccontroller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class ConnectActivity extends AppCompatActivity {

    private static final String TAG = ConnectActivity.class.getSimpleName();
    private boolean isThreadConnected = false;
    private ProgressDialog dialog;
    private boolean screenOff = false;

    // Sends command string to server
    protected abstract void sendCommand(String command);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Establishing connection...");
        dialog.show();
    }

    // must always be called in subclass
    protected void setIsConnected(boolean isConnected) {
        if (dialog.isShowing())
            dialog.dismiss();
        if (isConnected) {
            isThreadConnected = true;
            setContentView(R.layout.activity_connect);
        } else {
            Toast.makeText(this, "Could not connect, check log!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }


    // Buttons are mapped to command strings by their IDs
    protected void onButtonDown(View v) {
        if (!isThreadConnected) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.up:
                sendCommand(getResources().getString(R.string.server_up));
                break;
            case R.id.down:
                sendCommand(getResources().getString(R.string.server_down));
                break;
            case R.id.left:
                sendCommand(getResources().getString(R.string.server_left));
                break;
            case R.id.right:
                sendCommand(getResources().getString(R.string.server_right));
                break;
            case R.id.space:
                sendCommand(getResources().getString(R.string.server_space));
                break;
            case R.id.f5:
                sendCommand(getResources().getString(R.string.server_f5));
                break;
            case R.id.ctrl_f5:
                sendCommand(getResources().getString(R.string.server_ctrl_f5));
                break;
            case R.id.ctrl_l:
                sendCommand(getResources().getString(R.string.server_ctrl_l));
                break;
            default:
                Log.e(TAG, "Invalid message");
                break;
        }
    }

    protected void onDimScreenClicked(View v) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (screenOff) {
            params.screenBrightness = -1;
            screenOff = false;
        } else {
            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.screenBrightness = 0;
            screenOff = true;
        }
        getWindow().setAttributes(params);
    }

    // Volume keys are mapped to arrows:
    //   volume down -> left arrow
    //   volume up   -> right arrow
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            sendCommand(getResources().getString(R.string.server_left));
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            sendCommand(getResources().getString(R.string.server_right));
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

}
