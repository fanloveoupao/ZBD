package com.zbd.app.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by weinp on 2017/6/12.
 */

public class CircleCornerTransformation implements Transformation {
    private final Context mContext;
    private float mCornerX = 10f;
    private float mCornerY = 10f;

    public CircleCornerTransformation(Context context) {
        mContext = context;
    }


    @Override
    public Bitmap transform(Bitmap bitmap) {

        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());

        Bitmap finalBitmap = Bitmap.createBitmap(size, size, bitmap.getConfig());
        Canvas canvas = new Canvas(finalBitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        RectF rectf = new RectF(0, 0, size, size);
        canvas.drawRoundRect(rectf, mCornerX, mCornerY, paint);
        bitmap.recycle();
        return finalBitmap;
    }

    @Override
    public String key() {
        return "com.tgnet.android.ywq.picasso.CircleCornerTransformation";
    }


    public void setCornerSize(float cornerX, float cornerY) {
        mCornerY = cornerY;
        mCornerX = cornerX;
    }

    public float getCornerX() {
        return mCornerX;
    }

    public float getCornerY() {
        return mCornerY;
    }
}
