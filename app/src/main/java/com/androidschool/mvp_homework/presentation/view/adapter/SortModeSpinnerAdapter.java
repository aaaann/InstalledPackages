package com.androidschool.mvp_homework.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidschool.mvp_homework.presentation.view.enums.SortMode;

public class SortModeSpinnerAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return SortMode.values().length;
    }

    @Override
    public SortMode getItem(int position) {
        return SortMode.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            convertView.setTag(new SortModeHolder(convertView));
        }
        SortModeHolder holder = (SortModeHolder) convertView.getTag();
        int textResourceId = getItem(position).getSortTextId();
        holder.mSortLabel.setText(textResourceId);
        return convertView;
    }

    private class SortModeHolder {
        private final TextView mSortLabel;

        private SortModeHolder(View root) {
            mSortLabel = root.findViewById(android.R.id.text1);
        }
    }
}
