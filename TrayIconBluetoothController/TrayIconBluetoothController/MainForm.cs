using InTheHand.Net.Bluetooth;
using System;
using System.Collections.Generic;
using System.Net;
using System.Threading;
using System.Windows.Forms;

namespace TrayIconBluetoothController
{
    public partial class MainForm : Form
    {
        public bool isBluetoothRunning { get; private set; }
        public bool isWlanRunning { get; private set; }

        private BluetoothConnector btConnector;
        private List<WlanConnector> wlanConnectors = new List<WlanConnector>();


        public MainForm() {
            InitializeComponent();

            string wlanName = Environment.MachineName;
            string port = Properties.Settings.Default.port.ToString();
            string btName;
            this.tbWlanName.Text = wlanName;
            this.tbPort.Text = port;
            if (BluetoothRadio.IsSupported) {
                btName = BluetoothRadio.PrimaryRadio.Name;
                this.tbBluetoothName.Text = btName;
            }
            else {
                btName = "";
                this.tbBluetoothName.Text = "Not available";
            }
            string DL = ";"; // delimiter is used in clent
            string codeMessage = wlanName + DL + port + DL + btName;
            this.qrCodePicturebox.Image = QRCodeGenerator.GenerateQRCode(codeMessage, 240);
            this.qrCodePicturebox.Refresh();
        }

        private void Form1_Move(object sender, EventArgs e) {
            if (this.WindowState == FormWindowState.Minimized) {
                this.Hide();
            }
        }

        private void showToolStripMenuItem_Click(object sender, EventArgs e) {
            this.Show();
            this.Activate();
        }

        public delegate void SetConnectedDelegate(bool b);

        public void setBluetoothConnected(bool isConnected) {
            if (isConnected) {
                this.lblBtStatus.ForeColor = System.Drawing.Color.DarkGreen;
                this.lblBtStatus.Text = "Connected";
            } else {
                this.lblBtStatus.ForeColor = System.Drawing.Color.DarkOrange;
                this.lblBtStatus.Text = "Listening...";
            }
        }

        public void setWlanConnected(bool isConnected) {
            if (isConnected) {
                this.lblWlanStatus.ForeColor = System.Drawing.Color.DarkGreen;
                this.lblWlanStatus.Text = "Connected";
            } else {
                this.lblWlanStatus.ForeColor = System.Drawing.Color.DarkOrange;
                this.lblWlanStatus.Text = "Listening...";
            }
        }


        private void hideToolStripMenuItem_Click(object sender, EventArgs e) {
            Application.Exit();
        }

        private void notifyIcon1_DoubleClick(object sender, EventArgs e) {
            this.Show();
        }

        /*public void NotifyLostConnection() {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.red")));
        }*/

        /*public void NotifyEstablishedConnection() {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.green")));
        }*/

        private void beginListenWlan() {
            isWlanRunning = true;
            this.lblWlanStatus.ForeColor = System.Drawing.Color.DarkOrange;
            this.lblWlanStatus.Text = "Listening...";
            this.btnStartWlan.Text = "Stop Wlan";
            this.btnStartWlan.Image = Properties.Resources.ic_stop_black_24dp_1x;
            List<IPAddress> ipAdresses = WlanConnector.GetLocalIpAddresses();
            ipAdresses.ForEach(ipAddr => {
                new Thread(() =>
                {
                    Thread.CurrentThread.IsBackground = true;
                    wlanConnectors.Add(new WlanConnector(this, ipAddr));
                }).Start();
            });
        }

        private void endListenWlan() {
            isWlanRunning = false;
            wlanConnectors.ForEach(wlanConn => {
                wlanConn.stop();
            });
            this.lblWlanStatus.ForeColor = System.Drawing.Color.Maroon;
            this.lblWlanStatus.Text = "Stopped";
            this.btnStartWlan.Text = "Start Wlan";
            this.btnStartWlan.Image = Properties.Resources.ic_play_arrow_black_24dp_1x;
        }

        private void beginListenBluetooth() {
            if (BluetoothRadio.IsSupported) {
                isBluetoothRunning = true;
                this.lblBtStatus.ForeColor = System.Drawing.Color.DarkOrange;
                this.lblBtStatus.Text = "Listening...";
                this.btnStartBluetooth.Text = "Stop Bluetooth";
                this.btnStartBluetooth.Image = Properties.Resources.ic_stop_black_24dp_1x;
                new Thread(() => {
                    Thread.CurrentThread.IsBackground = true;
                    btConnector = new BluetoothConnector(this);
                }).Start();
            }
        }

        private void endListenBluetooth() {
            btConnector.stop();
            isBluetoothRunning = false;
            this.lblBtStatus.ForeColor = System.Drawing.Color.Maroon;
            this.lblBtStatus.Text = "Stopped";
            this.btnStartBluetooth.Text = "Start Bluetooth";
            this.btnStartBluetooth.Image = Properties.Resources.ic_play_arrow_black_24dp_1x;
        }

        private void btnStartAll_Click(object sender, EventArgs e) {
            if (!isBluetoothRunning)
                beginListenBluetooth();
            if (!isWlanRunning)
                beginListenWlan();
        }

        private void btnStartWlan_Click(object sender, EventArgs e) {
            if (isWlanRunning) {
                endListenWlan();
            } else {
                beginListenWlan();
            }
        }

        private void btnStartBluetooth_Click(object sender, EventArgs e) {
            if (isBluetoothRunning) {
                endListenBluetooth();
            } else {
                beginListenBluetooth();
            }
        }
    }
}
