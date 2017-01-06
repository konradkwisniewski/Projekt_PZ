package zaliczeniepz.wmsgpsapp.WMS;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Lenovo on 2017-01-06.
 * Klasa reprezentująca serwer WMS.
 * Nasłuchuje czy kamera zmienia swoje położenie i rysuje nowy podkład danych dla
 * aktualnej lokalizacji
 */

public class Wms implements GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap = null;
    private String wmsConnectionString = "http://195.191.184.8:8081/mvd/wms?";


    public Wms(GoogleMap initMap){
        this.mMap = initMap;
    }


    @Override
    public void onCameraIdle() {

    }
}
