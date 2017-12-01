package com.tgnet.app.utils.ui.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.ui.dialog.SimpleDialogFragment;
import com.tgnet.executor.ActionRequest;
import com.tgnet.log.LoggerResolver;
import com.tgnet.util.StringUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class ActionLoadingDialogFragment extends BaseActionRequestHandleDialogFragment {
    private static class Builder extends SimpleDialogFragment.Builder<ActionLoadingDialogFragment, Builder> {
        private String content;

        public String getContent() {
            return content;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        public ActionLoadingDialogFragment build() {
            return createInstance(this);
        }
    }

    private final static String FRAGMENT_TAG = "single_progress_dialog";
    private final AtomicInteger refCount = new AtomicInteger(0);

    private static ActionLoadingDialogFragment createInstance(Builder builder) {
        ActionLoadingDialogFragment fragment = new ActionLoadingDialogFragment();
        fragment.setBuilder(builder);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_progress, null);

        Builder builder = getBuilder();
        if (builder != null) {
            if (!StringUtil.isNullOrEmpty(builder.getContent())) {
                TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
                tvContent.setText(builder.getContent());
            }
        }

        return view;
    }

    public static ActionLoadingDialogFragment singleShow(FragmentActivity parent, ActionRequest request) {
        Builder builder = new Builder().setCancelable(true).setContent(request.getLoadingMessage());
        ActionLoadingDialogFragment fragment = singleShow(parent, ActionLoadingDialogFragment.class, FRAGMENT_TAG, request, builder.buildArgs(null));
        fragment.refCount.incrementAndGet();
        return fragment;
    }

    public static ActionLoadingDialogFragment singleShow(Fragment parent, ActionRequest request) {
        Builder builder = new Builder().setCancelable(true).setContent(request.getLoadingMessage());
        ActionLoadingDialogFragment fragment = singleShow(parent, ActionLoadingDialogFragment.class, FRAGMENT_TAG, request, builder.buildArgs(null));
        fragment.refCount.incrementAndGet();
        return fragment;
    }

    public static void dismiss(FragmentActivity parent) {
        DialogFragmentUtil.dismissAllowingStateLoss(parent.getSupportFragmentManager(), ActionLoadingDialogFragment.class, FRAGMENT_TAG);
    }

    public static void dismiss(Fragment parent) {
        if (parent.isAdded())
            DialogFragmentUtil.dismissAllowingStateLoss(parent.getChildFragmentManager(), ActionLoadingDialogFragment.class, FRAGMENT_TAG);
    }

    @Override
    public void dismiss() {
        final int count = refCount.decrementAndGet();
        if (count <= 0) {
            refCount.set(0);
            ThreadExecutor.runInMain(new Runnable() {
                @Override
                public void run() {
                    try {
                        ActionLoadingDialogFragment.super.dismiss();
                    } catch (Throwable e) {
                        LoggerResolver.getInstance().fail("ActionLoadingDialogFragment", e);
                    }
                }
            });
        }
    }
}
