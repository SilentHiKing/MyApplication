package com.hiking.common.network.rxjava.bean;

public class Either<A, B> {
    A a;
    B b;

    public Either<A, B> right(B b) {
        this.b = b;
        return this;
    }

    public Either<A, B> left(A a) {
        this.a = a;
        return this;
    }

    public A getLeft() {
        return a;
    }

    public B getRight() {
        return b;
    }

    public boolean isLeft() {
        return a != null;
    }

    public boolean isRight() {
        return b != null;
    }
}
