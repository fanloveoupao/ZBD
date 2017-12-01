package com.tgnet.app.utils.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.tgnet.app.utils.utils.KeyBoardUtils;
import com.tgnet.app.utils.utils.PresenterUtil;
import com.tgnet.basemvp.BasePresenter;
import com.tgnet.basemvp.IView;


/**
 * Created by fan-gk on 2017/4/19.
 */

public abstract class PresenterActivity<T extends BasePresenter<E>, E extends IView> extends BaseActivity {


    private T presenter;

    protected T getPresenter() {
        return presenter;
    }

    protected T createPresenter() {
        presenter = PresenterUtil.createPresenter(this);
        return presenter;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        judgeExceptionCreate();
        if (!canRotateScreen())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createPresenter();
        initView(savedInstanceState);
        presenter.onViewCreate();
    }

    protected void judgeExceptionCreate() {

    }

    protected boolean canRotateScreen() {
        return true;
    }

    protected abstract void initView(@Nullable Bundle savedInstanceState);

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroy();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        presenter.onViewResume();
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        presenter.onViewPause();
        KeyBoardUtils.hideKeyBoard(this);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        presenter.onViewStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onViewStop();
    }
}
