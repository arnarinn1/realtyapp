package is.arnar.realty.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import is.arnar.realty.utils.ConnectionListener;
import retrofit.RetrofitError;

public class RealtyFragment extends Fragment implements IRealtyView
{
    public RealtyFragment() {}

    @InjectView(R.id.realtyProgress) ProgressBar mProgressRealty;
    @InjectView(R.id.realtyImages)   ListView mListView;

    @InjectView(R.id.layoutNoConnection) FrameLayout mLayoutNoConnection;
    @InjectView(R.id.userInfoImage) ImageView mUserInfoImage;
    @InjectView(R.id.userInfoText) TextView mUserInfoText;

    private String mNoInternetConnectionStr;
    private String mNoDataStr;

    private RealtyPresenter Presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Notify the fragment to participate in populating the menu
        setHasOptionsMenu(true);

        GetStringValues();

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void Display(List<RealtyData> realties)
    {
        if (realties.isEmpty())
            ShowUserInformationLayout(UserInformationType.NoData);
        else
            mListView.setAdapter(new RealtyAdapter(Context(), realties));
    }

    @Override
    public RealtyData Model()
    {
        return null;
    }

    @Override
    public FragmentActivity Context()
    {
        return this.getActivity();
    }

    @Override
    public void ShowError(RetrofitError ex)
    {
        if (!ConnectionListener.isNetworkAvailable(Context()))
        {
            ShowUserInformationLayout(UserInformationType.NoConnection);
        }
    }

    private void ShowUserInformationLayout(UserInformationType type)
    {
        if (type == UserInformationType.NoConnection)
        {
            mUserInfoImage.setImageDrawable(getResources().getDrawable(R.drawable.no_internet));
            mUserInfoText.setText(mNoInternetConnectionStr);
        }
        else if (type == UserInformationType.NoData)
        {
            mUserInfoImage.setImageDrawable(getResources().getDrawable(R.drawable.filter));
            mUserInfoText.setText(mNoDataStr);
        }

        mLayoutNoConnection.setVisibility(View.VISIBLE);
    }

    @Override
    public void Busy(boolean isBusy)
    {
        mProgressRealty.setVisibility(isBusy ? View.VISIBLE : View.GONE);
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
        dialog.show((Context()).getFragmentManager(), "dialog");
    }

    private void GetStringValues()
    {
        mNoInternetConnectionStr = getActivity().getString(R.string.no_internet);
        mNoDataStr = getActivity().getString(R.string.no_data);
    }

    private void HideNoConnectionLayoutIfVisible()
    {
        if (mLayoutNoConnection.getVisibility() == View.VISIBLE)
            mLayoutNoConnection.setVisibility(View.GONE);
    }
}
