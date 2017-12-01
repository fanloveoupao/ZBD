package com.tgnet.app.utils.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.utils.TypedValueUtil;
import com.tgnet.app.utils.utils.ViewUtils;

import java.util.List;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class BaseListDialogFragment extends SimpleDialogFragment {

    private String mTitle;
    private List<?> mLists;
    private double mWidth;
    public int mTitleColor = Color.parseColor("#808080");
    private boolean mCanceledOnTouchOutside = true;
    private boolean mCancelable = true;
    private DialogItemClickListener mItemClickListener;
    private RecyclerView mRecyclerView;
    private int mTitleGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private int mItemGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    @NonNull
    public BaseListDialogFragment setItemClickListener(@NonNull DialogItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @NonNull
    public BaseListDialogFragment setStringList(List<?> lists) {
        mLists = lists;
        return this;
    }

    public BaseListDialogFragment setTitle(String title) {
        mTitle = title;
        return this;
    }

    public BaseListDialogFragment setWidth(double width) {
        mWidth = width;
        return this;
    }

    public BaseListDialogFragment setCancleOnTouchOutSide(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public BaseListDialogFragment setCancleable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    public BaseListDialogFragment setTitleColor(int color) {
        mTitleColor = color;
        return this;
    }

    public BaseListDialogFragment setTitleGravity(int mTitleGravity) {
        this.mTitleGravity = mTitleGravity;
        return this;
    }

    public BaseListDialogFragment setItemGravity(int mItemGravity) {
        this.mItemGravity = mItemGravity;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_list_fragment, null);
        final Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        dialog.setCancelable(mCancelable);
        View rl_container = view.findViewById(R.id.rl_container);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        View titleLine = view.findViewById(R.id.title_line);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        if (mTitle != null) {
            tvTitle.setText(mTitle);
            tvTitle.setTextColor(mTitleColor);
            tvTitle.setGravity(mTitleGravity);
        } else {
            tvTitle.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
        }
        rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCanceledOnTouchOutside)
                    dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        // window.getAttributes().windowAnimations = R.style.dialogAnim;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mLists != null && mLists.size() != 0)
            mRecyclerView.setAdapter(new ListAdapter(mLists));
        int maxHeight = TypedValueUtil.fromDip(300);
        if (ViewUtils.getViewHeight(mRecyclerView) > maxHeight)
            ViewUtils.setViewHeight(mRecyclerView, maxHeight);
    }

    public interface DialogItemClickListener {
        void itemClickCallBack(BaseListDialogFragment dialogFragment, String itemText, int position);
    }


    //===================================ListAdapter=======================================================================

    private class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<?> mStringList;

        public ListAdapter(List<?> strings) {
            mStringList = strings;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view, parent, false);
            return new DialogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            DialogViewHolder viewHolder = (DialogViewHolder) holder;
            viewHolder.mTextView.setText(mStringList.get(position).toString());
            viewHolder.mTextView.setGravity(mItemGravity);
            if (position == mStringList.size() - 1)
                viewHolder.mLine.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null)
                        mItemClickListener.itemClickCallBack(BaseListDialogFragment.this, mStringList.get(position).toString(), position);
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }

        private class DialogViewHolder extends RecyclerView.ViewHolder {
            private final TextView mTextView;
            private final View mLine;

            public DialogViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_item);
                mLine = itemView.findViewById(R.id.line);
            }
        }

    }
}

