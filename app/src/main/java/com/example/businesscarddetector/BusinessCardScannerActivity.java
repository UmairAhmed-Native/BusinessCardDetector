package com.example.businesscarddetector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.businesscarddetector.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusinessCardScannerActivity extends AppCompatActivity {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final long ONE_DAY = 24 * 60 * 60 * 1000;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card_scanner);
        checkValidation();


    }

    private void checkValidation() {
        Query checkTimeSpan = database.orderByChild("timespan");
        checkTimeSpan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String timeInDb = snapshot.child("validation").child("timespan").getValue(String.class);
                    if (timeInDb.isEmpty() || timeInDb == null) {
                        Date _now = new Date();
                        String dateString = formatter.format(_now);
                        database.child("validation").child("timespan").setValue(dateString);
                        startApp();
                    } else {
                        Date before = null;
                        try {
                            before = (Date) formatter.parse(timeInDb);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date now = new Date();
                        long diff = now.getTime() - before.getTime();
                        long days = diff / ONE_DAY;
                        if (days > 0) {
                            finish();
                        } else {
                            startApp();
                        }
                    }
                } else {
                    Date _now = new Date();
                    String dateString = formatter.format(_now);
                    database.child("validation").child("timespan").setValue(dateString);
                    startApp();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startApp() {
        if (!checkPermission()) {
            requestPermission();
        } else {
            startQRScannerActivity();
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