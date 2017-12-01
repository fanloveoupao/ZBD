package com.tgnet.app.utils.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.tgnet.app.utils.utils.KeyBoardUtils;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class BaseDialogFragment extends DialogFragment {
    private Activity mActivity;

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {
                    return onBeforeBackPressed();
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;

    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) mActivity;
    }

    protected boolean onBeforeBackPressed() {
        return false;
    }

    public void hideKeyBoard() {
        KeyBoardUtils.hideKeyBoard(this);
    }

    public void singleShow(FragmentManager manager) {
        if (!this.isAdded())
            show(manager, getClass().getName());
    }
}