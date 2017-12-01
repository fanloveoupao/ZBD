package com.tgnet.app.utils.ui.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tgnet.app.utils.R;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class CommonDialogFragment extends SimpleDialogFragment {

    private String mLeftButtonText;
    private String mCenterButtonText;
    private String mDefultRightButtonText = "确定";
    private String mRightButtonText;
    private String mTitleText;
    private CharSequence mContentText;

    private int mTitleTextSize = 15;
    private int mContentSize = 15;
    private int mDefaltButtonSize = 16;
    private int mLeftButtonSize;
    private int mCenterButtonSize;
    private int mRightButtonSize;

    private int mDefaultButtonColor = Color.parseColor("#208af0");
    private int mTitleTextColor = Color.parseColor("#333333");
    private int mContentTextColor = Color.parseColor("#333333");
    private int mLeftButtonColor;
    private int mCenterButtonColor;
    private int mRightButtonColor;

    private Runnable mLeftRunnable;
    private Runnable mCenterRunnable;
    private Runnable mRightRunnable;

    private float mCornerRadius = 9;

    private TextView tvLeft;
    private TextView tvCenter;
    private TextView tvRight;
    private TextView tvTitle;
    private TextView tvContent;
    private View titleLine;
    private View leftLine;
    private View rightLine;
    private View rl_container;
    private boolean mCanceledOnTouchOutside = true;


    public CommonDialogFragment() {

    }

    //*****************内容********************************

    public CommonDialogFragment setTitleText(String titleText) {
        mTitleText = titleText;
        return this;
    }

    public CommonDialogFragment setContentText(CharSequence content) {
        mContentText = content;
        return this;
    }

    public CommonDialogFragment setLeftButtonText(String leftButtonText) {
        mLeftButtonText = leftButtonText;
        return this;
    }

    public CommonDialogFragment setCenterButtonText(String centerButtonText) {
        mCenterButtonText = centerButtonText;
        return this;
    }

    public CommonDialogFragment setDefultButtonText(String defultButtonText) {
        mRightButtonText = defultButtonText;
        return this;
    }


    //******************字体大小******************************************
    public CommonDialogFragment setTitleTextSize(int size) {
        mTitleTextSize = size;
        return this;
    }

    public CommonDialogFragment setContentTextSize(int size) {
        mContentSize = size;
        return this;
    }

    public CommonDialogFragment setLeftButtonTextSize(int size) {
        mLeftButtonSize = size;
        return this;
    }

    public CommonDialogFragment setCenterButtonTextSize(int size) {
        mCenterButtonSize = size;
        return this;
    }

    public CommonDialogFragment setRightButtonTextSize(int size) {
        mRightButtonSize = size;
        return this;
    }

    //******************字体颜色******************************************************

    public CommonDialogFragment setTitleTextColor(int color) {
        mTitleTextColor = color;
        return this;
    }

    public CommonDialogFragment setContentTextColor(int color) {
        mContentTextColor = color;
        return this;
    }

    public CommonDialogFragment setLeftButtonColor(int color) {
        mLeftButtonColor = color;
        return this;
    }

    public CommonDialogFragment setCenterButtonColor(int color) {
        mCenterButtonColor = color;
        return this;
    }

    public CommonDialogFragment setRightButtonColor(int color) {
        mRightButtonColor = color;
        return this;
    }

    //********************事件**************************************************************

    public CommonDialogFragment setLeftRunnable(@NonNull Runnable runnable) {
        mLeftRunnable = runnable;
        return this;
    }

    public CommonDialogFragment setRightRunnable(@NonNull Runnable runnable) {
        mRightRunnable = runnable;
        return this;
    }

    public CommonDialogFragment setCenterRunnable(@NonNull Runnable runnable) {
        mCenterRunnable = runnable;
        return this;
    }

    public CommonDialogFragment setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        getDialog().setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        titleLine = view.findViewById(R.id.title_line);
        leftLine = view.findViewById(R.id.left_line);
        rightLine = view.findViewById(R.id.right_line);
        tvContent = (TextView) view.findViewById(R.id.tv_item);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvCenter = (TextView) view.findViewById(R.id.tv_center);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        rl_container = view.findViewById(R.id.dialog_container);
        rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCanceledOnTouchOutside)
                    dismiss();
            }
        });
        setUi();
        return view;
    }

    private void setUi() {
        if (mTitleText != null) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);
            tvTitle.setTextColor(mTitleTextColor);
            tvTitle.setText(mTitleText);
        } else {
            tvTitle.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
        }
        if (mContentText != null) {
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, mContentSize);
            tvContent.setTextColor(mContentTextColor);
            tvContent.setText(mContentText);

        }

        if (mLeftButtonText != null) {
            tvLeft.setText(mLeftButtonText);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLeftButtonSize == 0 ? mDefaltButtonSize : mLeftButtonSize);
            tvLeft.setTextColor(mLeftButtonColor == 0 ? mDefaultButtonColor : mLeftButtonColor);
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLeftRunnable == null)
                        dismiss();
                    else mLeftRunnable.run();
                }
            });
        } else {
            tvLeft.setVisibility(View.GONE);
            leftLine.setVisibility(View.GONE);
        }

        if (mCenterButtonText != null) {
            tvCenter.setText(mCenterButtonText);
            tvCenter.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCenterButtonSize == 0 ? mDefaltButtonSize : mCenterButtonSize);
            tvCenter.setTextColor(mCenterButtonColor == 0 ? mDefaultButtonColor : mCenterButtonColor);
            tvCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCenterRunnable == null)
                        dismiss();
                    mCenterRunnable.run();
                    dismiss();
                }
            });
        } else {
            tvCenter.setVisibility(View.GONE);
            rightLine.setVisibility(View.GONE);
        }
        tvRight.setText(mRightButtonText == null ? mDefultRightButtonText : mRightButtonText);
        tvRight.setTextSize(mRightButtonSize == 0 ? mDefaltButtonSize : mRightButtonSize);
        tvRight.setTextColor(mRightButtonColor == 0 ? mDefaultButtonColor : mRightButtonColor);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRightRunnable == null)
                    dismiss();
                else mRightRunnable.run();
                dismiss();
            }
        });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
    }

}
