package com.androidschool.mvp_homework.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.androidschool.mvp_homework.R;
import com.androidschool.mvp_homework.data.model.PackageModel;
import com.androidschool.mvp_homework.data.repository.PackageModelRepository;
import com.androidschool.mvp_homework.presentation.presenter.PackagePresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView{

    private RecyclerView mRecyclerView;
    private View mProgressFrameLayout;
    private FloatingActionButton mFabReload;
    private Spinner mSortSpinner;

    private PackagePresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        providePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsync();
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

    private void initViews() {
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mFabReload = findViewById(R.id.fab_reload);
        mFabReload.setOnClickListener(v -> {
            mMainPresenter.loadDataAsync();
        });

        mSortSpinner=findViewById(R.id.spinner_sort);


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
    public void showData(@NonNull List<PackageModel> modelList) {
        InstalledPackagesAdapter adapter =
                new InstalledPackagesAdapter(modelList);

        mRecyclerView.setAdapter(adapter);
    }
}
