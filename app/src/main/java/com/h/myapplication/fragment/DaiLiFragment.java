package com.h.myapplication.fragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.test.Book;
import com.hiking.common.arouter.ArouterPath;
import com.hiking.common.glide.GlideApp;
import com.hiking.common.network.rxjava.RxUtil;
import com.hiking.common.sharepreference.SpHelper;
import com.hiking.common.util.TLog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class DaiLiFragment extends BaseFragment {

    public DaiLiFragment() {

    }

    @BindView(R.id.img)
    ImageView img;


    @OnClick(R.id.sure)
    public void click() {
        GlideApp.with(img).load("https://www.baidu.com/img/bd_logo1.png?qua=high").into(img);
        ARouter.getInstance().build(ArouterPath.PATH_LOGIN).navigation();
    }


    @Override
    public int generateLayoutResID() {
        return R.layout.fragment_daili;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
//        test();
    }

    private void test() {



        String url = "http://www.baidu.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                Response response = client.newCall(request).execute();
//                TLog.d(response.body().string());
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        TLog.d(response.body().string());
                    }
                });

                emitter.onNext("hello");
            }
        }).compose(RxUtil.<Object>bothIOTransformer()).subscribe();


    }


    private void initView() {

        SpHelper.getIns().init(getContext());
        SpHelper.getIns().put("hello", new Book("你好", 1));
        Log.d("hello11", "gggg");
        Book i = SpHelper.getIns().get("hello", Book.class);
        Log.d("hello", SpHelper.getIns().get("hello", Integer.class) + "ggg");
    }
}
