package com.ysnet.zdb.presenter.login;

import com.tgnet.basemvp.IView;
import com.ysnet.zdb.presenter.BasePresenter;

/**
 * @author fan-gk
 * @date 2017/12/1.
 */
public class RegistrationPresenter extends BasePresenter<RegistrationPresenter.IRegistrationView> {
    public RegistrationPresenter(IRegistrationView iView) {
        super(iView);
    }

    public interface IRegistrationView extends IView {

    }
}
