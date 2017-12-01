package com.tgnet.app.utils.ui.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.ui.widget.TagCloudView;
import com.tgnet.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzh on 2017/9/13.
 */

public class TagsDialogFragment extends SimpleDialogFragment {
    private static final String tag = "TagsDialogFragment";
    private OnSingleClickListener mOnOneListener;
    private OnRightClickListener mOnRightListener;
    private OnButtonClickListener mButtonListener;

    public interface OnSingleClickListener {
        void onSave(String name, long id);
    }

    public interface OnRightClickListener {
        void onRightClick();
    }

    public interface OnButtonClickListener {
        void onRightButtonClick(Map<String, Long> ids, IClose self);
    }

    public interface IClose {
        void onClose();
    }

    IClose iClose = new IClose() {
        @Override
        public void onClose() {
            dismiss();
        }
    };

    public OnSingleClickListener getSingleListener() {
        return mOnOneListener;
    }

    public TagsDialogFragment setSingleListener(OnSingleClickListener mOnOneListener) {
        this.mOnOneListener = mOnOneListener;
        return this;
    }

    public OnRightClickListener getOnRightListener() {
        return mOnRightListener;
    }

    public TagsDialogFragment setOnRightListener(OnRightClickListener mOnRightListener) {
        this.mOnRightListener = mOnRightListener;
        return this;
    }

    public OnButtonClickListener getButtonListener() {
        return mButtonListener;
    }

    public TagsDialogFragment setButtonListener(OnButtonClickListener mButtonListener) {
        this.mButtonListener = mButtonListener;
        return this;
    }

    public static class Builder extends SimpleDialogFragment.Builder<TagsDialogFragment, Builder> {

        private int max;
        private String title;
        private ArrayList<String> mTagList = new ArrayList<>();
        private Map<String, Long> mTagMap;
        private String right;
        private boolean isButtonVisable = false;
        private String leftButton;
        private String rightButton;
        private String describe;
        private boolean isOneBtn = false;
        private boolean cancle = true;
        private int titleColor = Color.parseColor("#808080");
        private Map<String, Long> mSelectMap = new HashMap<>();

        public Map<String, Long> getmSelectMap() {
            return mSelectMap;
        }

        public int getMax() {
            return max;
        }

        public Builder setMax(int max) {
            this.max = max;
            return this;
        }

        public Builder setTitleColor(int color) {
            this.titleColor = color;
            return this;
        }

        public int getTitleColor() {
            return this.titleColor;
        }


        public Builder setOneBtn(boolean oneBtn) {
            this.isOneBtn = oneBtn;
            return this;
        }

        public boolean getOutCancle() {
            return cancle;
        }

        public Builder setOutsideCancle(boolean cancle) {
            this.cancle = cancle;
            return this;
        }


        public boolean isButtonVisable() {
            return isButtonVisable;
        }

        public Builder setButtonVisable(boolean buttonVisable) {
            isButtonVisable = buttonVisable;
            return this;
        }

        public String getLeftButton() {
            return leftButton;
        }

        public Builder setLeftButton(String leftText) {
            this.leftButton = leftText;
            return this;
        }


        public String getRightButton() {
            return rightButton;
        }

        public Builder setRightText(String rightText) {
            this.rightButton = rightText;
            return this;
        }

        public String getRight() {
            return right;
        }

        public Builder setRight(String right) {
            this.right = right;
            return this;
        }

        public String getDescribe() {
            return describe;
        }


        public Builder setDescribe(String describe) {
            this.describe = describe;
            return this;
        }


        public String getTitle() {
            return title;
        }

        public Map<String, Long> getmTagMap() {
            return mTagMap;
        }

        public Builder setmTagMap(Map<String, Long> mTagMap) {
            this.mTagMap = mTagMap;
            mTagList.addAll(mTagMap.keySet());
            return this;
        }

        public Builder setmTagList(List<String> list) {
            this.mTagMap = new HashMap<>();
            for (String s : list) {
                this.mTagMap.put(s, (long) 0);
            }
            mTagList.addAll(list);
            return this;
        }

