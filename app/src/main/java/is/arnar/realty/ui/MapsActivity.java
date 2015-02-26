package is.arnar.realty.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.presentation.presenter.MapsPresenter;
import is.arnar.realty.presentation.view.IMapsView;
import retrofit.RetrofitError;

public class MapsActivity extends ActionBarActivity implements IMapsView
{
    @InjectView(R.id.mapsLayout)   FrameLayout mLayout;
    @InjectView(R.id.mapsProgress) ProgressBar mProgressMaps;

    private GoogleMap mMap;

    private MapsPresenter Presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Initialize();
    }

    private void Initialize()
    {
        ButterKnife.inject(this);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Presenter = new MapsPresenter(this);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        RefreshData();
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

    private void RefreshData()
    {
        Presenter.WaitAndPerformAction(2000, new Action() {
            @Override
            public void PerformAction() {
                Presenter.GetRealties();
            }
        });
    }

    @Override
    public Context Context()
    {
        return this;
    }

    @Override
    public void ShowError(RetrofitError ex)
    {

    }

    @Override
    public void Busy(boolean isBusy)
    {
        mProgressMaps.setVisibility(isBusy ? View.VISIBLE : View.GONE);
    }

    @Override
    public void Display(List<RealtyData> realties)
    {
        for(RealtyData realty : realties)
        {
            final LatLng realtyLocation = new LatLng(realty.getLatitude(), realty.getLongitude());

            String title = realty.getName() + " - " + realty.getRealtyCode().getNameAndCode();

            mMap.addMarker(new MarkerOptions()
                    .position(realtyLocation)
                    .title(title)
                    .draggable(false));
        }

        final LatLng realtyLocation = new LatLng(64.084873, -21.915359);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(realtyLocation, 10));

        mLayout.setVisibility(View.VISIBLE);
    }
}
