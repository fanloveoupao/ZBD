package com.ysnet.share.callback;

import com.ysnet.share.entities.ThirdInfoEntity;

/**
 * Created by fan-gk on 17/11/24 15:46
 * Function：
 * Desc：
 */
public interface SocialLoginCallback extends SocialCallback{
    void loginSuccess(ThirdInfoEntity info);
}
