package com.zbd.app.widget;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.ysnet.zdb.resource.bean.AdvertisementsBean;
import com.zbd.app.R;
import com.zbd.app.picasso.ImageLoader;


public class LocalImageHolderView implements Holder<AdvertisementsBean> {
    private ImageView imageView;
    private int placeLoaderId;
    private int errorDrawableId;

    public LocalImageHolderView() {

    }

    public LocalImageHolderView(int placeLoaderId, int errorDrawableId) {
        this.placeLoaderId = placeLoaderId;
        this.errorDrawableId = errorDrawableId;
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, AdvertisementsBean data) {
            ImageLoader.loadImage(Uri.parse(data.imgUrl), placeLoaderId == 0 ? R.mipmap.ic_launcher : placeLoaderId,
                    errorDrawableId == 0 ? R.mipmap.ic_launcher : errorDrawableId, imageView);
    }
}

