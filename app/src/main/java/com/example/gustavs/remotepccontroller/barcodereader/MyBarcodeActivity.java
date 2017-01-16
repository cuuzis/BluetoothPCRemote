package com.example.gustavs.remotepccontroller.barcodereader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;

import com.example.gustavs.remotepccontroller.R;
import com.example.gustavs.remotepccontroller.barcodereader.ui.camera.CameraSourcePreview;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static com.example.gustavs.remotepccontroller.barcodereader.BarcodeCaptureActivity.BarcodeObject;

public class MyBarcodeActivity extends AppCompatActivity {
    private static final String TAG = MyBarcodeActivity.class.getSimpleName();

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);


        Context context = getApplicationContext();
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
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

        CameraSource mCameraSource = new CameraSource.Builder(context, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .build();

        CameraSourcePreview mPreview = (CameraSourcePreview) findViewById(R.id.preview);
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
                //mPreview.start(mCameraSource);
                mCameraSource.start();
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }

        /*mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        //mGraphicOverlay = (GraphicOverlay<BarcodeGraphic>) findViewById(R.id.graphicOverlay);

        // read parameters from the intent used to launch the activity.
        boolean autoFocus = true;
        boolean useFlash = false;

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
*/
    }
}
