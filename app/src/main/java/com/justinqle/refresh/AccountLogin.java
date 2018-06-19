package com.justinqle.refresh;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AccountLogin extends AppCompatActivity {

    private WebView webView;
    private static final String AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=tyVAE3jn8OsMlg&response_type=code&state=RANDOM_STRING&redirect_uri=http://localhost:8080&duration=permanent&scope=read";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Login to Reddit");
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setDisplayShowHomeEnabled(true);

        webView = findViewById(R.id.login);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(AUTH_URL);
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
