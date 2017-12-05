package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;

/**
 * @author fan-gk
 * @date 2017/12/5.
 */
public class BeVipPresenter extends BasePresenter<BeVipPresenter.IBeVipView> {
    public BeVipPresenter(IBeVipView iView) {
        super(iView);
    }

    public interface IBeVipView extends IView {

    }
}
