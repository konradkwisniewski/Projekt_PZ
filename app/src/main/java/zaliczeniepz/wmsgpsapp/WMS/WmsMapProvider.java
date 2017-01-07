package zaliczeniepz.wmsgpsapp.WMS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;



//TODO: Pobierać przezroczysta bitmape.
//TODO: Wystawic WMS w mercator

public class WmsMapProvider extends AsyncTask<URL, Void, Bitmap> {

    private final String TAG = WmsMapProvider.class.getSimpleName();
    private final String wmsConnectionString = "http://195.191.184.8:8081/mvd/wms?";

    private GroundOverlayOptions groundOverlayOptions;
    private GroundOverlay groundOverlay;
    private boolean grountOverlayInitiated = false;
    private LatLngBounds currentBoundingBox = new LatLngBounds(new LatLng(0, 0), new LatLng(0, 0));

    private GoogleMap mMap = null;
    private String screenW;
    private String screenH;


    public WmsMapProvider(GoogleMap initMap, int initScreenW, int initScreenH) {
        this.mMap = initMap;
        this.screenH = Integer.toString(initScreenH);
        this.screenW = Integer.toString(initScreenW);
    }

    public URL getMapUrl(LatLngBounds boundingBox) {

        this.currentBoundingBox = boundingBox;

        String maxX = Double.toString(boundingBox.southwest.latitude);
        String maxY = Double.toString(boundingBox.southwest.longitude);
        String minX = Double.toString(boundingBox.northeast.latitude);
        String minY = Double.toString(boundingBox.northeast.longitude);


        try {
            URL url = new URL(this.wmsConnectionString +
                    "LAYERS=OBSZARY_PROJEKTOW&STYLES=&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&BBOX=" +
                    minX + "," +
                    minY + "," +
                    maxX + "," +
                    maxY +
                    "&HEIGHT=" + this.screenH + "&WIDTH=" + this.screenW + "&INFO_FORMAT=text/html&SRS=EPSG:2178");
            Log.i(TAG, url.toString());

            return url;
        } catch (MalformedURLException ex) {
            Log.e(TAG, ex.getMessage());
        }

        return null;
    }


    @Override
    protected Bitmap doInBackground(URL... urls) {
        Bitmap layer = null;
        if (urls.length == 1) {
            URL tempUrl = urls[0];
            if (tempUrl != null) {
                try {
                    layer = BitmapFactory.decodeStream(tempUrl.openConnection().getInputStream());
                    Log.i(TAG, "Pobraną nową bitmape, wielkość bitmapy: " + layer.getRowBytes() * layer.getHeight());
                } catch (IOException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
        }
        return layer;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (!this.grountOverlayInitiated) {
                this.initGroundOverlay(bitmap);
                this.grountOverlayInitiated = true;
            } else {
                this.updateGroundOverlay(bitmap);
            }
        }
    }

    private synchronized void initGroundOverlay(Bitmap initBitmap) {
        this.groundOverlayOptions = new GroundOverlayOptions();
        this.groundOverlayOptions.image(BitmapDescriptorFactory.fromBitmap(initBitmap));
        this.groundOverlayOptions.positionFromBounds(this.currentBoundingBox);

        this.groundOverlay = this.mMap.addGroundOverlay(this.groundOverlayOptions);
    }

    private synchronized void updateGroundOverlay(Bitmap layer) {
        this.groundOverlay.setImage(BitmapDescriptorFactory.fromBitmap(layer));
        this.groundOverlay.setPositionFromBounds(this.currentBoundingBox);
    }


}
