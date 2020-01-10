package com.h.myapplication.base.viewstate;

public class Result<T> implements IViewState {
    T t;
    public final static Idle mIdle = new Idle();
    public final static Loading mLoading = new Loading();

    public T get(){
        return t;
    }

    public static <T extends Throwable> Failure getFailure(T t) {
        return new Failure(t);
    }

    public static <T extends Object> Success getSuccess(T data) {
        return new Success(data);
    }


    static public class Idle extends Result {

    }


    static public class Loading extends Result {
    }

    static public class Failure<T extends Throwable> extends Result {


        public Failure(T t) {
            this.t = t;
        }

    }

    static public class Success<T> extends Result {

        public Success(T t) {
            this.t = t;
        }
    }

}
