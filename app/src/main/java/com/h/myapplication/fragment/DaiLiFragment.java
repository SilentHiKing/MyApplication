package com.h.myapplication.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.test.Book;
import com.hiking.common.arouter.ArouterPath;
import com.hiking.common.glide.GlideApp;
import com.hiking.common.network.rxjava.RxUtil;
import com.hiking.common.preloader.PreLoader;
import com.hiking.common.preloader.interfaces.DataListener;
import com.hiking.common.preloader.interfaces.DataLoader;
import com.hiking.common.sharepreference.SpHelper;
import com.hiking.common.util.BlockLogPrinter;
import com.hiking.common.util.TLog;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

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
        Looper.myLooper().setMessageLogging(new BlockLogPrinter());
        testLoad();
        testOkhttp();
        testGlide();
        new Thread().start();
        Handler  handler = new A();
        Handler  handler1 = new A() {
        };
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }){
            @Override
            public void run() {
                super.run();
            }
        }.start();
        getFragmentManager().beginTransaction().add(new DaiLiFragment(),"hfsdaf").commitNow();
        testAnimate();

    }

    private void testAnimate() {
        ObjectAnimator anim1 = ObjectAnimator.ofInt(img, "backgroundColor", Color.RED,Color.GRAY);
        anim1.setInterpolator(new AccelerateInterpolator());
        anim1.setEvaluator(new ArgbEvaluator());
        anim1.setDuration(3000); // 设置动画的播放时长
        anim1.start(); // 开始播放属性动画
        img.invalidate();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        valueAnimator.start();
    }


    static class A extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    private void testGlide() {
        Glide.with(getContext())
                .load("https://3-im.guokr.com/0lSlGxgGIQkSQVA_Ja0U3Gxo0tPNIxuBCIXElrbkhpEXBAAAagMAAFBO.png")
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .apply(RequestOptions.skipMemoryCacheOf(false))
                .into(new Target<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable com.bumptech.glide.request.Request request) {

                    }

                    @Nullable
                    @Override
                    public com.bumptech.glide.request.Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });

    }

    private void testOkhttp() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("www.google.com").build();
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void testLoad() {
        new Thread() {
            @Override
            public void run() {
                super.run();
            }
        }.start();
        int preLoaderId = PreLoader.preLoad(new Loader());
        PreLoader.listenData(preLoaderId, new DataListener<Object>() {
            @Override
            public void onDataArrived(Object o) {

            }
        });
    }

    //预加载任务：模拟网络接口请求获取数据
    class Loader implements DataLoader<String> {
        @Override
        public String loadData() {
            //此方法在线程池中运行，无需再开子线程去加载数据
            try {
                Thread.sleep(600);
            } catch (InterruptedException ignored) {
            }
            return "data from network server";
        }
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
