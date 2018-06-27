package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RedditApi {

    @GET("/best")
    Call<Listing> getListing(
            @Query("limit") int limit
    );

    @GET("/best")
    Call<Listing> getListingAfter(
            @Query("after") String nextKey,
            @Query("limit") int limit
    );

    @GET("/api/v1/me")
    Call<Void> getUser();

}
