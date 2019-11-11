package com.androidschool.mvp_homework.presentation.presenter;

import androidx.annotation.NonNull;

import com.androidschool.mvp_homework.data.model.PackageModel;
import com.androidschool.mvp_homework.data.repository.PackageModelRepository;
import com.androidschool.mvp_homework.presentation.view.IMainView;

import java.lang.ref.WeakReference;
import java.util.List;

public class PackagePresenter {
    private final WeakReference<IMainView> mMainActivityWeakReference;

    private final PackageModelRepository mPackageInstalledRepository;

    public PackagePresenter(@NonNull IMainView mainActivity,
                                     @NonNull PackageModelRepository packageInstalledRepository) {
        mMainActivityWeakReference = new WeakReference<>(mainActivity);
        mPackageInstalledRepository = packageInstalledRepository;
    }

    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync() {
        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().showProgress();
        }

        List<PackageModel> data = mPackageInstalledRepository.getData(true);

        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().hideProgress();

            mMainActivityWeakReference.get().showData(data);
        }
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync() {
        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().showProgress();
        }

        PackageModelRepository.OnLoadingFinishListener onLoadingFinishListener = packageModels -> {
            if (mMainActivityWeakReference.get() != null) {
                mMainActivityWeakReference.get().hideProgress();
                mMainActivityWeakReference.get().showData(packageModels);
            }
        };

        mPackageInstalledRepository.loadDataAsync(true, onLoadingFinishListener);
    }

    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        mMainActivityWeakReference.clear();
    }
}
