package com.zbd.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.ShareAppPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareAppActivity extends BasePresenterActivity<ShareAppPresenter, ShareAppPresenter.IShareAppView>
        implements ShareAppPresenter.IShareAppView {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;


    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_share_app);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("推荐分享");
        mTitleBar.setBackClick(this);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, ShareAppPresenter presenter) {
        component.inject(presenter);
    }

    @OnClick({R.id.iv_wx, R.id.iv_wx_circle, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wx:
                break;
            case R.id.iv_wx_circle:
                break;
            case R.id.iv_sina:
                break;
        }
    }
}
