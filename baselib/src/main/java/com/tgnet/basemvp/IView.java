package com.tgnet.basemvp;

import com.tgnet.exceptions.IExceptionHandler;
import com.tgnet.executor.ActionRequest;

/**
 * Created by fan-gk on 2017/4/19.
 */

public interface IView extends IExceptionHandler {
    void hideKeyBoard();


    void runAction(ActionRequest request);

    /**
     * 显示一个加载中的弹出框
     *
     * @param request
     * @return 使用此返回值来关闭加载中的弹出框
     */
    void showLoadingView(ActionRequest request);

    void dismissLoadingView();

    boolean viewDestroyed();

}
