package com.example.rishikapadia.connectid;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private GestureDetectorCompat gestureObject;
    private ZXingScannerView mScannerView;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 86758309;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void QrScanner(View view){

        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);

            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();         // Start camera

        }else{
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }


        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }
    @Override
    public void onPause() {
        super.onPause();
        //mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
                setContentView(mScannerView);

                mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();         // Start camera
            }else{
                Toast.makeText(this, "App requires camera to proceed", Toast.LENGTH_LONG);
            }
        }
    }
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        String myUrl = rawResult.getText();

        if (Patterns.WEB_URL.matcher(myUrl).matches()) {
            Intent visitUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl));
            startActivity(visitUrl);
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        mScannerView.stopCamera();           // Stop camera on pause
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e2.getX()>e1.getX()){

                Intent intent = new Intent(MainActivity.this,PersonalProfile.class);
                finish();

                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_left,R.animator.slide_out_right);

            }
            else if (e2.getX()<e1.getX()){


                Intent intent = new Intent(MainActivity.this,ContactList.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.animator.slide_in_right,R.animator.slide_out_left);

            }


            return true;
        }
    }



    public void viewinfo(View view){

        Intent intent = new Intent(this,PersonalProfile.class);
        startActivity(intent);
    }

    public void viewcontact(View view){

        Intent intent = new Intent(this,ContactList.class);
        startActivity(intent);
    }


}
