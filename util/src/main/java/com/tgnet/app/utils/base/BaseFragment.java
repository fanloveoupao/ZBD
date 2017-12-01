package com.tgnet.app.utils.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.ui.widget.ActionLoadingDialogFragment;
import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/21.
 */
public class BaseFragment extends Fragment implements IView {


    private Activity mActivity;
    private RxPermissions rxPermissionsFragment;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxPermissionsFragment = new RxPermissions(getActivity());
    }

    public RxPermissions getRxPermissionsFragment() {
        return rxPermissionsFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        hideKeyBoard();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    //region BaseActivity
    @Override
    public void onNeedLogin(boolean otherDevice) {
        if (getBaseActivity() != null)
            getBaseActivity().onNeedLogin(otherDevice);
    }

    @Override
    public void onException(Exception e) {
        try {
            getBaseActivity().onException(e);
        } catch (Exception exception) {
            e.printStackTrace();
        }

    }

    @Override
    public void onException(Exception e, boolean finish) {
        getBaseActivity().onException(e, finish);
    }

    @Override
    public void onException(ActionRequest request, NetworkException e) {
        getBaseActivity().onException(request, e);
    }

    @Override
    public void onWarn(String message) {
        getBaseActivity().onWarn(message);
    }


    protected BaseActivity getBaseActivity() {
        return (BaseActivity) mActivity;
    }

    @Override
    public void hideKeyBoard() {
        getBaseActivity().hideKeyBoard();
    }

    @Override
    public void runAction(ActionRequest request) {
        getBaseActivity().runAction(request);
    }


    @Override
    public void showLoadingView(final ActionRequest request) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ActionLoadingDialogFragment.singleShow(BaseFragment.this, request);
            }
        });
    }

    @Override
    public void dismissLoadingView() {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ActionLoadingDialogFragment.dismiss(BaseFragment.this);
            }
        });
    }

    @Override
    public boolean viewDestroyed() {
        return getBaseActivity() != null && getBaseActivity().isDestroyed();
    }

    //endregion


}
