package com.example.gustavs.remotepccontroller.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gustavs.remotepccontroller.MainActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScanNetwork {
    private static final int NB_THREADS = 10;
    private final String SSID = "REMOTE-CONTROLLER";
    private final String KEY  = "unsafepassword";
    private Activity mainActivity;

    public void doScan(Activity mActivity) {
        this.mainActivity = mActivity;

        System.out.println("Starting subnet scan");
        ExecutorService executor = Executors.newFixedThreadPool(NB_THREADS);
        for(int dest=0; dest<255; dest++) {
            //String host = "172.24.20." + dest;
            String host = "10.7.153.161";
            executor.execute(pingRunnable(host));
        }

        System.out.println("Waiting for executor to terminate...");
        executor.shutdown();
        try { executor.awaitTermination(60*1000, TimeUnit.MILLISECONDS); } catch (InterruptedException ignored) { }

        System.out.println("Scan finished");
    }

    private void GetLocalIp() {
        System.out.println("Local IP:");

        // Only works when NOT tethering
        WifiManager wifi = (WifiManager) mainActivity.getBaseContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if (dhcp == null) {
            System.out.println("No DHCPInfo on WiFi side.");
            return;
        }
        String ipAddr = intToIp(dhcp.ipAddress);
        String netmask = intToIp(dhcp.netmask);
        System.out.println("IP: " + ipAddr);
        System.out.println("Netmask: " + netmask);
    }

    public String intToIp(int i) {

        String result = ((i >> 24 ) & 0xFF ) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ( i & 0xFF) ;
        return reverseStringByWords(result);
    }

    public static String reverseStringByWords(String string) {
        //return string;
        String result = "";
        String dot = "";
        String[] words = string.split("[.]");
        for (String w: words) {
            result = w + dot + result;
            dot = ".";
        };
        return result;
        //return  Arrays.toString(words);//.replace(", ", ".").replaceAll("[\\[\\]]", "");
    }

    private Runnable pingRunnable(final String host) {
        return new Runnable() {
            public void run() {
                System.out.println("Pinging " + host + "...");
                try {
                    InetAddress inet = InetAddress.getByName(host);
                    boolean reachable = inet.isReachable(1000);
                    System.out.println("=> Result: " + (reachable ? "reachable" : "not reachable"));
                } catch (UnknownHostException e) {
                    System.out.println("Not found:" + e);
                } catch (IOException e) {
                    System.out.println("IO Error:" + e);
                }
            }
        };
    }

    //region Find IPs by hostname

    private static class FindNetwork extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String hostname = strings[0];
            System.out.println("Hostname: " + hostname);
            hostname = "cuzis-pc";
            InetAddress[] addresses = new InetAddress[0];
            try {
                addresses = InetAddress.getAllByName(hostname);
            } catch (UnknownHostException e) {
                System.out.println("Unknown host exception");
                e.printStackTrace();
            }
            for (InetAddress address : addresses) {
                System.out.println("Address found: "+ address);
            }
            return null;
        }
    }

    public static void FindIPsByHostname(String hostname) {
        FindNetwork findNetwork = new FindNetwork();
        findNetwork.execute(hostname);
    }

    //endregion


    //region Connecting to a wifi AP
    // TODO: Make this seperated

    public static void ConnectToNetwork(String ssid, String key, Activity activity) {
        //First register broadcast receiver
        RegisterBroadcastReceiver(activity);


        //WPA2 connection:
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + ssid + "\"";
        conf.preSharedKey = "\""+ key +"\"";

        WifiManager wifiManager = (WifiManager)activity.getBaseContext().getSystemService(Context.WIFI_SERVICE);
        int netId = wifiManager.addNetwork(conf);
        if(!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
        System.out.println("Reconnecting WiFi...");
        // TODO: needs to wait until phone is connected to wifi before scan; should also scan if AP is in range with wifiManager.getScanResults
    }

    private static void RegisterBroadcastReceiver(final Activity activity) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Network changed", Toast.LENGTH_SHORT).show();
                WifiManager wifiManager = (WifiManager)activity.getBaseContext().getSystemService(Context.WIFI_SERVICE);
                System.out.println("Connected @ " + wifiManager.getConnectionInfo().getSSID());
                // TODO: Find Server
            }
        }, intentFilter);
    }

    /*private static class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Network changed", Toast.LENGTH_SHORT).show();
            WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
            System.out.println("Connected @ " + wifiManager.getConnectionInfo().getSSID());
        }
    }*/

    //endregion
}
