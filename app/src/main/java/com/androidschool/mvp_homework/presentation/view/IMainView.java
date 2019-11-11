package com.androidschool.mvp_homework.presentation.view;

import androidx.annotation.NonNull;

import com.androidschool.mvp_homework.data.model.PackageModel;

import java.util.List;

public interface IMainView {
    /**
     * Показать ProgressBar.
     */
    void showProgress();

    /**
     * Скрыть ProgressBar.
     */
    void hideProgress();

    /**
     * Отобразить данные об установленных приложениях.
     *
     * @param modelList список приложений.
     */
    void showData(@NonNull List<PackageModel> modelList);
}
