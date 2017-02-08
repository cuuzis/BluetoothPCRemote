package com.example.gustavs.remotepccontroller;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.gustavs.remotepccontroller.model.Profile;

import java.io.IOException;
import java.io.OutputStream;

public class ConnectionActivity extends AProfileConnecterActivity {

    private static final String TAG = ConnectionActivity.class.getSimpleName();
    private boolean screenDimmed = false;
    public static OutputStream outputStream;
    public static Profile currentProfile;
    private PowerManager.WakeLock mWakeLock; //drains battery


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        Toolbar connectionToolbar = (Toolbar) findViewById(R.id.connection_toolbar);
        setSupportActionBar(connectionToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        else
            throw new AssertionError("getSupportActionBar returned null");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        mWakeLock.acquire();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.connection_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else
            return super.onOptionsItemSelected(item);
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
        } else {
            Log.e(TAG, "Output stream is null");
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mWakeLock.isHeld())
            mWakeLock.acquire();
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
            case R.id.spacebar:
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

    public void onDimScreenClicked(MenuItem item) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (screenDimmed) {
            params.screenBrightness = -1;
            screenDimmed = false;
        } else {
            params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            params.screenBrightness = 0;
            screenDimmed = true;
        }
        getWindow().setAttributes(params);
        item.setChecked(screenDimmed);
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
