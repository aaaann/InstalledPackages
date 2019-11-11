package com.androidschool.mvp_homework.data.model;

import android.graphics.drawable.Drawable;

public class PackageModel {
    private String mAppName;
    private String mAppPackageName;
    private Drawable mAppIcon;

    public PackageModel(String appName, String appPackageName, Drawable appIcon) {
        this.mAppName = appName;
        this.mAppPackageName = appPackageName;
        this.mAppIcon = appIcon;
    }

    public String getAppName() {
        return mAppName;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }
}
