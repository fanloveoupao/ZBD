package com.tgnet.ui;

import com.google.gson.annotations.Expose;
import com.tgnet.Token;
import com.tgnet.lang.Version;
import com.tgnet.repositories.ClientRepositories;
import com.tgnet.repositories.ISingleRepository;
import com.tgnet.util.StringUtil;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by fan-gk on 2017/4/20.
 */
public class ClientSettings {

    public static class DeviceInfo {
        /**
         * 分辨率
         *
         * @return
         */
        public String getResolution() {
            return resolution;
        }

        private String resolution;

        /**
         * 系统版本
         *
         * @return
         */
        public String getOs() {
            return os;
        }

        private String os;

        /**
         * 手机型号
         *
         * @return
         */
        public String getTrademark() {
            return trademark;
        }

        private String trademark;

        private String versionName;
        private int versionCode;

        public String getVersionName() {
            return versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }


        public DeviceInfo(String resolution, String os, String trademark, String versionName, int versionCode) {
            this.resolution = resolution;
            this.os = os;
            this.trademark = trademark;
            this.versionName = versionName;
            this.versionCode = versionCode;
        }
    }


    public static class LoginSettings {
        @Expose(serialize = false, deserialize = false)
        private ClientSettings clientSettings;


        public synchronized Collection<String> getUsernames() {
            if (usernames == null)
                usernames = new HashSet<>();
            return usernames;
        }

        public void addUsername(String username) {
            if (!StringUtil.isNullOrEmpty(username)) {
                getUsernames().clear();
                getUsernames().add(username.trim());
                clientSettings.setLoginSettings(this);
            }
        }

        public BigInteger getLastLoginUid() {
            return lastLoginUid;
        }

        public void setLastLoginUid(long uid) {
            this.lastLoginUid = BigInteger.valueOf(uid);
            clientSettings.setLoginSettings(this);
        }

        @Expose
        private HashSet<String> usernames;
        @Expose
        private BigInteger lastLoginUid;

        private LoginSettings(ClientSettings clientSettings) {
            this.clientSettings = clientSettings;
        }

        private LoginSettings() {
        }
    }

    private <T> ISingleRepository<T> getSingleRepository(String key, Class<T> classOfT) {
        return ClientRepositories
                .getInstance()
                .getSharedPreferencesRepositoryProvider()
                .GetSingleRepository("ClientSettings", key, classOfT);
    }

    public Token getLastToken() {
        return getSingleRepository("last-token", Token.class).get();
    }

    public void setLastToken(Token lastToken) {
        getSingleRepository("last-token", Token.class).addOrReplace(lastToken);
    }

    /**
     * 已经登陆过
     *
     * @return true为已经登陆过
     */
    public boolean isFirstLoggined() {
        Boolean value = getSingleRepository("first-loggined", Boolean.class).get();
        return value != null && value.booleanValue();
    }

    public Collection<String> getCollectionUid() {
        HashSet<String> userUids = getSingleRepository("hash-uids", HashSet.class).get();
        if (userUids == null)
            userUids = new HashSet<>();
        return userUids;
    }

    public void addCollectionUid(String uid) {
        HashSet<String> userUids = getSingleRepository("hash-uids", HashSet.class).get();
        if (userUids == null)
            userUids = new HashSet<>();
        userUids.add(uid);
        getSingleRepository("hash-uids", HashSet.class).addOrReplace(userUids);
    }

    public void setFirstLoggined() {
        getSingleRepository("first-loggined", Boolean.class).addOrReplace(true);
    }

    public void setHasReadiedAddressBook() {
        getSingleRepository("readied-address-book", Boolean.class).addOrReplace(true);
    }

    public boolean isHasReadiedAddressBook() {
        Boolean value = getSingleRepository("readied-address-book", Boolean.class).get();
        return value != null && value.booleanValue();
    }

    public LoginSettings getLoginSettings() {
        LoginSettings settings = getSingleRepository("login-settings", LoginSettings.class).get();
        if (settings == null) {
            settings = new LoginSettings(this);
            setLoginSettings(settings);
        } else {
            settings.clientSettings = this;
        }
        return settings;
    }

    private ClientSettings setLoginSettings(LoginSettings loginSettings) {
        getSingleRepository("login-settings", LoginSettings.class).addOrReplace(loginSettings);
        return this;
    }

    public String getUuid() {
        return getSingleRepository("uuid", String.class).get();
    }

    public ClientSettings setUuid(String uuid) {
        getSingleRepository("uuid", String.class).addOrReplace(uuid.trim());
        return this;
    }

    public Version getVersion() {
        return deviceInfo.getVersionName() == null ? null : new Version(deviceInfo.getVersionName());
    }

    private static DeviceInfo deviceInfo;

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public ClientSettings setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        return this;
    }

    public void setKeyboardHeight(int height) {
        getSingleRepository("keyboard_height", Integer.class).addOrReplace(height);
    }

    public int getKeyboardHeight() {
        Integer value = getSingleRepository("keyboard_height", Integer.class).get();
        return value == null ? -1 : value.intValue();

    }

    /**
     * 是否已经弹过没有打开通知栏的
     * 为true表示已经弹过
     */
    public boolean isShowOpenNotification() {
        Boolean value = getSingleRepository("is-open-notification", Boolean.class).get();
        return value != null && value.booleanValue();
    }

    public void setIsShowOpenNotification() {
        getSingleRepository("is-open-notification", Boolean.class).addOrReplace(true);
    }

    public void setPushNotifyType(boolean pushnotify) {
        getSingleRepository("push-notify-type", Boolean.class).addOrReplace(pushnotify);
    }

    public boolean getPushNotifyType() {
        Boolean notifyType = getSingleRepository("push-notify-type", Boolean.class).get();
        return notifyType == null ? true : notifyType;
    }

    public void setSaveToAddress(String cid, String contactId) {
        HashMap<String, String> hashMap = getSaveToAddress();
        hashMap.put(cid, contactId);
        getSingleRepository("save-to-address", HashMap.class).addOrReplace(hashMap);
    }

    public HashMap<String, String> getSaveToAddress() {
        HashMap<String, String> hashMap = getSingleRepository("save-to-address", HashMap.class).get();
        return hashMap == null ? new HashMap<String, String>() : hashMap;
    }

    public void setSaveToDynamicImg(String cid, String contactId) {
        HashMap<String, String> hashMap = getSaveToAddress();
        hashMap.put(cid, contactId);
        getSingleRepository("save-to-dynamic-img", HashMap.class).addOrReplace(hashMap);
    }

    public HashMap<String, String> getSaveToDynamicImg() {
        HashMap<String, String> hashMap = getSingleRepository("save-to-dynamic-img", HashMap.class).get();
        return hashMap == null ? new HashMap<String, String>() : hashMap;
    }

}

