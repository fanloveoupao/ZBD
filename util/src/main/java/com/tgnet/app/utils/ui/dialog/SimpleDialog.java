package com.tgnet.app.utils.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.utils.ScreenUtils;


/**
 * author: ZK.
 * date:   On 2016/10/21
 */

public class SimpleDialog extends Dialog {
    private Context mContext;

    public SimpleDialog(Context context) {
        super(context, R.style.Dialog);
        mContext = context;
    }

    public SimpleDialog(Context context, int style) {
        super(context, style);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getWindowsWidth(mContext) * 0.822);
        getWindow().setAttributes(params);


    }
}
