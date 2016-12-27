package zaliczeniepz.wmsgpsapp;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Lenovo on 2016-12-27.
 */

public class MyJobButton implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Kliknięto przycisk do wyboru zadania", Toast.LENGTH_LONG).show();
    }
}
