package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RedditApi {

    @GET("/best")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "User-Agent: android:com.justinqle.refresh:v1.0.0 (by /u/doctor_re)"})
    Call<Listing> getListing(
            @Header("Authorization") String token,
            @Query("limit") int limit
    );

    @GET("/best")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "User-Agent: android:com.justinqle.refresh:v1.0.0 (by /u/doctor_re)"})
    Call<Listing> getListingAfter(
            @Header("Authorization") String token,
            @Query("after") String nextKey,
            @Query("limit") int limit
    );

}
