package com.androidschool.mvp_homework.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.data.repository.PackageModelRepository;
import com.androidschool.mvp_homework.presentation.presenter.PackagePresenter;
import com.androidschool.mvp_homework.presentation.view.adapter.InstalledPackagesAdapter;
import com.androidschool.mvp_homework.presentation.view.adapter.SortModeSpinnerAdapter;
import com.androidschool.mvp_homework.presentation.view.enums.SortMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements IMainView {

    private RecyclerView mRecyclerView;
    private View mProgressFrameLayout;
    private FloatingActionButton mFabReload;
    private InstalledPackagesAdapter mAdapter;
    private Spinner mSortModeSpinner;
    private CheckBox mNeedSystemCheckBox;

    private PackagePresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providePresenter();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsync(mNeedSystemCheckBox.isChecked());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    private void providePresenter() {
        PackageModelRepository packageInstalledRepository =
                new PackageModelRepository(this);

        mMainPresenter = new PackagePresenter(this, packageInstalledRepository);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mAdapter = new InstalledPackagesAdapter(mMainPresenter);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViews() {
        initRecyclerView();
        mFabReload = findViewById(R.id.fab_reload);
        mFabReload.setOnClickListener(v -> {
            mMainPresenter.loadDataAsync(mNeedSystemCheckBox.isChecked()); //todo: remove parameter
        });

        initSortSpinner();

        mNeedSystemCheckBox=findViewById(R.id.check_is_system);
        mNeedSystemCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> mMainPresenter.refreshShowSystem(isChecked));

        mProgressFrameLayout = findViewById(R.id.progress_frame_layout);
    }

    @Override
    public void showProgress() {
        mProgressFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        mAdapter.notifyDataSetChanged();
    }

    private void initSortSpinner() {
        mSortModeSpinner = findViewById(R.id.spinner_sort);
        mSortModeSpinner.setAdapter(new SortModeSpinnerAdapter());
        mSortModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SortMode selectedDisplayMode = SortMode.values()[position];
                mMainPresenter.refreshSort(selectedDisplayMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
