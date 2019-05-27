package com.h.myapplication.architecturecomponents.model;

import android.arch.lifecycle.LiveData;

import com.h.myapplication.architecturecomponents.bean.UserDao;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Response;
import com.h.myapplication.architecturecomponents.bean.User;

public class UserRepository {

    private final Webservice webservice;
    private final UserDao userDao;
    private final Executor executor;
    int FRESH_TIMEOUT = 60000;

    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.executor = executor;
    }


    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        // return a LiveData directly from the database.(从数据库中直接返回LiveData)
        return userDao.load(userId);
    }

    private void refreshUser(final String userId) {
        executor.execute(() -> {
            // running in a background thread(在后台线程运行)
            // check if user was fetched recently（检查数据库中是否有数据）

//            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
            if (!true) {
                // refresh the data
                Response response = null;
                try {
                    response = webservice.getUser(userId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // TODO check for error etc.
                // Update the database.The LiveData will automatically refresh so
                // we don't need to do anything else here besides updating the database(不用其他代码就能实现数据自动更新)
                userDao.save((User)response.body());
            }
        });
    }



}
