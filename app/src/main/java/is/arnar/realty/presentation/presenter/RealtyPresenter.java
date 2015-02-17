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
        Prefs.with(View.Context()).GetStringSet(FilterDialog.REALTY_CODES, new HashSet<String>());

        System.QueryRealties(1.0, 2000000000.0, GetRealtyCodeString(), callback);
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

    private String GetRealtyCodeString()
    {
        Set<String> codes = Prefs.with(View.Context()).GetStringSet(FilterDialog.REALTY_CODES, new HashSet<String>());
        String serviceCode = "";
        for(String code : codes)
        {
            serviceCode += code.replace(" ", "") + "-";
        }

        serviceCode = serviceCode.substring(0, serviceCode.length()-1);

        return serviceCode;
    }
}
