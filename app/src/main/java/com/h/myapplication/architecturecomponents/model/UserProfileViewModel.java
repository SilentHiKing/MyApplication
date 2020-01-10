package com.h.myapplication.architecturecomponents.model;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserProfileViewModel extends ViewModel {
    private String userId;

    private UserRepository userRepo;

    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    //private User user;
    private LiveData<User> user;
    public LiveData<User> getUser() {
        return user;
    }


    //初始化传递uid进来
    public void init(String userId) {
        this.userId = userId;
    }

    public void setUser(){
//        user = userRepo.getUser(userId);
    }



}

