package com.androidschool.mvp_homework.presentation.view;

import android.graphics.drawable.Drawable;

public interface IPackageRowView {

    void setAppPackageName(String name);
    void setAppName(String name);
    void setAppIcon(Drawable icon);
    void setSystemText(String text);
    //void setSystemIcon(Drawable icon);
}
