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
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.presentation.presenter.RealtyPresenter;
import is.arnar.realty.presentation.view.IRealtyView;
import is.arnar.realty.ui.adapters.RealtyAdapter;
import retrofit.RetrofitError;

public class RealtyFragment extends Fragment implements IRealtyView
{
    public RealtyFragment() {}

    @InjectView(R.id.realtyProgress) ProgressBar mProgressRealty;
    @InjectView(R.id.realtyImages)   ListView mListView;

    private RealtyPresenter Presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Notify the fragment to participate in populating the menu
        setHasOptionsMenu(true);

        Presenter = new RealtyPresenter(this);
        RefreshData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
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
    }

    @Override
    public void Busy(boolean isBusy)
    {
        mProgressRealty.setVisibility(isBusy ? View.VISIBLE : View.GONE);
    }

    private void RefreshData()
    {
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
}
