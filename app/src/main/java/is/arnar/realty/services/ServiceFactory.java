package is.arnar.realty.services;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.ApacheClient;

public class ServiceFactory
{
    private static final String RealtyApiBaseUrl = "http://arnarh.com/realtyapi/public";

    private static RestAdapter _adapter;

    public static RestAdapter GetRestAdapter()
    {
        if(_adapter == null)
        {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestInterceptor.RequestFacade request)
                {
                    request.addHeader("Content-Type", "application/json");
                    request.addHeader("Accept", "application/json");
                }
            };

            _adapter = new RestAdapter.Builder()
                    .setEndpoint(RealtyApiBaseUrl)
                    .setClient(new ApacheClient())
                    .setRequestInterceptor(requestInterceptor)
                    .build();
        }

        return _adapter;
    }
}
