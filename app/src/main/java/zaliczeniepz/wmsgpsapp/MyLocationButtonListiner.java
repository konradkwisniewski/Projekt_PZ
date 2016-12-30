package zaliczeniepz.wmsgpsapp;

import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import zaliczeniepz.wmsgpsapp.LocationTracking.LocationDrawer;

/**
 * Created by Lenovo on 2016-12-30.
 */

public class MyLocationButtonListiner implements View.OnClickListener {

    private GoogleMap mMap;
    private LocationDrawer locationDrawer;

    public MyLocationButtonListiner(GoogleMap initGoogleMap, LocationDrawer initLocationDrawer){
        this.locationDrawer = initLocationDrawer;
        this.mMap = initGoogleMap;
    }


    @Override
    public void onClick(View view) {

            this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(this.locationDrawer.getCurrentLocation().getLatitude(), this.locationDrawer.getCurrentLocation().getLongitude())));
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

    }
}
