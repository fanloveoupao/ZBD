package com.zbd.app.fragment;

import com.tgnet.app.utils.ui.PresenterFragment;
import com.tgnet.basemvp.BasePresenter;
import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.executor.ActionRequest;
import com.zbd.app.ZbdApplication;
import com.zbd.app.ioc.component.DaggerPresenterComponent;
import com.zbd.app.ioc.component.PresenterComponent;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */
public abstract class IocPresenterFragment<T extends BasePresenter<E>, E extends IView> extends PresenterFragment<T, E> {

    @Override
    protected T createPresenter() {
        T presenter = super.createPresenter();
        ZbdApplication application = (ZbdApplication) getActivity().getApplication();
        PresenterComponent component = DaggerPresenterComponent.builder()
                .daoMasterModule(application.daoMasterModule)
                .build();
        injectPresenter(component, getPresenter());
        return presenter;
    }

    protected abstract void injectPresenter(PresenterComponent component, T presenter);


    @Override
    public void onException(ActionRequest request, NetworkException e) {
        ActionReloadDialogFragment.singleShow(this, request);
    }



}
