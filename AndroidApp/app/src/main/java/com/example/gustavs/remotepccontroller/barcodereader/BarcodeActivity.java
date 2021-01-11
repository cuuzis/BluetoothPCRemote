package com.example.gustavs.remotepccontroller.barcodereader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gustavs.remotepccontroller.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.mlkit.vision.barcode.Barcode.FORMAT_QR_CODE;

public class BarcodeActivity extends AppCompatActivity {
    private static final String TAG = BarcodeActivity.class.getSimpleName();

    private static final int CAMERA_REQUEST_CODE = 10;
    public static final String BarcodeObject = "Barcode";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.camera_preview);
        requestCameraPermission();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Log.w(TAG, "Camera permission denied");
            }
        }
    }

    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        // Show/hide informational texts
        findViewById(R.id.text_camera_not_available).setVisibility(View.INVISIBLE);
        findViewById(R.id.text_please_scan_qr_code).setVisibility(View.VISIBLE);

        // Set-up camera preview
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder().build();
        PreviewView previewView = findViewById(R.id.previewView);
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Set-up image analysis
        ImageAnalysis imageAnalyzer = buildImageAnalyzer();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer);
    }

    private ImageAnalysis buildImageAnalyzer() {
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {

            @Override
            @androidx.camera.core.ExperimentalGetImage
            public void analyze(@NonNull ImageProxy imageProxy) {
                Image mediaImage = imageProxy.getImage();
                if (mediaImage != null) {
                    InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                    // Pass image to an ML Kit Vision API
                    BarcodeScannerOptions options =
                            new BarcodeScannerOptions.Builder()
                                    .setBarcodeFormats(FORMAT_QR_CODE)
                                    .build();
                    BarcodeScanner scanner = BarcodeScanning.getClient(options);
                    Task<List<Barcode>> scanningTask = scanner.process(image);

                    scanningTask.addOnSuccessListener(barcodes -> {
                        if (barcodes != null && barcodes.size() > 0) {
                            final String barcodeValue = barcodes.get(0).getDisplayValue();
                            Log.d(TAG, "Using barcode 1 of " + barcodes.size() + ": " + barcodeValue);
                            Intent data = new Intent();
                            data.putExtra(BarcodeObject, barcodeValue);
                            setResult(CommonStatusCodes.SUCCESS, data);
                            finish();
                        } else {
                            Log.d(TAG, "No barcodes in frame");
                            imageProxy.close();
                        }
                    });

                    scanningTask.addOnFailureListener(e -> {
                        Log.e(TAG, "Barcode scanning exception", e);
                    });
                }
            }
        });
        return imageAnalysis;
    }

}
