package com.example.gustavs.remotepccontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.bluetooth.ConnectBluetoothActivity;
import com.example.gustavs.remotepccontroller.barcodereader.BarcodeGraphic;
import com.example.gustavs.remotepccontroller.barcodereader.BarcodeMainActivity;
import com.example.gustavs.remotepccontroller.wifi.ConnectWlanActivity;
import com.example.gustavs.remotepccontroller.wifi.ScanNetwork;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;



//TODO: lost connection on portrait<->landscape change; no connection establishes if bluetooth is not already on; icon
public class MainActivity extends AppCompatActivity {

    private final String SSID = "REMOTE-CONTROLLER";
    private final String KEY  = "unsafepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //ConnectToNetwork(SSID, KEY, this);
    }

    public void connectViaBluetooth(View v) {
        Intent i = new Intent(this, ConnectBluetoothActivity.class);
        startActivity(i);
    }

    public void connectViaWiFiDirect(View v) {
        //ScanNetwork scanNetwork = new ScanNetwork();
        //scanNetwork.FindIPsByHostname("cuzis-pc");
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();

        //ScanNetwork scanNetwork = new ScanNetwork();
        //scanNetwork.doScan(this);
        //Intent i = new Intent(this, ConnectWiFiDirectActivity.class);
        //startActivity(i);
    }

    public void connectViaWlan(View v) {
        Intent intent = new Intent(this, ConnectWlanActivity.class);
        /*Bundle bundle = new Bundle();
        bundle.putString("SERVER_NAME", "cuzis-pc");
        intent.putExtras(bundle);*/
        startActivity(intent);
    }

    public void scanQRCode(View v) {
        Intent i = new Intent(this, BarcodeMainActivity.class);
        startActivity(i);
    }
}
