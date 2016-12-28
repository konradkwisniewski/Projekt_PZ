package zaliczeniepz.wmsgpsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
                                                              GoogleApiClient.OnConnectionFailedListener,
                                                              LocationListener, View.OnClickListener {

    private GoogleMap mMap;
    private PermissionChecker permissionChecker;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private FloatingActionButton myLocationButton;
    private FloatingActionButton myJobButton;

    private Location currentLocation;

    private final String LOG_TAG = "MapsActivity";

    private boolean firstLocationRead = true;
    private boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.myJobButton = (FloatingActionButton) findViewById(R.id.myJob_button);
        this.myJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, JobsActivity.class);
                startActivity(intent);
            }
        });

        this.myLocationButton = (FloatingActionButton) findViewById(R.id.myLocation_button);
        this.myLocationButton.setOnClickListener(this);

        this.permissionChecker = new PermissionChecker(this);
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(1000);
        Location location = null;
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, this.locationRequest, this);
            location = LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        } catch (SecurityException exc) {
            Toast.makeText(this, R.string.No_permissions, Toast.LENGTH_LONG).show();
        }

        if (location != null) {
            this.currentLocation = location;
            this.myLocationButton.callOnClick();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(this.LOG_TAG, " Connection got suspended");
        this.googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(this.LOG_TAG, " Connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        this.setLocation(location);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.isMapReady = true;
        final LocationManager MANAGER = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (!MANAGER.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.setMarkerToWarsaw();
        }

        if (!this.permissionChecker.isNetworkPermissionGranted())
            Toast.makeText(this, R.string.network_warning, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.googleApiClient.connect();

    }

    @Override
    protected void onStop() {
        this.googleApiClient.disconnect();
        super.onStop();

    }

    private void setMarkerToWarsaw() {
        LatLng location = new LatLng(52.257559, 20.983877);
        this.mMap.addMarker(new MarkerOptions().position(location).title("Marker in Warsaw"));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        this.mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    private void setLocation(Location newLocation) {
        if (newLocation != null) {
            this.currentLocation = newLocation;
            this.mMap.clear();

            CircleOptions circle = new CircleOptions();
            circle.center(new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude()));
            circle.radius(15);
            circle.fillColor(Color.BLUE);
            circle.strokeWidth(3);
            circle.strokeColor(Color.WHITE);
            circle.clickable(false);
            this.mMap.addCircle(circle);
        }
    }

    @Override
    public void onClick(View view) {
        if (this.isMapReady) {
            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(this.currentLocation.getLatitude(), this.currentLocation.getLongitude())));
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        }
    }
}
