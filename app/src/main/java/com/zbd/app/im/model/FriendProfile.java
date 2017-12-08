package com.zbd.app.im.model;

import android.content.Context;

import com.tencent.TIMUserProfile;
import com.zbd.app.R;
import com.zbd.app.ZbdApplication;


/**
 * 好友资料
 */
public class FriendProfile implements ProfileSummary {


    private TIMUserProfile profile;
    private boolean isSelected;

    public FriendProfile(TIMUserProfile profile){
        this.profile = profile;
    }


    /**
     * 获取头像资源
     */
    @Override
    public int getAvatarRes() {
        return R.mipmap.head_other;
    }

    /**
     * 获取头像地址
     */
    @Override
    public String getAvatarUrl() {
        return null;
    }

    /**
     * 获取名字
     */
    @Override
    public String getName() {
        if (!profile.getRemark().equals("")){
            return profile.getRemark();
        }else if (!profile.getNickName().equals("")){
            return profile.getNickName();
        }
        return profile.getIdentifier();
    }

    /**
     * 获取描述信息
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * 显示详情
     *
     * @param context 上下文
     */
    @Override
    public void onClick(Context context) {

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    /**
     * 获取用户ID
     */
    @Override
    public String getIdentify(){
        return profile.getIdentifier();
    }


    /**
     * 获取用户备注名
     */
    public String getRemark(){
        return profile.getRemark();
    }


    /**
     * 获取好友分组
     */
    public String getGroupName(){
        if (profile.getFriendGroups().size() == 0){
            return ZbdApplication.getContext().getString(R.string.default_group_name);
        }else{
            return profile.getFriendGroups().get(0);
        }
    }

}
