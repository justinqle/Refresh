package com.justinqle.refresh.retrofit;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.justinqle.refresh.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Route;

import static android.content.Context.MODE_PRIVATE;

public class TokenAuthenticatorApp implements Authenticator {

    private static final String TAG = "TokenAuthenticatorApp";

    private String accessToken;

    @Override
    public Request authenticate(Route route, okhttp3.Response response) {
        if (accessToken.equals(response.request().header("Authorization"))) {
            return null; // If we already failed with this access token, don't retry.
        }
        Log.d(TAG, "Authenticating after HTTP error");
        Log.d(TAG, "HTTP error 401: Token expired");
        Log.d(TAG, "Route: " + route.toString());

        // Refresh access_token using a synchronous api request
        accessToken = getApplicationAccessTokenSync();

        Log.d(TAG, "Request URL: " + response.request().url());

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization", "bearer " + accessToken)
                .build();
    }

    /**
     * Synchronously returns application-only access token and writes it to storage (SharedPreferences).
     * Static, so it can also be used in PostDataSource to retrieve initial token.
     *
     * @return application-only access token
     */
    public static String getApplicationAccessTokenSync() {
        OkHttpClient client = new OkHttpClient();

        final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
        final String GRANT_TYPE_VALUE = "https://oauth.reddit.com/grants/installed_client";
        final String DEVICE_ID = UUID.randomUUID().toString();
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
                                "&device_id=" + DEVICE_ID))
                .build();

        Log.d(TAG, "Request for application-only access token: " + request.toString());

        String accessToken = null;
        // Synchronous http request (wait for it to finish before moving on to another task)
        try {
            String json = client.newCall(request).execute().body().string();
            Log.d(TAG, "JSON response of retrieving application-only access token: " + json);

            JSONObject data = new JSONObject(json);
            accessToken = data.optString("access_token");

            Log.d(TAG, "Access Token retrieved = " + accessToken);

            // Store access token in shared preferences for later retrieval
            setApplicationAccessTokenIntoStorage(accessToken);
        } catch (IOException io) {
            Log.e(TAG, "IOException attempting to retrieve application-only access token");
            io.printStackTrace();
        } catch (JSONException e) {
            Log.e(TAG, "JSONException attempting to retrieve application-only access token");
            e.printStackTrace();
        }

        return accessToken;
    }

    private static void setApplicationAccessTokenIntoStorage(String accessToken) {
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("oauth_app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", accessToken);
        editor.commit();
    }

    public static String getApplicationAccessTokenFromStorage() {
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("oauth_app", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", null);
    }

}
