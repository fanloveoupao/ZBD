package com.ysnet.zdb.presenter;

import com.tgnet.basemvp.IView;

/**
 * @author fan-gk
 * @date 2017/12/5.
 */
public class ShareAppPresenter extends BasePresenter<ShareAppPresenter.IShareAppView> {

    public ShareAppPresenter(IShareAppView iView) {
        super(iView);
    }

    public interface IShareAppView extends IView {

    }
}
