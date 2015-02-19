package is.arnar.realty.presentation.presenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.presentation.view.IRealtyView;
import is.arnar.realty.services.IRealtyService;
import is.arnar.realty.systems.Prefs;
import is.arnar.realty.systems.SystemFacade;
import is.arnar.realty.ui.FilterDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RealtyPresenter extends BasePresenter<IRealtyView, IRealtyService, RealtyData>
{
    public RealtyPresenter(IRealtyView view)
    {
        super(view, SystemFacade.GetRealtyService());
    }

    public void GetRealties()
    {
        double priceFrom = PriceQueryParam(FilterDialog.Q_LOWER_PRICE_RANGE, 0);
        double priceTo = PriceQueryParam(FilterDialog.Q_UPPER_PRICE_RANGE, 100);

        String lowerRoom = Prefs.with(View.Context()).GetString(FilterDialog.Q_LOWER_ROOM_RANGE, "1");
        String upperRoom = Prefs.with(View.Context()).GetString(FilterDialog.Q_UPPER_ROOM_RANGE, "9");

        System.QueryRealties(priceFrom, priceTo, lowerRoom, upperRoom, RealtyCodeQueryString(), callback);
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

    private String RealtyCodeQueryString()
    {
        Set<String> codes = Prefs.with(View.Context()).GetStringSet(FilterDialog.REALTY_CODES, new HashSet<String>());
        if (codes.isEmpty()) return null;
        String serviceCode = "";
        for(String code : codes)
        {
            String[] strings = code.split("-");
            if (strings.length == 2)
            {
                serviceCode += strings[1].replace(" ", "") + "-";
            }
        }

        serviceCode = serviceCode.substring(0, serviceCode.length()-1);

        return serviceCode;
    }

    private double PriceQueryParam(String extraValue, int defValue)
    {
        int value = Prefs.with(View.Context()).GetInt(extraValue, defValue);
        double factor = 1000000.0;
        return (double) value * factor;
    }
}
