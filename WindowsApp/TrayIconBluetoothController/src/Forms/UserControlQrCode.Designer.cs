
namespace TrayIconBluetoothController.src.Forms
{
    partial class UserControlQrCode
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.labelQrCodeInfo = new System.Windows.Forms.Label();
            this.qrCodePicturebox = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.qrCodePicturebox)).BeginInit();
            this.SuspendLayout();
            // 
            // labelQrCodeInfo
            // 
            this.labelQrCodeInfo.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelQrCodeInfo.Location = new System.Drawing.Point(0, 0);
            this.labelQrCodeInfo.Margin = new System.Windows.Forms.Padding(2, 10, 2, 10);
            this.labelQrCodeInfo.Name = "labelQrCodeInfo";
            this.labelQrCodeInfo.Size = new System.Drawing.Size(337, 17);
            this.labelQrCodeInfo.TabIndex = 3;
            this.labelQrCodeInfo.Text = "To connect, scan this code with your App:";
            // 
            // qrCodePicturebox
            // 
            this.qrCodePicturebox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.qrCodePicturebox.Location = new System.Drawing.Point(0, 0);
            this.qrCodePicturebox.Margin = new System.Windows.Forms.Padding(2);
            this.qrCodePicturebox.MinimumSize = new System.Drawing.Size(100, 100);
            this.qrCodePicturebox.Name = "qrCodePicturebox";
            this.qrCodePicturebox.Size = new System.Drawing.Size(337, 331);
            this.qrCodePicturebox.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.qrCodePicturebox.TabIndex = 4;
            this.qrCodePicturebox.TabStop = false;
            // 
            // UserControlQrCode
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.Controls.Add(this.labelQrCodeInfo);
            this.Controls.Add(this.qrCodePicturebox);
            this.MinimumSize = new System.Drawing.Size(337, 331);
            this.Name = "UserControlQrCode";
            this.Size = new System.Drawing.Size(337, 331);
            ((System.ComponentModel.ISupportInitialize)(this.qrCodePicturebox)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label labelQrCodeInfo;
        private System.Windows.Forms.PictureBox qrCodePicturebox;
    }
}
