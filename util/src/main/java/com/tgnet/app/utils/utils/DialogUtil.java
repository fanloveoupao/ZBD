package com.tgnet.app.utils.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.tgnet.app.utils.ui.dialog.BaseListDialogFragment;
import com.tgnet.app.utils.ui.dialog.CommonDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class DialogUtil {

    //单按钮自定义点击事件，点击外部不可取消
    public static void hardOneBtnDialog(final FragmentActivity context, CharSequence content, Runnable runnable) {
        showNoTitleDialog(context, content, null, null, null, false, null, runnable, null);

    }

    public static void hardOneBtnDialog(final FragmentActivity context, CharSequence content, String btnContent) {
        showNoTitleDialog(context, content, null, btnContent, null, false, null, null, null);
    }

    public static void hardOneBtnDialog(final FragmentActivity context, CharSequence content, String btnContent, Runnable runnable) {
        showNoTitleDialog(context, content, null, btnContent, null, false, null, runnable, null);
    }

    public static void softOneBtnDialog(final FragmentActivity context, CharSequence content, String btnContent, Runnable runnable) {
        showNoTitleDialog(context, content, null, btnContent, null, true, null, runnable, null);
    }

    //单按钮自定义点击事件,点击外部可消失
    public static void softOneBtnDialog(final FragmentActivity context, CharSequence content, Runnable runnable) {
        showNoTitleDialog(context, content, null, null, null, true, null, runnable, null);
    }

    //单按钮默认点击消失
    public static void hardOneBtnDialog(final FragmentActivity context, CharSequence content) {
        showNoTitleDialog(context, content, null, null, null, false, null, null, null);
    }

    //单按钮默认点击消失,点击外部可取消
    public static void softOneBtnDialog(final FragmentActivity context, CharSequence content) {
        showNoTitleDialog(context, content, null, null, null, true, null, null, null);
    }

    //双按钮自定义文案，点击外部不可取消
    public static void hardTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText,
                                        String rightText, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, null, false, null, rightRunnable, null);
    }

    //双按钮自定义文案，点击外部不可取消
    public static void hardTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText,
                                        String rightText, Runnable leftRunnable, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, null, false, leftRunnable, rightRunnable, null);
    }

    //双按钮自定义文案，点击外部可取消
    public static void softTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText,
                                        String rightText, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, null, true, null, rightRunnable, null);
    }

    public static void softTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText,
                                        String rightText, Runnable leftRunnable, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, null, true, leftRunnable, rightRunnable, null);
    }


    //双按钮普通对话框，点击外部不可取消
    public static void hardTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, Runnable rightRunnable) {
        showNoTitleDialog(context, content, "取消", "确定", null, false, null, rightRunnable, null);
    }


    //双按钮普通对话框，，点击外部可取消
    public static void softTwoBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, Runnable rightRunnable) {
        showNoTitleDialog(context, content, "取消", "确定", null, true, null, rightRunnable, null);
    }

    public static void softThreeBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText, String
            centerText, String rightText,
                                          Runnable leftRunnable, Runnable centerRunnable, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, centerText, true, leftRunnable, rightRunnable, centerRunnable);
    }

    public static void hardThreeBtnDialog(final FragmentActivity context, @NonNull final CharSequence content, String leftText, String
            centerText, String rightText,
                                          Runnable leftRunnable, Runnable centerRunnable, Runnable rightRunnable) {
        showNoTitleDialog(context, content, leftText, rightText, centerText, false, leftRunnable, rightRunnable, centerRunnable);
    }

    public static void softNotitleListDialog(FragmentActivity context, @NonNull List<String> lists, BaseListDialogFragment
            .DialogItemClickListener listener) {
        showListDialog(context, null, lists, true, listener);
    }

    public static void hardNotitleListDialog(FragmentActivity context, @NonNull List<String> lists, boolean cancleable,
                                             BaseListDialogFragment
                                                     .DialogItemClickListener listener) {
        showListDialog(context, null, lists, cancleable, listener);
    }

    public static void softNotitleListDialog(FragmentActivity context, @NonNull String[] lists, BaseListDialogFragment
            .DialogItemClickListener listener) {
        List<String> stringList = new ArrayList<>();
        if (lists != null) {
            for (String string : lists) {
                stringList.add(string);
            }
        }
        showListDialog(context, null, stringList, true, listener);
    }


    public static void showNoTitleDialog(FragmentActivity context, CharSequence content, String leftBtn, String rightBtn, String
            centerBtn, boolean canceledOnTouchOutside, Runnable leftRunnable, Runnable rightRunnable, Runnable centerRunable) {
        if (context != null && !context.isDestroyed()) {
            CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setContentText(content)
                    .setLeftButtonText(leftBtn)
                    .setDefultButtonText(rightBtn)
                    .setCenterButtonText(centerBtn)
                    .setCanceledOnTouchOutside(canceledOnTouchOutside)
                    .setLeftRunnable(leftRunnable)
                    .setRightRunnable(rightRunnable)
                    .setCenterRunnable(centerRunable);
            context.getSupportFragmentManager().beginTransaction().add(dialogFragment, "CommomDialogFragment").commitAllowingStateLoss();
        }
    }


    public static void showListDialog(FragmentActivity context, String title, List<?> lists, boolean cancleable, BaseListDialogFragment
            .DialogItemClickListener listener) {
        if (context == null || context.isDestroyed() || lists == null || lists.size() == 0)
            return;
        BaseListDialogFragment dialogFragment = new BaseListDialogFragment();
        dialogFragment.setTitle(title)
                .setStringList(lists)
                .setCancleOnTouchOutSide(cancleable)
                .setCancleable(cancleable).setItemClickListener(listener);
        context.getSupportFragmentManager().beginTransaction().add(dialogFragment, "ListDialogFragment").commitAllowingStateLoss();
    }


}

