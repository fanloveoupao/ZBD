package com.tgnet.app.utils.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tgnet.app.utils.R;

/**
 * author: ZK.
 * date:   On 2017/9/14.
 */
public class CommomLoadingDialogFragment extends SimpleDialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress, null);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }
}
