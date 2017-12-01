package com.tgnet.app.utils.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by fan-gk on 2017/4/24.
 */


public class ImageHelper {
    public static Bitmap compressImage(File file, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        double width = options.outWidth, heigth = options.outHeight;
        if (width > maxWidth || heigth > maxHeight) {
            double wScale = width / maxWidth, hScale = heigth / maxHeight;
            options.inSampleSize = (int) Math.floor(Math.max(wScale, hScale));
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return bitmap;
    }


    /**
     * @param file
     * @param maxWidth
     * @param maxHeight
     * @param size      文件大小，字节
     * @return 压缩失败返回null, 采用jpeg格式压缩
     */
    public static ByteArrayOutputStream compressImage(File file, int maxWidth, int maxHeight, int size) {
        Bitmap bitmap = compressImage(file, maxWidth, maxHeight);
        ByteArrayOutputStream byteArrayOutputStream = compressImage(bitmap, size);
        if (byteArrayOutputStream == null) {
            //失败采用另外一种压缩
            byteArrayOutputStream = compressImage(file.getPath(), maxWidth, maxHeight, size);
        }


        return byteArrayOutputStream;
    }


    public static ByteArrayOutputStream compressImage(String fileName, int maxWdith, int maxHeight, int size) {
        Bitmap bitmap = decodeSampleBitmapFile(fileName, maxWdith, maxHeight);
        ByteArrayOutputStream byteArrayOutputStream = compressImage(bitmap, size);
        bitmap.recycle();
        return byteArrayOutputStream;
    }



    public static ByteArrayOutputStream compressImage(Bitmap bitmap, int size) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            do {
                stream.reset();
                bitmap.compress(format, quality, stream);
                quality -= 10;
                if (quality == 10) {
                    break;
                }
            } while (stream.size() > size);
        } catch (Exception e) {
            if (stream != null)
                try {
                    stream.close();
                    return null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        }
        return stream;
    }

    /**
     * 使用采样率压缩Bitmap
     *
     * @param resources
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampleBitmapResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calcuteInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);

    }

    /**
     * 使用采样率压缩Bitmap
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampleBitmapFile(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calcuteInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     *
     *
     * */
    private static int calcuteInSampleSize(BitmapFactory.Options option, int reqWidth, int reqHeight) {
        if (reqHeight == 0 || reqWidth == 0) {
            return 1;
        }
        int height = option.outHeight;
        int width = option.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHight = height / 2;
            int halgWidth = width / 2;
            while ((halfHight / inSampleSize) >= reqHeight && (halgWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}

