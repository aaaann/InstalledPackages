package com.androidschool.mvp_homework.presentation.view.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.data.model.PackageModel;
import com.androidschool.mvp_homework.presentation.view.enums.SortMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InstalledPackagesAdapter extends RecyclerView.Adapter<InstalledPackagesAdapter.InstalledPackageViewHolder> {

    private List<PackageModel> mPackages = Collections.emptyList();
    private SortMode mSortMode = SortMode.SORT_BY_APP_NAME;

    @NonNull
    @Override
    public InstalledPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstalledPackageViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.package_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstalledPackageViewHolder holder, int position) {
        holder.bind(mPackages.get(position));
    }

    @Override
    public int getItemCount() {
        return mPackages.size();
    }

    public void setPackages(@NonNull List<PackageModel> packages) {
        mPackages = new ArrayList<>(packages);
        switch (mSortMode) {
            case SORT_BY_APP_NAME:
                groupByAppName(mPackages);
            case SORT_BY_PACKAGE_NAME:
                groupByPackageName(mPackages);
            default:
        }

        notifyDataSetChanged();
    }

    private void groupByAppName(List<PackageModel> packageModels) {
        Collections.sort(packageModels, (o1, o2) -> o1.getAppName().compareTo(o2.getAppName()));
        //packageModels.sort(Comparator.comparing(PackageModel::getAppName));
    }

    private void groupByPackageName(List<PackageModel> packageModels) {
        Collections.sort(packageModels, (o1, o2) -> o1.getAppPackageName().compareTo(o2.getAppPackageName()));
        //packageModels.sort(Comparator.comparing(PackageModel::getAppPackageName));
    }

    public void setSortMode(@NonNull SortMode sortMode) {
        mSortMode = sortMode;
        setPackages(mPackages);
    }


    static class InstalledPackageViewHolder extends RecyclerView.ViewHolder {
        private TextView mAppPackageName;
        private TextView mAppName;
        private ImageView mAppIcon;
        private ImageView mSystemIcon;

        InstalledPackageViewHolder(@NonNull View itemView) {
            super(itemView);

            mAppPackageName = itemView.findViewById(R.id.app_package_text_view);
            mAppName = itemView.findViewById(R.id.app_name_text_view);
            mAppIcon = itemView.findViewById(R.id.app_icon_image_view);
            mSystemIcon = itemView.findViewById(R.id.iv_is_system);

        }

        public void bind(PackageModel model) {
            mAppPackageName.setText(model.getAppPackageName());
            mAppName.setText(model.getAppName());
            mAppIcon.setImageDrawable(model.getAppIcon());
            if (model.isSystem())
                mSystemIcon.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
