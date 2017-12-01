package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class DateTimeAdapter extends BaseAdapter {

    private List<String> data;
    private Context mContext;

    public DateTimeAdapter(Context context, List<String> data) {

        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size() + 4;
    }

    @Override
    public Object getItem(int position) {

        if (position > 1 && position < data.size() + 2)
            return data.get(position - 2);

        return "";

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textParams.width = 200;
        textParams.gravity = Gravity.CENTER;

        view.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(parent.getContext());
        tv.setTextSize(16);
        tv.setTextColor(Color.parseColor("#a9a9a9"));
        tv.setPadding(5, 10, 5, 10);
        tv.setGravity(Gravity.CENTER);
        tv.setId(1);
        tv.setLayoutParams(textParams);
        view.setLayoutParams(viewParams);
        view.addView(tv);

        if (position == 0 || position == 1 || position == data.size() + 2
                || position == data.size() + 3) {
            tv.setVisibility(View.INVISIBLE);
        } else {
            tv.setVisibility(View.VISIBLE);
        }

        if (position > 1 && position < data.size() + 2) {

            tv.setText(data.get(position - 2));
        }

        return view;
    }


    class ViewHolder {
        TextView tv;
    }


}
