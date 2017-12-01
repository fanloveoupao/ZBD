package com.tgnet.ui;

import com.tgnet.repositories.ClientRepositories;
import com.tgnet.repositories.ISingleRepository;
import com.tgnet.repositories.IWriteableSingleRepository;

/**
 * Created by lzh on 2017/7/17.
 */

public class VisitorSettings {
    private static final String FIRST_IN_LOGIN="first_in_login";
    private static final String DETAIL_OF_PROJECT_TIPS1="show_detail_of_project_tips1";
    private static final String DETAIL_OF_PROJECT_TIPS2="show_detail_of_project_tips2";
    private static final String DETAIL_OF_PROJECT_TIPS3="show_detail_of_project_tips3";
    private static final String TG_PROJECT_DETAIL_TIPS="tg_project_detail_tips";
    private static final String PROJECT_DETAIL_TIPS="project_detail_tips";

    //--------------------第一次在登录界面登录-----------------------------
    public boolean getIsFirstInLogin() {
        Boolean value = getSingleRepository(FIRST_IN_LOGIN, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }

    public void setIsFirstInLogin(boolean isFirstInLogin) {
        getWriteableSingleRepository(FIRST_IN_LOGIN).addOrReplace(isFirstInLogin);
    }

    private <T> ISingleRepository<T> getSingleRepository(String key, Class<T> classOfT) {
        return ClientRepositories
                .getInstance()
                .getSharedPreferencesRepositoryProvider()
                .GetSingleRepository("LocalSettings_" , key, classOfT);
    }
    private IWriteableSingleRepository getWriteableSingleRepository(String key) {
        return getSingleRepository(key, Object.class);
    }
    //-------------------第一次弹出项目底层页的右上角提示----------------------
    public boolean getIsShowDetailOfProjectTip1() {
        Boolean value = getSingleRepository(DETAIL_OF_PROJECT_TIPS1, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }

    public void setIsShowDetailOfProjectTip1(boolean isFirstInLogin) {
        getWriteableSingleRepository(DETAIL_OF_PROJECT_TIPS1).addOrReplace(isFirstInLogin);
    }
    public void setIsShowDetailOfProjectTip2(boolean isFirstInLogin) {
        getWriteableSingleRepository(DETAIL_OF_PROJECT_TIPS2).addOrReplace(isFirstInLogin);
    }
    public boolean getIsShowDetailOfProjectTip2() {
        Boolean value = getSingleRepository(DETAIL_OF_PROJECT_TIPS2, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }
    public void setIsShowDetailOfProjectTip3(boolean isFirstInLogin) {
        getWriteableSingleRepository(DETAIL_OF_PROJECT_TIPS3).addOrReplace(isFirstInLogin);
    }
    public boolean getIsShowDetailOfProjectTip3() {
        Boolean value = getSingleRepository(DETAIL_OF_PROJECT_TIPS3, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }

    //----------------是否显示参考信息提示---------------------------------
    public boolean getProjectDetailTips() {
        Boolean value = getSingleRepository(PROJECT_DETAIL_TIPS, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }

    public void setProjectDetailTips(boolean isFirstPersonalCard) {
        getWriteableSingleRepository(PROJECT_DETAIL_TIPS).addOrReplace(isFirstPersonalCard);
    }
    //----------------是否显示参考信息提示---------------------------------
    public boolean getTgProjectDetailTips() {
        Boolean value = getSingleRepository(TG_PROJECT_DETAIL_TIPS, boolean.class).get();
        if (value == null)
            return true;
        else
            return value.booleanValue();
    }

    public void setTgProjectDetailTips(boolean isFirstPersonalCard) {
        getWriteableSingleRepository(TG_PROJECT_DETAIL_TIPS).addOrReplace(isFirstPersonalCard);
    }
}
