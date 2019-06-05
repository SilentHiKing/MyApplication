package com.hiking.annotation.singleton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Singleton {

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.CONSTRUCTOR)
    @interface Constructor {
    }
}
