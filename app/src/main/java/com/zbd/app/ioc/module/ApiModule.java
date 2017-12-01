package com.zbd.app.ioc.module;

import com.zbd.app.http.HttpApiProxyCreate;

import javax.inject.Singleton;

import dagger.Module;

/**
 * Created by fan-gk on 2017/4/19.
 */
@Singleton
@Module
public class ApiModule {
    private HttpApiProxyCreate creater;

    public ApiModule() {
        this.creater = new HttpApiProxyCreate();
    }



}