        public ArrayList<String> getTagList() {
            return mTagList;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        @Override
        public TagsDialogFragment build() {
            TagsDialogFragment dialogFragment = new TagsDialogFragment();
            dialogFragment.setBuilder(this);
            return dialogFragment;
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_tags_fragment, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        final TagCloudView tcv = (TagCloudView) view.findViewById(R.id.tcv_select);
        TextView tvRight = (TextView) view.findViewById(R.id.tv_new_group);
        TextView tvDescribe = (TextView) view.findViewById(R.id.tv_descirbe);
        TextView tvRightButton = (TextView) view.findViewById(R.id.tv_right);
        TextView tvLeftButton = (TextView) view.findViewById(R.id.tv_left);
        LinearLayout llButton = (LinearLayout) view.findViewById(R.id.ll_button);
        View line = view.findViewById(R.id.line_button);

        final Builder builder = getBuilder();
        tvTitle.setTextColor(builder.getTitleColor());

        if (builder != null) {
            if (builder.isOneBtn)
                tvLeftButton.setVisibility(View.GONE);
            if (!StringUtil.isNullOrEmpty(builder.getTitle()))
                tvTitle.setText(builder.getTitle());
            if (!StringUtil.isNullOrWhiteSpace(builder.getRight())) {
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText(builder.getRight());
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (mOnRightListener != null)
                            mOnRightListener.onRightClick();
                    }
                });
            } else {
                tvRight.setVisibility(View.GONE);
            }
            if (builder.isButtonVisable()) {
                if (!StringUtil.isNullOrWhiteSpace(builder.getLeftButton()))
                    tvLeftButton.setText(builder.getLeftButton());
                if (!StringUtil.isNullOrWhiteSpace(builder.getRightButton()))
                    tvRightButton.setText(builder.getRightButton());
                llButton.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                tvLeftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
                tvRightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mButtonListener != null)
                            mButtonListener.onRightButtonClick(builder.getmSelectMap(), iClose);

                    }
                });
            } else {
                llButton.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            }
            if (!StringUtil.isNullOrEmpty(builder.getDescribe())) {
                tvDescribe.setText(builder.getDescribe());
                tvDescribe.setVisibility(View.VISIBLE);
            }


            tcv.setTags(builder.getTagList());
            tcv.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
                @Override
                public void onTagClick(int position) {
                    if (builder.getMax() == 0 && !builder.isButtonVisable()) {
                        dismiss();
                        if (mOnOneListener != null)
                            mOnOneListener.onSave(builder.getTagList().get(position),
                                    builder.getmTagMap().get(builder.getTagList().get(position)));
                    } else {
                        if (builder.getmSelectMap().containsKey(builder.getTagList().get(position))) {
                            tcv.getChildAt(position).setSelected(false);
                            builder.getmSelectMap().remove(builder.getTagList().get(position));
                        } else {
                            if (builder.getmSelectMap().size() < builder.getMax()) {
                                builder.getmSelectMap().put(builder.getTagList().get(position),
                                        builder.getmTagMap().get(builder.getTagList().get(position)));
                                tcv.getChildAt(position).setSelected(true);
                            }

                        }

                    }
                }

                @Override
                public void onTailTagClick() {

                }
            });

            view.findViewById(R.id.rl_background).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (builder.getOutCancle())
                        dismiss();
                }
            });

        }
        return view;
    }


    public void show(FragmentActivity fragmentActivity) {
        if (fragmentActivity.isDestroyed())
            return;
        show(fragmentActivity.getSupportFragmentManager());
    }

    public static void dismiss(FragmentActivity fragmentActivity) {
        dismiss(fragmentActivity.getSupportFragmentManager());
    }

    public void show(FragmentManager fragmentManager) {
        if (isAdded() || isVisible() || isRemoving())
            return;
        show(fragmentManager, tag);
    }

    public static void dismiss(FragmentManager fragmentManager) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null && fragment instanceof TagsDialogFragment) {
            ((TagsDialogFragment) fragment).dismiss();
        }
    }
}
