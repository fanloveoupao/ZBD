package com.zbd.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.share.SocialHelper;
import com.ysnet.share.callback.SocialShareCallback;
import com.ysnet.share.entities.ShareEntity;
import com.ysnet.share.entities.WBShareEntity;
import com.ysnet.share.entities.WXShareEntity;
import com.ysnet.zdb.presenter.ShareAppPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;
import com.zbd.app.utils.SocialUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareAppActivity extends BasePresenterActivity<ShareAppPresenter, ShareAppPresenter.IShareAppView>
        implements ShareAppPresenter.IShareAppView {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    private SocialHelper socialHelper;
    private SocialShareCallback socialShareCallback = new SocialShareCallback() {
        @Override
        public void shareSuccess() {

        }

        @Override
        public void socialError(String msg) {

        }
    };

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_share_app);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("推荐分享");
        mTitleBar.setBackClick(this);
        socialHelper = SocialUtil.getInstance().socialHelper();
    }

    @Override
    protected void injectPresenter(PresenterComponent component, ShareAppPresenter presenter) {
        component.inject(presenter);
    }

    @OnClick({R.id.iv_wx, R.id.iv_wx_circle, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wx:
                ShareEntity shareEntity = WXShareEntity.createWebPageInfo(false, "www.bai.com", R.mipmap.ic_launcher,
                        "租宝店", "美丽你我共享");
                socialHelper.shareWX(this, socialShareCallback, shareEntity);
                break;
            case R.id.iv_wx_circle:
                ShareEntity shareCircleEntity = WXShareEntity.createWebPageInfo(true, "www.bai.com", R.mipmap.ic_launcher,
                        "租宝店", "美丽你我共享");
                socialHelper.shareWX(this, socialShareCallback, shareCircleEntity);
                break;
            case R.id.iv_sina:
                ShareEntity shareWbEntity = WBShareEntity.createWebInfo("www.bai.com", "租宝店", "美丽你我共享", R.mipmap.ic_launcher
                        , "美丽你我共享");
                socialHelper.shareWX(this, socialShareCallback, shareWbEntity);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && socialHelper != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
            socialHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (socialHelper != null) {
            socialHelper.onNewIntent(intent);
        }
    }
}
