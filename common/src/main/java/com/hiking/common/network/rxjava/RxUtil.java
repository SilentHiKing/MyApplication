package com.hiking.common.network.rxjava;

import android.app.Fragment;
import android.app.FragmentManager;

import com.hiking.common.network.rxjava.lifecycle.RxLifecycle;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    public static <T> ObservableTransformer<T, T> bindLifecycle(Object o) {
        if (o instanceof AppCompatActivity) {
            return RxLifecycle.bind((AppCompatActivity) o).toLifecycleTransformer();
        }
        if (o instanceof Fragment) {
            return RxLifecycle.bind((Fragment) o).toLifecycleTransformer();
        }
        if (o instanceof FragmentManager) {
            return RxLifecycle.bind((FragmentManager) o).toLifecycleTransformer();
        }
        if (o instanceof  androidx.fragment.app.Fragment) {
            return RxLifecycle.bind((androidx.fragment.app.Fragment) o).toLifecycleTransformer();
        }
        if(o instanceof androidx.fragment.app.FragmentManager){
            return RxLifecycle.bind((androidx.fragment.app.FragmentManager)o).toLifecycleTransformer();
        }
        throw new RuntimeException("请传入正确的生命周期对象");

    }

    public static <T> ObservableTransformer<T, T> singleClick() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.throttleFirst(500, TimeUnit.MILLISECONDS);
            }
        };
    }

    public static <T> ObservableTransformer<T, T> commonTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<T, T> bothIOTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> mainTransformer() {
        return upstream -> upstream.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> singleCommonTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> singleMainTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> singleBothIOTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}
