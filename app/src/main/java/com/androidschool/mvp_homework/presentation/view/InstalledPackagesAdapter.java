package com.androidschool.mvp_homework.presentation.view;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.data.model.PackageModel;

import java.util.List;

public class InstalledPackagesAdapter extends RecyclerView.Adapter<InstalledPackagesAdapter.InstalledPackageViewHolder> {

    private List<PackageModel> mPackages;

    public InstalledPackagesAdapter(@NonNull List<PackageModel> packages) {
        this.mPackages = packages;
    }

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

    static class InstalledPackageViewHolder extends RecyclerView.ViewHolder {
        private TextView mAppPackageName;
        private TextView mAppName;
        private ImageView mAppIcon;


        InstalledPackageViewHolder(@NonNull View itemView) {
            super(itemView);

            mAppPackageName = itemView.findViewById(R.id.app_package_text_view);
            mAppName = itemView.findViewById(R.id.app_name_text_view);
            mAppIcon = itemView.findViewById(R.id.app_icon_image_view);
        }

        public void bind(PackageModel model) {
            mAppPackageName.setText(model.getAppPackageName());
            mAppName.setText(model.getAppName());
            mAppIcon.setImageDrawable(model.getAppIcon());
        }
    }
}
