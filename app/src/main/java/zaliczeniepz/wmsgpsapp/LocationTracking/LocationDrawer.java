package zaliczeniepz.wmsgpsapp.LocationTracking;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import zaliczeniepz.wmsgpsapp.MapsActivity;


/**
 * Created by Lenovo on 2016-12-30.
 */

public class LocationDrawer extends LocationTracker {

    private GoogleMap mMap;
    private Circle circle;

    private boolean isMapInit = false;
    private boolean isTracingEnabled = true;

    private Context context;

    public LocationDrawer(Context initContext) {
        super(initContext);
        this.context = initContext;
    }

    private static final String TAG = LocationDrawer.class.getSimpleName();

    public boolean isTracingEnabled() {
        return isTracingEnabled;
    }

    public void setTracingEnabled(boolean tracingEnabled) {
        isTracingEnabled = tracingEnabled;
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        if (this.isMapInit) {
            if (location != null) {
                this.circle.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
            }
            if (isTracingEnabled) {
                setCurrentPositionOnMap();
            }
        }
    }

    public void initMapCircle(GoogleMap initMap) {
        this.setmMap(initMap);

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(0, 0));
        circleOptions.radius(15);
        circleOptions.fillColor(Color.BLUE);
        circleOptions.strokeWidth(3);
        circleOptions.strokeColor(Color.WHITE);
        circleOptions.clickable(false);
        circleOptions.visible(true);

        this.circle = this.mMap.addCircle(circleOptions);
    }

    public void setCurrentPositionOnMap() {
        this.mMap.setOnCameraMoveStartedListener(null);
        if (this.isConnected()) {
            try {
                this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(this.getCurrentLocation().getLatitude(), this.getCurrentLocation().getLongitude())));
                this.mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 4000, null);

            } catch (SecurityException exc) {
                Log.i(TAG, "No GPS or Internet permissions");
            }
        }
        this.mMap.setOnCameraMoveStartedListener((MapsActivity) this.context);
    }

    private void setmMap(GoogleMap newMMap) {
        this.mMap = newMMap;
        this.isMapInit = true;
    }
}
