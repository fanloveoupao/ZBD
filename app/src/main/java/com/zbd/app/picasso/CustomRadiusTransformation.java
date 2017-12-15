package com.zbd.app.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by fan-gk on 2017/7/20.
 */


public class CustomRadiusTransformation implements Transformation {
    private final Context mContext;
    private float mCornerX = 20f;
    private float mCornerY = 20f;

    public CustomRadiusTransformation(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        int WidthSize = bitmap.getWidth();
        int HeightSize =  bitmap.getHeight();

        Bitmap finalBitmap = Bitmap.createBitmap(WidthSize, HeightSize, bitmap.getConfig());
        Canvas canvas = new Canvas(finalBitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        RectF rectf = new RectF(0, 0, WidthSize, HeightSize);
        canvas.drawRoundRect(rectf, mCornerX, mCornerY, paint);
        bitmap.recycle();
        return finalBitmap;
    }

    @Override
    public String key() {
        return "com.tgnet.information.picasso.CustomRadiusTransformation";
    }

}
