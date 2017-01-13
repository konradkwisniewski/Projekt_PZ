package zaliczeniepz.wmsgpsapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import zaliczeniepz.wmsgpsapp.LocationTracking.LocationDrawer;


class TrackingButtonListener implements View.OnClickListener {

    private static final String TAG = TrackingButtonListener.class.getSimpleName();

    private LocationDrawer locationDrawer;
    private Context mapActivityCtx;
    private GoogleMap mMap;

    TrackingButtonListener(GoogleMap mMap, LocationDrawer initLocationDrawer, Context mapActivityCtx) {
        this.locationDrawer = initLocationDrawer;
        this.mapActivityCtx = mapActivityCtx;
        this.mMap = mMap;
    }


    @Override
    public void onClick(View view) {
        this.locationDrawer.setTracingEnabled(true);
        Toast.makeText(mapActivityCtx, "Position tracking enabled", Toast.LENGTH_SHORT).show();
        this.locationDrawer.setCurrentPositionOnMap();
    }
}
