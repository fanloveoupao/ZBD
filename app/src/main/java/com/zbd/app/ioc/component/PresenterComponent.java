package com.zbd.app.ioc.component;


import com.ysnet.zdb.presenter.MainPresenter;
import com.ysnet.zdb.presenter.login.ForgetPasswordPresenter;
import com.ysnet.zdb.presenter.login.LoginPresenter;
import com.ysnet.zdb.presenter.login.RegistrationPresenter;
import com.zbd.app.ioc.module.DaoMasterModule;
import com.zbd.app.ioc.module.DbRepositoryModule;
import com.zbd.app.ioc.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by fan-gk on 2017/4/19.
 */
@Singleton
@Component(modules = {ServiceModule.class, DbRepositoryModule.class, DaoMasterModule.class})
public interface PresenterComponent {

    void inject(MainPresenter presenter);

    void inject(LoginPresenter presenter);

    void inject(RegistrationPresenter presenter);

    void inject(ForgetPasswordPresenter presenter);

}
