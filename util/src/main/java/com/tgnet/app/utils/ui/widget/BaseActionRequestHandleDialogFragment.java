package com.tgnet.app.utils.ui.widget;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tgnet.app.utils.ui.dialog.SimpleDialogFragment;
import com.tgnet.executor.ActionRequest;
import com.tgnet.ui.IActionRequestHandleView;
import com.tgnet.ui.ICloseable;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by fan-gk on 2017/4/20.
 */


public abstract class BaseActionRequestHandleDialogFragment extends SimpleDialogFragment
        implements IActionRequestHandleView, ICloseable {
    protected final Queue<ActionRequest> actions = new ArrayDeque<>();
    private int handleBackType;
    private Fragment parentFragment;

    private void closeSelfAndParent() {
        if (getActivity() != null) {
            if (parentFragment != null) {
                if (parentFragment instanceof DialogFragment)
                    ((DialogFragment) parentFragment).dismiss();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().remove(parentFragment).commit();
            } else {
                getActivity().finish();
            }
        }
        dismiss();
    }

    @Override
    protected boolean onBeforeBackPressed() {
        for (ActionRequest action : actions) {
            action.setResultFailed();
        }
        switch (handleBackType) {
            case ActionRequest.BACK_ON_EXCEPTION_TYPE_DISALLOWED:
                return true;
            case ActionRequest.BACK_ON_EXCEPTION_TYPE_FINISH_PARENT:
                closeSelfAndParent();
                return true;
            default:
                return false;
        }
    }


    @Override
    public synchronized void addActionRequest(ActionRequest request) {
        if (request != null) {
            if (request.getBackOnExceptionType() > this.handleBackType)
                this.handleBackType = request.getBackOnExceptionType();
            actions.add(request);
        }
    }

    @Override
    public void close() {
        dismiss();
    }

    protected void setParentFragment(Fragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    protected static <T extends BaseActionRequestHandleDialogFragment> T singleShow(Fragment parent, Class<T> classOfT, String tag,
                                                                                    ActionRequest request, Bundle args) {
        T result = DialogFragmentUtil.singleShow(parent.getContext(), parent.getChildFragmentManager(), classOfT, tag, args);
        result.setParentFragment(parent);
        result.addActionRequest(request);
        return result;
    }

    protected static <T extends BaseActionRequestHandleDialogFragment> T singleShow(FragmentActivity parent, Class<T> classOfT, String
            tag, ActionRequest request, Bundle args) {
        T result = DialogFragmentUtil.singleShow(parent, parent.getSupportFragmentManager(), classOfT, tag, args);
        result.addActionRequest(request);
        return result;
    }
}
