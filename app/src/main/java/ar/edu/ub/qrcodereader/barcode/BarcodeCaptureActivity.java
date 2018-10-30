package ar.edu.ub.qrcodereader.barcode;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.FocusingProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import ar.edu.ub.qrcodereader.R;
import ar.edu.ub.qrcodereader.camera.CameraSourcePreview;

public class BarcodeCaptureActivity extends Activity implements BarcodeTracker.BarcodeUpdateListener {

    private static final String TAG = BarcodeCaptureActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_PERMISSION_CODE = 101;
    private static final int GMS_REQUEST_PERMISSION_CODE = 2;
    private static final String EXTRA_DATA_NAME = "Schedule";
    private CameraSource cameraSource;
    private CameraSourcePreview cameraPreview;
    private AlertDialog barcodeInformation;
    private BarcodeCapturePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_capture);

        cameraPreview = findViewById(R.id.camera_preview);

        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        presenter = new BarcodeCapturePresenter(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraPreview != null){
            cameraPreview.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cameraPreview != null){
            cameraPreview.release();
        }
    }

    @Override
    public void onBarcodeDetected(final Barcode barcode) {
        String message = this.presenter.onBarcodeDetected(barcode);
        if(message != null){
            Intent intent = new Intent(getApplicationContext(), BarcodeInformationActivity.class);
            intent.putExtra(EXTRA_DATA_NAME, message);
            startActivity(intent);
        }
    }

    @Override
    public void onBarcodeNoLongerPresent() {
        if(barcodeInformation != null){
            barcodeInformation.dismiss();
            barcodeInformation = null;
        }
    }

    private void createCameraSource() {

        BarcodeDetector detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        BarcodeTracker tracker = new BarcodeTracker.Builder(this).build();

        FocusingProcessor<Barcode> processor = new CentralBarcodeFocusingProcessor.Builder(detector, tracker).build();

        detector.setProcessor(processor);

        if(!detector.isOperational()){
            Log.w(TAG, "Detector dependencies are not yet available.");
            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowStorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error,
                        Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraSource.Builder builder = new CameraSource.Builder(this, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f);

        cameraSource = builder.build();
    }

    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST_PERMISSION_CODE);
    }

    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, GMS_REQUEST_PERMISSION_CODE);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                cameraPreview.start(cameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != CAMERA_REQUEST_PERMISSION_CODE) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }
}
