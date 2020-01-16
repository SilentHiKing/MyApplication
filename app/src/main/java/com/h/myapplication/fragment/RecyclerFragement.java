package com.h.myapplication.fragment;

import android.os.Bundle;
import android.view.View;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.test.Book;
import com.h.myapplication.test.CusRecyclerAdapter;
import com.h.myapplication.test.CusRecyclerView;
import com.h.myapplication.test.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

public class RecyclerFragement extends BaseFragment {


    @BindView(R.id.recycler_view)
    CusRecyclerView mCusRecyclerView;



    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_recycler;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        CusRecyclerAdapter cusRecyclerAdapter = new CusRecyclerAdapter(initBooks());
        SpacesItemDecoration dividerItemDecoration = new SpacesItemDecoration(getContext(), layoutManager.getOrientation(),10);
        mCusRecyclerView.addItemDecoration(dividerItemDecoration);
        mCusRecyclerView.setLayoutManager(layoutManager);
        mCusRecyclerView.setAdapter(cusRecyclerAdapter);
    }


    private List<Book> initBooks() {
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book01 = new Book("Book"+i,R.mipmap.ic_launcher);
            list.add(book01);
            Book book02 = new Book("Book"+i,R.mipmap.ic_launcher);
            list.add(book02);
            Book book03 = new Book("Book"+i,R.mipmap.ic_launcher);
            list.add(book03);
        }
        return list;
    }
}
