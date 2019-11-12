package com.androidschool.mvp_homework.presentation.view.enums;

import androidx.annotation.StringRes;

import com.androidschool.mvp_homework.R;

public enum SortMode {

    SORT_BY_APP_NAME(R.string.sort_by_app_name),
    SORT_BY_PACKAGE_NAME(R.string.sort_by_package_name);

    @StringRes
    private final int mSortTextId;

    SortMode(@StringRes int textId) {
        mSortTextId = textId;
    }

    public int getSortTextId() {
        return mSortTextId;
    }
}
