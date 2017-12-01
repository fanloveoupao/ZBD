package com.ysnet.zdb.presenter.login;

import com.tgnet.basemvp.IView;
import com.ysnet.zdb.presenter.BasePresenter;

/**
 * @author fan-gk
 * @date 2017/12/1.
 */
public class LoginPresenter extends BasePresenter<LoginPresenter.LoginView> {
    public LoginPresenter(LoginView iView) {
        super(iView);
    }

    public interface LoginView extends IView {

    }
}
