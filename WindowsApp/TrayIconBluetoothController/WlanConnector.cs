using System;
using System.Net;
using System.Net.Sockets;
using System.Net.NetworkInformation;
using System.Collections.Generic;
using System.IO;
using static TrayIconBluetoothController.MainForm;

namespace TrayIconBluetoothController
{

    public class WlanConnector
    {
        private readonly MainForm form;

        private bool isStopped = false;
        TcpListener myListener;
        private Stream activeStream;

        public WlanConnector(MainForm form1, IPAddress ipAddr) {
            this.form = form1;
            myListener = new TcpListener(ipAddr, Properties.Settings.Default.port);
            myListener.Start();
            BeginAcceptWlanConnector();
        }



        #region getting IPV4 host addresses
        public static List<IPAddress> GetLocalIpAddresses()
        {
            List<IPAddress> result = new List<IPAddress>();
            NetworkInterfaceType eth = NetworkInterfaceType.Ethernet;
            NetworkInterfaceType wifi = NetworkInterfaceType.Wireless80211;
            foreach (NetworkInterface interf in NetworkInterface.GetAllNetworkInterfaces())
                if ( (interf.NetworkInterfaceType == eth || interf.NetworkInterfaceType == wifi)
                    && (interf.OperationalStatus == OperationalStatus.Up) )
                    foreach (UnicastIPAddressInformation ip in interf.GetIPProperties().UnicastAddresses)
                        if (ip.Address.AddressFamily == AddressFamily.InterNetwork
                            //&& (interf.Name == "Ethernet" || interf.Name == "Wi-Fi"))
                            //&& (interf.Name == "Wi-Fi"))
                            && (interf.Name == "Ethernet" || interf.Name == "Wi-Fi" || interf.Name.StartsWith("Local Area Connection")))
                            result.Add(ip.Address);
            return result;
        }

        private static void DiagnoseNetwork() {
            Console.WriteLine("Ethernet:");
            GetLocalIPv4(NetworkInterfaceType.Ethernet);
            Console.WriteLine("Wireless:");
            GetLocalIPv4(NetworkInterfaceType.Wireless80211);
            Console.WriteLine("Method 2, all together:");
            GetLocalIPAddress();
        }

        public static void GetLocalIPv4(NetworkInterfaceType _type) {
            foreach (NetworkInterface item in NetworkInterface.GetAllNetworkInterfaces()) {
                if (item.NetworkInterfaceType == _type && item.OperationalStatus == OperationalStatus.Up) {
                    foreach (UnicastIPAddressInformation ip in item.GetIPProperties().UnicastAddresses) {
                        if (ip.Address.AddressFamily == AddressFamily.InterNetwork) {
                            Console.WriteLine(ip.Address.ToString() + " @ " + item.Name); // "Ethernet" || "Wi-Fi"
                        }
                    }
                }
            }
        }

        public static void GetLocalIPAddress() {
            var host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (var ip in host.AddressList) {
                if (ip.AddressFamily == AddressFamily.InterNetwork) {
                    Console.WriteLine(ip.ToString());
                }
            }
        }
        #endregion

        private void BeginAcceptWlanConnector() {
            Console.WriteLine("Listening for Wlan clients at endpoint: " + myListener.LocalEndpoint);
            myListener.BeginAcceptSocket(AcceptConnection, myListener);
        }

        public void stop() {
            isStopped = true;
            myListener.Stop();
            if (activeStream != null)
                activeStream.Close();
        }

        private void AcceptConnection(IAsyncResult result) {
            if (result.IsCompleted) {
                if (isStopped)
                    Console.WriteLine("Wlan listening cancelled");
                else {
                    TcpClient remoteDevice = ((TcpListener)result.AsyncState).EndAcceptTcpClient(result);
                    using (Stream peerStream = remoteDevice.GetStream()) {
                        //form.setWlanConnected(true);
                        SetConnectedDelegate dt = new SetConnectedDelegate(form.setWlanConnected);
                        form.Invoke(dt, true);
                        Console.WriteLine("Wlan connected to: {0}", remoteDevice.Client.LocalEndPoint);
                        this.activeStream = peerStream;
                        VirtualKeyboard.readWhileOpen(peerStream);
                    }
                    Console.WriteLine("Wlan connection closed.");
                    //form.setWlanConnected(false);
                    if (!isStopped) {
                        SetConnectedDelegate df = new SetConnectedDelegate(form.setWlanConnected);
                        form.Invoke(df, false);
                        BeginAcceptWlanConnector();
                    }
                }
            }
        }
    }
}
