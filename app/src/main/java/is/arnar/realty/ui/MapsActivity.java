package is.arnar.realty.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import is.arnar.realty.R;

public class MapsActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Initialize();
    }

    private void Initialize()
    {
        GoogleMap map;
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        final LatLng realtyLocation = new LatLng(64.084873, -21.915359);
        Marker marker = map.addMarker(new MarkerOptions()
                .position(realtyLocation)
                .title("Gata")
                .draggable(false));

        final LatLng loc = new LatLng(65.685474, -18.086897);
        Marker mark = map.addMarker(new MarkerOptions()
                .position(loc)
                .title("Vesturgata")
                .draggable(false));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(realtyLocation, 10));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_realty, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        switch (itemId)
        {
        }

        return true;
    }
}
