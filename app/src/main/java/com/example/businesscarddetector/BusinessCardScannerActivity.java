package com.example.businesscarddetector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.businesscarddetector.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessCardScannerActivity extends AppCompatActivity {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final long ONE_DAY = 24 * 60 * 60 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_scanner);
        checkValidation();
        if (!checkPermission()) {
            requestPermission();
        } else {
            startQRScannerActivity();
        }

    }

    private void checkValidation() {
        Date _now = new Date();
        String dateString = formatter.format(_now);
        Date before = null;
        try {
            before = (Date)formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        long diff = now.getTime() - before.getTime();
        long days = diff / ONE_DAY;
        if(days > 30) { // More than 30 days?
            // Expired !!!
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                Constants.PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startQRScannerActivity();

                    // main logic
                } else {
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startQRScannerActivity() {
        Intent i = new Intent(this, QRScannerActivity.class);
        this.startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}