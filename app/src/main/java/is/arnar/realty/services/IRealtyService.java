package is.arnar.realty.services;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface IRealtyService
{
    @GET("/realty")
    void GetAllRealties(Callback<List<RealtyData>> realties);

    @GET("/realtyQuery")
    void QueryRealties(@Query("lower_price") double lowerPrice,
                       @Query("upper_price") double upperPrice,
                       @Query("realtyCodes") String realtyCodes,
                       Callback<List<RealtyData>> realties);
}