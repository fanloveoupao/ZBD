package com.zbd.app.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zbd.app.R;

import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */

public class LoadMoreAdapter<T> extends BaseQuickAdapter<T> {


    public LoadMoreAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public LoadMoreAdapter(List<T> data) {
        super(data);
    }

    public LoadMoreAdapter(View contentView, List<T> data) {
        super(contentView, data);
    }

    @Override
    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (layoutResId == com.chad.library.R.layout.def_loading) {
            layoutResId = R.layout.load_more_view;
            return super.createBaseViewHolder(parent, layoutResId);
        }
        return super.createBaseViewHolder(parent, layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, T t) {

    }

    public void addNoMoreDataFooter(Context context, RecyclerView recyclerView) {
        this.removeAllFooterView();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_no_more_data,
                (ViewGroup) recyclerView.getParent(), false);
        this.addFooterView(view);

    }

}
