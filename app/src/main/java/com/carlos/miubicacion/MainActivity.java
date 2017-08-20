package com.carlos.miubicacion;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback {

    private  static  final  int ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE=0;

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Location userLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                getUserLastLocation(userLocation);
            }
            else
            {
                final  String[] permissions= new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions,ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            Location userLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            getUserLastLocation(userLocation);
        }
    }
    @RequiresApi(api= Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE){
               if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   googleApiClient.reconnect();
               }

            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acceder a la Ubicacion del Telefono");
                builder.setMessage("Debes Aceptar este permito para poder utilizar la app");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final  String[] permissions= new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                        requestPermissions(permissions,ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }
    }

    private void getUserLastLocation(Location userLocation) {
        if (userLocation != null) {
            TextView locationTextView = (TextView) findViewById(R.id.main_activity_location_textView);
            String longitude = String.valueOf(userLocation.getLongitude());
            String latitude = String.valueOf(userLocation.getLatitude());
            locationTextView.setText("Longitude: " + longitude + ", Latitude: " + latitude);

            this.userLocation=userLocation;
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng UserCoordinates = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(UserCoordinates).title("User Locatión"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UserCoordinates));
    }
}
