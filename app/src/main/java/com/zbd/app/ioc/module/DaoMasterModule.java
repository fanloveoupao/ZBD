package com.zbd.app.ioc.module;



import com.zbd.app.ZbdApplication;

import dagger.Module;

/**
 * Created by fan-gk on 2017/4/19.
 */
@Module
public class DaoMasterModule {

    public DaoMasterModule(ZbdApplication context) {
        this.context = context;
    }

    private ZbdApplication context;

}