package com.androidschool.mvp_homework.data.model;

import android.graphics.drawable.Drawable;

public class PackageModel {
    private String mAppName;
    private String mAppPackageName;
    private Drawable mAppIcon;
    private String mSystemLabel;
    private boolean mIsSystem;

    public PackageModel(String appName, String appPackageName, Drawable appIcon, boolean isSystem, String systemLabel) {
        this.mAppName = appName;
        this.mAppPackageName = appPackageName;
        this.mAppIcon = appIcon;
        this.mIsSystem = isSystem;
        this.mSystemLabel = systemLabel;
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

    public String getSystemLabel() {
        return mSystemLabel;
    }

    public boolean isSystem() {
        return mIsSystem;
    }
}
