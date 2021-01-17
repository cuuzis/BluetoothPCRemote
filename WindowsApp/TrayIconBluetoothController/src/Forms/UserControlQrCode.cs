using System.Windows.Forms;

namespace TrayIconBluetoothController.src.Forms
{
    public partial class UserControlQrCode : UserControl
    {
        public UserControlQrCode(string barcodeText)
        {
            InitializeComponent();
            qrCodePicturebox.Image = QRCodeGenerator.GenerateQRCode(barcodeText, 240);
            qrCodePicturebox.Refresh();
            this.Refresh();
        }
    }
}
