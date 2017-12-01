package com.tgnet.app.utils.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.tgnet.app.utils.base.BaseFragment;
import com.tgnet.app.utils.utils.PresenterUtil;
import com.tgnet.basemvp.BasePresenter;
import com.tgnet.basemvp.IView;

/**
 * Created by fan-gk on 2017/4/21.
 */

public abstract class PresenterFragment<T extends BasePresenter<E>, E extends IView> extends BaseFragment {

    private T presenter;

    protected T getPresenter(){
        return presenter;
    }

    protected T createPresenter(){
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPresenter();

    }

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onViewCreate();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroy();
    }

    @Override
    @CallSuper
    public void onResume() {
        presenter.onViewResume();
        super.onResume();
    }



    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        presenter.onViewPause();
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        presenter.onViewStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewStop();
    }
}
