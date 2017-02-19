using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Security.Principal;
using Windows.Devices.Enumeration;
using Windows.Devices.WiFiDirect;
using Windows.Storage.Streams;
//using Windows.Foundation;
//#region Assembly Windows.Devices, Version=255.255.255.255, Culture=neutral, PublicKeyToken=null, ContentType=WindowsRuntime
// C:\WINDOWS\System32\WinMetadata\Windows.Devices.winmd
//#endregion
//using System.Runtime;

namespace TrayIconBluetoothController
{

    public class WiFiDirectConnector
    {
        private readonly MainForm form1;
        Windows.Devices.WiFiDirect.WiFiDirectDevice wfdDevice;
        DeviceInformationCollection devInfoCollection;


        private readonly string SSID = "REMOTE-CONTROLLER";
        private readonly string KEY  = "unsafepassword";


        public WiFiDirectConnector(MainForm form1) {
            this.form1 = form1;
            Console.WriteLine("Initializing WiFi Direct...");

            CreateAccessPoint();

        }


        #region create a Windows wifi Access Point
        private void CreateAccessPoint() {
            if (!IsAdmin()) {
                Console.WriteLine("Not Admin, initializing restart...");
                throw new NotImplementedException("Needs to be run as admin");
                RestartElevated();
            }
            else {
                Console.WriteLine("Starting WiFi Access Point...");
                Hotspot(SSID, KEY, true);
            }
        }

        public static bool IsAdmin() {
            WindowsIdentity id = WindowsIdentity.GetCurrent();
            WindowsPrincipal p = new WindowsPrincipal(id);
            return p.IsInRole(WindowsBuiltInRole.Administrator);
        }

        public void RestartElevated() {
            ProcessStartInfo startInfo = new ProcessStartInfo();
            startInfo.UseShellExecute = true;
            startInfo.CreateNoWindow = true;
            startInfo.WorkingDirectory = Environment.CurrentDirectory;
            startInfo.FileName = System.Windows.Forms.Application.ExecutablePath;
            startInfo.Verb = "runas";
                Process p = Process.Start(startInfo); // needs try catch
            System.Windows.Forms.Application.Exit();
        }

        private void Hotspot(string ssid, string key, bool status) {
            ProcessStartInfo processStartInfo = new ProcessStartInfo("cmd.exe");
            processStartInfo.RedirectStandardInput = true;
            processStartInfo.RedirectStandardOutput = true;
            processStartInfo.CreateNoWindow = true;
            processStartInfo.UseShellExecute = false;
            Process process = Process.Start(processStartInfo);

            if (process != null) {
                if (status) {
                    process.StandardInput.WriteLine("netsh wlan set hostednetwork mode=allow ssid=" + ssid + " key=" + key);
                    process.StandardInput.WriteLine("netsh wlan start hosted network");
                    process.StandardInput.Close();
                    process.WaitForExit();
                    string result = process.StandardOutput.ReadToEnd();
                    if (result.Contains("The hosted network started")) {
                        Console.WriteLine("The hosted network started successfuly");
                    } else {
                        throw new Exception(result);
                    }
                }
                else {
                    process.StandardInput.WriteLine("netsh wlan stop hostednetwork");
                    process.StandardInput.Close();
                }
            }
            /*process.StandardInput.WriteLine("netsh wlan show wirelesscap");
            process.StandardInput.Close();
            process.WaitForExit();
            string result = process.StandardOutput.ReadToEnd();
            Console.WriteLine("Result: " + result);*/
        }
        #endregion



