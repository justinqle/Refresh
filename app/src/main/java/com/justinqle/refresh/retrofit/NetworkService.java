package com.justinqle.refresh.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Singleton
 */
public class NetworkService {

    private static final String TAG = "NetworkService";

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://oauth.reddit.com";
    private static Retrofit mRetrofit;
    private static OkHttpClient okHttpClient;

    private NetworkService() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("User-Agent", "android:com.justinqle.refresh:v1.0.0 (by /u/doctor_re)")
                            .addHeader("Accept", "application/json")
                            .build();
                    return chain.proceed(request);
                }).authenticator(new TokenAuthenticatorApp())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public RedditApi getJSONApi() {
        return mRetrofit.create(RedditApi.class);
    }

}
