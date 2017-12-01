package com.tgnet.app.utils.utils;

import android.Manifest;
import android.content.Context;
import android.media.AudioManager;

import com.tgnet.app.utils.base.BaseActivity;
import com.tgnet.app.utils.base.BaseFragment;

/**
 * Created by fan-gk on 2017/4/21.
 */
public class AudioHelper {
    private final BaseActivity activity;
    private AudioManager audioManager;
    private AudioManager audioManager(){
        if(audioManager == null)
            audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        return audioManager;
    }

    public AudioHelper(BaseActivity activity) {
        this.activity = activity;
    }

    public AudioHelper(BaseFragment fragment){
        this((BaseActivity) fragment.getActivity());
    }

    public void setSpeakerphoneOn(boolean on){
        activity.getRxPermissions().request(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        if(on) {
            audioManager().setMode(AudioManager.MODE_NORMAL);
            audioManager().setSpeakerphoneOn(true);
        } else {
            audioManager().setMode(AudioManager.MODE_IN_CALL);
            audioManager().setSpeakerphoneOn(false);
        }
    }

    public boolean isSpeakerphoneOn(){
        return audioManager().isSpeakerphoneOn();
    }
}
