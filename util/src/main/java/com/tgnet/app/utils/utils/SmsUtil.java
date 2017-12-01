package com.tgnet.app.utils.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tgnet.util.StringUtil;

/**
 * Created by fan-gk on 2017/5/2.
 */

public final class SmsUtil {
    public static void launch(Activity context, String mobile, String content){
        context.startActivity(createIntent(mobile, content));
    }

    public static void launchForResult(Activity context, String mobile, String content, int requestCode){
        context.startActivityForResult(createIntent(mobile, content), requestCode);
    }

    public static Intent createIntent(String mobile, String content){
        Uri uri = Uri.parse("smsto:" + mobile);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if(!StringUtil.isNullOrWhiteSpace(content))
            intent.putExtra("sms_body", content);
        return intent;
    }
}
