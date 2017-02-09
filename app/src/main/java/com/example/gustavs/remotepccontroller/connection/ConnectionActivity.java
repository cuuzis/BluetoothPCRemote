package com.example.gustavs.remotepccontroller.connection;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.gustavs.remotepccontroller.R;
import com.example.gustavs.remotepccontroller.profile.Profile;

import java.io.IOException;
import java.io.OutputStream;

public class ConnectionActivity extends AProfileConnecterActivity {

    private static final String TAG = ConnectionActivity.class.getSimpleName();
    private boolean screenDimmed = false;
    public static OutputStream outputStream;
    public static Profile currentProfile;
    private PowerManager.WakeLock mWakeLock; //drains battery
    private FrameLayout mMainLayout;
    private View mPresentationView;
    private View mMediaView;
    private boolean isPresentationLayout = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mPresentationView = LayoutInflater.from(this).inflate(R.layout.fragment_presentation, null, false);
        mMainLayout = (FrameLayout) findViewById(R.id.connection_activity);
        mMainLayout.addView(mPresentationView);

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
        menu.findItem(R.id.action_presentation_layout).setChecked(true);
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

    private void write(WindowsKey keyPress) {
        write(WindowsKey.EMPTY, keyPress);
    }

    private void write(WindowsKey keyDown, WindowsKey keyPress) {
        if (outputStream != null) {
            try {
                byte keys[] = new byte[] {keyDown.keyCode, keyPress.keyCode};
                Log.v(TAG, "Sent bytes: " + keys[0] + ", " + keys[1]);
                outputStream.write(keys);
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
        if (mWakeLock.isHeld())
            mWakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mWakeLock.isHeld() && isPresentationLayout)
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

    public void setPresentationLayout(MenuItem item) {
        if (!isPresentationLayout) {
            mMainLayout.removeView(mMediaView);
            mMainLayout.addView(mPresentationView);
            item.setChecked(true);
            if (!mWakeLock.isHeld())
                mWakeLock.acquire();
            isPresentationLayout = true;
        }
    }

    public void setMediaLayout(MenuItem item) {
        if (isPresentationLayout) {
            if (mMediaView == null)
                mMediaView = LayoutInflater.from(this).inflate(R.layout.fragment_media, null, false);
            mMainLayout.removeView(mPresentationView);
            mMainLayout.addView(mMediaView);
            item.setChecked(true);
            if (mWakeLock.isHeld())
                mWakeLock.release();
            isPresentationLayout = false;
        }
    }

    //region key mapping
    //
    // Buttons are mapped to command bytes by their IDs
    public void onButtonDown(View v) {
        switch (v.getId()) {
            case R.id.up:
                write(WindowsKey.UP);
                break;
            case R.id.down:
                write(WindowsKey.DOWN);
                break;
            case R.id.left:
                write(WindowsKey.LEFT);
                break;
            case R.id.right:
                write(WindowsKey.RIGHT);
                break;
            case R.id.spacebar:
                write(WindowsKey.SPACE);
                break;
            case R.id.f5:
                write(WindowsKey.F5);
                break;
            case R.id.ctrl_f5:
                write(WindowsKey.CONTROL, WindowsKey.F5);
                break;
            case R.id.ctrl_l:
                write(WindowsKey.CONTROL, WindowsKey.VK_L);
                break;
            case R.id.media_pause:
                write(WindowsKey.MEDIA_PLAY_PAUSE);
                break;
            case R.id.media_next:
                write(WindowsKey.MEDIA_NEXT_TRACK);
                break;
            case R.id.media_previous:
                write(WindowsKey.MEDIA_PREV_TRACK);
                break;
            case R.id.volume_mute:
                write(WindowsKey.VOLUME_MUTE);
                break;
            case R.id.volume_up:
                write(WindowsKey.VOLUME_UP);
                break;
            case R.id.volume_down:
                write(WindowsKey.VOLUME_DOWN);
                break;
            default:
                Log.e(TAG, "Invalid key: " + v.toString());
                break;
        }
    }
    // Volume keys are mapped to arrows:
    //   volume down -> left arrow
    //   volume up   -> right arrow
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            write(WindowsKey.LEFT);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            write(WindowsKey.RIGHT);
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
    //
    //endregion key mapping
}
