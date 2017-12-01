package com.tgnet.app.utils.ui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.tgnet.app.utils.base.BaseDialogFragment;
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.ui.widget.ActionLoadingDialogFragment;
import com.tgnet.app.utils.utils.PresenterUtil;
import com.tgnet.basemvp.BasePresenter;
import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/27.
 */

public abstract class DialogPresenterFragment<T extends BasePresenter<E>, E extends IView> extends BaseDialogFragment
        implements IView {

    private T presenter;

    protected T getPresenter() {
        return presenter;
    }

    protected T createPresenter() {
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

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreate();
    }

    //region IView

    @Override
    public void showLoadingView(final ActionRequest request) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ActionLoadingDialogFragment.singleShow(DialogPresenterFragment.this, request);
            }
        });
    }

    @Override
    public void dismissLoadingView() {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ActionLoadingDialogFragment.dismiss(DialogPresenterFragment.this);
            }
        });
    }

    @Override
    public void onNeedLogin(boolean otherDevice) {
        if (getBaseActivity() != null)
            getBaseActivity().onNeedLogin(otherDevice);
    }

    @Override
    public void onException(ActionRequest request, NetworkException e) {
        if (getBaseActivity() != null)
            getBaseActivity().onException(request, e);
    }

    @Override
    public void onException(Exception e) {
        if (getBaseActivity() != null)
            getBaseActivity().onException(e);
    }

    @Override
    public void onException(Exception e, boolean finish) {
        if (getBaseActivity() != null)
            getBaseActivity().onException(e, finish);
    }

    @Override
    public void onWarn(String message) {
        if (getBaseActivity() != null)
            getBaseActivity().onWarn(message);
    }

    @Override
    public boolean viewDestroyed() {
        return getBaseActivity() != null && getBaseActivity().isDestroyed();
    }

    @Override
    public void runAction(ActionRequest request) {
        if (getBaseActivity() != null)
            getBaseActivity().runAction(request);
    }

    //endregion
}
