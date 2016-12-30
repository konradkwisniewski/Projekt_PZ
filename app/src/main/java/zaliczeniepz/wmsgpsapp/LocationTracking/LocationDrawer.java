package zaliczeniepz.wmsgpsapp.LocationTracking;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lenovo on 2016-12-30.
 */

public class LocationDrawer extends LocationTracker {

    private GoogleMap mMap;
    private Circle circle;

    private boolean isMapInit = false;

    public LocationDrawer(Context initContext) {
        super(initContext);
    }


    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        if (this.isMapInit) {
            if (location != null) {
                this.circle.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
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

    private void setmMap(GoogleMap newMMap){
        this.mMap = newMMap;
        this.isMapInit = true;
    }
}
