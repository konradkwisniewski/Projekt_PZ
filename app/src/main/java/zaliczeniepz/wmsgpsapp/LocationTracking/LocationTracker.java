package zaliczeniepz.wmsgpsapp.LocationTracking;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import zaliczeniepz.wmsgpsapp.R;

/**
 * Created by Lenovo on 2016-12-30.
 */

public class LocationTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = LocationTracker.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Context context;

    protected Location currentLocation;

    public LocationTracker(Context initContext) {
        this.context = initContext;

        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void start() {
        this.googleApiClient.connect();
    }

    public void stop() {
        this.googleApiClient.disconnect();
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
            Toast.makeText(this.context, R.string.No_permissions, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(this.TAG, "Connection suspended");
        this.googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(this.TAG, " Connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            this.currentLocation = location;
    }
}
