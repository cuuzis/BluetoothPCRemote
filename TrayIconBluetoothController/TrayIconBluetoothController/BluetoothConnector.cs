using System;
using InTheHand.Net.Sockets;
using System.IO;
using System.Windows.Forms;

namespace TrayIconBluetoothController
{
    public class BluetoothConnector
    {
        // Constants used in server and client
        private static Guid GUID = new Guid("{d07c0736-07b9-4ec5-b876-53647c4d047b}");
        private const string UP       = "UP";
        private const string DOWN     = "DOWN";
        private const string LEFT     = "LEFT";
        private const string RIGHT    = "RIGHT";
        private const string SPACE    = "SPACE";
        private const string F5       = "F5";
        private const string CTRL_F5  = "CTRL_F5";
        private const string CTRL_L   = "CTRL_L";

        private static BluetoothListener bluetoothListener = new BluetoothListener(GUID);
        private readonly Form1 form;

        public BluetoothConnector(Form1 form) {
            this.form = form;
            Console.WriteLine("Initializing BT...");
            bluetoothListener.Start();
            BeginAcceptBluetoothClient();

            //Address:"44D4E076D15E", Name:"Xperia Zirgs"
            // Discover all devices
            /*var cli = new BluetoothClient();
            BluetoothDeviceInfo[] peers = cli.DiscoverDevices();
            foreach (BluetoothDeviceInfo peer in peers) {
                Console.WriteLine("Address:\"{0}\", Name:\"{1}\"", peer.DeviceAddress, peer.DeviceName);
            }*/
        }

        private void BeginAcceptBluetoothClient() {
            Console.WriteLine("Listening for BT clients...");
            bluetoothListener.BeginAcceptBluetoothClient(new AsyncCallback(AcceptConnection), bluetoothListener);
        }

        private void AcceptConnection(IAsyncResult result) {
            if (result.IsCompleted) {
                BluetoothClient remoteDevice = ((BluetoothListener)result.AsyncState).EndAcceptBluetoothClient(result);
                using (Stream peerStream = remoteDevice.GetStream()) {
                    form.NotifyEstablishedConnection();
                    Console.WriteLine("Bluetooth connected to: {0}", remoteDevice.RemoteMachineName);
                    VirtualKeyboard.readWhileOpen(peerStream);
                }
                Console.WriteLine("Bluetooth connection closed.");
                form.NotifyLostConnection();
                BeginAcceptBluetoothClient();
            }
        }
    }
}
