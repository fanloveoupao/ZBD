package com.tgnet.app.utils.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tgnet.app.utils.R;
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.ui.ActivitiesHelper;
import com.tgnet.app.utils.ui.ActivitiesManger;
import com.tgnet.app.utils.ui.IIntentInterceptor;
import com.tgnet.app.utils.ui.TgnetApplication;
import com.tgnet.app.utils.ui.widget.ActionLoadingDialogFragment;
import com.tgnet.app.utils.utils.DialogUtil;
import com.tgnet.app.utils.utils.KeyBoardUtils;
import com.tgnet.app.utils.utils.ToastUtils;
import com.tgnet.basemvp.IView;
import com.tgnet.exceptions.NetworkException;
import com.tgnet.exceptions.TgnetException;
import com.tgnet.exceptions.UnloginException;
import com.tgnet.executor.ActionRequest;
import com.tgnet.executor.ActionRunnable;
import com.tgnet.log.LoggerResolver;
import com.tgnet.util.StringUtil;

import java.util.ArrayDeque;
import java.util.Queue;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by fan-gk on 2017/4/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {
    private final Queue<Runnable> saveInstanceStateRunnables = new ArrayDeque<>();
    private boolean isSavedInstanceState;
    private static final int REQUEST_CODE_LOGIN = 1;
    private ActivitiesManger activitiesManger = new ActivitiesManger(this);

    protected ActivitiesManger getActivitiesManger() {
        return activitiesManger;
    }

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.red_f47b7e));
        }
        ActivitiesHelper.addActivity(this);
        isSavedInstanceState = false;
        rxPermissions = new RxPermissions(this);
        runSaveInstanceStateRunnables();
    }

    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    public void launch(final Intent intent, final IIntentInterceptor intentInterceptor, final boolean finish) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                if (intentInterceptor != null)
                    intentInterceptor.intercept(intent);
                BaseActivity.this.startActivity(intent);
                if (finish)
                    finish();
            }
        });
    }

    public void launch(String action, final boolean finish) {
        Intent intent = new Intent(action);
        launch(intent, null, finish);
    }

    public void launch(final Class<?> activity, final boolean finish) {
        launch(activity, null, finish);
    }

    public void launch(Class<?> activity, IIntentInterceptor intentInterceptor, boolean finish) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launch(intent, intentInterceptor, finish);
    }

    public void launch(final Class<?> activity, final IIntentInterceptor intentInterceptor) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launch(intent, intentInterceptor, false);
    }

    public void launchForResult(final Class<?> activity, final int requestCode) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launchForResult(intent, requestCode, null);
    }

    public void launchForResult(final Class<?> activity, final int requestCode, final IIntentInterceptor intentInterceptor) {
        Intent intent = new Intent(BaseActivity.this, activity);
        launchForResult(intent, requestCode, intentInterceptor);
    }

    public void launchForResult(final Intent intent, final int requestCode, final IIntentInterceptor intentInterceptor) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {

                if (intentInterceptor != null)
                    intentInterceptor.intercept(intent);
                BaseActivity.this.startActivityForResult(intent, requestCode);
            }
        });
    }


    @Override
    final public void runAction(final ActionRequest request) {
        if (request != null) {
            final ActionRunnable runnable = request.getRunnable();
            if (runnable != null) {
                if (request.getRunType() == ActionRequest.RUN_TYPE_LOCK) {
                    try {
                        runnable.run();
                        request.setResultSuccessful();
                    } catch (Exception ex) {
                        onException(request, ex);
                    }
                } else {
                    final boolean isShowProgress = request.getRunType() == ActionRequest.RUN_TYPE_LOADING;
                    if (isShowProgress)
                        request.getView().showLoadingView(request);
                    ThreadExecutor.runInAsync(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                runnable.run();
                                request.setResultSuccessful();
                            } catch (final Exception e) {
                                if (request.getRunType() != ActionRequest.RUN_TYPE_BACKGROUND)
                                    onException(request, e);
                                else {
                                    ThreadExecutor.runInMain(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!StringUtil.isNullOrEmpty(e.getMessage())) {
                                                if (e instanceof NetworkException) {
                                                    ToastUtils.showShort(BaseActivity.this, e.getMessage());
                                                }
                                            }

                                        }
                                    });
                                }
                            } finally {
                                if (isShowProgress)
                                    request.getView().dismissLoadingView();
                            }
                        }
                    });
                }
            }
        }
    }

    private void showExceptionDialog(final String msg, final boolean finish) {
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {
                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        if (!finish)
                            DialogUtil.softOneBtnDialog(BaseActivity.this, msg);
                        else
                            DialogUtil.hardTwoBtnDialog(BaseActivity.this, msg, new Runnable() {
                                @Override
                                public void run() {
                                    BaseActivity.this.finish();
                                }
                            });
                    }
                });
            }
        });
    }

    @Override
    public void showLoadingView(final ActionRequest request) {
        Log.d("safeRunAfter", "show loading");
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {
                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        ActionLoadingDialogFragment.singleShow(BaseActivity.this, request);
                    }
                });
            }
        });
    }

    @Override
    public void dismissLoadingView() {
        Log.d("safeRunAfter", "dismiss loading");
        safeRunAfterSaveInstanceState(new Runnable() {
            @Override
            public void run() {
                ThreadExecutor.runInMain(new Runnable() {
                    @Override
                    public void run() {
                        ActionLoadingDialogFragment.dismiss(BaseActivity.this);
                    }
                });
            }
        });
    }

    /**
     * 在前一个界面还没关闭的时候后一个界面有可能是 After SaveInstanceState 的状态，
     * 如果此时调用后一个界面的界面操作会报Can not perform this action after onSaveInstanceState
     * 应调用这个方法来保证回能正确显示
     *
     * @param runnable
     */
    protected void safeRunAfterSaveInstanceState(Runnable runnable) {
        if (runnable != null) {
            if (isSavedInstanceState) {
                Log.d("safeRunAfter", "offer");
                saveInstanceStateRunnables.offer(runnable);
            } else {
                Log.d("safeRunAfter", "run");
                runnable.run();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        isSavedInstanceState = true;
        super.onSaveInstanceState(outState);
        LoggerResolver.getInstance().info("onSaveInstanceState", getClass().getName());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LoggerResolver.getInstance().info("onCreate", "重用");
        LoggerResolver.getInstance().info("onRestoreInstanceState", getClass().getName());
        isSavedInstanceState = false;
        runSaveInstanceStateRunnables();
        getTgnetApplication().onRestoreInstanceState(this);
    }

    private synchronized void runSaveInstanceStateRunnables() {
        Runnable run;
        while ((run = saveInstanceStateRunnables.poll()) != null) {
            try {
                run.run();
                LoggerResolver.getInstance().info("onCreate", "执行");
            } catch (Throwable e) {
                LoggerResolver.getInstance().fail("runSaveState", e);
                LoggerResolver.getInstance().info("onCreate", "异常");
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LoggerResolver.getInstance().info("onRestart", getClass().getName());
        isSavedInstanceState = false;
        runSaveInstanceStateRunnables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivitiesHelper.setLastResumeActivity(this);
        LoggerResolver.getInstance().info("onCreate", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveInstanceStateRunnables.clear();
        ActivitiesHelper.removeActivity(this);
        hideKeyBoard();
        if (getTgnetApplication().DEBUG)
            LoggerResolver.getInstance().info("onDestroy", getClass().getName());
    }
    //endregion

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (onBeforeBackPressed())
                return true;
            else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract void onForceLogout();

    protected abstract void onTokenOutDate();

    protected boolean onBeforeBackPressed() {
        if (ActivitiesHelper.size() == 1) {
            moveTaskToBack(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 需要登陆时调用
     *
     * @param otherDevice 如果为true会使用通知交给Application处理，如果为false直接跳转登陆界面
     *                    Application处理时会掉用当前界面的onNeetLogin方法，所以Application
     *                    调用onNeetLogin方法是，otherDevice必须为false。
     */
    public void onNeedLogin(boolean otherDevice) {
        if (otherDevice)
            onForceLogout();
        else {
            onTokenOutDate();
            getTgnetApplication().launchLoginActivity(this, REQUEST_CODE_LOGIN);
        }
    }

    private void onException(ActionRequest request, Exception e) {
        if (e instanceof NetworkException) {
            if (request != null && request.getHandleOnExceptionType() == ActionRequest.HANDLE_ON_EXCEPTION_USER_RELOAD) {
                request.getView().onException(request, (NetworkException) e);
            } else {
                onException(e);
            }
        } else {
            onException(e);
        }
    }

    @Override
    public void onException(Exception e, boolean finish) {
        e.printStackTrace();
        if (e instanceof UnloginException)
            onNeedLogin(((UnloginException) e).getType() == UnloginException.TYPE_OTHER_DEVICE);
        else if (e instanceof TgnetException)
            onException((TgnetException) e, finish);
        else if (e instanceof NetworkException)
            onException((NetworkException) e, finish);
        else
            showExceptionDialog(e.toString(), finish);
    }

    @Override
    public void onException(ActionRequest request, NetworkException e) {
        onException(e, request != null && request.getBackOnExceptionType() == ActionRequest.BACK_ON_EXCEPTION_TYPE_FINISH_PARENT);
    }

    @Override
    public void onException(Exception e) {
        onException(e, false);
    }

    protected void onException(TgnetException e, boolean finish) {
        switch (e.getErrorCode()) {
            default:
                if (!StringUtil.isNullOrEmpty(e.getMessage()))
                    showExceptionDialog(e.getMessage(), finish);
                break;
        }
    }

    protected void onException(final NetworkException e, boolean finish) {
        if (!finish)
            ThreadExecutor.runInMain(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(BaseActivity.this, e.getMessage());
                }
            });
        else
            showExceptionDialog(e.getMessage(), true);
    }

    @Override
    public void onWarn(final String message) {
        ThreadExecutor.runInMain(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(BaseActivity.this, message);
            }
        });
    }

    protected void setResultOk() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Resources getResources() {//字体不随着系统设置而变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    @Override
    public void hideKeyBoard() {
        KeyBoardUtils.hideKeyBoard(this);
    }


    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (intent != null && ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (uri != null && ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))) {
                if (launchWeb(uri))
                    return;
            }
        }
        super.startActivity(intent, options);
    }

    /**
     * 拦截转跳web的请求
     *
     * @param uri
     * @return 返回true表示已经转跳，其他返回false
     */
    protected boolean launchWeb(Uri uri) {
        return false;
    }

    protected TgnetApplication getTgnetApplication() {
        return (TgnetApplication) this.getApplication();
    }

    @Override
    public boolean viewDestroyed() {
        return this.isDestroyed();
    }

}
