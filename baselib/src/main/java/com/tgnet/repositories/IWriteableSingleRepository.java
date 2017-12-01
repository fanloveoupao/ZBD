package com.tgnet.repositories;

/**
 * Created by fan-gk on 2017/4/20.
 */

public interface IWriteableSingleRepository<T> {
    void addOrReplace(T value);
    void clear();
}