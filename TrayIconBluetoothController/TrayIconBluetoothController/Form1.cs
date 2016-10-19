using System;
using System.Windows.Forms;

namespace TrayIconBluetoothController
{
    public partial class Form1 : Form
    {
        public Form1() {
            InitializeComponent();

            //this.notifyIcon1.Icon = new ((System.Drawing.Icon)(resources.GetObject("redBTicon.ico")));
            //this.notifyIcon1.Icon = new Icon("Resources/redBTicon.ico"); //((System.Drawing.Icon)(("redBTicon.ico")));
            //System.ComponentModel.ComponentResourceManager resources

            BluetoothConnector btConnector = new BluetoothConnector(this);
        }

        private void Form1_Move(object sender, EventArgs e) {
            if (this.WindowState == FormWindowState.Minimized) {
                this.Hide();
            }
        }

        private void showToolStripMenuItem_Click(object sender, EventArgs e) {
            this.Show();
        }

        private void hideToolStripMenuItem_Click(object sender, EventArgs e) {
            Application.Exit();
        }

        private void notifyIcon1_DoubleClick(object sender, EventArgs e) {
            this.Show();
        }

        public void NotifyLostConnection() {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.red")));
        }

        public void NotifyEstablishedConnection() {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.green")));
        }
    }
}
