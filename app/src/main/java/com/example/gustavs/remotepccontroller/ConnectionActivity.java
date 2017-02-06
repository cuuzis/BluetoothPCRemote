package com.example.gustavs.remotepccontroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.gustavs.remotepccontroller.model.Profile;

import java.io.IOException;
import java.io.OutputStream;

public class ConnectionActivity extends AProfileConnecterActivity {

    private static final String TAG = ConnectionActivity.class.getSimpleName();
    private boolean screenOff = false;
    public static OutputStream outputStream;
    public static Profile currentProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    private void write(String message) {
        if (outputStream != null) {
            byte[] msg = message.getBytes();
            try {
                outputStream.write(msg);
            } catch (IOException e) {
                Log.e(TAG, "Write exception", e);
                outputStream = null;
                // Try to reconnect
                connect(currentProfile);
            }
        } else
            Log.e(TAG, "Output stream is null");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing stream");
            }
            outputStream = null;
        }
    }

    //region buttons
    // Buttons are mapped to command strings by their IDs
    public void onButtonDown(View v) {
        switch (v.getId()) {
            case R.id.up:
                write(getResources().getString(R.string.server_up));
                break;
            case R.id.down:
                write(getResources().getString(R.string.server_down));
                break;
            case R.id.left:
                write(getResources().getString(R.string.server_left));
                break;
            case R.id.right:
                write(getResources().getString(R.string.server_right));
                break;
            case R.id.space:
                write(getResources().getString(R.string.server_space));
                break;
            case R.id.f5:
                write(getResources().getString(R.string.server_f5));
                break;
            case R.id.ctrl_f5:
                write(getResources().getString(R.string.server_ctrl_f5));
                break;
            case R.id.ctrl_l:
                write(getResources().getString(R.string.server_ctrl_l));
                break;
            default:
                Log.e(TAG, "Invalid message");
                break;
        }
    }

    public void onDimScreenClicked(View v) {
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
            write(getResources().getString(R.string.server_left));
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            write(getResources().getString(R.string.server_right));
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
    //endregion buttons

}
