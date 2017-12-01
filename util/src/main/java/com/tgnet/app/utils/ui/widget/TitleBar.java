package com.tgnet.app.utils.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.ui.widget.statusbar.GradientToolBar;
import com.tgnet.app.utils.utils.KeyBoardUtils;
import com.tgnet.app.utils.utils.RecyclerViewScroller;
import com.tgnet.app.utils.utils.ViewUtils;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class TitleBar extends LinearLayout {
    public static String EXTRA_DATA = "cid";
    TextView mLeftTextView;
    TextView mCenterTextView;
    public TextView mRightTextView;
    public TextView mRightTwoTextView;
    public View mBackView;
    private RelativeLayout mToolBar;
    private RecyclerView mScrollToTop;
    private long mClickTime, mDoubleTime;
    private RecyclerViewScroller mViewScroller;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.titlebar_view, this);

        mLeftTextView = (TextView) findViewById(R.id.leftTextView);
        mCenterTextView = (TextView) findViewById(R.id.centerTextView);
        mRightTextView = (TextView) findViewById(R.id.rightTextView);
        mRightTwoTextView = (TextView) findViewById(R.id.rightTwoTextView);
        mBackView = findViewById(R.id.backView);
        mToolBar = (RelativeLayout) findViewById(R.id.toolbar_base_activity);
        initListener();
    }

    private void initListener() {
        mToolBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickTime = mDoubleTime;
                mDoubleTime = System.currentTimeMillis();
                if (mDoubleTime - mClickTime < 300 && mViewScroller!=null) {
                    mViewScroller.scrollTo(0);
                }
            }
        });
    }


    public void setBackView(int drawableId) {
        ViewUtils.setLeftDrawable(mLeftTextView, getResources().getDrawable(drawableId));
    }

    public void setScrollToTopView(RecyclerView view) {
        this.mScrollToTop = view;
        mViewScroller = new RecyclerViewScroller(this.mScrollToTop);
    }

    public void setBackViewIsBack() {
        setBackView(R.mipmap.icon_lef_arrow_whitet);
    }

    public void setBackViewIsClose() {
        setBackView(R.mipmap.icon_close);
    }

    public void setBackViewOnClickListener(OnClickListener listener) {
        mBackView.setOnClickListener(listener);
    }

    public void setBackViewIsGone() {
        mBackView.setVisibility(View.GONE);
    }

    public void setBackViewIsVisiable() {
        mBackView.setVisibility(View.VISIBLE);
    }


    public void setBackgroundColor(@ColorRes int colorId) {
        mToolBar.setBackgroundColor(ContextCompat.getColor(getContext(), colorId));
    }


    public void setBackgroundWhite() {
        mToolBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white_ffffff));

    }

    public void setTextColor(@ColorRes int colorId) {
        mLeftTextView.setTextColor(ContextCompat.getColor(getContext(), colorId));
        mCenterTextView.setTextColor(ContextCompat.getColor(getContext(), colorId));
        mRightTextView.setTextColor(ContextCompat.getColor(getContext(), colorId));
    }


    public void setBackClick(final Activity activity) {
        mBackView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyBoard(activity);
                activity.finish();
            }
        });
    }

    public void setBackClick(final DialogFragment fragment) {
        mBackView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyBoard(fragment.getActivity());
                fragment.dismiss();
            }
        });
    }


    public void setLeftText(String text) {
        mLeftTextView.setText(text);
        mLeftTextView.setVisibility(VISIBLE);
        ViewUtils.clearCompoundDrawable(mLeftTextView);
    }

    public void setCenterText(String text) {
        mCenterTextView.setText(text);
        mCenterTextView.setVisibility(VISIBLE);
        ViewUtils.clearCompoundDrawable(mCenterTextView);
    }

    public void setLeftViewOnClickListener(OnClickListener listener) {
        mLeftTextView.setOnClickListener(listener);
    }

    public void setLeftViewIsGone() {
        mLeftTextView.setVisibility(View.INVISIBLE);
    }

    public void setLeftViewIsVisiable() {
        mLeftTextView.setVisibility(View.VISIBLE);
    }


    public void setRightText(String text) {
        mRightTextView.setText(text);
        mRightTextView.setVisibility(View.VISIBLE);
        ViewUtils.clearCompoundDrawable(mRightTextView);
    }

    public void setRightView(@DrawableRes int drawableId) {
        mRightTextView.setVisibility(View.VISIBLE);
        ViewUtils.setLeftDrawable(mRightTextView, getResources().getDrawable(drawableId));
    }

    public void setRightView(String text, int drawableId) {
        mRightTextView.setText(text);
        mRightTextView.setVisibility(View.VISIBLE);
        ViewUtils.setLeftDrawable(mRightTextView, getResources().getDrawable(drawableId));
    }

    public void setRightViewOnClickListener(OnClickListener listener) {
        mRightTextView.setOnClickListener(listener);
    }

    public void setRightViewIsGone() {
        mRightTextView.setVisibility(View.INVISIBLE);
    }

    public void setRightViewIsVisible() {
        mRightTextView.setVisibility(View.VISIBLE);
    }

    public void setRightViewClickable(boolean clickable) {
        mRightTextView.setClickable(clickable);
    }

    public void setRightTwoText(String text) {
        mRightTwoTextView.setText(text);
        ViewUtils.clearCompoundDrawable(mRightTwoTextView);
    }

    public void setRightTwoView(@DrawableRes int drawableId) {
        ViewUtils.setLeftDrawable(mRightTwoTextView, getResources().getDrawable(drawableId));
    }

    public void setRightTwoView(String text, int drawableId) {
        mRightTwoTextView.setText(text);
        ViewUtils.setLeftDrawable(mRightTwoTextView, getResources().getDrawable(drawableId));
    }

    public void setRightTwoViewOnClickListener(OnClickListener listener) {
        mRightTwoTextView.setOnClickListener(listener);
    }

    public void setRightTwoViewIsGone() {
        mRightTwoTextView.setVisibility(View.INVISIBLE);
    }

    public void setRightTwoViewIsVisible() {
        mRightTwoTextView.setVisibility(View.VISIBLE);
    }

    public void setRightTwoViewClickable(boolean clickable) {
        mRightTwoTextView.setClickable(clickable);
    }

    public void setmBackViewClickable(boolean clickable) {
        mLeftTextView.setClickable(clickable);
    }

}

