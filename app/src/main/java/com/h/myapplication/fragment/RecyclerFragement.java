package com.h.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.test.Book;
import com.h.myapplication.test.CusRecyclerAdapter;
import com.h.myapplication.test.CusRecyclerView;
import com.h.myapplication.test.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerFragement extends BaseFragment {


    @BindView(R.id.recycler_view)
    CusRecyclerView mCusRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
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
