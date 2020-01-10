package com.h.myapplication.bean;

public class Errors {

    public final static Throwable ERROR_NETWORK = new NetworkError();
    public final static Throwable ERROR_SINGLE = new SingleError();
    public final static Throwable ERROR_EMPTY_INPUT = new EmptyInputError();
    public final static Throwable ERROR_EMPTY_RESULTS = new EmptyResultsError();

    static public class NetworkError extends Throwable {
    }

    static public class EmptyInputError extends Throwable {
    }

    static public class EmptyResultsError extends Throwable {
    }

    static public class SingleError extends Throwable {
    }
}
