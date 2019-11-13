package com.androidschool.mvp_homework.presentation.view;

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
     */
    void showData();
}
