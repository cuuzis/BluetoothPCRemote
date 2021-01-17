using InTheHand.Net.Bluetooth;
using System;
using System.Collections.Generic;
using System.Net;
using System.Threading;
using System.Windows.Forms;
using TrayIconBluetoothController.src.Forms;

namespace TrayIconBluetoothController
{
    public partial class MainForm : Form
    {
        public bool isBluetoothRunning { get; private set; }
        public bool isWlanRunning { get; private set; }

        public State serverState = State.Stopped;

        //private BluetoothConnector btConnector;
        //private List<WlanConnector> wlanConnectors = new List<WlanConnector>();
        private WlanConnector wlanConnector = new WlanConnector();

        public MainForm() {
            InitializeComponent();
            listBoxWirelessInterfaces.DataSource = wlanConnector.getAvailableNetworkNames();
            // bind connection state to form
            wlanConnector.onStateChanged = updateServerState;






            //string wlanName = Environment.MachineName;
            //string port = Properties.Settings.Default.port.ToString();
            //string btName;
            //tbWlanName.Text = wlanName;
            //tbPort.Text = port;
            //if (BluetoothRadio.IsSupported) {
            //    btName = BluetoothRadio.PrimaryRadio.Name;
            //    tbBluetoothName.Text = btName;
            //}
            //else {
            //    btName = "";
            //    tbBluetoothName.Text = "Not available";
            //}
        }

        public void setBluetoothConnected(bool isConnected) {
//            if (isConnected) {
//                lblBtStatus.ForeColor = System.Drawing.Color.DarkGreen;
//                lblBtStatus.Text = "Connected";
//            } else {
//                lblBtStatus.ForeColor = System.Drawing.Color.DarkOrange;
//                lblBtStatus.Text = "Listening...";
//            }
        }

//        private void beginListenBluetooth() {
//            if (BluetoothRadio.IsSupported) {
//                isBluetoothRunning = true;
//                //lblBtStatus.ForeColor = System.Drawing.Color.DarkOrange;
//                //lblBtStatus.Text = "Listening...";
//                //btnStartBluetooth.Text = "Stop Bluetooth";
//                //btnStartBluetooth.Image = Properties.Resources.ic_stop_black_24dp_1x;
//                new Thread(() => {
//                    Thread.CurrentThread.IsBackground = true;
//                    btConnector = new BluetoothConnector(this);
//                }).Start();
//            }
//        }
//
//        private void endListenBluetooth() {
//            btConnector.stop();
//            isBluetoothRunning = false;
//            //lblBtStatus.ForeColor = System.Drawing.Color.Maroon;
//            //lblBtStatus.Text = "Stopped";
//            //btnStartBluetooth.Text = "Start Bluetooth";
//            //btnStartBluetooth.Image = Properties.Resources.ic_play_arrow_black_24dp_1x;
//        }

        private void btnStartStop_Click(object sender, EventArgs e) {
            string buttonText = ((Button)sender).Text;
            if (buttonText == Properties.Resources.Start)
            {
                string ipAddress = (string)listBoxWirelessInterfaces.SelectedItem;
                wlanConnector.beginAcceptWlanConnector(ipAddress);
            }
            else if (buttonText == Properties.Resources.Stop)
            {
                wlanConnector.Stop();
            }
            else
            {
                throw new NotImplementedException("Button action not implemented for " + buttonText);
            }
        }

        private void updateServerState(State serverState)
        {
            switch(serverState)
            {
                case State.Listening:
                    lblWlanStatus.ForeColor = System.Drawing.Color.DarkOrange;
                    lblWlanStatus.Text = Properties.Resources.Listening;
                    btnStartWlan.Text = Properties.Resources.Stop;
                    btnStartWlan.Image = Properties.Resources.ic_stop_black_24dp_1x;
                    UserControlQrCode userControl = new UserControlQrCode("Test 123");
                    panelQrCode.Controls.Add(userControl);
                    break;
                case State.Connected:
                    lblWlanStatus.ForeColor = System.Drawing.Color.DarkGreen;
                    lblWlanStatus.Text = Properties.Resources.Connected;
                    btnStartWlan.Text = Properties.Resources.Stop;
                    btnStartWlan.Image = Properties.Resources.ic_stop_black_24dp_1x;
                    panelQrCode.Controls.Clear();
                    break;
                case State.Stopped:
                    lblWlanStatus.ForeColor = System.Drawing.Color.Maroon;
                    lblWlanStatus.Text = Properties.Resources.Stopped;
                    btnStartWlan.Text = Properties.Resources.Start;
                    btnStartWlan.Image = Properties.Resources.ic_play_arrow_black_24dp_1x;
                    panelQrCode.Controls.Clear();
                    break;
                default:
                    throw new NotImplementedException();
            }
            this.serverState = serverState;
        }

        private void MainForm_Resize(object sender, EventArgs e)
        {
            if (this.WindowState == FormWindowState.Minimized)
            {
                Hide();
            }
        }

        private void notifyIcon1_DoubleClick(object sender, EventArgs e)
        {
            Show();
            this.WindowState = FormWindowState.Normal;
        }

        private void hideToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
