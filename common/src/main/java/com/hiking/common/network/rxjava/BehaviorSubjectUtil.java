package com.hiking.common.network.rxjava;

import io.reactivex.subjects.BehaviorSubject;

public class BehaviorSubjectUtil {

    public static void assertNoNullValue(BehaviorSubject bj) {
        if (bj.getValue() == null) {
            throw new NullPointerException("bj not contain value. >>>" + bj);
        }
    }
}
