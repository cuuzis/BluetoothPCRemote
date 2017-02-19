using System;
using InTheHand.Net.Sockets;
using System.IO;
using static TrayIconBluetoothController.MainForm;

namespace TrayIconBluetoothController
{
    public class BluetoothConnector
    {
        // Constants used in server and client
        private static Guid GUID = new Guid("{d07c0736-07b9-4ec5-b876-53647c4d047b}");

        private static BluetoothListener bluetoothListener = new BluetoothListener(GUID);
        private readonly MainForm form;
        private bool isStopped = false;
        private Stream activeStream;

        public BluetoothConnector(MainForm form) {
            this.form = form;
            bluetoothListener.Start();
            BeginAcceptBluetoothClient();
        }

        public void stop() {
            isStopped = true;
            bluetoothListener.Stop();
            if (activeStream != null)
                activeStream.Close();
        }

        private void BeginAcceptBluetoothClient() {
            Console.WriteLine("Listening for BT clients...");
            bluetoothListener.BeginAcceptBluetoothClient(new AsyncCallback(AcceptConnection), bluetoothListener);
        }

        private void AcceptConnection(IAsyncResult result) {
            if (result.IsCompleted) {
                if (isStopped)
                    Console.WriteLine("Bluetooth listening cancelled");
                else {
                    BluetoothClient remoteDevice = ((BluetoothListener)result.AsyncState).EndAcceptBluetoothClient(result);
                    using (Stream peerStream = remoteDevice.GetStream()) {
                        //form.setBluetoothConnected(true);
                        SetConnectedDelegate dt = new SetConnectedDelegate(form.setBluetoothConnected);
                        form.Invoke(dt, true);
                        Console.WriteLine("Bluetooth connected to: {0}", remoteDevice.RemoteMachineName);
                        this.activeStream = peerStream;
                        VirtualKeyboard.readWhileOpen(peerStream);
                    }
                    Console.WriteLine("Bluetooth connection closed.");
                    //form.setBluetoothConnected(false);
                    if (!isStopped) {
                        SetConnectedDelegate df = new SetConnectedDelegate(form.setBluetoothConnected);
                        form.Invoke(df, false);
                        BeginAcceptBluetoothClient();
                    }
                }
            }
        }
    }
}
