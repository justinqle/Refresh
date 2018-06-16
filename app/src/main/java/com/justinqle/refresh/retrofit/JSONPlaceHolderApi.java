package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("/.json")
    Call<Listing> getListing();

    @GET("/.json")
    Call<Listing> getListingAfter(
            @Query("after") String nextKey
    );

}
