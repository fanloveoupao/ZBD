package com.ysnet.share;

import com.ysnet.share.callback.SocialLoginCallback;
import com.ysnet.share.callback.SocialShareCallback;
import com.ysnet.share.entities.ShareEntity;
import com.ysnet.share.entities.ThirdInfoEntity;

/**
 * Created by fan-gk on 17/11/24 16:06
 * Function：
 * Desc：
 */
public interface ISocial {
    void login(SocialLoginCallback callback);

    ThirdInfoEntity createThirdInfo();

    void share(SocialShareCallback callback, ShareEntity shareInfo);

    void onDestroy();
}
