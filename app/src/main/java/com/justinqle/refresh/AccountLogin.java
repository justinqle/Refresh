package com.justinqle.refresh;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountLogin extends AppCompatActivity {

    private static final String TAG = "AccountLogin";

    private WebView webView;

    private static final String AUTH_URL =
            "https://www.reddit.com/api/v1/authorize.compact" +
                    "?client_id=%s" +
                    "&response_type=code" +
                    "&state=%s" +
                    "&redirect_uri=%s" +
                    "&duration=permanent" +
                    "&scope=read";

    private static final String CLIENT_ID = "tyVAE3jn8OsMlg";

    private static final String STATE = UUID.randomUUID().toString();

    private static final String REDIRECT_URI = "https://example.com";

    private static final String ACCESS_TOKEN_URL =
            "https://www.reddit.com/api/v1/access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Login to Reddit");
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);

        webView = findViewById(R.id.login);
        webView.setWebViewClient(new LoginWebViewClient());
        webView.loadUrl(String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI));
    }

    private class LoginWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(REDIRECT_URI)) {
                final Uri uri = Uri.parse(url);
                return handleUri(uri);
            } else {
                return false;
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            if (uri.toString().startsWith(REDIRECT_URI)) {
                return handleUri(uri);
            } else {
                return false;
            }
        }

        private boolean handleUri(final Uri uri) {
            Log.i(TAG, "Uri =" + uri);
            if (uri.getQueryParameter("error") != null) {
                String error = uri.getQueryParameter("error");
                Log.e(TAG, "An error has occurred : " + error);
                switch (error) {
                    case "access_denied":
                        Toast.makeText(getApplicationContext(), "Can not login without user approval", Toast.LENGTH_LONG).show();
                    case "unsupported_response_type":
                    case "invalid_scope":
                    case "invalid_request":
                    default:
                        break;
                }
            } else {
                String state = uri.getQueryParameter("state");
                if (state.equals(STATE)) {
                    String code = uri.getQueryParameter("code");
                    getUserAccessToken(code);
                }
            }
            finish();
            return true;
        }
    }

    private void getUserAccessToken(String code) {

        OkHttpClient client = new OkHttpClient();

        String authString = CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(),
                Base64.NO_WRAP);

        Request request = new Request.Builder()
                .addHeader("User-Agent", "Sample App")
                .addHeader("Authorization", "Basic " + encodedAuthString)
                .url(ACCESS_TOKEN_URL)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                        "grant_type=authorization_code&code=" + code +
                                "&redirect_uri=" + REDIRECT_URI))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "ERROR: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();

                Log.i(TAG, json);

                JSONObject data = null;
                try {
                    data = new JSONObject(json);
                    String accessToken = data.optString("access_token");
                    String refreshToken = data.optString("refresh_token");

                    Log.d(TAG, "Access Token = " + accessToken);
                    Log.d(TAG, "Refresh Token = " + refreshToken);

                    SharedPreferences sharedPref = getSharedPreferences("oauth", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("access_token", accessToken);
                    editor.putString("refresh_token", refreshToken);
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        CookieManager.getInstance().removeAllCookies(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
