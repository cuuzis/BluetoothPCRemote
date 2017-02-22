# Android remote control application for a PC

## Problem definition
Switching between slides when giving a presentation is very convenient with a wireless remote
control. But these devices are expensive and rarely at hand. However, an Android device could
easily substitute such a designated remote. Additionally, the Android device could work as a generic
remote controller and also change the computer’s volume, play a music track, etc. from a distance.

## Problem Solution
Android bluetooth remote controller application, which connects to a Windows PC with the server application installed and sends keystrokes to it. 
The solution includes a Windows server application, that listens to the Android device, and interprets the commands.

## Features
Implemented
* Connecting to PC via Bluetooth
* Connecting to PC via WiFi network Implemented
* Connecting to PC alternating between BT and WiFi on lost connection
* QR code scanning to get connection info (using Google Barcode API)
* Send commands to PC (swich slides with the volume buttons and send keystrokes from the screen)

Proposed
* Connect to PC via WiFi direct
* Act as keyboard (send text)
* User chooses what buttons do
* Control mouse cursor


# Windows Server application
A C# Windows Forms application, listens for incoming connections. Bluetooth and network listening can be started and stopped on demand. Generates a QR code with the necessary information to initiate a connection.