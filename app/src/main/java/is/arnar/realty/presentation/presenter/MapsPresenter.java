package is.arnar.realty.presentation.presenter;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.presentation.view.IMapsView;
import is.arnar.realty.services.IRealtyService;
import is.arnar.realty.systems.Prefs;
import is.arnar.realty.systems.SystemFacade;
import is.arnar.realty.ui.FilterDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapsPresenter extends BasePresenter<IMapsView, IRealtyService, RealtyData>
{
    public MapsPresenter(IMapsView view)
    {
        super(view, SystemFacade.GetRealtyService());
    }

    public void GetRealties()
    {
        double priceFrom = PriceQueryParam(FilterDialog.Q_LOWER_PRICE_RANGE, 0);
        double priceTo = PriceQueryParam(FilterDialog.Q_UPPER_PRICE_RANGE, 100);

        String lowerRoom = Prefs.with(View.Context()).GetString(FilterDialog.Q_LOWER_ROOM_RANGE, "1");
        String upperRoom = Prefs.with(View.Context()).GetString(FilterDialog.Q_UPPER_ROOM_RANGE, "9");

        System.QueryRealties(priceFrom,
                priceTo,
                lowerRoom,
                upperRoom,
                RealtyCodeQueryString(),
                RealtyTypeQueryString(),
                callback);
    }

    Callback<List<RealtyData>> callback = new Callback<List<RealtyData>>()
    {
        @Override
        public void success(final List<RealtyData> realties, Response response)
        {
            View.Display(realties);

            View.Busy(false);
        }

        @Override
        public void failure(RetrofitError retrofitError)
        {
            View.Busy(false);

            View.ShowError(retrofitError);
        }
    };
}
