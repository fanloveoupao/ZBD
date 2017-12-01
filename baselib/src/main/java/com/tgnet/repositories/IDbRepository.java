package com.tgnet.repositories;

import java.util.Collection;
import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */

public interface IDbRepository<T, K> {
    interface LongRepository<T> extends IDbRepository<T,Long>{ }
    interface StringRepository<T> extends IDbRepository<T,String>{ }

    void save(T entity);
    void save(Iterable<T> entities);
    void delete(T entity);
    void delete(Iterable<T> entites);
    void deleteByKey(K key);
    void deleteByKey(Iterable<K> keys);
    T load(K key);
    List<T> load(Collection<K> keys);
    boolean exists(K key);
}