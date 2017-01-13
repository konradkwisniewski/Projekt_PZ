package zaliczeniepz.wmsgpsapp;

import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import zaliczeniepz.wmsgpsapp.LocationTracking.LocationDrawer;


class MyLocationButtonListener implements View.OnClickListener {

    private static final String TAG = MyLocationButtonListener.class.getSimpleName();

    private GoogleMap mMap;
    private LocationDrawer locationDrawer;

    MyLocationButtonListener(GoogleMap initGoogleMap, LocationDrawer initLocationDrawer) {
        this.locationDrawer = initLocationDrawer;
        this.mMap = initGoogleMap;
    }


    @Override
    public void onClick(View view) {
        if (this.locationDrawer.isConnected()) {
            try {
                this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(this.locationDrawer.getCurrentLocation().getLatitude(), this.locationDrawer.getCurrentLocation().getLongitude())));
                this.mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

            } catch (SecurityException exc) {
                Log.i(TAG, "No GPS or Internet permissions");
            }
        }
    }
}
