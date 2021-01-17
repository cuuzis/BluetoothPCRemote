using System;
using System.IO;
using WindowsInput.Native;

namespace TrayIconBluetoothController
{
    public static class VirtualKeyboard
    {
        private const byte EMPTY = 0;

        private static WindowsInput.InputSimulator mInput = new WindowsInput.InputSimulator();

        public static void sendKey(byte keyDown, byte keyPress) {
            Console.WriteLine("Received bytes: {0}, {1}", keyDown, keyPress);
            if (keyDown == EMPTY) {
                VirtualKeyCode key = (VirtualKeyCode)Enum.Parse(typeof(VirtualKeyCode), keyPress.ToString());
                mInput.Keyboard.KeyPress(key);
            } else {
                VirtualKeyCode downKey = (VirtualKeyCode)Enum.Parse(typeof(VirtualKeyCode), keyDown.ToString());
                VirtualKeyCode pressKey = (VirtualKeyCode)Enum.Parse(typeof(VirtualKeyCode), keyPress.ToString());
                mInput.Keyboard.KeyDown(downKey);
                mInput.Keyboard.KeyPress(pressKey);
                mInput.Keyboard.KeyUp(downKey);
            }
        }

        public static void readWhileOpen(Stream peerStream) {
            try {
                while (true) {
                    byte[] buf = new byte[2];
                    int readLen = peerStream.Read(buf, 0, 2);
                    if (readLen == 2)
                        sendKey(buf[0], buf[1]);
                    else
                        return;
                }
            } catch {
                Console.WriteLine("Stream closed");
            }
        }
    }
}
