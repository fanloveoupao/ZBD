package com.tgnet.app.utils.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.base.BaseActivity;

/**
 * Created by lzh on 2017/7/13.
 */

public class GenderDialog extends SimpleDialog {
    public interface OnCheckChangeListener{
        void onCheckChange(String gender);
    }
    private String mGender="";
    private BaseActivity mContext;
    public OnCheckChangeListener mListener;

    public GenderDialog(BaseActivity context) {
        super(context);
        this.mContext=context;
        init();
    }

    public GenderDialog(BaseActivity context,  String gender) {
        super(context);
        this.mContext=context;
        this.mGender = gender == null ? "" : gender;
        init();
    }
    private void init(){
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_gender,null);
        setContentView(view);
        final RadioButton rbMale=(RadioButton)view.findViewById(R.id.rb_male);
        final RadioButton rbFemale=(RadioButton)view.findViewById(R.id.rb_female);
        if (mGender.equals("男")) {
            rbMale.setChecked(true);
            rbFemale.setChecked(false);
        } else if (mGender.equals("女")) {
            rbMale.setChecked(false);
            rbFemale.setChecked(true);
        }
        findViewById(R.id.ll_male).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbMale.setChecked(true);
                rbFemale.setChecked(false);
                if (null != mListener)
                    if (null != mListener)
                        mListener.onCheckChange("男");
                dismiss();
            }
        });
        findViewById(R.id.ll_female).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbMale.setChecked(false);
                rbFemale.setChecked(true);
                if (null != mListener)
                    if (null != mListener)
                        mListener.onCheckChange("女");
                dismiss();
            }
        });
    }

    public void setOnCheckChangeListener(OnCheckChangeListener listener){
        this.mListener=listener;
    }
}
