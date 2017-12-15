package com.zbd.app.activity.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ysnet.zdb.resource.bean.IndexShopCartBean;
import com.zbd.app.R;
import com.zbd.app.picasso.ImageLoader;
import com.zbd.app.widget.LoadMoreAdapter;

import java.util.List;

/**
 * @author fan-gk
 * @date 2017/12/11.
 */
public class IndexShopCartAdapter extends LoadMoreAdapter<IndexShopCartBean> {
    public IndexShopCartAdapter(List<IndexShopCartBean> data) {
        super(R.layout.adapter_index_shop_cart, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, IndexShopCartBean bean) {
        baseViewHolder
                .setText(R.id.tv_content, bean.content)
                .setText(R.id.tv_price, String.format(mContext.getString(R.string.str_price), bean.price))
                .setText(R.id.tv_count, String.format(mContext.getString(R.string.str_count), String.valueOf(bean.count)));
        ImageView imageView = baseViewHolder.getView(R.id.iv_product);
        ImageLoader.loadImage(Uri.parse(bean.imgUrl), imageView,true);
    }
}
