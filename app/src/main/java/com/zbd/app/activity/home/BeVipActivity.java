package com.zbd.app.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tgnet.app.utils.ui.widget.RadioButtonGroup;
import com.tgnet.app.utils.ui.widget.TitleBar;
import com.ysnet.zdb.presenter.BeVipPresenter;
import com.zbd.app.BasePresenterActivity;
import com.zbd.app.R;
import com.zbd.app.ioc.component.PresenterComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeVipActivity extends BasePresenterActivity<BeVipPresenter, BeVipPresenter.IBeVipView>
        implements BeVipPresenter.IBeVipView {

    @BindView(R.id.titleBar)
    TitleBar mTitleBar;
    @BindView(R.id.radio_one)
    RadioButton mRadioOne;
    @BindView(R.id.radio_two)
    RadioButton mRadioTwo;
    @BindView(R.id.radio_three)
    RadioButton mRadioThree;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    private RadioButtonGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioButtonGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView) {
            switch (buttonView.getId()) {
                case R.id.radio_one:
                    mTvPrice.setText("¥ 388.00");
                    break;
                case R.id.radio_two:
                    mTvPrice.setText("¥ 688.00");
                    break;
                case R.id.radio_three:
                    mTvPrice.setText("¥ 988.00");
                    break;
            }
        }
    };

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_be_vip);
        ButterKnife.bind(this);
        mTitleBar.setCenterText("成为会员");
        mTitleBar.setBackClick(this);
        initListener();
    }

    private void initListener() {
        RadioButtonGroup radioButtonGroup = new RadioButtonGroup(mRadioOne, mRadioTwo, mRadioThree);
        radioButtonGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        mRadioOne.setChecked(true);
    }

    @Override
    protected void injectPresenter(PresenterComponent component, BeVipPresenter presenter) {
        component.inject(presenter);
    }

    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one:
                mRadioOne.setChecked(true);
                break;
            case R.id.ll_two:
                mRadioTwo.setChecked(true);
                break;
            case R.id.ll_three:
                mRadioThree.setChecked(true);
                break;
            case R.id.tv_pay:
                break;
        }
    }
}
