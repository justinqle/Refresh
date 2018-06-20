package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("/best")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "User-Agent: Sample App"})
    Call<Listing> getListing(
            @Header("Authorization") String token,
            @Query("limit") int limit
    );

    @GET("/best")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "User-Agent: Sample App"})
    Call<Listing> getListingAfter(
            @Header("Authorization") String token,
            @Query("after") String nextKey,
            @Query("limit") int limit
    );

}
