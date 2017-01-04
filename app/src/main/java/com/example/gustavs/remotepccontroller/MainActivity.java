package com.example.gustavs.remotepccontroller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.model.ProfileDataDbHelper;

import static android.provider.BaseColumns._ID;
import static com.example.gustavs.remotepccontroller.ProfileActivity.PROFILE_ID;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.ALL_COLUMNS;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_BLUETOOTHNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANNAME;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.COLUMN_NAME_WLANPORT;
import static com.example.gustavs.remotepccontroller.model.ProfileData.ProfileEntry.TABLE_NAME;


//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProfileCursorAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        // bind data to listView
        Cursor profileCursor = queryAllProfiles();
        profileAdapter = new ProfileCursorAdapter(this, profileCursor);
        ListViewCompat listView = (ListViewCompat) findViewById(R.id.list_view_compat);
        listView.setAdapter(profileAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                editProfile(id);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                deleteProfile(id);
                return true;
            }
        });
    }

    private Cursor queryAllProfiles() {
        ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        profileAdapter.changeCursor(queryAllProfiles());
        profileAdapter.notifyDataSetChanged();
    }

    private void editProfile(long profileId) {
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
                        Log.d(TAG, "Deleting profile"+profileId);
                        ProfileDataDbHelper mDbHelper = new ProfileDataDbHelper(MainActivity.this);
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        db.delete(TABLE_NAME, _ID+" = "+profileId, null);
                        profileAdapter.changeCursor(queryAllProfiles());
                        profileAdapter.notifyDataSetChanged();
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
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANNAME))
                    + ":" + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_WLANPORT));
            ((TextView)view.findViewById(R.id.title)).setText(title);
            String subtitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_BLUETOOTHNAME));
            ((TextView)view.findViewById(R.id.subtitle)).setText(subtitle);
        }

    }

}
