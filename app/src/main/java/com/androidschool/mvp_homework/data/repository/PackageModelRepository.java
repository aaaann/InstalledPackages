package com.androidschool.mvp_homework.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.data.model.PackageModel;

import java.util.ArrayList;
import java.util.List;

public class PackageModelRepository {
    private final Context mContext;
    private final PackageManager mPackageManager;

    public PackageModelRepository(@NonNull Context context) {
        mContext = context;

        mPackageManager = context.getPackageManager();
    }

    public void loadDataAsync(boolean isSystem, @NonNull OnLoadingFinishListener onLoadingFinishListener) {
        LoadingPackagesAsyncTask loadingPackagesAsyncTask = new LoadingPackagesAsyncTask(onLoadingFinishListener);
        loadingPackagesAsyncTask.execute(isSystem);
    }

    public List<PackageModel> getData(boolean isSystem) {
        List<PackageModel> installedPackageModels = new ArrayList<>();

        for (String packageName : getInstalledPackages(isSystem)) {
            getAppSize(packageName);

            PackageModel installedPackageModel = new PackageModel(
                    getAppName(packageName), packageName, getAppIcon(packageName));

            installedPackageModels.add(installedPackageModel);
        }

        //todo: sort data with the given mode in parameters (add)
        return installedPackageModels;
    }

    private List<String> getInstalledPackages(boolean isSystem) {
        List<String> apkPackageName = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfoList = mPackageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resolveInfoList) {

            if (isSystem || !isSystemPackage(resolveInfo)) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                apkPackageName.add(activityInfo.applicationInfo.packageName);
            }
        }

        return apkPackageName;
    }

    private String getAppName(@NonNull String packageName) {
        String appName = "";
        ApplicationInfo applicationInfo;

        try {
            applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            appName = (String) mPackageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    private Drawable getAppIcon(@NonNull String packageName) {
        Drawable drawable;
        try {
            drawable = mPackageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
        }

        return drawable;
    }

    //данный метод не очень просто реализовать. здесь он нужен только для того, чтобы увеличить время загрузки и понаслаждаться работой презентера
    //по переключению видов.
    private int getAppSize(@NonNull String packageName) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private boolean isSystemPackage(@NonNull ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private class LoadingPackagesAsyncTask extends AsyncTask<Boolean, Void, List<PackageModel>> {

        private final OnLoadingFinishListener mOnLoadingFinishListener;

        LoadingPackagesAsyncTask(@NonNull OnLoadingFinishListener onLoadingFinishListener) {
            mOnLoadingFinishListener = onLoadingFinishListener;
        }

        @Override
        protected void onPostExecute(List<PackageModel> installedPackageModels) {
            super.onPostExecute(installedPackageModels);

            mOnLoadingFinishListener.onFinish(installedPackageModels);
        }

        @Override
        protected List<PackageModel> doInBackground(Boolean... booleans) {
            return getData(booleans[0]);
        }
    }

    public interface OnLoadingFinishListener {
        void onFinish(List<PackageModel> packageModels);
    }
}
