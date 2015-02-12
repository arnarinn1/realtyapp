package is.arnar.realty.services;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;
import retrofit.Callback;
import retrofit.http.GET;

public interface IRealtyService
{
    @GET("/realty")
    void GetAllRealties(Callback<List<RealtyData>> realties);
}