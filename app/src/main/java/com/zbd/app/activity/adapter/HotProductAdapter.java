package com.zbd.app.activity.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.tgnet.util.StringUtil;
import com.ysnet.zdb.resource.bean.HotProductBean;
import com.zbd.app.R;
import com.zbd.app.picasso.ImageLoader;
import com.zbd.app.widget.LoadMoreAdapter;

import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */
public class HotProductAdapter extends LoadMoreAdapter<HotProductBean> {
    public HotProductAdapter(List<HotProductBean> data) {
        super(R.layout.adapter_hot_product, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HotProductBean hotProductBean) {
        baseViewHolder.setText(R.id.tv_name, hotProductBean.name)
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.str_price), hotProductBean.price));
        ImageView imageView = baseViewHolder.getView(R.id.iv_product);
        if (!StringUtil.isNullOrEmpty(hotProductBean.imgUrl))
            ImageLoader.loadImage(Uri.parse(hotProductBean.imgUrl), imageView);
    }
}
