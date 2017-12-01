package com.tgnet.repositories;

/**
 * Created by fan-gk on 2017/4/20.
 */

public interface ISingleRepository<T> extends IWriteableSingleRepository<T> {
    T get();
}
