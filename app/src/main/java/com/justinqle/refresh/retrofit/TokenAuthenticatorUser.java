/*
PLACEHOLDER
 */

//package com.justinqle.refresh.retrofit;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import com.justinqle.refresh.MainActivity;
//
//import java.io.IOException;
//
//import okhttp3.Route;
//import retrofit2.Response;/    private class TokenAuthenticatorApp implements Authenticator {
//
//        Context applicationContext = MainActivity.getContextOfApplication();
//        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("oauth_user", MODE_PRIVATE);
//        String accessToken = sharedPreferences.getString("access_token", null);
//
//        @Override
//        public Request authenticate(Route route, okhttp3.Response response) throws IOException {
//            if (response.code() == 401) {
//                //Call<Void> refreshCall = refreshAccessToken(refreshToken);
//
//                //make it as retrofit synchronous call
//                Response<Void> refreshResponse = refreshCall.execute();
//                if (refreshResponse != null && refreshResponse.code() == 200) {
//                    //read new JWT value from response body or interceptor depending upon your JWT availability logic
//                    newCookieValue = readNewJwtValue();
//                    return response.request().newBuilder()
//                            .header("basic-auth", newCookieValue)
//                            .build();
//                } else {
//                    return null;
//                }
//            }
//        }
//
//        private void getAccessToken() {
//
//        }
//
//        private void refreshAccessToken(String refreshToken) {
//
//        }
//    }