package com.justinqle.refresh.retrofit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.justinqle.refresh.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private static final String TAG = "TokenAuthenticator";

    private String accessToken;

    @Override
    public Request authenticate(@NonNull Route route, @NonNull okhttp3.Response response) {
        Log.d(TAG, "Authenticating after HTTP error");
        Log.d(TAG, "HTTP error 401: Token expired");

        Request request = response.request();
        String header = request.header("Authorization");
        if (header != null) {
            if (header.equals(accessToken)) {
                Log.d(TAG, "Retry with this access token failed already");
                return null; // If we already failed with this access token, don't retry.
            }
        }

        // Refresh access_token using a synchronous api request
        accessToken = getAccessToken(PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getBoolean("logged_in", false));

        Log.d(TAG, "Request URL: " + response.request().url());

        // Add new header to rejected request and retry it
        return request.newBuilder()
                .header("Authorization", "bearer " + accessToken)
                .build();
    }

    /**
     * Synchronously returns access token and writes it to storage (SharedPreferences).
     *
     * @param loggedIn If the user is not logged in, retrieve an application-only access token. If they are logged in, retrieve a new user access token using the refresh token stored.
     * @return new access token
     */
    private static String getAccessToken(boolean loggedIn) {
        OkHttpClient client = new OkHttpClient();

        final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
        final String GRANT_TYPE_VALUE;
        final String SECOND_PARAM_KEY;
        final String SECOND_PARAM_VALUE;
        if (!loggedIn) {
            GRANT_TYPE_VALUE = "https://oauth.reddit.com/grants/installed_client";
            SECOND_PARAM_KEY = "&device_id=";
            // Device ID
            SECOND_PARAM_VALUE = UUID.randomUUID().toString();
        } else {
            GRANT_TYPE_VALUE = "refresh_token";
            // Refresh Token
            SECOND_PARAM_KEY = "&refresh_token=";
            SECOND_PARAM_VALUE = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getString("refresh_token", null);
        }
        final String CLIENT_ID = "tyVAE3jn8OsMlg";
        final String authString = CLIENT_ID + ":";
        final String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = new Request.Builder()
                .addHeader("User-Agent", "android:com.justinqle.refresh:v1.0.0 (by /u/doctor_re)")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=" + GRANT_TYPE_VALUE +
                                SECOND_PARAM_KEY + SECOND_PARAM_VALUE))
                .build();

        Log.d(TAG, "Request for access token: " + request.toString());

        String accessToken = null;
        // Synchronous http request (wait for it to finish before moving on to another task)
        try {

            ResponseBody responseBody = client.newCall(request).execute().body();
            if (responseBody != null) {
                String json = responseBody.string();
                Log.d(TAG, "JSON response of retrieving access token: " + json);

                JSONObject data = new JSONObject(json);
                accessToken = data.optString("access_token");

                Log.d(TAG, "Access Token retrieved = " + accessToken);

                // Store access token in shared preferences for later retrieval
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
                sharedPreferences.edit().putString("access_token", accessToken).apply();
            }
        } catch (IOException io) {
            Log.e(TAG, "IOException attempting to retrieve access token");
            io.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException attempting to retrieve access token");
            e.printStackTrace();
        }
        return accessToken;
    }

}
