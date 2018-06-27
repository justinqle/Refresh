package com.justinqle.refresh.retrofit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.justinqle.refresh.architecture.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Route;

public class TokenAuthenticatorUser implements Authenticator {

    private static final String TAG = "TokenAuthenticatorUser";

    private String accessToken;

    @Override
    public Request authenticate(Route route, okhttp3.Response response) {
        Log.d(TAG, "Authenticating after HTTP error");
        Log.d(TAG, "HTTP error 401: Token expired");

        if (response.request().header("Authorization").equals(accessToken)) {
            Log.d(TAG, "Retry with this access token failed already");
            return null; // If we already failed with this access token, don't retry.
        }

        // Refresh access_token using a synchronous api request
        accessToken = getUserAccessToken();

        Log.d(TAG, "Request URL: " + response.request().url());

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization", "bearer " + accessToken)
                .build();
    }

    /**
     * Synchronously returns user access token and writes it to storage (SharedPreferences).
     *
     * @return user access token
     */
    private String getUserAccessToken() {
        OkHttpClient client = new OkHttpClient();

        final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
        final String GRANT_TYPE_VALUE = "refresh_token";
        final String REFRESH_TOKEN = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContextOfApplication()).getString("refresh_token", null);
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
                                "&refresh_token=" + REFRESH_TOKEN))
                .build();

        Log.d(TAG, "Request for user access token: " + request.toString());

        String accessToken = null;
        // Synchronous http request (wait for it to finish before moving on to another task)
        try {
            String json = client.newCall(request).execute().body().string();
            Log.d(TAG, "JSON response of retrieving user access token: " + json);

            JSONObject data = new JSONObject(json);
            accessToken = data.optString("access_token");

            Log.d(TAG, "Access Token retrieved = " + accessToken);

            // Store access token in shared preferences for later retrieval
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getContextOfApplication());
            sharedPreferences.edit().putString("access_token", accessToken).commit();
        } catch (IOException io) {
            Log.e(TAG, "IOException attempting to retrieve user access token");
            io.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException attempting to retrieve user access token");
            e.printStackTrace();
        }
        return accessToken;
    }
}
