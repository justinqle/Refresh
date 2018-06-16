package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("/.json")
    Call<Listing> getListing(
            @Query("limit") int limit
    );

    @GET("/.json")
    Call<Listing> getListingAfter(
            @Query("after") String nextKey,
            @Query("limit") int limit
    );

}
