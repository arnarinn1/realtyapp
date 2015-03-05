package is.arnar.realty.ui;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import is.arnar.realty.ui.enums.UserInformationType;
import is.arnar.realty.utils.ConnectionHandler;
import retrofit.RetrofitError;

public class MapsActivity extends BaseActivity implements IMapsView
{
    @InjectView(R.id.mapsLayout)   FrameLayout mLayout;
    @InjectView(R.id.mapsProgress) ProgressBar mProgressMaps;

    @InjectView(R.id.layoutNoConnection) FrameLayout mLayoutNoConnection;
    @InjectView(R.id.userInfoImage)      ImageView mUserInfoImage;
    @InjectView(R.id.userInfoText)       TextView mUserInfoText;

    private final LatLng mDefaultLocation = new LatLng(64.084873, -21.915359);

    private GoogleMap mMap;

    private MapsPresenter Presenter;

    @Override
    public void onCreate(Bundle savedInstanceState)
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
        mMap.setMyLocationEnabled(true);

        RefreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maps, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_refresh:
                RefreshData();
                return true;
            case R.id.action_filter:
                ShowFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Context Context()
    {
        return this;
    }

    @Override
    public void ShowError(RetrofitError ex)
    {
        if (!ConnectionHandler.isNetworkAvailable(Context()))
        {
            ShowUserInformationLayout(UserInformationType.NoConnection);
        }
    }

    @Override
    public void Busy(boolean isBusy)
    {
        mProgressMaps.setVisibility(isBusy ? View.VISIBLE : View.GONE);
    }

    @Override
    public void Display(List<RealtyData> realties)
    {
        if (realties.isEmpty())
        {
            ShowUserInformationLayout(UserInformationType.NoData);
            return;
        }

        mLayout.setVisibility(View.VISIBLE);

        AddRealtyMarkersToMap(realties);

        LatLng latLng = GetCurrentLocation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

        mLayout.setVisibility(View.VISIBLE);
    }

    /*
     Region - Private
     */

    private void ShowUserInformationLayout(UserInformationType type)
    {
        ShowUserInformationLayout(type, mUserInfoText, mUserInfoImage, mLayoutNoConnection);
    }

    private void RefreshData()
    {
        mLayout.setVisibility(View.INVISIBLE);
        mMap.clear();
        Presenter.WaitAndPerformAction(2000, new Action() {
            @Override
            public void PerformAction() {
                Presenter.GetRealties();
            }
        });
    }

    private void ShowFilter()
    {
        FilterDialog dialog = FilterDialog.newInstance(new Action() {
            @Override
            public void PerformAction() {
                RefreshData();
            }
        });
        dialog.show(getFragmentManager(), "dialog");
    }

    private LatLng GetCurrentLocation()
    {
        Location loc;
        try {
            loc = mMap.getMyLocation();
        }
        catch (Exception ex)
        {
            loc = null;
        }
        if(loc == null)
            return mDefaultLocation;

        return new LatLng(loc.getLatitude(), loc.getLongitude());
    }

    private void AddRealtyMarkersToMap(List<RealtyData> realties)
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
    }
}
