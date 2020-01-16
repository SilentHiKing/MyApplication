package com.h.myapplication.github;

import com.h.myapplication.base.viewstate.Result;
import com.h.myapplication.github.repo.RepoRepository;
import com.hiking.common.bean.login.UserRepo;
import com.hiking.common.util.TLog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.paging.PagedList.BoundaryCallback;
import androidx.paging.RxPagedListBuilder;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static com.h.myapplication.github.repo.RepoRepository.PAGING_REMOTE_PAGE_SIZE;

public class RepoViewModel extends ViewModel {


    RepoRepository mRepoRepository;
    public final Observable<PagedList<UserRepo>> pagedListObservable;

    public RepoViewModel() {
        mRepoRepository = RepoRepository.getIns();
        pagedListObservable = new RxPagedListBuilder<>(mRepoRepository.fetchRepoDataSourceFactory(), PAGING_REMOTE_PAGE_SIZE)
                .setBoundaryCallback(new RepoBoundaryCallback())
                .buildObservable()
        ;
    }

    public void refresh(){
        mRepoRepository.fetchRepoByPage(1).subscribe(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                dealResult(1, result);
            }
        });
    }

    private void dealResult(int index, Result result) {
        TLog.d("index=" + index);
        if (result instanceof Result.Success) {
            List<UserRepo> repos = (List<UserRepo>) result.get();
            if (index == 1) {
                mRepoRepository.clearAndInsertNewData(repos);
            } else {
                mRepoRepository.insertNewPageData(repos);
            }
        } else {
            Throwable t = (Throwable) result.get();
            TLog.d("请求结果失败 t=" + t.toString());
        }
    }

    class RepoBoundaryCallback extends BoundaryCallback<UserRepo> {


        public RepoBoundaryCallback() {
            super();
        }

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            mRepoRepository.fetchRepoByPage(1).subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result result) throws Exception {
                    dealResult(1, result);
                }
            });
        }


        @Override
        public void onItemAtFrontLoaded(@NonNull UserRepo itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull UserRepo itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
            int currentPageIndex = (itemAtEnd.indexInSortResponse / PAGING_REMOTE_PAGE_SIZE) + 1;
            int nextPageIndex = currentPageIndex + 1;
            mRepoRepository.fetchRepoByPage(nextPageIndex)
                    .subscribe(new Consumer<Result>() {
                        @Override
                        public void accept(Result result) throws Exception {
                            dealResult(nextPageIndex, result);
                        }
                    });
        }


    }
}
