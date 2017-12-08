package com.zbd.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zbd.app.ioc.component.PresenterComponent;

import com.ysnet.zdb.presenter.MainPresenter;


/**
 * @author tgnet
 */
public class MainActivity extends BasePresenterActivity<MainPresenter, MainPresenter.MainView>
        implements MainPresenter.MainView {

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        getPresenter().init();
    }

    @Override
    protected void injectPresenter(PresenterComponent component, MainPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void onVisitorMode() {
        onNeedLogin(false);
        finish();
    }

    @Override
    public void onFirstOpen() {

    }


}
