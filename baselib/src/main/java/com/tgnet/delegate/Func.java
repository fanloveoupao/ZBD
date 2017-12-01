package com.tgnet.delegate;

/**
 * Created by fan-gk on 2017/4/24.
 */

public abstract class Func<T> {
    public static <T> Func<T> create(final T value){
        return new Func<T>() {
            @Override
            public T run() {
                return value;
            }
        };
    }
    public abstract T run();
}
