package com.tgnet.app.utils.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.ui.widget.ScrollEditText;
import com.tgnet.app.utils.utils.DialogUtil;
import com.tgnet.app.utils.utils.KeyBoardUtils;
import com.tgnet.delegate.Func1;
import com.tgnet.ui.ICloseable;
import com.tgnet.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.maxLines;
import static android.R.attr.minLines;

/**
 * Created by fan-gk on 2017/7/20.
 */


public class InputDialogFragment extends SimpleDialogFragment {
    private static final String tag = "InputDialogFragment";

    public interface OnSaveListener {
        void onSave(String inputText, ICloseable self);
    }

    public static class Role implements Serializable {
        public CharSequence mMessage;
        public Func1<Boolean, String> mFilter;

        public Role(CharSequence msg, Func1<Boolean, String> filter) {
            mMessage = msg;
            mFilter = filter;
        }
    }


    public static class Builder extends SimpleDialogFragment.Builder<InputDialogFragment, Builder> {
        private String title;
        private String hint;
        private String content;
        private int maxEms;
        private int minEms;
        private InputFilter[] mFilters;
        public List<Role> mFilterRoles = new ArrayList<>();
        private String leftText;
        private String rightText;
        private String tips;
        private Integer titleGravity;
        private boolean isShowWordLimit;
        private int height;
        private int maxLines;
        private int minLines;
        private int lines;

        public int getLines() {
            return lines;
        }

        public Builder setLines(int lines) {
            this.lines = lines;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setMaxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        public String getTips() {
            return tips;
        }

        public Integer getTitleGravity() {
            return titleGravity;
        }

        public Builder setTitleGravity(int titleGravity) {
            this.titleGravity = titleGravity;
            return this;
        }

        public Builder setTips(String tips) {
            this.tips = tips;
            return this;
        }

        public Builder setMinLines(int minLines) {
            this.minLines = minLines;
            return this;
        }

        public int getMaxLines() {
            return maxLines;
        }

        public int getMinLines() {
            return minLines;
        }

        public int getHeight() {
            return height;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Builder setFilters(InputFilter[] filters) {
            mFilters = filters;
            return this;
        }

        public InputFilter[] getFilters() {
            return mFilters;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }


        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setMaxEms(int maxEms) {
            this.maxEms = maxEms;
            return this;
        }

        public Builder setMinEms(int minEms) {
            this.minEms = minEms;
            return this;
        }

        public Builder addRole(Role role) {
            mFilterRoles.add(role);
            return this;
        }

        public Builder setShowWordLimit(boolean showWordLimit) {
            isShowWordLimit = showWordLimit;
            return this;
        }

        public String getTitle() {
            return title;
        }


        public int getMaxEms() {
            return maxEms;
        }

        public String getHint() {
            return hint;
        }

        public int getMinEms() {
            return minEms;
        }

        public boolean isShowWordLimit() {
            return isShowWordLimit;
        }

        @Override
        public InputDialogFragment build() {
            InputDialogFragment dialogFragment = new InputDialogFragment();
            dialogFragment.setBuilder(this);
            return dialogFragment;
        }
    }

    public InputDialogFragment setOnSaveListener(OnSaveListener onSaveListener) {
        mOnSaveListener = onSaveListener;
        return this;
    }

    public OnSaveListener getOnSaveListener() {
        return mOnSaveListener;
    }

    private final ICloseable closeable = new ICloseable() {
        @Override
        public void close() {
            hideKeyBoard();
            dismiss();
        }
    };

    private OnSaveListener mOnSaveListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.InputDialogFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input_fragment, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        final ScrollEditText editText = (ScrollEditText) view.findViewById(R.id.edit_content);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvSave = (TextView) view.findViewById(R.id.tv_save);
        TextView tvTips = (TextView) view.findViewById(R.id.tv_tips);
        final TextView tvWordCount = (TextView) view.findViewById(R.id.tv_word_count);
        TextView tvWordLimit = (TextView) view.findViewById(R.id.tv_word_limit);
        View line = view.findViewById(R.id.line_title);
        final Builder builder = getBuilder();
        if (builder != null) {
            if (!StringUtil.isNullOrEmpty(builder.getTitle()))
                tvTitle.setText(builder.getTitle());
            if (!StringUtil.isNullOrEmpty(builder.getHint()))
                editText.setHint(builder.getHint());
            if (builder.getMaxLines() != 0)
                editText.setMaxLines(maxLines);
            if (builder.minLines != 0)
                editText.setMinLines(minLines);
            if (builder.getFilters() != null)
                editText.setFilters(builder.getFilters());
            if (builder.getLines() != 0)
                editText.setLines(builder.getLines());
            if (builder.getMaxEms() != 0)
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(builder.getMaxEms())});
            if (builder.getMinEms() != 0)
                editText.setMinEms(builder.getMinEms());
            if (builder.getHeight() != 0) {
                ViewGroup.LayoutParams layoutParams = editText.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = builder.getHeight();
                editText.setLayoutParams(layoutParams);
            }
            if (!StringUtil.isNullOrWhiteSpace(builder.getTips())) {
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText(builder.getTips());
                line.setVisibility(View.VISIBLE);
            }
            if (builder.getTitleGravity() != null) {
                tvTitle.setGravity(builder.getTitleGravity());
            }


            if (!StringUtil.isNullOrEmpty(builder.leftText))
                tvCancel.setText(builder.leftText);
            if (!StringUtil.isNullOrEmpty(builder.rightText))
                tvSave.setText(builder.rightText);
            if (builder.isShowWordLimit()) {
                tvWordCount.setVisibility(View.VISIBLE);
                tvWordLimit.setVisibility(View.VISIBLE);
                tvWordCount.setText("  0");
                tvWordLimit.setText("/" + String.valueOf(builder.getMaxEms()));
            } else {
                tvWordCount.setVisibility(View.GONE);
                tvWordLimit.setVisibility(View.GONE);
            }
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideKeyBoard();
                    dismiss();
                }
            });
            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String inputText = editText.getText().toString();
                    for (final Role filterRole : builder.mFilterRoles) {
                        if (filterRole != null && filterRole.mFilter != null) {
                            if (!filterRole.mFilter.run(inputText)) {
                                DialogUtil.softOneBtnDialog(getActivity(), filterRole.mMessage);
                                return;
                            }
                        }
                    }
                    final OnSaveListener listener = getOnSaveListener();
                    if (listener != null) {
                        hideKeyBoard();
                        listener.onSave(inputText, closeable);
                    }
                }
            });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() > builder.getMaxEms())
                        tvWordCount.setText("  " + String.valueOf(builder.getMaxEms()));
                    else
                        tvWordCount.setText("  " + String.valueOf(s.toString().trim().length()));

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            String content = builder.getContent();
            if (!StringUtil.isNullOrEmpty(content) && content.length() < builder.getMaxEms()) {
                editText.setText(content);
                editText.setSelection(content.length());
            }
        }
        KeyBoardUtils.showKeyBord(getActivity(), editText);

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
        if (fragment != null && fragment instanceof InputDialogFragment) {
            ((InputDialogFragment) fragment).dismiss();
        }
    }
}

