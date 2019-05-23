package com.example.h.myapplication.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.h.myapplication.R;
import com.example.h.myapplication.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderFragement extends BaseFragment {
    private final static String TAG = "ProviderFragement";

    @BindView(R.id.uri)
    EditText mUri;

    @BindView(R.id.projection)
    EditText mProjection;
    @BindView(R.id.selection)
    EditText mSelection;
    @BindView(R.id.selection_args)
    EditText mSelectionArgs;
    @BindView(R.id.sort_oder)
    EditText mSortOder;

    @BindView(R.id.msg)
    TextView mMsg;

    @BindView(R.id.sure)
    Button sure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_provider, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sure.requestFocus();
    }

    @OnClick(R.id.sure)
    public void onClick() {

        run();


    }

    private void run(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String uri = mUri.getText().toString();

                String p = mProjection.getText().toString();
                String[] projection = TextUtils.isEmpty(p) ? null : p.split(",");
                String selection = mSelection.getText().toString();

                String sa = mSelectionArgs.getText().toString();
                String[] selectionArgs = TextUtils.isEmpty(sa) ? null : sa.split(",");

                String sortOder = mSortOder.getText().toString();
                Log.d(TAG, "uri=" + uri + ";    selection=" + selection + ";  p="+p+";    sa="+sa+";");
                if(selectionArgs != null){
                    Log.d(TAG, "selectionArgs="+selectionArgs[0]);
                }
                ContentResolver contentResolver = getContext().getContentResolver();
                String msg="";

                try{
                    Cursor cursor = contentResolver.query(Uri.parse(uri), projection, selection, selectionArgs, sortOder);

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndexOrThrow("suggest_text_1"));
                            String desc = cursor.getString(cursor.getColumnIndexOrThrow("suggest_text_2"));
                            Log.d(TAG, "name=" + name + "    desc=" + desc);
                            msg = msg+"name=" + name + "    desc=" + desc+"\n";
                        }
                        cursor.close();
                    }
                }catch (Exception e){
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
        }.start();
    }

}
