package com.justinqle.refresh.networking;

import com.justinqle.refresh.models.listing.Listing;
import com.justinqle.refresh.models.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApi {

    @GET("/{sort}")
    Call<Listing> getFrontpageListing(
            @Path("sort") String sort,
            @Query("limit") int limit,
            @Query("after") String nextKey
    );

    @GET("/r/{subreddit}/{sort}")
    Call<Listing> getSubredditListing(
            @Path("subreddit") String subreddit,
            @Path("sort") String sort,
            @Query("limit") int limit,
            @Query("after") String nextKey
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
