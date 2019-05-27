package com.h.myapplication.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.media.tv.TvContractCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestTvProviderFragement extends BaseFragment {
    private final static String TAG = "TestTvProviderFragement";

    @BindView(R.id.msg)
    TextView mMsg;

    @BindView(R.id.test)
    Button mTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_provider, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTest.requestFocus();
    }

    @OnClick(R.id.test)
    public void onClick() {

        run();


    }

    private void run() {
        ContentResolver contentResolver = getContext().getContentResolver();
        String msg = "";
        Log.d(TAG, "测试TvProvider");
        try {
            Cursor cursor = contentResolver.query(TvContractCompat.Channels.CONTENT_URI, null, null, null, null);
            Log.d(TAG, "cursor is null is " + (cursor == null));
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Log.d(TAG, "开始循环");
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("display_name"));
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    Log.d(TAG, "name=" + name + "    desc=" + desc);
                    msg = msg + "name=" + name + "    desc=" + desc + "\n";
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*new Thread() {
            @Override
            public void run() {
                super.run();
                ContentResolver contentResolver = getContext().getContentResolver();
                String msg = "";
                Log.d(TAG, "测试TvProvider");
                try {
                    Cursor cursor = contentResolver.query(TvContractCompat.Channels.CONTENT_URI, null, null, null, null);
                    Log.d(TAG, "cursor is null is " + (cursor == null));
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Log.d(TAG, "开始循环");
                            String name = cursor.getString(cursor.getColumnIndexOrThrow("display_name"));
                            String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                            Log.d(TAG, "name=" + name + "    desc=" + desc);
                            msg = msg + "name=" + name + "    desc=" + desc + "\n";
                        }
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String s = msg;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMsg.setText(s);
                    }
                });
//
            }
        }.start();*/
    }

}
