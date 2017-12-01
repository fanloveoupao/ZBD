package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.exceptions.TgnetException;
import com.tgnet.executor.ActionRunnable;
import com.tgnet.log.LoggerResolver;

/**
 * Created by fan-gk on 2017/4/20.
 */

public abstract class BasePresenter<E extends IView> extends com.tgnet.basemvp.BasePresenter<E> {


    public BasePresenter(E iView) {
        super(iView);
    }

    public final void initUser() {
        newActionBuilder().setRunnable(new ActionRunnable() {
            @Override
            public void run() throws Exception {
                LoggerResolver.getInstance().debug("initUser", "Start");

            }
        }).setRunBackground().run();

    }

    public void initUserRelation() {
        newActionBuilder().setRunnable(new ActionRunnable() {
            @Override
            public void run() throws Exception {
                LoggerResolver.getInstance().debug("initUserRelation", "Start");

            }
        }).setRunBackground().run();
    }


    protected void onVisitorModeEntry() {

    }

    protected void onInitUserFailed(TgnetException e) {
        getView().onException(e);
    }

    protected void onInitUserFailed(NetworkException e) {
        getView().onException(e);
    }

    protected void onInitUserSuccessed() {

    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
    }




}
