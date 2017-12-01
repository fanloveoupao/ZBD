package com.tgnet.app.utils.ui.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.ui.widget.DateTimeAdapter;
import com.tgnet.app.utils.ui.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class ExperiencePicker extends SimpleDialog {

    private Activity mContext;


    private int year = 1;

    private WheelView wv_experience;

    private List<String> data_year;

    private OnYearSelectListener listener;

    public ExperiencePicker(Activity context) {
        super(context);
        mContext = context;

        intDateView();
    }

    public ExperiencePicker(Activity context, int year) {
        super(context);
        mContext = context;
        this.year = year;

        intDateView();
    }

    private void intDateView() {

        initData();
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_experience_picker, null);
        setContentView(view);
        wv_experience = (WheelView) view.findViewById(R.id.wv_experience);


        wv_experience.setDrawText("年");

        DateTimeAdapter adapter_year = new DateTimeAdapter(mContext,
                data_year);


        wv_experience.setAdapter(adapter_year);

        wv_experience.setOnSelectItemListener(new WheelView.OnSelectItemListener() {

            @Override
            public void onSelectItem(String item) {

                try {

                    int value = Integer.parseInt(item);

                    year = value;

                } catch (Exception e) {

                }
            }
        });


        wv_experience.setDefaultItem(year + "");
        TextView tvOK = (TextView) view.findViewById(R.id.tv_OK);

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    if (null != listener)
                        listener.onSelect(year);
                dismiss();
            }
        });

//        builder.show(mContext, "选择业务经验", view, "确定", "取消", new MaterialDialog.ButtonCallback() {
//            @Override
//            public void onPositive(MaterialDialog dialog) {
//                super.onPositive(dialog);
//                if (null != listener)
//                    listener.onSelect(year);
//            }
//
//
//        });
    }

    private void initData() {

        data_year = new ArrayList<String>();
        for (int i = 1; i < 41; i++) {
            data_year.add(i + "");
        }


    }


    public void setOnYearSelectListener(OnYearSelectListener listener) {

        this.listener = listener;
    }

    public interface OnYearSelectListener {

        public void onSelect(int year);
    }
}
