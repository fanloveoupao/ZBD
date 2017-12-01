package com.tgnet.app.utils.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.base.BaseDialogFragment;

import java.io.Serializable;

/**
 * Created by fan-gk on 2017/4/20.
 */
public abstract class SimpleDialogFragment extends BaseDialogFragment {
    private final static String ARG_KEY_BUILDER = "__builder";

    public static abstract class Builder<T extends SimpleDialogFragment, E extends Builder> implements Serializable {
        private boolean cancelable = true;

        public boolean isCancelable() {
            return cancelable;
        }


        public E setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return (E)this;
        }

        final public Bundle buildArgs(Bundle baseArgs){
            if(baseArgs == null)
                baseArgs = new Bundle();
            baseArgs.putSerializable(ARG_KEY_BUILDER, this);
            return baseArgs;
        }

        public abstract T build();
    }

    final protected <T extends Builder> void setBuilder(T builder){
        this.setArguments(builder.buildArgs(getArguments()));
    }

    final protected <T extends Builder> T getBuilder(){
        Bundle bundle = getArguments();
        if(bundle == null)
            return null;
        else
            return (T) bundle.getSerializable(ARG_KEY_BUILDER);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TranslucentDialogFragment);

        Builder builder = getBuilder();
        if(builder != null){
            setCancelable(builder.isCancelable());
        }
    }
}
