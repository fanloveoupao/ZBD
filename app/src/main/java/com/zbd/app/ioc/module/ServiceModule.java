package com.zbd.app.ioc.module;

import com.tgnet.app.utils.utils.repositories.DiskLruCacheProvicder;
import com.tgnet.cachebean.SharedPreferenceSettings;
import com.tgnet.repositories.CacheStoreProvider;
import com.tgnet.ui.ClientSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fan-gk on 2017/4/19.
 */
@Module(includes = ApiModule.class)
public class ServiceModule {
    @Provides
    @Singleton
    public CacheStoreProvider provideCacheStoreProvider() {
        return new DiskLruCacheProvicder("tgnet-frame", 50 * 1024 * 1024);//50M
    }


    @Provides
    public ClientSettings provideClientSettings() {
        return new ClientSettings();
    }

    @Provides
    public SharedPreferenceSettings provideSharePreferenceSettings() {
        return new SharedPreferenceSettings();
    }


}
