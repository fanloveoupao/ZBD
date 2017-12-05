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
import com.tgnet.app.utils.executor.ThreadExecutor;
import com.tgnet.app.utils.utils.TypedValueUtil;
import com.tgnet.util.StringUtil;
import com.zbd.app.R;
import com.zbd.app.http.OkHttp3Downloader;

import java.io.IOException;

/**
 * @author fan-gk
 * @date 2017/12/4.
 */

public class ImageLoader {
    public static String NO_USE_DEFAULT_IMG = "no_use_default_img";
    private static Picasso iconPicasso = null;
    private static Picasso imagePicasso = null;
    private static CircleTransformation circleTransformation = null;
    private static CircleCornerTransformation circleCornerTransformation = null;
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
        circleCornerTransformation = new CircleCornerTransformation(context);
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

    public static void loadCircleCornerIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache, float cornerX, float
            cornerY) {
        if (circleCornerTransformation != null) {
            circleCornerTransformation.setCornerSize(TypedValueUtil.fromDip(cornerX), TypedValueUtil.fromDip(cornerY));
            loadIcon(Uri.parse(url), imageView, useCache, false, true);
        }
    }


    public static void loadIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache, boolean isRound) {
        loadIcon(Uri.parse(url), imageView, useCache, isRound, false);
    }

    public static void loadIcon(@NonNull Uri url, @NonNull final ImageView imageView, boolean useCache
            , boolean isRound, boolean isCircleCorner) {
        RequestCreator creator = iconPicasso
                .load(url)
                .fit();

        if (!useCache) {
            creator = creator.networkPolicy(NetworkPolicy.NO_CACHE);
            creator = creator.memoryPolicy(MemoryPolicy.NO_CACHE);
        }
        if (isRound) {
            creator.placeholder(defaultRoundIcon)
                    .error(defaultRoundIcon)
                    .transform(circleTransformation);
        } else if (isCircleCorner) {
            creator.placeholder(R.mipmap.default_portrait_circle_corner)
                    .error(R.mipmap.default_portrait_circle_corner)
                    .transform(circleCornerTransformation);
        } else {
            creator.placeholder(R.mipmap.default_portrait)
                    .error(R.mipmap.default_portrait);
        }

        creator.into(imageView);
    }


    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView) {
        loadImage(url, null, imageView, null, null);
    }

    public static void loadImage(@NonNull Uri url, @DrawableRes int placeHolder, @DrawableRes int error, @NonNull final ImageView
            imageView) {
        loadImage(url, null, placeHolder, error, imageView, false, null, null);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView, final Runnable onLoaded) {
        loadImage(url, null, imageView, onLoaded, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView) {
        loadImage(url, thumbnailUrl, imageView, null, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView, final Runnable
            onLoaded) {
        loadImage(url, thumbnailUrl, imageView, onLoaded, null);
    }

    public static void loadImage(@NonNull Uri url, String thumbnailUrl, @NonNull final ImageView imageView, final Runnable onLoaded,
                                 final Runnable onError) {
        loadImage(url, thumbnailUrl, R.mipmap.icon_pic_loding, R.mipmap.ic_launcher, imageView, false, onLoaded, onError);
    }

    public static void loadCircleCornerImage(@NonNull String url, String thumbnailUrl, @NonNull ImageView imageView, int cornerX, int
            cornerY, final Runnable onLoaded, final Runnable onError) {
        if (circleCornerTransformation != null) {
            circleCornerTransformation.setCornerSize(TypedValueUtil.fromDip(cornerX), TypedValueUtil.fromDip(cornerY));
            loadImage(Uri.parse(url), thumbnailUrl, R.mipmap.icon_pic_loding, R.mipmap.ic_launcher, imageView, true, onLoaded,
                    onError);
        }

    }


    /**
     * @param url
     * @param thumbnailUrl 缩略图地址，只从缓存加载如果缓存没有，显示默认加载中的图片。设置为null时显示默认加载图
     * @param imageView
     * @param onLoaded
     * @param onError
     */
    public static void loadImage(@NonNull Uri url, String thumbnailUrl, @DrawableRes int placeHolderRes, @DrawableRes int error, @NonNull
    final ImageView imageView, boolean isRound, final Runnable onLoaded, final Runnable onError) {
        RequestCreator loader;
        if (!StringUtil.isNullOrWhiteSpace(thumbnailUrl)) {
            if (StringUtil.equals(thumbnailUrl, NO_USE_DEFAULT_IMG))
                loader = createrLoader(url, null, -1);
            else {
                ImageView placeholder = new ImageView(imageView.getContext());
                imagePicasso
                        .load(thumbnailUrl)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(placeHolderRes)
                        .error(placeHolderRes)
                        .into(placeholder);
                loader = createrLoader(url, placeholder.getDrawable(), error);
            }
        } else {
            loader = createrLoader(url, placeHolderRes, error);
        }
        if (isRound)
            loader.transform(circleCornerTransformation);

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

    private static RequestCreator createrLoader(@NonNull Uri url, Drawable placeholder, @DrawableRes int error) {
        if (placeholder != null) {
            return imagePicasso
                    .load(url)
                    .placeholder(placeholder)
                    .error(error);
        } else {
            return imagePicasso
                    .load(url);
        }
    }

    private static RequestCreator createrLoader(@NonNull Uri url, @DrawableRes int placeholder, @DrawableRes int error) {
        return imagePicasso
                .load(url)
                .placeholder(placeholder)
                .error(error);
    }


    public static void loadImage(final Context context, @NonNull final String url, final LoadImageCallBack loadImageCallBack) {
        ThreadExecutor.runInAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap bitmap = Picasso.with(context).load(url).get();
                    if (bitmap != null)
                        ThreadExecutor.runInMain(new Runnable() {
                            @Override
                            public void run() {
                                loadImageCallBack.loadImageSuccess(bitmap);
                            }
                        });

                } catch (IOException e) {
                    e.printStackTrace();
                    ThreadExecutor.runInAsync(new Runnable() {
                        @Override
                        public void run() {
                            loadImageCallBack.loadImageFailed();
                        }
                    });

                }
            }
        });


    }


    public interface LoadImageCallBack {
        void loadImageSuccess(Bitmap bitmap);

        void loadImageFailed();
    }


}
