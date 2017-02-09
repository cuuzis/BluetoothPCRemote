using System;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net.NetworkInformation;
using System.Collections.Generic;
using WindowsInput;
using System.IO;

namespace TrayIconBluetoothController
{

    public class WlanConnector
    {
        private readonly Form1 form;
        //private readonly string IP_ADDRESS;// = "192.168.137.1";//"172.24.20.133";
        private readonly int PORT = 8001;


        private const string UP = "UP";
        private const string DOWN = "DOWN";
        private const string LEFT = "LEFT";
        private const string RIGHT = "RIGHT";
        private const string SPACE = "SPACE";
        private const string F5 = "F5";
        private const string CTRL_F5 = "CTRL_F5";
        private const string CTRL_L = "CTRL_L";


        public WlanConnector(Form1 form1) {
            this.form = form1;
            Console.WriteLine("Initializing WLAN...");
            List<string> ipAdresses = GetLocalIpAddresses();

            // TODO: Async foreach, that would cancel connection bradcast after creating a connection
            // Documentation of Parallel: operations MAY run in parallel
            Parallel.ForEach(ipAdresses, ipAddr => {
                BeginAcceptWlanConnector(ipAddr);
            });
            //DiagnoseNetwork();
    }



        #region getting IPV4 host addresses
        private List<string> GetLocalIpAddresses()
        {
            List<string> result = new List<string>();
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
                            result.Add(ip.Address.ToString());
            return result;
        }

        private void DiagnoseNetwork() {
            Console.WriteLine("Ethernet:");
            GetLocalIPv4(NetworkInterfaceType.Ethernet);
            Console.WriteLine("Wireless:");
            GetLocalIPv4(NetworkInterfaceType.Wireless80211);
            Console.WriteLine("Method 2, all together:");
            GetLocalIPAddress();
        }

        public void GetLocalIPv4(NetworkInterfaceType _type) {
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

        private void BeginAcceptWlanConnector(string ip) {
            IPAddress ipAddr = IPAddress.Parse(ip);
            TcpListener myListener = new TcpListener(ipAddr, PORT);
            myListener.Start(); // Start Listeneting

            Console.WriteLine("Listening for Wlan clients at endpoint: " + myListener.LocalEndpoint);
            AcceptConnection(myListener, ip);
        }

        private void AcceptConnection(TcpListener myListener, string ip) {
            using (Socket peerSocket = myListener.AcceptSocket()) {
                using (Stream peerStream = new NetworkStream(peerSocket)) {
                    form.NotifyEstablishedConnection();
                    Console.WriteLine("Endpoint {0} connected to: {1}", myListener.LocalEndpoint, peerSocket.RemoteEndPoint);
                    VirtualKeyboard.readWhileOpen(peerStream);
                }
                Console.WriteLine("Wlan connection closed.");
                form.NotifyLostConnection();
                myListener.Stop();
            }
            BeginAcceptWlanConnector(ip);
        }
    }
}
