package com.tgnet.app.utils.ui;

import android.app.Activity;
import android.util.Log;

import com.tgnet.app.utils.base.BaseActivity;
import com.tgnet.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class ActivitiesHelper {
    private static BaseActivity lastResumeActivity;
    private static final LinkedList<Activity> activities = new LinkedList<>();

    private static void finish(Activity activity) {
        if (activity != null) {
            Log.d("ActivitiesHelper", "finish " + activity.getClass().getName());
            activity.finish();
        }
    }

    public static void setLastResumeActivity(BaseActivity activity) {
        lastResumeActivity = activity;
    }

    public static BaseActivity getLastResumeActivity() {
        return lastResumeActivity;
    }

    public static <T> boolean existsActivity(Class<T> classOfActivity) {
        synchronized (activities) {
            for (Activity activity : activities) {
                if (activity.getClass().getName().equals(classOfActivity.getName()))
                    return true;
            }
            return false;
        }
    }

    public static void addActivity(Activity activity) {
        removeActivity(activity);
        synchronized (activities) {
            Log.d("ActivitiesHelper", "add " + activity.getClass().getName());
            activities.addFirst(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        synchronized (activities) {
            Log.d("ActivitiesHelper", "remove " + activity.getClass().getName());
            activities.remove(activity);
        }
    }

    public static Activity getTop() {
        synchronized (activities) {
            return activities.peek();
        }
    }

    public static void finishAll() {
        synchronized (activities) {
            Activity activity;
            do {
                activity = activities.poll();
                finish(activity);
            } while (activity != null);
        }
    }

    public static void finishAllButThis(Activity activity) {
        synchronized (activities) {
            Activity item;
            do {
                item = activities.poll();
                if (item != activity)
                    finish(item);
            } while (item != null);
        }
    }

    public static <T> void finishAllButThis(Class<T> activityClass) {
        synchronized (activities) {
            Activity activity;
            do {
                activity = activities.poll();
                if (activity != null && !StringUtil.equals(activity.getClass().getName(), activityClass.getName()))
                    finish(activity);
            } while (activity != null);
        }
    }

    public static int size() {
        synchronized (activities) {
            return activities.size();
        }
    }

    public static <T> void finishActivities(Class<T> activityClass) {
        List<Activity> finishes = new ArrayList<>();
        synchronized (activities) {
            for (Activity activity : activities) {
                if (activity != null && StringUtil.equals(activity.getClass().getName(), activityClass.getName())) {
                    finishes.add(activity);
                }
            }
        }
        for (Activity finish : finishes) {
            finish.finish();
        }
    }
}
