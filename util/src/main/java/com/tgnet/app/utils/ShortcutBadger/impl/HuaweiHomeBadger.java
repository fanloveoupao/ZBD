package com.tgnet.app.utils.ShortcutBadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.tgnet.app.utils.ShortcutBadger.Badger;
import com.tgnet.app.utils.ShortcutBadger.ShortcutBadgeException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jason Ling
 */
public class HuaweiHomeBadger implements Badger {

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {

        /**
         * http://developer.huawei.com/consumer/cn/wiki/index.php?title=%E5%8D%8E%E4%B8%BA%E6%A1%8C%E9%9D%A2%E8%A7%92%E6%A0%87%E5%BC%80%E5%8F%91%E6%8C%87%E5%AF%BC%E4%B9%A6
         * 查看有道文档
         */
        Bundle extra = new Bundle();
        extra.putString("package", context.getPackageName());
        extra.putString("class", componentName.getClassName());
        extra.putInt("badgenumber", badgeCount);
        context.getContentResolver().call(Uri.parse(
                "content://com.huawei.android.launcher.settings/badge/"),
                "change_badge", null, extra);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.huawei.android.launcher"
        );
    }
}
