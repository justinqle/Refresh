package com.justinqle.refresh;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mContext;

    public static MyApplication getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

}
