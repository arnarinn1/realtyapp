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
                       @Query("lower_room")  String lowerRoom,
                       @Query("upper_room")  String upperRoom,
                       @Query("realtyCodes") String realtyCodes,
                       @Query("realty_types") String realtyTypes,
                       Callback<List<RealtyData>> realties);
}