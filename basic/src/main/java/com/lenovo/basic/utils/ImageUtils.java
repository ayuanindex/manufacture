package com.lenovo.basic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.lenovo.basic.BasicApplication;
import com.lenovo.basic.R;

import java.io.File;

public class ImageUtils {

    /**
     * 设置DrawableLeft等的大小
     *
     * @param textView 需要设置的控件(TextView or EditText)
     */
    public static void setDrawableSize(TextView textView) {
        if (textView == null) {
            return;
        }
        Context context = BasicApplication.getmContext();
        //左，上，右，下
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        for (int i = 0; i < compoundDrawables.length; i++) {
            if (compoundDrawables[i] != null) {
                int size = (int) context.getResources().getDimension(R.dimen.dp_20);
                compoundDrawables[i].setBounds(0, 0, size, size);
            }
        }
        textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }


    /**
     * 裁剪填充
     *
     * @param resId     图片资源
     * @param imageView 图片显示控件
     */
    @SuppressLint("CheckResult")
    public static void setBitmapCenterCrop(Context context, @DrawableRes int resId, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        Glide.with(context).load(resId).apply(requestOptions).into(imageView);
    }

    @SuppressLint("CheckResult")
    public static void setBitmapCenterCrop(Context context, String resId, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        Glide.with(context).load(resId).apply(requestOptions).into(imageView);
    }

}
