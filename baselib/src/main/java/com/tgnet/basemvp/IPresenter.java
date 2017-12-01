package com.tgnet.basemvp;

/**
 * Created by fan-gk on 2017/4/19.
 */

public interface IPresenter<T extends IView> {
    T getView();

    void onViewCreate();

    void onViewDestroy();
}
