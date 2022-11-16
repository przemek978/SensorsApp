package com.example.sensorapp;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LocationActivity extends AppCompatActivity {

    private Button getLocationButton;
    public static final int REQUEST_LOCATION_PERMISSION = 100;
    public String TAG = "LocationActivity";
    private Location lastLocation;
    private TextView locationTextView;
    //private FusedLocationProviderClient fusedLocationClient;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getLocationButton = findViewById(R.id.get_location_button);
        getLocationButton.setOnClickListener((View v)->getLocation());
        locationTextView = findViewById(R.id.textview_location);
       //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //addressTextView = findViewById(R.id.textview_address);
        //Button getAddressButton = findViewById(R.id.get_address_button);
        //getAddressButton.setOnClickListener(v->executeGeocoding());
    }
    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            Log.d(TAG, "getLocation: permissions granted");
        }

//        fusedLocationClient.getLastLocation().addOnSuccessListener(location->{
//            if(location!=null){
//                lastLocation = location;
//                locationTextView.setText(
//                        getString(R.string.location_text, location.getLatitude(), location.getLongitude(), location.getTime()));
//            }
//            else{
//                locationTextView.setText(R.string.no_location);
//            }
//        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}