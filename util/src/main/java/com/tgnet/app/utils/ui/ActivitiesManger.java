package com.tgnet.app.utils.ui;

import android.support.annotation.NonNull;

import com.tgnet.app.utils.base.BaseActivity;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class ActivitiesManger {
    private final BaseActivity currentActivity;

    public ActivitiesManger(@NonNull BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void finishOtherActivities() {
        ActivitiesHelper.finishAllButThis(currentActivity);
    }

    public <T> void finishActivities(Class<T> classOfActivities) {
        ActivitiesHelper.finishActivities(classOfActivities);
    }

    public <T> void finishAllButThis(Class<T> activityClass) {
        ActivitiesHelper.finishAllButThis(activityClass);
    }

    /**
     * 当前打开的activity数量
     * @return
     */
    public int size() {
        return ActivitiesHelper.size();
    }
}