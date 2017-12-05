package com.zbd.app.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by nabychan on 2016/12/7.
 */

public class CircleTransformation implements Transformation {
    private final Context mContext;

    public CircleTransformation(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        Bitmap finalBitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(finalBitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        source.recycle();

        return finalBitmap;

    }

    @Override
    public String key(){
        return "com.tgnet.android.ywq.picasso.CircleTransformation";
    }
}
