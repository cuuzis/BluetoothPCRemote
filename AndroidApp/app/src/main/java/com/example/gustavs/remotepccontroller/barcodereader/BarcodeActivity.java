package com.example.gustavs.remotepccontroller.barcodereader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.util.Log;
import android.util.SparseArray;

import com.example.gustavs.remotepccontroller.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

//TODO: Add text saying "Please scan QR code from PC screen"; Validate Barcode before exit!
public class BarcodeActivity extends AppCompatActivity {
    private static final String TAG = BarcodeActivity.class.getSimpleName();
    public static final String BarcodeObject = "Barcode";

    private static final float PREVIEW_FPS = 15.0f;

    private CameraSource mCameraSource;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_barcode);
        createCameraSource();
        startCameraSource();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Creates a camera source with QR tag detector attached
     */
    private void createCameraSource() {
        Context context = getApplicationContext();
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        barcodeDetector.setProcessor(new FocusingProcessor<Barcode>(barcodeDetector, new Tracker<Barcode>()) {
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                if (detections != null) {
                    SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    if (barcodes != null && barcodes.size() > 0) {
                        int key = barcodes.keyAt(0);
                        Barcode best = barcodes.get(key);
                        if (best != null) {
                            System.out.println("BARCODE FOUND:" + best.displayValue);
                            Intent data = new Intent();
                            data.putExtra(BarcodeObject, best);
                            setResult(CommonStatusCodes.SUCCESS, data);
                            finish();
                        }
                    }
                }
            }
            @Override
            public int selectFocus(Detector.Detections<Barcode> detections) {
                return 0;
            }
        });

        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);

        mCameraSource = new CameraSource.Builder(context, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(displaySize.x, displaySize.y)
                .setAutoFocusEnabled(true)
                .setRequestedFps(PREVIEW_FPS)
                .build();

    }


    private void startCameraSource() {
        if (mCameraSource != null) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mPreview = (CameraPreview) findViewById(R.id.cameraPreview);
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }
}
