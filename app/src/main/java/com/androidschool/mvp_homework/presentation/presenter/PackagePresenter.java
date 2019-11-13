package com.androidschool.mvp_homework.presentation.presenter;

import androidx.annotation.NonNull;

import com.androidschool.mvp_homework.data.model.PackageModel;
import com.androidschool.mvp_homework.data.repository.PackageModelRepository;
import com.androidschool.mvp_homework.presentation.view.IMainView;
import com.androidschool.mvp_homework.presentation.view.IPackageRowView;
import com.androidschool.mvp_homework.presentation.view.enums.SortMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackagePresenter {
    private final WeakReference<IMainView> mMainActivityWeakReference;
    private final PackageModelRepository mPackageInstalledRepository;
    private List<PackageModel> mPackageModels = new ArrayList<>();
    private List<PackageModel> mPackageModelsAll = new ArrayList<>();
    private SortMode mSortMode = SortMode.SORT_BY_APP_NAME;
    private boolean mIsNeedSystem = true;


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

        mPackageModels = mPackageInstalledRepository.getData(true);

        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().hideProgress();

            mMainActivityWeakReference.get().showData();
        }
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync(boolean isNeedSystem) {
        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().showProgress();
        }

        PackageModelRepository.OnLoadingFinishListener onLoadingFinishListener = packageModels -> {
            if (mMainActivityWeakReference.get() != null) {
                mMainActivityWeakReference.get().hideProgress();
                mPackageModelsAll = packageModels;
                mPackageModels = packageModels;
                refreshShowSystem(mIsNeedSystem);
            }
        };

        mPackageInstalledRepository.loadDataAsync(isNeedSystem, onLoadingFinishListener);
    }

    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        mMainActivityWeakReference.clear();
    }

    public void onBindPackageRowViewAtPosition(int position, IPackageRowView rowView) {
        PackageModel model = mPackageModels.get(position);
        rowView.setAppName(model.getAppName());
        rowView.setAppPackageName(model.getAppPackageName());
        rowView.setAppIcon(model.getAppIcon());
        rowView.setSystemText(model.isSystem() ? "system" : null);
    }

    public int getPackagesRowsCount() {
        return mPackageModels.size();
    }

    public void refreshSort(SortMode sortMode) {
        mSortMode = sortMode;
        switch (mSortMode) {
            case SORT_BY_APP_NAME:
                sortByAppName(mPackageModels);
                break;
            case SORT_BY_PACKAGE_NAME:
                sortByPackageName(mPackageModels);
                break;
            default:
                break;
        }
        mMainActivityWeakReference.get().showData();
    }

    public void refreshShowSystem(boolean isNeedSystem) {
        mIsNeedSystem = isNeedSystem;
        mPackageModels = mIsNeedSystem ? mPackageModelsAll : getNotSystem();

        refreshSort(mSortMode);
    }

    private void sortByAppName(List<PackageModel> packageModels) {
        Collections.sort(packageModels, (o1, o2) -> o1.getAppName().compareTo(o2.getAppName()));
        //packageModels.sort(Comparator.comparing(PackageModel::getAppName));
    }

    private void sortByPackageName(List<PackageModel> packageModels) {
        Collections.sort(packageModels, (o1, o2) -> o1.getAppPackageName().compareTo(o2.getAppPackageName()));
        //packageModels.sort(Comparator.comparing(PackageModel::getAppPackageName));
    }

    private List<PackageModel> getNotSystem() {
        //filter
        List<PackageModel> packageModels = new ArrayList<>();
        for (PackageModel packageModel : mPackageModelsAll) {
            if (!packageModel.isSystem())
                packageModels.add(packageModel);
        }

        return packageModels;
    }
}
