package com.example.android.waves;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Singleton
 */
public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://www.reddit.com";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

}
