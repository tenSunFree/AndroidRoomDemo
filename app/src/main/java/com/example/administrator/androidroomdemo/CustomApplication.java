package com.example.administrator.androidroomdemo;

import android.app.Application;

/**
 * Created by hsu on 2017/6/11.
 */

public class CustomApplication extends Application {

    private static CustomApplication instance;
    public static int ContributionAdapterParentMeasuredHeight = 0;
    public static int ContributionAdapterParentMeasuredWidth = 0;

    public CustomApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
