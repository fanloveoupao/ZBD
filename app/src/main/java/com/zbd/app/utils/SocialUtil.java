package com.zbd.app.utils;

import com.ysnet.share.SocialHelper;

/**
 * @author fan-gk
 * @date 2017/12/6.
 */
public class SocialUtil {
    private static SocialUtil sInstance = new SocialUtil();

    private SocialHelper socialHelper;

    private SocialUtil() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId("qqAppId")
                .setWxAppId("wxAppId")
                .setWxAppSecret("wxAppSecret")
                .setWbAppId("wbAppId")
                .setWbRedirectUrl("wbRedirectUrl")
                .build();
    }

    public static SocialUtil getInstance() {
        return sInstance;
    }

    public SocialHelper socialHelper() {
        return socialHelper;
    }
}