        //private void CreateAdvertiser_Click() {
        //    Console.WriteLine("Creating Advertisement");
        /*
                    #region Advertiser
                    public void StartAdvertisement(
                        string serviceName,
                        bool autoAccept,
                        bool preferGO,
                        string pin,
                        //IList<WiFiDirectServiceConfigurationMethod> configMethods,
                        //WiFiDirectServiceStatus status,
                        uint customStatus,
                        string serviceInfo,
                        string deferredServiceInfo,
                        IList<String> prefixList
                        ) {
                    //ThrowIfDisposed();

                    // Create Advertiser object for the service
                    // NOTE: service name is internally limited to up to 255 bytes in UTF-8 encoding
                    // Valid characters include alpha-numeric, '.', '-', and any multi-byte character
                    // characters a-z, A-Z are case-insensitive when discovering services
                    WiFiDirectServiceAdvertiser advertiser = new WiFiDirectServiceAdvertiser(serviceName);

                    // Auto-accept services will connect without interaction from advertiser
                    // NOTE: if the config method used for a connection requires a PIN, then the advertiser will have to accept the connection
                    //advertiser.AutoAcceptSession = autoAccept;

                    // Set the Group Owner intent to a large value so that the advertiser will try to become the group owner (GO)
                    // NOTE: The GO of a P2P connection can connect to multiple clients while the client can connect to a single GO only
                    //advertiser.PreferGroupOwnerMode = preferGO;

                    // Default status is "Available", but services may use a custom status code (value > 1) if applicable
                    //advertiser.ServiceStatus = status;
                    //advertiser.CustomServiceStatusCode = customStatus;

                    // Service information can be up to 65000 bytes.
                    // Service Seeker may explicitly discover this by specifying a short buffer that is a subset of this buffer.
                    // If seeker portion matches, then entire buffer is returned, otherwise, the service information is not returned to the seeker
                    // This sample uses a string for the buffer but it can be any data
                    if (serviceInfo != null && serviceInfo.Length > 0) {
                        using (var tempStream = new Windows.Storage.Streams.InMemoryRandomAccessStream()) {
                            using (var serviceInfoDataWriter = new Windows.Storage.Streams.DataWriter(tempStream)) {
                                serviceInfoDataWriter.WriteString(serviceInfo);
                                advertiser.ServiceInfo = serviceInfoDataWriter.DetachBuffer();
                            }
                        }
                    }
                    else {
                        advertiser.ServiceInfo = null;
                    }

                    // This is a buffer of up to 144 bytes that is sent to the seeker in case the connection is "deferred" (i.e. not auto-accepted)
                    // This buffer will be sent when auto-accept is false, or if a PIN is required to complete the connection
                    // For the sample, we use a string, but it can contain any data
                    if (deferredServiceInfo != null && deferredServiceInfo.Length > 0) {
                        using (var tempStream = new Windows.Storage.Streams.InMemoryRandomAccessStream()) {
                            using (var deferredSessionInfoDataWriter = new Windows.Storage.Streams.DataWriter(tempStream)) {
                                deferredSessionInfoDataWriter.WriteString(deferredServiceInfo);
                                advertiser.DeferredSessionInfo = deferredSessionInfoDataWriter.DetachBuffer();
                            }
                        }
                    }
                    else {
                        advertiser.DeferredSessionInfo = null;
                    }

                    // The advertiser supported configuration methods
                    // Valid values are PIN-only (either keypad entry, display, or both), or PIN (keypad entry, display, or both) and WFD Services default
                    // WFD Services Default config method does not require explicit PIN entry and offers a more seamless connection experience
                    // Typically, an advertiser will support PIN display (and WFD Services Default), and a seeker will connect with either PIN entry or WFD Services Default
                    if (configMethods != null) {
                        advertiser.PreferredConfigurationMethods.Clear();
                        foreach (var configMethod in configMethods) {
                            advertiser.PreferredConfigurationMethods.Add(configMethod);
                        }
                    }

                    // Advertiser may also be discoverable by a prefix of the service name. Must explicitly specify prefixes allowed here.
                    if (prefixList != null && prefixList.Count > 0) {
                        advertiser.ServiceNamePrefixes.Clear();
                        foreach (var prefix in prefixList) {
                            advertiser.ServiceNamePrefixes.Add(prefix);
                        }
                    }

                    // For this sample, we wrap the advertiser in our own object which handles the advertiser events
                    AdvertisementWrapper advertiserWrapper = new AdvertisementWrapper(advertiser, this, pin);

                    AddAdvertiser(advertiserWrapper);

                    RootPage.NotifyUser("Starting service...", NotifyType.StatusMessage);

                    try {
                        // This may fail if the driver is unable to handle the request or if services is not supported
                        // NOTE: this must be called from the UI thread of the app
                        advertiser.Start();
                    }
                    catch (Exception ex) {
                        RootPage.NotifyUser(String.Format(CultureInfo.InvariantCulture, "Failed to start service: {0}", ex.Message), NotifyType.ErrorMessage);
                        throw;
                    }
                }


        */



















        // GET DEVICES ->
        private async void GetDevices() {
            try {
                Console.WriteLine("Enumerating WiFiDirect devices...");
                devInfoCollection = null;

                String deviceSelector = Windows.Devices.WiFiDirect.WiFiDirectDevice.GetDeviceSelector();
                devInfoCollection = await DeviceInformation.FindAllAsync(deviceSelector);
                if (devInfoCollection.Count == 0) {
                    Console.WriteLine("No WiFiDirect devices found.");
                }
                else {
                    foreach (var devInfo in devInfoCollection) {
                        Console.WriteLine("Found device: " + devInfo.Name);
                    }
                    Console.WriteLine("Enumerating WiFiDirect devices completed successfully.");
                }
            }
            catch (Exception err) {
                Console.WriteLine("Enumeration failed: " + err.Message);
            }
        }



        private async System.Threading.Tasks.Task<String> Connect(string deviceId) {
            string result = "";

            try {
                // No device Id specified.
                if (String.IsNullOrEmpty(deviceId)) { return "Please specify a Wi- Fi Direct device Id."; }

                // Connect to the selected Wi-Fi Direct device.
                wfdDevice = await Windows.Devices.WiFiDirect.WiFiDirectDevice.FromIdAsync(deviceId);

                if (wfdDevice == null) {
                    result = "Connection to " + deviceId + " failed.";
                }

                // Register for connection status change notification.
                wfdDevice.ConnectionStatusChanged += new Windows.Foundation.TypedEventHandler<Windows.Devices.WiFiDirect.WiFiDirectDevice, object>(OnConnectionChanged);

                // Get the EndpointPair information.
                var EndpointPairCollection = wfdDevice.GetConnectionEndpointPairs();

                if (EndpointPairCollection.Count > 0) {
                    var endpointPair = EndpointPairCollection[0];
                    result = "Local IP address " + endpointPair.LocalHostName.ToString() +
                        " connected to remote IP address " + endpointPair.RemoteHostName.ToString();
                }
                else {
                    result = "Connection to " + deviceId + " failed.";
                }
            }
            catch (Exception err) {
                // Handle error.
                result = "Error occurred: " + err.Message;
            }

            return result;
        }

        private void OnConnectionChanged(object sender, object arg) {
            Windows.Devices.WiFiDirect.WiFiDirectConnectionStatus status =
                (Windows.Devices.WiFiDirect.WiFiDirectConnectionStatus)arg;

            if (status == Windows.Devices.WiFiDirect.WiFiDirectConnectionStatus.Connected) {
                // Connection successful.
            }
            else {
                // Disconnected.
                Disconnect();
            }
        }

        private void Disconnect() {
            if (wfdDevice != null) {
                wfdDevice.Dispose();
            }
        }
    }
}