package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgnet.app.utils.R;

/**
 * Created by lzh on 2017/8/5.
 */

public class MultilineEditText extends RelativeLayout {


    public interface OnInputListener {
        void onInput(boolean input);
    }

    private ScrollEditText mEt;
    private TextView mTvCount;
    private int mMaxCount;
    private Context mContext;
    private OnInputListener mListener;

    public boolean ismInput() {
        return mInput;
    }

    private boolean mInput;

    public void setmListener(OnInputListener mListener) {
        this.mListener = mListener;
    }

    public void setmMax(int mMax) {
        this.mMaxCount = mMax;
        mTvCount.setText(mMax + "");
        mEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMax)});
    }


    public MultilineEditText(Context context) {
        this(context, null);
    }

    public void setHint(String hint) {
        mEt.setHint(hint);
    }

    public MultilineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultilineEditText);
        mMaxCount = array.getInteger(R.styleable.MultilineEditText_max_count, 0);
        array.recycle();
        init();
    }

    public String getText() {
        return mEt.getText().toString().trim();
    }

    public ScrollEditText getEditText() {
        return mEt;
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_multiline_edittext, this);
        mEt = (ScrollEditText) view.findViewById(R.id.et_multiline);
        mTvCount = (TextView) view.findViewById(R.id.tv_count);
        mEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int lenght = mEt.getText().toString().trim().length();
                mTvCount.setText((mMaxCount - lenght) + "");
                if (lenght > 0) {
                    if (mListener != null)
                        mListener.onInput(true);
                    mInput = true;
                } else {
                    if (mListener != null)
                        mListener.onInput(false);
                    mInput = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setText(String text) {
        if(text.length()>mMaxCount)
        mEt.setText(text.substring(0,mMaxCount));
        else mEt.setText(text);
        mEt.setSelection(mEt.getText().length());
    }

}
