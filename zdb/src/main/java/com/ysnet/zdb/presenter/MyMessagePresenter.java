package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;

/**
 * @author fan-gk
 * @date 2017/12/6.
 */
public class MyMessagePresenter extends BasePresenter<MyMessagePresenter.IMyMessageView> {

    public MyMessagePresenter(IMyMessageView iView) {
        super(iView);
    }

    public interface IMyMessageView extends IView {

    }

    private final int limit = 20;
    private int start = 0;


    public int getLimit() {
        return limit;
    }
}
