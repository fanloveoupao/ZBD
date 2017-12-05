package com.zbd.app;

import android.app.NotificationManager;

import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tgnet.app.utils.base.BaseActivity;
import com.tgnet.app.utils.ui.TgnetApplication;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zbd.app.activity.LoginActivity;
import com.zbd.app.ioc.module.DaoMasterModule;
import com.zbd.app.picasso.ImageLoader;

/**
 * @author fan-gk
 * @date 2017/11/30.
 */
public class ZbdApplication extends TgnetApplication {
    public final DaoMasterModule daoMasterModule = new DaoMasterModule(this);

    @Override
    public void onCreate() {
        super.onCreate();

        initTIMManager();

        clearNotification();

        ImageLoader.init(this);

    }


    @Override
    public void launchLoginActivity(BaseActivity currentActivity, int requestCode) {
        if (!existsActivity(LoginActivity.class))
            currentActivity.launchForResult(LoginActivity.class, requestCode);
    }

    @Override
    public void launchLoginActivityForForce(BaseActivity currentActivity) {
        currentActivity.launch(LoginActivity.class, false);
    }


    @Override
    public void onRestoreInstanceState(BaseActivity currentActivity) {

    }

    @Override
    protected void checkUpdate(BaseActivity activity) {

    }

    @Override
    protected boolean hasUpdate() {
        return false;
    }

    @Override
    public String getAppName() {
        return null;
    }

    private void initTIMManager() {
        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
        }
    }

    /**
     * 清楚所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
        MiPushClient.clearNotification(getApplicationContext());
    }
}
