package zaliczeniepz.wmsgpsapp;

import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import zaliczeniepz.wmsgpsapp.LocationTracking.LocationDrawer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String LOG_TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;
    private FloatingActionButton myLocationButton;
    private FloatingActionButton myJobButton;
    private PermissionChecker permissionChecker;

    private LocationDrawer locationDrawer;

    private boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.permissionChecker = new PermissionChecker(this);
        this.locationDrawer = new LocationDrawer(this);

        this.myJobButton = (FloatingActionButton) findViewById(R.id.myJob_button);
        this.myJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, JobsActivity.class);
                startActivity(intent);
            }
        });

        this.myLocationButton = (FloatingActionButton) findViewById(R.id.myLocation_button);

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

        this.locationDrawer.initMapCircle(this.mMap);

        this.myLocationButton.setOnClickListener(new MyLocationButtonListiner(this.mMap, this.locationDrawer));

        if (!this.permissionChecker.isNetworkPermissionGranted())
            Toast.makeText(this, R.string.network_warning, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.locationDrawer.start();
    }

    @Override
    protected void onStop() {
        this.locationDrawer.stop();
        super.onStop();
    }

}
