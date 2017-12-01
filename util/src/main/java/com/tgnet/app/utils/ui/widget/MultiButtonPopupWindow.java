package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.exceptions.UnloginException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fan-gk on 2017/4/25.
 */


public class MultiButtonPopupWindow extends SimplePopupWindow {
    public static class Builder {
        private Runnable onDismissedListener;
        private List<BtnItem> btnItems;
        private final Context context;
        private final MultiButtonPopupWindow popupWindow;

        private Builder(Context context) {
            this.context = context;
            this.popupWindow = null;
        }

        private Builder(MultiButtonPopupWindow popupWindow) {
            this.context = null;
            this.popupWindow = popupWindow;
        }

        public <T> Builder init(T data, Map<String, OnClickListener<T>> options) {
            List<BtnItem> items = new ArrayList<>();
            int index = 0;
            for (String name : options.keySet()) {
                items.add(new BtnItem(index++, name, data, options.get(name)));
            }
            this.btnItems = items;
            return this;
        }

        public Builder init(List<String> names, OnClickListener<Integer> listener) {
            List<BtnItem> items = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                items.add(new BtnItem(i, names.get(i), i, listener));
            }
            this.btnItems = items;
            return this;
        }

        public <T> Builder init(List<String> names, T data, OnClickListener<T> listener) {
            List<BtnItem> items = new ArrayList<>();
            for (int i = 0; i < names.size(); i++) {
                items.add(new BtnItem(i, names.get(i), data, listener));
            }
            this.btnItems = items;
            return this;
        }

        public <T> Builder init(Map<String, T> values, OnClickListener<T> listener) {
            List<BtnItem> items = new ArrayList<>();
            int position = 0;
            for (String name : values.keySet()) {
                items.add(new BtnItem(position++, name, values.get(name), listener));
            }
            this.btnItems = items;
            return this;
        }

        public Builder setOnDismissedListener(Runnable onDismissedListener){
            this.onDismissedListener = onDismissedListener;
            return this;
        }

        public MultiButtonPopupWindow build() {
            MultiButtonPopupWindow popupWindow = this.popupWindow;
            if (popupWindow == null)
                popupWindow = new MultiButtonPopupWindow(context);
            popupWindow.init(btnItems == null ? new ArrayList<BtnItem>() : btnItems);
            popupWindow.onDismissedListener = onDismissedListener;
            return popupWindow;
        }
    }

    public interface OnClickListener<T> {
        void onClick(int position, T data) throws UnloginException;
    }

    private static class BtnItem {
        public final int position;
        public final String name;
        public final Object value;
        public final OnClickListener listener;

        public BtnItem(int position, String name, Object value, OnClickListener listener) {
            this.position = position;
            this.name = name;
            this.value = value;
            this.listener = listener;
        }
    }

    private class BtnListAdpater extends BaseAdapter {

        private List<BtnItem> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = View.inflate(
                        MultiButtonPopupWindow.this.getContentView().getContext(),
                        R.layout.view_multi_btn_item, null);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BtnItem tagData = (BtnItem) v.getTag();
                        if(tagData != null && tagData.listener != null)
                            try {
                                tagData.listener.onClick(tagData.position, tagData.value);
                            } catch (UnloginException e) {
                                e.printStackTrace();
                            }
                        dismiss();
                    }
                });
            }
            BtnItem data = (BtnItem) getItem(position);
            if (position == getCount() - 1) {
                view.findViewById(R.id.v_line).setVisibility(View.GONE);
            }
            ((TextView) view.findViewById(R.id.tv_title)).setText(data.name);
            view.setTag(data);
            return view;
        }

        public void setNewData(List<BtnItem> items) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }


    private ListView btnLists;
    private BtnListAdpater btnListAdpater;
    private Runnable onDismissedListener;

    private MultiButtonPopupWindow(Context context) {
        super(context, R.layout.popup_multi_btn);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparency_30)));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        btnListAdpater = new BtnListAdpater();
        btnLists = (ListView) getContentView().findViewById(R.id.ll_container);
        btnLists.setAdapter(btnListAdpater);
        getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void init(List<BtnItem> items) {
        btnListAdpater.setNewData(items);
    }

    public void show(@NonNull View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public Builder newBuilder(){
        Builder builder = new Builder(this);
        builder.btnItems = btnListAdpater.items;
        return builder;
    }

    public static Builder builder(Context context){
        return new Builder(context);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(onDismissedListener != null)
            onDismissedListener.run();
    }
}
