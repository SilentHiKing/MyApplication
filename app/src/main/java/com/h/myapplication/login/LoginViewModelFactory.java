package com.h.myapplication.login;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    LoginRepository mLoginRepository;

    public LoginViewModelFactory(LoginRepository loginRepository) {
        mLoginRepository = loginRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        try {
            return modelClass.getConstructor(LoginRepository.class).newInstance(mLoginRepository);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}