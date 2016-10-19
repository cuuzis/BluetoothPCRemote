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
                Console.WriteLine("Connected to: {0}", remoteDevice.RemoteMachineName);
                form.NotifyEstablishedConnection();
                Stream peerStream = remoteDevice.GetStream();

                while (true) {
                    byte[] buf = new byte[1024];
                    int readLen = peerStream.Read(buf, 0, buf.Length);
                    if (readLen == 0) {
                        Console.WriteLine("Connection closed.");
                        form.NotifyLostConnection();
                        BeginAcceptBluetoothClient();
                        break;
                    } else {
                        String readbytes = System.Text.Encoding.UTF8.GetString(buf, 0, readLen);
                        Console.WriteLine("Recevied {0} bytes: {1}", readLen, readbytes);
                        switch (readbytes) {
                            case UP:
                                SendKeys.SendWait("{UP}");
                                break;
                            case DOWN:
                                SendKeys.SendWait("{DOWN}");
                                break;
                            case LEFT:
                                SendKeys.SendWait("{LEFT}");
                                break;
                            case RIGHT:
                                SendKeys.SendWait("{RIGHT}");
                                break;
                            case SPACE:
                                SendKeys.SendWait(" ");
                                break;
                            case F5:
                                SendKeys.SendWait("{F5}");
                                break;
                            case CTRL_F5:
                                SendKeys.SendWait("^{F5}");
                                break;
                            case CTRL_L:
                                SendKeys.SendWait("^l");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }
}
