package com.justinqle.refresh.networking;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.justinqle.refresh.MyApplication;
import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.models.listing.Subreddit;
import com.justinqle.refresh.models.listing.Type;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Singleton
 */
public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://oauth.reddit.com";
    private static Retrofit mRetrofit;

    private NetworkService() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    final String accessToken = sharedPreferences.getString("access_token", null);
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "bearer " + accessToken)
                            .addHeader("User-Agent", "android:com.justinqle.refresh:v1.0.0 (by /u/doctor_re)")
                            .addHeader("Accept", "application/json")
                            .build();
                    return chain.proceed(request);
                }).authenticator(new TokenAuthenticator())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Type.class, new TypeDeserializer()).create()))
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

    private static class TypeDeserializer implements JsonDeserializer<Type> {

        @Override
        public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            // Link
            if (name.startsWith("t3")) {
                return context.deserialize(json, Post.class);
            }
            // Subreddit
            else if (name.startsWith("t5")) {
                return context.deserialize(json, Subreddit.class);
            }
            // None of the above
            else {
                return null;
            }
        }

    }

}
