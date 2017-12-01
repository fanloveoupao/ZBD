package com.tgnet.app.utils.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.tgnet.app.utils.ui.dialog.BaseListDialogFragment;
import com.tgnet.app.utils.utils.DialogUtil;

import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class ShowCallDialog {

    public static void call(@NonNull final FragmentActivity context, @NonNull final CharSequence content, final String tel){
        DialogUtil.softTwoBtnDialog(context, content, "取消", "呼叫", new Runnable() {
            @Override
            public void run() {
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                context.startActivity(call);
            }
        });
    }

    public static void call(@NonNull final FragmentActivity context, @NonNull final String content,final List<String> tel){
        DialogUtil.showListDialog(context,content,tel,true, new BaseListDialogFragment.DialogItemClickListener() {
            @Override
            public void itemClickCallBack(BaseListDialogFragment dialogFragment, String itemText, int position) {
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.get(position)));
                context.startActivity(call);
            }
        });
    }
}
