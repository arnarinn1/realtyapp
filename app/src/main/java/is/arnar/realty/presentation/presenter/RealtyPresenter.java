package is.arnar.realty.presentation.presenter;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.presentation.view.IRealtyView;
import is.arnar.realty.services.IRealtyService;
import is.arnar.realty.systems.SystemFacade;
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
        System.GetAllRealties(callback);
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
