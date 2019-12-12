package com.h.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.h.myapplication.R;
import com.h.myapplication.base.BaseFragment;
import com.h.myapplication.skin.Skin;
import com.h.myapplication.skin.SkinUtils;
import com.hiking.common.network.NetworkHelper;
import com.hiking.common.network.rxjava.lifecycle.RxLifecycle;
import com.hiking.common.skin.SkinManager;
import com.hiking.common.skin.util.SkinPreference;
import com.hiking.common.util.TLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class SkinFragement extends BaseFragment {
    private final static String TAG = SkinFragement.class.getSimpleName();


    @BindView(R.id.change_skin)
    Button change_skin;
    List<Skin> skins = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skin, container, false);
        ButterKnife.bind(this, view);
        skins.add(new Skin("e0893ca73a972d82bcfc3a5a7a83666d",
                "1111111.skin", "app_skin-debug.apk"));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        change_skin.requestFocus();
    }

    @OnClick(R.id.change_skin)
    public void onClick() {
        if (TextUtils.isEmpty(SkinPreference.getInstance().getSkin())) {
            //换肤
            Skin skin = skins.get(0);
            selectSkin(skin);
            SkinManager.getInstance().loadSkin(skin.path);
        } else {
            restore();
        }
        test();
    }

    private void test() {
        /*NetworkHelper.getIns().getUsers(1, 10)
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        TLog.d(responseBody.string());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        TLog.d(throwable.toString());
                        throwable.printStackTrace();
                    }
                });*/
        NetworkHelper.getIns().request()
                .compose(RxLifecycle.bind(this).toLifecycleTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        TLog.d(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        TLog.d(throwable.toString());
                    }
                });
    }

    private void selectSkin(Skin skin) {
        File theme = new File(getContext().getFilesDir(), "theme");
        TLog.d(theme.getAbsolutePath());
        if (theme.exists() && theme.isFile()) {
            theme.delete();
        }
        theme.mkdirs();
        File skinFile = skin.getSkinFile(theme);
        if (skinFile.exists()) {
            TLog.d(TAG, "SkinActivity selectSkin: " + "皮肤包已存在，开始换肤");
            return;
        }
        TLog.d(TAG, "SkinActivity selectSkin: " + "皮肤包不存在，开始下载");
        FileOutputStream fos = null;
        InputStream is = null;
        //临时文件
        File tempSkin = new File(skinFile.getParentFile(), skin.name + ".temp");
        try {
            fos = new FileOutputStream(tempSkin);
            //假设下载皮肤包
            is = getContext().getAssets().open(skin.url);
            byte[] bytes = new byte[10240];
            int len;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            //下载成功，将皮肤包信息insert已下载数据库
            TLog.d(TAG, "SkinActivity selectSkin: " + "皮肤包下载完成开始校验");
            //皮肤包的md5校验，防止下载文件损坏（但是会减慢速度，从数据库查询已下载皮肤表数据库中保留的md5字段）
            if (TextUtils.equals(SkinUtils.getSkinMD5(tempSkin), skin.md5) || true) {
                TLog.d(SkinUtils.getSkinMD5(tempSkin));
                TLog.d(TAG, "SkinActivity selectSkin: " + "检验成功，修改文件名");
                tempSkin.renameTo(skinFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempSkin.delete();
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void restore() {
        SkinManager.getInstance().loadSkin(null);
    }

}
