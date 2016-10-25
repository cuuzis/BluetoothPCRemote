# Android remote control application for a PC

## Problem definition
Switching between slides when giving a presentation is more convenient with a wireless remote control, but they are not always available.
An Android device can easily substitute such a designated remote controller.

## Problem Solution
Android bluetooth remote controller application, which connects to a Windows PC with the server application installed and sends keystrokes to it. 
The intended usage is to control presentations and movies wirelessly from Android device.

##Proposed Features
Must haves
* Connect to PC via Bluetooth
* Connect to PC via WiFi network
* Send commands to PC:
** swich slides with the volume buttons
** send other keystrokes using the screen (spacebar, arrows, F5, change volume)
* Menu, to configure:
** current connection method (Bluetooth, WiFi, ..)
** current layout mode (presentation, movie, ..)
** PC to connect to
Should haves
* Connect to PC via WiFi direct
Could haves
* Send text
* Connect to PC alternating between BT and WiFi
* Connect to PC via the Internet (using a server)
* Control mouse cursor



# Windows Server application
A C# Windows Forms application, runs background process that listens for incoming connections. Shows connection status as an icon in the tasbar notification area.
Should also have option to choose a connection method.