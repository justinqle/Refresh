package com.justinqle.refresh.paging;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.justinqle.refresh.MainActivity;
import com.justinqle.refresh.models.Child;
import com.justinqle.refresh.models.Data;
import com.justinqle.refresh.models.Listing;
import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.retrofit.JSONPlaceHolderApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PostDataSource extends PageKeyedDataSource<String, Post> {

    private static final String TAG = "PostDataSource";

    // pass whatever dependencies are needed to make the network call
    private JSONPlaceHolderApi jsonPlaceHolderApi;

    private String accessToken;
    private String refreshToken;

    // define the type of data that will be emitted by this datasource
    PostDataSource(JSONPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;

        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("oauth", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
        refreshToken = sharedPreferences.getString("refresh_token", null);

        if (accessToken == null || refreshToken == null) {
            Log.d(TAG, "Getting new Application access token");
            getApplicationAccessToken();
        }
    }

    private void getApplicationAccessToken() {
        OkHttpClient client = new OkHttpClient();

        final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
        final String GRANT_TYPE_VALUE = "https://oauth.reddit.com/grants/installed_client";
        final String DEVICE_ID = UUID.randomUUID().toString();
        final String CLIENT_ID = "tyVAE3jn8OsMlg";

        String authString = CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=" + GRANT_TYPE_VALUE +
                                "&device_id=" + DEVICE_ID))
                .build();

        Log.d(TAG, "Request for access token: " + request.toString());

        try {
            String json = client.newCall(request).execute().body().string();
            Log.d(TAG, "JSON response of retrieving Application-only access token: " + json);

            JSONObject data = null;
            data = new JSONObject(json);
            String accessToken = data.optString("access_token");
            String refreshToken = data.optString("refresh_token");

            Log.d(TAG, "Access Token retrieved = " + accessToken);
            Log.d(TAG, "Refresh Token retrieved = " + refreshToken);

            this.accessToken = accessToken;
            this.refreshToken = refreshToken;

            SharedPreferences sharedPref = MainActivity.getContextOfApplication().getSharedPreferences("oauth", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("access_token", accessToken);
            editor.putString("refresh_token", refreshToken);
            editor.commit();
        } catch (IOException io) {
            Log.e(TAG, "IOException attempting to retrieve application-only access token");
            io.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException attempting to retrieve application-only access token");
            e.printStackTrace();
        }
    }

    private static void backgroundThreadShortToast(final String msg) {
        if (msg != null) {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.getContextOfApplication(), msg, Toast.LENGTH_LONG).show());
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        Log.d(TAG, "(Load Initial) Access Token = " + accessToken);
        jsonPlaceHolderApi.getListing("bearer " + accessToken, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    Data data = listing.getData();
                    List<Child> children = data.getChildren();
                    List<Post> posts = new ArrayList<>();
                    for (Child child : children) {
                        posts.add(child.getData());
                    }
                    callback.onResult(posts, data.getBefore(), data.getAfter());
                } else {
                    Log.d(TAG, "Load Initial: Http response status code is " + response.code());
                    if (response.code() == 401) {
                        Log.i(TAG, "HTTP 401: Access token expired, refreshing token");
                        getApplicationAccessToken();
                        call.clone().enqueue(this);
                    } else {
                        Log.e(TAG, "Unresolved HTTP error");
                        backgroundThreadShortToast("Bad Request: HTTP Error " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load Initial: Network request failed");
                t.printStackTrace();
                backgroundThreadShortToast("Network request failed");
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        // ignored, since we only ever append to our initial load
    }

    // TODO Use rxJava, as this all runs on the main thread
    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {
        Log.d(TAG, "(Load After) Access Token = " + accessToken);
        jsonPlaceHolderApi.getListingAfter("bearer " + accessToken, params.key, params.requestedLoadSize).enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(@NonNull Call<Listing> call, @NonNull Response<Listing> response) {
                MainActivity.loading(false);
                Log.i(TAG, "Successfully connected to server");
                if (response.isSuccessful()) {
                    Log.i(TAG, "HTTP Response Code between 200-300");
                    Listing listing = response.body();
                    Data data = listing.getData();
                    List<Child> children = data.getChildren();
                    List<Post> posts = new ArrayList<>();
                    for (Child child : children) {
                        posts.add(child.getData());
                    }
                    callback.onResult(posts, data.getAfter());
                } else {
                    Log.d(TAG, "Load After: Http response status code is " + response.code());
                    if (response.code() == 401) {
                        Log.i(TAG, "HTTP 401: Access token expired, refreshing token");
                        getApplicationAccessToken();
                        call.clone().enqueue(this);
                    } else {
                        Log.e(TAG, "Unresolved HTTP error");
                        backgroundThreadShortToast("Bad Request: HTTP Error " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Listing> call, @NonNull Throwable t) {
                MainActivity.loading(false);
                Log.e(TAG, "Load After: Network request failed");
                t.printStackTrace();
                backgroundThreadShortToast("Network request failed");
            }
        });
    }
}
