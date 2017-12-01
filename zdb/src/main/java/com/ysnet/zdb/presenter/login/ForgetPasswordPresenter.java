package com.ysnet.zdb.presenter.login;

import com.tgnet.basemvp.IView;
import com.ysnet.zdb.presenter.BasePresenter;

/**
 * @author fan-gk
 * @date 2017/12/1.
 */
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordPresenter.IForgetPasswordView> {
    public ForgetPasswordPresenter(IForgetPasswordView iView) {
        super(iView);
    }

    public interface IForgetPasswordView extends IView {

    }
}
