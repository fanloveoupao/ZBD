package com.zbd.app;

import android.content.Intent;
import android.net.Uri;

import com.tgnet.app.utils.base.BaseActivity;
import com.tgnet.app.utils.base.PresenterActivity;
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.ui.IIntentInterceptor;
import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.executor.ActionRequest;
import com.zbd.app.activity.LoginActivity;
import com.zbd.app.fragment.ActionReloadDialogFragment;
import com.zbd.app.ioc.component.DaggerPresenterComponent;
import com.zbd.app.ioc.component.PresenterComponent;

import com.ysnet.zdb.presenter.BasePresenter;

/**
 * @author fan-gk
 * @date 2017/11/30.
 */

public abstract class BasePresenterActivity<T extends BasePresenter<E>, E extends IView>
        extends PresenterActivity<T, E> {


    @Override
    protected T createPresenter() {
        T presenter = super.createPresenter();
        PresenterComponent component = DaggerPresenterComponent.builder()
                .daoMasterModule(((ZbdApplication) getApplication()).daoMasterModule)
                .build();
        injectPresenter(component, getPresenter());
        return presenter;
    }

    @Override
    protected void judgeExceptionCreate() {


    }

    @Override
    protected boolean canRotateScreen() {
        return false;
    }

    protected abstract void injectPresenter(PresenterComponent component, T presenter);

    @Override
    protected void onForceLogout() {

    }
    @Override
    protected void onTokenOutDate() {

    }

    @Override
    protected boolean launchWeb(Uri uri) {

        return true;
    }

    @Override
    public void onException(final ActionRequest request, NetworkException e) {
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {
                ActionReloadDialogFragment.singleShow(BasePresenterActivity.this, request);
            }
        });
    }


    public ZbdApplication getTgnetFrameApplication() {
        return (ZbdApplication) getApplication();
    }

    public boolean whetherLogin() {
       return false;
    }

    public void launchWithLoginState(final Class<? extends BaseActivity> activity) {
        launchWithLoginState(activity, null, false);
    }

    public void launchWithLoginState(final Class<? extends BaseActivity> activity, final boolean finish) {
        launchWithLoginState(activity, null, finish);
    }

    public void launchWithLoginState(final Class<? extends BaseActivity> activity, final IIntentInterceptor intentInterceptor) {
        launchWithLoginState(activity, intentInterceptor, false);
    }

    public void launchWithLoginState(final Class<? extends BaseActivity> activity, final IIntentInterceptor intentInterceptor, final boolean finish) {
        Intent intent;
        if (!whetherLogin()) {
            intent = new Intent(BasePresenterActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(BasePresenterActivity.this, activity);
        }
        launch(intent, intentInterceptor, finish);
    }

    public void launchWithLoginStateForResult(final Class<? extends BaseActivity> activity, final int requestCode) {
        launchWithLoginStateForResult(activity, requestCode, null);
    }

    public void launchWithLoginStateForResult(final Class<? extends BaseActivity> activity, final int requestCode, final IIntentInterceptor intentInterceptor) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!whetherLogin()) {
                    intent = new Intent(BasePresenterActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(BasePresenterActivity.this, activity);
                }
                launchForResult(intent, requestCode, intentInterceptor);
            }
        });
    }

    @Override
    protected void onPause() {
        //Session.onPause(this);
        super.onPause();
    }



    @Override
    protected void onResume() {
        // Session.onResume(this);
        super.onResume();
    }
}

