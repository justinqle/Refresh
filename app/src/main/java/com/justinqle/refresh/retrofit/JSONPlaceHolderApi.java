package com.justinqle.refresh.retrofit;

import com.justinqle.refresh.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {

    @GET("/.json")
    Call<Listing> getListing();

}
