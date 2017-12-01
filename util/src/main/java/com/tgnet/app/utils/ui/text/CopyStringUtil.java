package com.tgnet.app.utils.ui.text;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;

import com.tgnet.lang.HtmlUtil;

/**
 * Created by lzh on 2017/9/19.
 */

public class CopyStringUtil {
    public static void copy(Context context,String str){
        ClipboardManager cm=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null,str));
    }

    public static String paste(Context context){
        ClipboardManager cm=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if(cm!=null && cm.getPrimaryClip()!=null){
            String text=cm.getPrimaryClip().getItemAt(0).getText().toString();
            text=HtmlUtil.clearTags(text);
            return text;
        }
        return "";
    }
}
