package com.bank.quickpay.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bank.quickpay.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * Glide图像加载工具类
 *
 * @author muxin
 * @time 2016-08-22 11:16
 */
public class GlideUtils {

    public static GlideUtils instance = new GlideUtils();

    public GlideUtils() {
    }

    public static GlideUtils getInstance() {
        return instance;
    }

    // 加载网络图片
    public void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
//                .placeholder(R.color.abc_tab_text_normal)//显示加载时的图片
//                .error(R.color.abc_tab_text_normal)//加载失败默认显示的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                .dontAnimate()//无动画
                .into(imageView);
    }

    // 加载本地图片
    public void load(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .asBitmap()//加载静态图片
                .dontAnimate()//无动画
                .into(imageView);
    }

    // 加载网络图片动画
    public void loadAnima(Context context, String url, ViewPropertyAnimation.Animator animator, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(animator)//属性动画
                .crossFade()//淡入淡出动画300毫秒
                .into(imageView);
    }

    // 加载网络图片动画
    public void loadAnima(Context context, String url, int animationId, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(animationId)
                .crossFade()
                .into(imageView);
    }

    // 加载本地图片动画
    public void loadAnima(Context context, int resId, ViewPropertyAnimation.Animator animator, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .animate(animator)
                .crossFade()
                .into(imageView);
    }

    // 加载drawable图片
    public void loadAnima(Context context, int resId, int animationId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .animate(animationId)
                .into(imageView);
    }
    public  ImageView getItemImageView(Context context, String resUrl) {
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(resUrl)
                .into(imageView)
                ;
        imageView.setClickable(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    /**
     * 获取随机默认色值图
     * @return
     */
    public int getRandomDefaultColor(){
        int[] colors=new int[]{R.color.img_e5f8ff,R.color.img_fff5e5,R.color.img_e5edff};
        java.util.Random r=new java.util.Random();
        return colors[r.nextInt(3)];
    }
    //    // 加载圆型本地图片
//    public void loadCircle(Context context, int resId, ImageView imageView) {
//        Glide.with(context)
//                .load(resId)
//                .placeholder(R.color.abc_tab_text_normal)
//                .error(R.color.abc_tab_text_normal)
//                .transform(new GlideCircleTransform(context))
//                .crossFade()
//                .into(imageView);
//    }

    // 加载圆型网络图片
//    public void loadCircle(Context context, String url, ImageView imageView) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.color.abc_tab_text_normal)
//                .error(R.color.abc_tab_text_normal)
//                .transform(new GlideCircleTransform(context))
//                .crossFade()
//                .into(imageView);
//    }
}
