package com.tgnet.app.utils.ui;

import android.app.Application;

import com.tgnet.app.utils.base.BaseActivity;
import com.tgnet.app.utils.logs.Logger;
import com.tgnet.app.utils.repositories.SharedPreferencesRepositoryProvider;
import com.tgnet.app.utils.utils.AndroidUtil;
import com.tgnet.app.utils.utils.DeviceUtil;
import com.tgnet.log.LoggerResolver;
import com.tgnet.repositories.ClientRepositories;
import com.tgnet.ui.ClientSettings;
import com.tgnet.util.StringUtil;

/**
 * Created by fan-gk on 2017/4/20.
 */

public abstract class TgnetApplication extends Application {
    public static final boolean DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        onRealCreate();
    }


    public void onRealCreate() {
        LoggerResolver.getInstance().clearLoggers();
        LoggerResolver.getInstance().addLogger(new Logger());
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LoggerResolver.getInstance().error(t.toString(), e);
                e.printStackTrace();
                exit();
            }
        });

        InitRepositories();

        ClientSettings clientSettings = new ClientSettings();
        if (StringUtil.isNullOrWhiteSpace(clientSettings.getUuid())) {
            clientSettings.setUuid(AndroidUtil.getAndroidId(this));
        }

        ClientSettings.DeviceInfo deviceInfo = new ClientSettings.DeviceInfo(
                DeviceUtil.getDeviceDisplay(this),
                DeviceUtil.getOSVersion(),
                DeviceUtil.getDeviceModel(),
                AndroidUtil.getVersionName(this),
                AndroidUtil.getVersionCode(this));
        clientSettings.setDeviceInfo(deviceInfo);

//        super.onCreate();
    }

    protected BaseActivity getLastResumeActivity() {
        return ActivitiesHelper.getLastResumeActivity();
    }

    public abstract void launchLoginActivity(BaseActivity currentActivity, int requestCode);

    public abstract void launchLoginActivityForForce(BaseActivity currentActivity);

    public abstract void onRestoreInstanceState(BaseActivity currentActivity);

    protected abstract void checkUpdate(BaseActivity activity);

    protected abstract boolean hasUpdate();

    private void InitRepositories() {
        ClientRepositories
                .getInstance()
                .setSharedPreferencesRepositoryProvider(new SharedPreferencesRepositoryProvider(this));
    }

    protected <T> boolean existsActivity(Class<T> classOfActivity) {
        return ActivitiesHelper.existsActivity(classOfActivity);
    }

    protected <T> void finishAllActivitiesButThis(Class<T> classOfActivity) {
        ActivitiesHelper.finishAllButThis(classOfActivity);
    }

    public void exit() {
        ActivitiesHelper.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public abstract String getAppName();


}
