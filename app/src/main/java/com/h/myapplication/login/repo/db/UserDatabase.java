package com.h.myapplication.login.repo.db;


import android.content.Context;

import com.h.myapplication.login.repo.entity.LoginUserInfo;
import com.hiking.common.bean.login.ReceivedEvent;
import com.hiking.common.bean.login.UserRepo;
import com.hiking.common.util.Util;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {ReceivedEvent.class, UserRepo.class, LoginUserInfo.class},
        version = 1
)
public abstract class UserDatabase extends RoomDatabase {
    private static volatile UserDatabase INSTANCE;

    public abstract UserReceivedEventDao userReceivedEventDao();

    public abstract UserReposDao userReposDao();

    public abstract LoginUserInfoDao loginUserInfoDao();

    public static UserDatabase getIns() {
        return getIns(Util.getApplicationByReflect());
    }

    public static UserDatabase getIns(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "UserDatabase.db")
                            .build();
                }

            }
        }
        return INSTANCE;
    }

}
