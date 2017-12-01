package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.tgnet.app.utils.R;


/**
 * Created by wulf on 2016/12/28.
 */

public class NetworkErrorView extends LinearLayout {
    private onReloadListener mOnReloadListener;

    public interface onReloadListener {
        void onReload();
    }

    public NetworkErrorView(Context context) {
        super(context);
        init(context);
    }

    public NetworkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetworkErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnReloadListener(onReloadListener listener) {
        mOnReloadListener = listener;
    }

    private void init(Context context) {
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_network_error, this, true);
        view.findViewById(R.id.btn_reload_data).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnReloadListener != null)
                    mOnReloadListener.onReload();
            }
        });

    }
}
