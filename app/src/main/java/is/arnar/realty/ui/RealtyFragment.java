package is.arnar.realty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.presentation.presenter.RealtyPresenter;
import is.arnar.realty.presentation.view.IRealtyView;
import is.arnar.realty.ui.adapters.RealtyAdapter;
import is.arnar.realty.ui.enums.UserInformationType;
import is.arnar.realty.utils.ConnectionHandler;
import retrofit.RetrofitError;

public class RealtyFragment extends Fragment implements IRealtyView, AdapterView.OnItemClickListener
{
    public RealtyFragment() {}

    public static String EXTRA_REALTY = "is.arnar.realty.ui.REALTY";

    @InjectView(R.id.realtyProgress) ProgressBar mProgressRealty;
    @InjectView(R.id.realtyImages)   ListView mListView;

    @InjectView(R.id.layoutNoConnection) FrameLayout mLayoutNoConnection;
    @InjectView(R.id.userInfoImage) ImageView mUserInfoImage;
    @InjectView(R.id.userInfoText) TextView mUserInfoText;

    private RealtyPresenter Presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Notify the fragment to participate in populating the menu
        setHasOptionsMenu(true);

        mListView.setOnItemClickListener(this);

        Presenter = new RealtyPresenter(this);
        RefreshData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_realty, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_realty, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.action_maps:
                ShowMaps();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
     Region - IRealtyView Members
     */

    @Override
    public void Display(List<RealtyData> realties)
    {
        if (realties.isEmpty())
            ShowUserInformationLayout(UserInformationType.NoData);
        else
            mListView.setAdapter(new RealtyAdapter(Context(), realties));
    }

    @Override
    public Context Context()
    {
        return this.getActivity();
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
        mProgressRealty.setVisibility(isBusy ? View.VISIBLE : View.GONE);
    }

     /*
     Region - Event Handlers
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        RealtyData realty = (RealtyData) mListView.getAdapter().getItem(position);
        Intent intent = new Intent(Context(), DetailedRealtyActivity.class);
        intent.putExtra(EXTRA_REALTY, realty);
        startActivity(intent);
    }

    /*
     Region - Private
     */

    private void ShowUserInformationLayout(UserInformationType type)
    {
        ((MainActivity)getActivity()).ShowUserInformationLayout(
                type,
                mUserInfoText,
                mUserInfoImage,
                mLayoutNoConnection);
    }

    private void ShowMaps()
    {
        Intent intent = new Intent(Context(), MapsActivity.class);
        startActivity(intent);
    }

    private void HideNoConnectionLayoutIfVisible()
    {
        if (mLayoutNoConnection.getVisibility() == View.VISIBLE)
            mLayoutNoConnection.setVisibility(View.GONE);
    }

    private void RefreshData()
    {
        HideNoConnectionLayoutIfVisible();
        mListView.setAdapter(null);
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
        dialog.show(getActivity().getFragmentManager(), "dialog");
    }
}
