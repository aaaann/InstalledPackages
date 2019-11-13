package com.androidschool.mvp_homework.presentation.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.presentation.presenter.PackagePresenter;
import com.androidschool.mvp_homework.presentation.view.IPackageRowView;

public class InstalledPackagesAdapter extends RecyclerView.Adapter<InstalledPackagesAdapter.InstalledPackageViewHolder> {

    private final PackagePresenter mPresenter;

    public InstalledPackagesAdapter(PackagePresenter presenter) {
        mPresenter = presenter;
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
        mPresenter.onBindPackageRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getPackagesRowsCount();
    }


    static class InstalledPackageViewHolder extends RecyclerView.ViewHolder implements IPackageRowView {
        private TextView mAppPackageName;
        private TextView mAppName;
        private ImageView mAppIcon;
        private TextView mSystemText;
        //private ImageView mSystemIcon;

        InstalledPackageViewHolder(@NonNull View itemView) {
            super(itemView);

            mAppPackageName = itemView.findViewById(R.id.app_package_text_view);
            mAppName = itemView.findViewById(R.id.app_name_text_view);
            mAppIcon = itemView.findViewById(R.id.app_icon_image_view);
            mSystemText = itemView.findViewById(R.id.tv_is_system);
            //mSystemIcon = itemView.findViewById(R.id.iv_is_system);
        }

        @Override
        public void setAppPackageName(String name) {
            mAppPackageName.setText(name);
        }

        @Override
        public void setAppName(String name) {
            mAppName.setText(name);
        }

        @Override
        public void setAppIcon(Drawable icon) {
            mAppIcon.setImageDrawable(icon);
        }

        @Override
        public void setSystemText(String text) {
            mSystemText.setText(text);
        }
    }
}
