package com.riskyd.omahjamur.api;

import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.city.ResponseCity;
import com.riskyd.omahjamur.api.response.cost.ResponseCost;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RajaOngkirApiInterface {

    @GET("city")
    Call<ResponseCity> getCity();

    @GET("province")
    Call<ResponseCity> getProvince(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("cost")
    Call<ResponseCost> postCost(
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") int weight,
            @Field("courier") String courier
    );
}
