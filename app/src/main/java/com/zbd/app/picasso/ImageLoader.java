package com.zbd.app.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.tgnet.util.StringUtil;
import com.zbd.app.R;
import com.zbd.app.http.OkHttp3Downloader;

import java.io.File;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */

public class ImageLoader {
    public static String NO_USE_DEFAULT_IMG = "no_use_default_img";
    public final static String IMGSBASEURL = "http://file.tgimg.cn/Image/Show?fid=";
    public final static String mImg200_200 = "@200w_200h_90Q"; // 缩略图的尺寸
    public final static String mImg270_270 = "@270w_270h_90Q"; // 缩略图的尺寸
    public final static String mImg360_360 = "@360w_360h_90Q"; // 缩略图的尺寸
    private static Picasso iconPicasso = null;
    private static Picasso imagePicasso = null;
    private static CircleTransformation circleTransformation = null;
    private static CustomRadiusTransformation customRadiusTransformation = null;
    private static RoundedBitmapDrawable defaultRoundIcon = null;
    private static OkHttp3Downloader iconDownloader;

    public static void init(@NonNull Context context) {
        iconDownloader = new OkHttp3Downloader(context, "tgnet-cahce-icon");
        iconPicasso = new Picasso
                .Builder(context)
                .downloader(iconDownloader)
                .build();
        imagePicasso = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(context, "tgnet-cache-image", 500 * 1024 * 1024)) //500M
                .build();
        circleTransformation = new CircleTransformation(context);
        customRadiusTransformation = new CustomRadiusTransformation(context);
        defaultRoundIcon = RoundedBitmapDrawableFactory.create(context.getResources()
                , BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_portrait));
        defaultRoundIcon.setCircular(true);
    }

    public static void invalidateIcon(@NonNull String url) {
        iconPicasso.invalidate(url);
        iconDownloader.remove(url);
    }

    public static boolean loadSystemIcon(@NonNull String sessionType, @NonNull ImageView imageView) {
        int iconResId = getResIcon(sessionType);
        if (iconResId == -1)
            return false;
        else {
            imageView.setImageResource(iconResId);
            return true;
        }
    }

    private static int getResIcon(String sessionType) {

        return -1;
    }

    public static void loadIcon(@NonNull Bitmap image, @NonNull ImageView imageView, boolean isRound) {
        if (isRound) {
            RoundedBitmapDrawable roundIcon = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources()
                    , image);
            roundIcon.setCircular(true);
            imageView.setImageDrawable(roundIcon);
        } else {
            imageView.setImageBitmap(image);
        }
    }

    public static void loadIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache) {
        loadIcon(Uri.parse(url), imageView, useCache, false, false);
    }

    public static void loadCircleCornerIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache) {
        loadIcon(Uri.parse(url), imageView, useCache, false, true);
    }

    public static void loadIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache, boolean isRound) {
        loadIcon(Uri.parse(url), imageView, useCache, isRound, false);
    }


    private static void loadIcon(@NonNull Uri url, @NonNull final ImageView imageView, boolean useCache
            , boolean isRound, boolean isCircleCorner) {
        loadIcon(url, imageView, useCache, isRound, isCircleCorner, R.mipmap.default_portrait);
    }


    private static void loadIcon(@NonNull Uri url, @NonNull final ImageView imageView, boolean useCache
            , boolean isRound, boolean isCircleCorner, @DrawableRes int defaultDrawableId) {
        RequestCreator creator = iconPicasso
                .load(url)
                .fit();

        if (!useCache) {
            creator = creator.networkPolicy(NetworkPolicy.NO_CACHE);
            creator = creator.memoryPolicy(MemoryPolicy.NO_CACHE);
        }
        creator.placeholder(defaultDrawableId)
                .error(defaultDrawableId);

        if (isRound) {
            creator.placeholder(defaultDrawableId)
                    .error(defaultDrawableId)
                    .transform(circleTransformation);
        }
        if (isCircleCorner) {
            creator.placeholder(defaultDrawableId)
                    .error(defaultDrawableId)
                    .transform(customRadiusTransformation);
        }

        creator.into(imageView);
    }


    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView) {
        loadImage(url, null, imageView, false, false, null, null);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView, final Runnable onLoaded) {
        loadImage(url, null, imageView, false, false, onLoaded, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView) {
        loadImage(url, thumbnailUrl, imageView, false, false, null, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView, final Runnable
            onLoaded) {
        loadImage(url, thumbnailUrl, imageView, false, false, onLoaded, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView, final Runnable
            onLoaded, final Runnable onError) {
        loadImage(url, thumbnailUrl, imageView, false, false, onLoaded, onError);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView, boolean isCircleCornerRect) {
        loadImage(url, null, imageView, isCircleCornerRect, true, null, null);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView
            , boolean isCircleCornerRect, boolean isFile, final Runnable onLoaded,
                                 final Runnable onError) {
        loadImage(url, null, imageView, isCircleCornerRect, isFile, onLoaded, onError);

    }


    /**
     * @param url
     * @param thumbnailUrl 缩略图地址，只从缓存加载如果缓存没有，显示默认加载中的图片。设置为null时显示默认加载图
     * @param imageView
     * @param onLoaded
     * @param onError
     */
    public static void loadImage(@NonNull Uri url, String thumbnailUrl, @NonNull final ImageView imageView, boolean isCircleCornerRect, boolean isFile, final Runnable onLoaded,
                                 final Runnable onError) {
        RequestCreator loader;
        if (!StringUtil.isNullOrWhiteSpace(thumbnailUrl)) {
            if (StringUtil.equals(thumbnailUrl, NO_USE_DEFAULT_IMG))
                loader = createrLoader(url, null);
            else {
                ImageView placeholder = new ImageView(imageView.getContext());
                imagePicasso
                        .load(thumbnailUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.mipmap.icon_pic_loding)
                        .error(R.mipmap.icon_pic_loding)
                        .into(placeholder);
                loader = createrLoader(url, placeholder.getDrawable());
            }
        } else
            loader = createrLoader(url, R.mipmap.icon_pic_loding);
        if (isCircleCornerRect) {
            loader = createrLoader(url);
        }
        if (isFile) {
            loader = createrLoader(new File(url.toString())
                    , R.mipmap.icon_pic_loding
                    , R.mipmap.ic_launcher);
        }

        loader.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (onLoaded != null)
                    onLoaded.run();
            }

            @Override
            public void onError() {
                if (onError != null)
                    onError.run();
            }
        });
    }

    private static RequestCreator createrLoader(File file, @DrawableRes int placeholder, @DrawableRes int error) {
        return imagePicasso
                .load(file)
                .placeholder(placeholder)
                .error(error)
                .resize(700, 700)
                .centerCrop();
    }

    private static RequestCreator createrLoader(@NonNull Uri url, Drawable placeholder) {
        if (placeholder != null) {
            return imagePicasso
                    .load(url)
                    .placeholder(placeholder)
                    .error(R.mipmap.ic_launcher);
        } else {
            return imagePicasso
                    .load(url);
        }
    }

    private static RequestCreator createrLoader(@NonNull Uri url, @DrawableRes int placeholder) {
        return imagePicasso
                .load(url)
                .placeholder(placeholder)
                .error(R.mipmap.ic_launcher);
    }

    private static RequestCreator createrLoader(@NonNull Uri url) {
        return imagePicasso
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(customRadiusTransformation);
    }


    public static void getBitmap(@NonNull String url, final OnLoadedListener listener) {
        imagePicasso.load(Uri.parse(url)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.onLoadedListen(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    public interface OnLoadedListener {
        void onLoadedListen(Bitmap bitmap);
    }
}
