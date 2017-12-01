package com.zbd.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.login.RegistrationPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends BasePresenterActivity<RegistrationPresenter, RegistrationPresenter.IRegistrationView>
        implements RegistrationPresenter.IRegistrationView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("注册");
        mTitleBar.setBackClick(this);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, RegistrationPresenter presenter) {
        component.inject(presenter);
    }

}
