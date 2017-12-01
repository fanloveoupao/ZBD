package com.tgnet.app.utils.ui.widget;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by fan-gk on 2017/4/21.
 */


public class RadioButtonGroup {
    public interface OnCheckedChangeListener{
        void onCheckedChanged(CompoundButton buttonView);
    }


    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private OnCheckedChangeListener listener;
    private CompoundButton.OnCheckedChangeListener internalCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                for (RadioButton radioButton : radioButtons) {
                    if(radioButton != buttonView)
                        setChecked(radioButton, false);
                }
                final OnCheckedChangeListener l = listener;
                if (l != null) {
                    l.onCheckedChanged(buttonView);
                }
            }
        }
    };

    public RadioButtonGroup(RadioButton... radioButtons){
        if(radioButtons != null)
            for (RadioButton radioButton : radioButtons) {
                addRadioButton(radioButton);
            }
    }

    public void addRadioButton(RadioButton radioButton){
        if(radioButton != null) {
            radioButton.setOnCheckedChangeListener(internalCheckedChangeListener);
            this.radioButtons.add(radioButton);
        }
    }

    public void removeRadioButton(RadioButton radioButton){
        if(radioButton != null) {
            radioButton.setOnCheckedChangeListener(null);
            this.radioButtons.remove(radioButton);
        }
    }

    public void setCheckedIndex(int index) {
        if(radioButtons.size() == 0) return;
        if(index >= radioButtons.size())
            index = 0;
        setChecked(radioButtons.get(index), true);
    }

    public void setChecked(RadioButton buttonView){
        setChecked(buttonView, true);
    }

    public void unCheck(){
        for (RadioButton radioButton : radioButtons) {
            setChecked(radioButton, false);
        }
    }

    private void setChecked(RadioButton radioButton, boolean checked){
        radioButton.setChecked(checked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        this.listener = listener;
    }
}

