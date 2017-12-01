package com.tgnet.repositories;

/**
 * Created by fan-gk on 2017/4/20.
 */
public interface ISharedPreferencesRepositoryProvider {
    <T> ISingleRepository<T> GetSingleRepository(String sharedPreferencesName, String key, Class<T> classOfT);

    IWriteableSingleRepository GetWriteableSingleRepository(String sharedPreferencesName, String key);
}