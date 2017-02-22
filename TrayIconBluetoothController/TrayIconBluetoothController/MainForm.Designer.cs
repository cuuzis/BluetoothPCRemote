using System;

namespace TrayIconBluetoothController
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.showToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.hideToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.infoLabel = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.tbWlanName = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.tbPort = new System.Windows.Forms.TextBox();
            this.tbBluetoothName = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.lblWlanStatus = new System.Windows.Forms.Label();
            this.lblBtStatus = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.btnStartBluetooth = new System.Windows.Forms.Button();
            this.btnStartAll = new System.Windows.Forms.Button();
            this.btnStartWlan = new System.Windows.Forms.Button();
            this.qrCodePicturebox = new System.Windows.Forms.PictureBox();
            this.contextMenuStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.qrCodePicturebox)).BeginInit();
            this.SuspendLayout();
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.showToolStripMenuItem,
            this.hideToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(121, 56);
            // 
            // showToolStripMenuItem
            // 
            this.showToolStripMenuItem.Name = "showToolStripMenuItem";
            this.showToolStripMenuItem.Size = new System.Drawing.Size(120, 26);
            this.showToolStripMenuItem.Text = "Show";
            this.showToolStripMenuItem.Click += new System.EventHandler(this.showToolStripMenuItem_Click);
            // 
            // hideToolStripMenuItem
            // 
            this.hideToolStripMenuItem.Name = "hideToolStripMenuItem";
            this.hideToolStripMenuItem.Size = new System.Drawing.Size(120, 26);
            this.hideToolStripMenuItem.Text = "Exit";
            this.hideToolStripMenuItem.Click += new System.EventHandler(this.hideToolStripMenuItem_Click);
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.ContextMenuStrip = this.contextMenuStrip1;
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "notifyIcon1";
            this.notifyIcon1.Visible = true;
            this.notifyIcon1.DoubleClick += new System.EventHandler(this.notifyIcon1_DoubleClick);
            // 
            // infoLabel
            // 
            this.infoLabel.AutoEllipsis = true;
            this.infoLabel.Location = new System.Drawing.Point(14, 274);
            this.infoLabel.Name = "infoLabel";
            this.infoLabel.Size = new System.Drawing.Size(300, 21);
            this.infoLabel.TabIndex = 2;
            this.infoLabel.Text = "To connect, scan this code with your App:";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(46, 96);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(83, 17);
            this.label1.TabIndex = 3;
            this.label1.Text = "Wlan name:";
            // 
            // tbWlanName
            // 
            this.tbWlanName.Location = new System.Drawing.Point(135, 91);
            this.tbWlanName.Name = "tbWlanName";
            this.tbWlanName.ReadOnly = true;
            this.tbWlanName.Size = new System.Drawing.Size(182, 22);
            this.tbWlanName.TabIndex = 6;
            this.tbWlanName.TabStop = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(56, 125);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(73, 17);
            this.label2.TabIndex = 7;
            this.label2.Text = "Wlan port:";
            // 
            // tbPort
            // 
            this.tbPort.Location = new System.Drawing.Point(135, 120);
            this.tbPort.Name = "tbPort";
            this.tbPort.ReadOnly = true;
            this.tbPort.Size = new System.Drawing.Size(62, 22);
            this.tbPort.TabIndex = 8;
            this.tbPort.TabStop = false;
            // 
            // tbBluetoothName
            // 
            this.tbBluetoothName.Location = new System.Drawing.Point(135, 215);
            this.tbBluetoothName.Name = "tbBluetoothName";
            this.tbBluetoothName.ReadOnly = true;
            this.tbBluetoothName.Size = new System.Drawing.Size(182, 22);
            this.tbBluetoothName.TabIndex = 11;
            this.tbBluetoothName.TabStop = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(18, 220);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(111, 17);
            this.label3.TabIndex = 10;
            this.label3.Text = "Bluetooth name:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(77, 152);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(52, 17);
            this.label4.TabIndex = 12;
            this.label4.Text = "Status:";
            // 
            // lblWlanStatus
            // 
            this.lblWlanStatus.AutoSize = true;
            this.lblWlanStatus.ForeColor = System.Drawing.Color.Maroon;
            this.lblWlanStatus.Location = new System.Drawing.Point(132, 152);
            this.lblWlanStatus.Name = "lblWlanStatus";
            this.lblWlanStatus.Size = new System.Drawing.Size(61, 17);
            this.lblWlanStatus.TabIndex = 13;
            this.lblWlanStatus.Text = "Stopped";
            // 
            // lblBtStatus
            // 
            this.lblBtStatus.AutoSize = true;
            this.lblBtStatus.ForeColor = System.Drawing.Color.Maroon;
            this.lblBtStatus.Location = new System.Drawing.Point(132, 246);
            this.lblBtStatus.Name = "lblBtStatus";
            this.lblBtStatus.Size = new System.Drawing.Size(61, 17);
            this.lblBtStatus.TabIndex = 16;
            this.lblBtStatus.Text = "Stopped";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(77, 246);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(52, 17);
            this.label7.TabIndex = 15;
            this.label7.Text = "Status:";
            // 
            // btnStartBluetooth
            // 
            this.btnStartBluetooth.Image = global::TrayIconBluetoothController.Properties.Resources.ic_play_arrow_black_24dp_1x;
            this.btnStartBluetooth.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnStartBluetooth.Location = new System.Drawing.Point(17, 177);
            this.btnStartBluetooth.Name = "btnStartBluetooth";
            this.btnStartBluetooth.Size = new System.Drawing.Size(177, 32);
            this.btnStartBluetooth.TabIndex = 3;
            this.btnStartBluetooth.Text = "Start Bluetooth";
            this.btnStartBluetooth.UseVisualStyleBackColor = true;
            this.btnStartBluetooth.Click += new System.EventHandler(this.btnStartBluetooth_Click);
            // 
            // btnStartAll
            // 
            this.btnStartAll.Image = global::TrayIconBluetoothController.Properties.Resources.ic_playlist_play_black_24dp_1x;
            this.btnStartAll.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnStartAll.Location = new System.Drawing.Point(18, 18);
            this.btnStartAll.Margin = new System.Windows.Forms.Padding(0);
            this.btnStartAll.Name = "btnStartAll";
            this.btnStartAll.Size = new System.Drawing.Size(177, 32);
            this.btnStartAll.TabIndex = 1;
            this.btnStartAll.Text = "Start All";
            this.btnStartAll.UseVisualStyleBackColor = true;
            this.btnStartAll.Click += new System.EventHandler(this.btnStartAll_Click);
            // 
            // btnStartWlan
            // 
            this.btnStartWlan.Image = global::TrayIconBluetoothController.Properties.Resources.ic_play_arrow_black_24dp_1x;
            this.btnStartWlan.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnStartWlan.Location = new System.Drawing.Point(18, 53);
            this.btnStartWlan.Name = "btnStartWlan";
            this.btnStartWlan.Size = new System.Drawing.Size(177, 32);
            this.btnStartWlan.TabIndex = 2;
            this.btnStartWlan.Text = "Start Wlan";
            this.btnStartWlan.UseVisualStyleBackColor = true;
            this.btnStartWlan.Click += new System.EventHandler(this.btnStartWlan_Click);
            // 
            // qrCodePicturebox
            // 
            this.qrCodePicturebox.Location = new System.Drawing.Point(17, 298);
            this.qrCodePicturebox.MinimumSize = new System.Drawing.Size(100, 100);
            this.qrCodePicturebox.Name = "qrCodePicturebox";
            this.qrCodePicturebox.Size = new System.Drawing.Size(300, 300);
            this.qrCodePicturebox.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
            this.qrCodePicturebox.TabIndex = 1;
            this.qrCodePicturebox.TabStop = false;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(369, 621);
            this.Controls.Add(this.lblBtStatus);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.lblWlanStatus);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.tbBluetoothName);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.btnStartBluetooth);
            this.Controls.Add(this.tbPort);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.tbWlanName);
            this.Controls.Add(this.btnStartAll);
            this.Controls.Add(this.btnStartWlan);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.infoLabel);
            this.Controls.Add(this.qrCodePicturebox);
            this.MaximizeBox = false;
            this.Name = "MainForm";
            this.Padding = new System.Windows.Forms.Padding(15);
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Android Remote";
            this.Move += new System.EventHandler(this.Form1_Move);
            this.contextMenuStrip1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.qrCodePicturebox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem showToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem hideToolStripMenuItem;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.PictureBox qrCodePicturebox;
        private System.Windows.Forms.Label infoLabel;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnStartWlan;
        private System.Windows.Forms.Button btnStartAll;
        private System.Windows.Forms.TextBox tbWlanName;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox tbPort;
        private System.Windows.Forms.Button btnStartBluetooth;
        private System.Windows.Forms.TextBox tbBluetoothName;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label lblWlanStatus;
        private System.Windows.Forms.Label lblBtStatus;
        private System.Windows.Forms.Label label7;
    }
}

