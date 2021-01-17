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
            this.notifyIcon = new System.Windows.Forms.NotifyIcon(this.components);
            this.lblWlanStatus = new System.Windows.Forms.Label();
            this.btnStartWlan = new System.Windows.Forms.Button();
            this.panelQrCode = new System.Windows.Forms.Panel();
            this.listBoxWirelessInterfaces = new System.Windows.Forms.ListBox();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.showToolStripMenuItem,
            this.hideToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(104, 48);
            // 
            // showToolStripMenuItem
            // 
            this.showToolStripMenuItem.Name = "showToolStripMenuItem";
            this.showToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.showToolStripMenuItem.Text = global::TrayIconBluetoothController.Properties.Resources.Open;
            this.showToolStripMenuItem.Click += new System.EventHandler(this.notifyIcon1_DoubleClick);
            // 
            // hideToolStripMenuItem
            // 
            this.hideToolStripMenuItem.Name = "hideToolStripMenuItem";
            this.hideToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.hideToolStripMenuItem.Text = global::TrayIconBluetoothController.Properties.Resources.Exit;
            this.hideToolStripMenuItem.Click += new System.EventHandler(this.hideToolStripMenuItem_Click);
            // 
            // notifyIcon
            // 
            this.notifyIcon.ContextMenuStrip = this.contextMenuStrip1;
            this.notifyIcon.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon.Icon")));
            this.notifyIcon.Text = "notifyIcon";
            this.notifyIcon.Visible = true;
            this.notifyIcon.DoubleClick += new System.EventHandler(this.notifyIcon1_DoubleClick);
            this.notifyIcon.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.notifyIcon1_DoubleClick);
            // 
            // lblWlanStatus
            // 
            this.lblWlanStatus.AutoSize = true;
            this.lblWlanStatus.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblWlanStatus.ForeColor = System.Drawing.Color.Maroon;
            this.lblWlanStatus.Location = new System.Drawing.Point(0, 26);
            this.lblWlanStatus.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.lblWlanStatus.Name = "lblWlanStatus";
            this.lblWlanStatus.Size = new System.Drawing.Size(47, 13);
            this.lblWlanStatus.TabIndex = 13;
            this.lblWlanStatus.Text = "Stopped";
            // 
            // btnStartWlan
            // 
            this.btnStartWlan.Dock = System.Windows.Forms.DockStyle.Top;
            this.btnStartWlan.Image = global::TrayIconBluetoothController.Properties.Resources.ic_play_arrow_black_24dp_1x;
            this.btnStartWlan.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnStartWlan.Location = new System.Drawing.Point(0, 0);
            this.btnStartWlan.Margin = new System.Windows.Forms.Padding(2);
            this.btnStartWlan.Name = "btnStartWlan";
            this.btnStartWlan.Size = new System.Drawing.Size(341, 26);
            this.btnStartWlan.TabIndex = 2;
            this.btnStartWlan.Text = global::TrayIconBluetoothController.Properties.Resources.Start;
            this.btnStartWlan.UseVisualStyleBackColor = true;
            this.btnStartWlan.Click += new System.EventHandler(this.btnStartStop_Click);
            // 
            // panelQrCode
            // 
            this.panelQrCode.AutoSize = true;
            this.panelQrCode.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.panelQrCode.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panelQrCode.Location = new System.Drawing.Point(0, 356);
            this.panelQrCode.MinimumSize = new System.Drawing.Size(100, 100);
            this.panelQrCode.Name = "panelQrCode";
            this.panelQrCode.Size = new System.Drawing.Size(341, 100);
            this.panelQrCode.TabIndex = 17;
            // 
            // listBoxWirelessInterfaces
            // 
            this.listBoxWirelessInterfaces.Dock = System.Windows.Forms.DockStyle.Top;
            this.listBoxWirelessInterfaces.FormattingEnabled = true;
            this.listBoxWirelessInterfaces.Items.AddRange(new object[] {
            "wlan1",
            "wlan2"});
            this.listBoxWirelessInterfaces.Location = new System.Drawing.Point(0, 39);
            this.listBoxWirelessInterfaces.MinimumSize = new System.Drawing.Size(100, 100);
            this.listBoxWirelessInterfaces.Name = "listBoxWirelessInterfaces";
            this.listBoxWirelessInterfaces.Size = new System.Drawing.Size(341, 95);
            this.listBoxWirelessInterfaces.TabIndex = 18;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(96F, 96F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoSize = true;
            this.AutoValidate = System.Windows.Forms.AutoValidate.EnablePreventFocusChange;
            this.ClientSize = new System.Drawing.Size(341, 456);
            this.Controls.Add(this.panelQrCode);
            this.Controls.Add(this.listBoxWirelessInterfaces);
            this.Controls.Add(this.lblWlanStatus);
            this.Controls.Add(this.btnStartWlan);
            this.MaximizeBox = false;
            this.Name = "MainForm";
            this.Text = "Android Remote";
            this.TopMost = true;
            this.Resize += new System.EventHandler(this.MainForm_Resize);
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem showToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem hideToolStripMenuItem;
        private System.Windows.Forms.NotifyIcon notifyIcon;
        private System.Windows.Forms.Button btnStartWlan;
        private System.Windows.Forms.Label lblWlanStatus;
        private System.Windows.Forms.Panel panelQrCode;
        private System.Windows.Forms.ListBox listBoxWirelessInterfaces;
    }
}

