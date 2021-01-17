using System;
using System.Net;
using System.Net.Sockets;
using System.Net.NetworkInformation;
using System.Collections.Generic;
using System.IO;

namespace TrayIconBluetoothController
{

    public class WlanConnector
    {

        private TcpListener myListener;

        IDictionary<string, IPAddress> ipAddressesByName = new Dictionary<string, IPAddress>();
        private List<NetworkInterface> availableNetworkInterfaces = new List<NetworkInterface>();

        public Action<State> onStateChanged { get; internal set; }

        private List<NetworkInterface> getAvailableNetworkInterfaces()
        {
            if (availableNetworkInterfaces.Count == 0)
            {
                NetworkInterfaceType eth = NetworkInterfaceType.Ethernet;
                NetworkInterfaceType wifi = NetworkInterfaceType.Wireless80211;
                foreach (NetworkInterface interf in NetworkInterface.GetAllNetworkInterfaces())
                {
                    if ((interf.NetworkInterfaceType == eth || interf.NetworkInterfaceType == wifi) && (interf.OperationalStatus == OperationalStatus.Up))
                    {
                        availableNetworkInterfaces.Add(interf);
                    }
                }
            }
            return availableNetworkInterfaces;
        }

        public List<string> getAvailableNetworkNames()
        {
            foreach (NetworkInterface interf in getAvailableNetworkInterfaces())
            {
                foreach (UnicastIPAddressInformation ip in interf.GetIPProperties().UnicastAddresses)
                {
                    if (ip.Address.AddressFamily == AddressFamily.InterNetwork)
                    {
                        //&& (interf.Name.StartsWith("Ethernet") || interf.Name.StartsWith("Wi-Fi") || interf.Name.StartsWith("Local Area Connection")))
                        // result.Add(ip.Address.ToString() + " (" + interf.Name + ")");
                        string key = ip.Address.ToString() + " (" + interf.Name + ")";
                        ipAddressesByName.Add(key, ip.Address);
                    }
                }
            }
            return new List<string>(ipAddressesByName.Keys);
        }


        public bool beginAcceptWlanConnector(string ipAddressString) {
            try
            {
                IPAddress ipAddress = ipAddressesByName[ipAddressString];
                myListener = new TcpListener(ipAddress, Properties.Settings.Default.port);
                myListener.Start();
                myListener.BeginAcceptSocket(AcceptConnection, myListener);
                Console.WriteLine("Listening for Wlan clients at endpoint: " + myListener.LocalEndpoint);
                onStateChanged.Invoke(State.Listening);
                return true;
            } catch (Exception e)
            {
                Console.Error.WriteLine("Could not create socket", e);
                return false;
            }
        }

        private void AcceptConnection(IAsyncResult result) {
            if (result.IsCompleted)
            {
                TcpListener tcpListener = (TcpListener)result.AsyncState;
                if (tcpListener.Server.IsBound)
                {
                    TcpClient remoteDevice = ((TcpListener)result.AsyncState).EndAcceptTcpClient(result);
                    using (Stream peerStream = remoteDevice.GetStream())
                    {
                        Console.WriteLine("Wlan connected to: {0}", remoteDevice.Client.LocalEndPoint);
                        VirtualKeyboard.readWhileOpen(peerStream);
                    }
                }
            }
        }

        internal void Stop()
        {
            if (myListener != null)
            {
                myListener.Stop();
            }
            onStateChanged.Invoke(State.Stopped);
        }
    }
}
