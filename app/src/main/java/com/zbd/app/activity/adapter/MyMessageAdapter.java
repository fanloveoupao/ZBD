package com.zbd.app.activity.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ysnet.zdb.resource.bean.MyMessageBean;
import com.zbd.app.R;
import com.zbd.app.picasso.ImageLoader;
import com.zbd.app.widget.LoadMoreAdapter;

import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/6.
 */
public class MyMessageAdapter extends LoadMoreAdapter<MyMessageBean> {
    public MyMessageAdapter(List<MyMessageBean> data) {
        super(R.layout.adapter_my_message, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyMessageBean myMessageBean) {
        baseViewHolder.setText(R.id.tv_content, myMessageBean.content);
        ImageView imageView = baseViewHolder.getView(R.id.iv_message);
        ImageLoader.loadIcon(myMessageBean.url,imageView,false);
    }
}
