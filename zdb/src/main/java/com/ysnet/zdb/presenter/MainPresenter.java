package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;

/**
 * @author fan-gk
 * @date 2017/11/30.
 */
public class MainPresenter extends BasePresenter<MainPresenter.MainView> {
    public MainPresenter(MainView iView) {
        super(iView);
    }

    public interface MainView extends IView {

        void onVisitorMode();

        void onFirstOpen();
    }

    public void init() {
        getView().onVisitorMode();
    }
}
