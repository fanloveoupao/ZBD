package com.zbd.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.login.LoginPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BasePresenterActivity<LoginPresenter, LoginPresenter.LoginView>
        implements LoginPresenter.LoginView {


    @BindView(R.id.title_bar)
    TitleBar mTtitleBar;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mTtitleBar.setCenterText("登录");
        mTtitleBar.setBackClick(this);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, LoginPresenter presenter) {
        component.inject(presenter);
    }

    @OnClick({R.id.tv_login_forget_password, R.id.bt_login, R.id.bt_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget_password:
                launch(ForgetPasswordActivity.class, false);
                break;
            case R.id.bt_login:
                break;
            case R.id.bt_registration:
                launch(RegistrationActivity.class, false);
                break;
        }
    }
}
