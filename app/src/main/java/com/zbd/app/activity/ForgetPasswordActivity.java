package com.zbd.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.login.ForgetPasswordPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends BasePresenterActivity<ForgetPasswordPresenter, ForgetPasswordPresenter.IForgetPasswordView>
        implements ForgetPasswordPresenter.IForgetPasswordView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("找回密码");
        mTitleBar.setBackClick(this);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, ForgetPasswordPresenter presenter) {
        component.inject(presenter);
    }


}
