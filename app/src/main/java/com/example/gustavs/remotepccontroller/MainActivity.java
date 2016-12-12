package com.example.gustavs.remotepccontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private final String SSID = "REMOTE-CONTROLLER";
    private final String KEY  = "unsafepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String myStringArray[] = {"My favourite PC", "KITCHEN-PC", "Windows 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.main_activity_item, R.id.text1, myStringArray){
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, myStringArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View result = super.getView(position, convertView, parent);
                //System.out.println("position: " + position);
                //System.out.println("result: " + ((TextView)result.findViewById(R.id.text1)).getText());
                //((ImageView)result.findViewById(android.R.id.icon)).setImageIcon((Icon)findViewById(R.drawable.ic_bluetooth_black_24dp));
                //((ImageView)result.findViewById(android.R.id.icon)).setImageResource(R.drawable.ic_bluetooth_black_24dp);
                //android.R.drawable.btn_plus
                return result;
            }
        };


        ListViewCompat listView = (ListViewCompat) findViewById(R.id.list_view_compat);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(MainActivity.this, "Item " + i +" clicked", Toast.LENGTH_SHORT).show();
                    }
                }
        );*/

        //ConnectToNetwork(SSID, KEY, this);
    }



    public void addProfile(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }



}
