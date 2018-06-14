package com.example.android.waves.retrofit;

import com.example.android.waves.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {

    @GET("/.json")
    Call<Listing> getListing();

}
