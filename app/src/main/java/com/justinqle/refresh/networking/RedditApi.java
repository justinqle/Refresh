package com.justinqle.refresh.networking;

import com.justinqle.refresh.models.listing.Listing;
import com.justinqle.refresh.models.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Call<User> getUser();

    @GET("/subreddits/{type}")
    Call<Listing> getSubreddits(
            @Path("type") String type,
            @Query("limit") int limit,
            @Query("after") String nextKey
    );
}
