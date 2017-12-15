
package com.zbd.app.activity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.tgnet.app.utils.ui.widget.RadioButtonGroup;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.ysnet.zdb.presenter.IndexPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.fragment.IndexHomeFragment;
import com.zbd.app.fragment.IndexMessageFragment;
import com.zbd.app.fragment.IndexMineFragment;
import com.zbd.app.fragment.IndexShopCartFragment;
import com.zbd.app.fragment.IndexSortFragment;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexActivity extends BasePresenterActivity<IndexPresenter, IndexPresenter.IndexView>
        implements IndexPresenter.IndexView {


    private static final String INTERT_FRAGMENT_TAG_KEY = "fragment_tag";
    public static final String TAG_MESSAGE_FRAGMENT = "tag_message_fragment";
    public static final String TAG_CIRCLE_FRAGMENT = "tag_circle_fragment";
    public static final String TAG_MIME_FRAGMENT = "tag_mime_fragment";
    public static final String TAG_CONTACTS_FRAGMENT = "tag_contacts_ftagment";
    public static final String TAG_MESSAGES_FRAGMENT = "tag_messages_ftagment";
    @BindView(R.id.rdobtn_circle)
    RadioButton rdoBtnCircle;
    @BindView(R.id.rdobtn_message)
    RadioButton rdoBtnMessage;
    @BindView(R.id.rdobtn_msg)
    RadioButton rdoBtnMsg;
    @BindView(R.id.rdobtn_peopleHub)
    RadioButton rdoBtnHub;
    @BindView(R.id.rdobtn_mine)
    RadioButton rdoBtnMine;

    private IndexHomeFragment indexHomeFragment;
    private IndexSortFragment indexSortFragment;
    private IndexMessageFragment indexMessageFragment;
    private IndexShopCartFragment indexShopCartFragment;
    private IndexMineFragment indexMineFragment;


    private RadioButtonGroup.OnCheckedChangeListener tagCheckedChangedListener = new RadioButtonGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView) {
            FragmentTransaction mFt = getSupportFragmentManager().beginTransaction();
            Fragment currentFragment = null;
            switch (buttonView.getId()) {
                case R.id.rdobtn_circle:
                    if (indexHomeFragment == null) {
                        indexHomeFragment = new IndexHomeFragment();

                        mFt.add(R.id.v_framelayout, indexHomeFragment, TAG_CIRCLE_FRAGMENT);
                    }
                    currentFragment = indexHomeFragment;
                    break;
                case R.id.rdobtn_message:
                    if (indexShopCartFragment == null) {
                        indexShopCartFragment = new IndexShopCartFragment();
                        mFt.add(R.id.v_framelayout, indexShopCartFragment, TAG_MESSAGE_FRAGMENT);
                    }

                    currentFragment = indexShopCartFragment;
                    break;
                case R.id.rdobtn_msg:
                    if (indexMessageFragment == null) {
                        indexMessageFragment = new IndexMessageFragment();
                        mFt.add(R.id.v_framelayout, indexMessageFragment, TAG_MESSAGES_FRAGMENT);
                    }

                    currentFragment = indexMessageFragment;
                    break;
                case R.id.rdobtn_peopleHub:
                    if (indexSortFragment == null) {
                        indexSortFragment = new IndexSortFragment();
                        mFt.add(R.id.v_framelayout, indexSortFragment, TAG_CONTACTS_FRAGMENT);
                    }

                    currentFragment = indexSortFragment;
                    break;
                case R.id.rdobtn_mine:
                    if (indexMineFragment == null) {
                        indexMineFragment = new IndexMineFragment();
                        mFt.add(R.id.v_framelayout, indexMineFragment, TAG_MIME_FRAGMENT);
                    }

                    currentFragment = indexMineFragment;
                    break;
            }
            if (currentFragment != null)
                showFragment(mFt, currentFragment);
            mFt.commit();
        }
    };

    private void showFragment(FragmentTransaction ft, Fragment fragment) {
        if (fragment == null) return;
        showFragment(ft, indexShopCartFragment, fragment);
        showFragment(ft, indexHomeFragment, fragment);
        showFragment(ft, indexMessageFragment, fragment);
        showFragment(ft, indexSortFragment, fragment);
        showFragment(ft, indexMineFragment, fragment);
    }

    private void showFragment(FragmentTransaction ft, Fragment testFragment, Fragment targetFragment) {
        if (testFragment != null) {
            if (testFragment == targetFragment)
                ft.show(targetFragment);
            else
                ft.hide(testFragment);
        }
    }

    private void showFragment(String tag) {
        if (tag == null)
            tag = TAG_CIRCLE_FRAGMENT;
        switch (tag) {
            case TAG_MESSAGE_FRAGMENT:
                rdoBtnMessage.setChecked(true);
                break;
            case TAG_CONTACTS_FRAGMENT:
                rdoBtnHub.setChecked(true);
                break;
            case TAG_MESSAGES_FRAGMENT:
                rdoBtnMsg.setChecked(true);
                break;
            case TAG_MIME_FRAGMENT:
                rdoBtnMine.setChecked(true);
                break;
            default:
                rdoBtnCircle.setChecked(true);
                break;
        }
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        clearNotification();
        if (savedInstanceState != null) {
            indexHomeFragment = (IndexHomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_CIRCLE_FRAGMENT);
            indexSortFragment = (IndexSortFragment) getSupportFragmentManager().findFragmentByTag(TAG_CONTACTS_FRAGMENT);
            indexMessageFragment= (IndexMessageFragment) getSupportFragmentManager().findFragmentByTag(TAG_MESSAGES_FRAGMENT);
            indexShopCartFragment = (IndexShopCartFragment) getSupportFragmentManager().findFragmentByTag(TAG_MESSAGE_FRAGMENT);
            indexMineFragment = (IndexMineFragment) getSupportFragmentManager().findFragmentByTag(TAG_MIME_FRAGMENT);
        }
        RadioButtonGroup radioGroup = new RadioButtonGroup(rdoBtnCircle, rdoBtnMessage,rdoBtnMsg, rdoBtnHub, rdoBtnMine);
        radioGroup.setOnCheckedChangeListener(tagCheckedChangedListener);
        String tag = getIntent().getStringExtra(INTERT_FRAGMENT_TAG_KEY);
        showFragment(tag);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, IndexPresenter presenter) {
        component.inject(presenter);
    }

    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        MiPushClient.clearNotification(getApplicationContext());
    }
}
