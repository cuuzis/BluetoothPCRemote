package com.example.gustavs.remotepccontroller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.connection.AProfileConnecterActivity;
import com.example.gustavs.remotepccontroller.profile.Profile;
import com.example.gustavs.remotepccontroller.profile.ProfileActivity;
import com.example.gustavs.remotepccontroller.profile.ProfileDataDbHelper;

import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry._ID;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_BLUETOOTHNAME;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.profile.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;


public class MainActivity extends AProfileConnecterActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PROFILE_ID = "ProfileID";
    private ProfileCursorAdapter mProfileAdapter;
    private ProfileDataDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        mDbHelper = new ProfileDataDbHelper(this);
        // bind profile data to listView
        mProfileAdapter = new ProfileCursorAdapter(this, mDbHelper.queryAllProfiles());
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(mProfileAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        mProfileAdapter.changeCursor(mDbHelper.queryAllProfiles());
        mProfileAdapter.notifyDataSetChanged();
    }

    private void editProfile(int profileId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(PROFILE_ID, profileId);
        startActivity(intent);
    }

    public void addProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void deleteProfile(final long profileId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(R.string.confirm_delete_profile);
        alertDialogBuilder
                //.setMessage("All data will be lost!")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "Deleting profile...");
                        mDbHelper.deleteProfile(profileId);
                        mProfileAdapter.changeCursor(mDbHelper.queryAllProfiles());
                        mProfileAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, R.string.profile_deleted, Toast.LENGTH_SHORT).show(); //TODO: Snackbar with undo
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void connectProfile(int profileId) {
        Profile profile = new Profile(this, profileId);
        connect(profile);
    }

    public class ProfileCursorAdapter extends CursorAdapter {
        private ProfileCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.main_activity_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Profile name and information
            final int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            final String wlanName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANNAME));
            final int wlanPort = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANPORT));
            final String bluetoothName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BLUETOOTHNAME));
            //final int firstPriority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_FIRST_PRIORITY));
            //final int secondPriority = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_SECOND_PRIORITY));

            ((TextView)view.findViewById(R.id.listitem_title)).setText(wlanName + ":" + wlanPort);
            ((TextView)view.findViewById(R.id.listitem_subtitle)).setText(bluetoothName);

            // Short click actions
            View.OnClickListener mOnItemIconClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    connectProfile(id);
                }
            };
            View.OnClickListener mOnItemTextClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editProfile(id);
                }
            };
            view.findViewById(R.id.listitem_connect).setOnClickListener(mOnItemIconClick);
            view.findViewById(R.id.listitem_title).setOnClickListener(mOnItemTextClick);
            view.findViewById(R.id.listitem_subtitle).setOnClickListener(mOnItemTextClick);

            // Long click actions
            View.OnLongClickListener mOnItemLongClick = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    deleteProfile(id);
                    return false;
                }
            };
            view.findViewById(R.id.listitem_connect).setOnLongClickListener(mOnItemLongClick);
            view.findViewById(R.id.listitem_title).setOnLongClickListener(mOnItemLongClick);
            view.findViewById(R.id.listitem_subtitle).setOnLongClickListener(mOnItemLongClick);
        }
    }

}
