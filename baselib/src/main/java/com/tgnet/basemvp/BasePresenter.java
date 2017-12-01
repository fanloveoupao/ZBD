package com.tgnet.basemvp;

import com.tgnet.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/19.
 */

public abstract class BasePresenter<E extends IView> implements IPresenter<E> {
    private E iView;

    public BasePresenter(E iView) {
        this.iView = iView;
    }

    @Override
    public E getView() {
        return iView;
    }


    public void onViewCreate() {
    }

    /**
     * run in mian thread
     */
    public void onViewDestroy() {
    }

    /**
     * run in mian thread
     */
    public void onViewResume() {
    }

    /**
     * run in mian thread
     */
    public void onViewPause() {
    }

    /**
     * run in mian thread
     */
    public void onViewStart() {
    }

    /**
     * run in mian thread
     */
    public void onViewStop() {
    }

    protected ActionRequest.Builder newActionBuilder() {
        return new ActionRequest.Builder().setView(getView());
    }
}
