package com.tgnet.app.utils.ui.widget.gridimg;

import android.content.Context;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by fan-gk on 2017/6/5.
 */

public interface ItemImageClickListener<T> {
    void onItemImageClick(Context context, ImageView imageView, int index, List<T> list);
}

