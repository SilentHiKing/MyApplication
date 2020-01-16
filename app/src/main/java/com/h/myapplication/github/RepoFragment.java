package com.h.myapplication.github;

import android.os.Bundle;
import android.view.View;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.hiking.common.bean.login.UserRepo;

import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class RepoFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RepoAdapter repoAdapter;
    RepoViewModel mRepoViewModel;

    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_repo;
    }


    @Override
    public void initEvent(View view, Bundle savedInstanceState) {
        super.initEvent(view, savedInstanceState);
        mRepoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        repoAdapter = new RepoAdapter();
        recyclerView.setAdapter(repoAdapter);
        addDisposable(mRepoViewModel.pagedListObservable.subscribe(new Consumer<PagedList<UserRepo>>() {
            @Override
            public void accept(PagedList<UserRepo> userRepos) throws Exception {
                repoAdapter.submitList(userRepos);
            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRepoViewModel.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
