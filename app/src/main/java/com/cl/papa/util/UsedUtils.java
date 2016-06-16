package com.cl.papa.util;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;

/**
 * Created by Administrator on 2016/4/29.
 */
public class UsedUtils {
    private  static ImageOptions imageOptions = null;

    public static ImageOptions getImageOptions() {
        if (imageOptions == null) {
            imageOptions = new ImageOptions.Builder()
                    // 加载中或错误图片的ScaleType
                    .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    // 默认自动适应大小
                    // 是否忽略GIF格式的图片
                    .setIgnoreGif(false)
                    // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                    .setUseMemCache(false)
                    .setImageScaleType(ImageView.ScaleType.FIT_XY).build();
        }
    return imageOptions;
    }

    public static String getTime(int second) {
        String strh = null;
        String strd = null;
        String strs = null;
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if(h < 10)
                strh = "0" + h;
            else
                strh = String.valueOf(h);
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
            d = second / 60;
            if(d < 10)
                strd = "0" + d;
            else
                strd = String.valueOf(d);
            if(s < 10)
                strs = "0" + s;
            else
                strs = String.valueOf(s);
        } else {
            strh = "00";
            d = second / 60;
            if(d < 10)
                strd = "0" + d;
            else
             strd = String.valueOf(d);

            if (second % 60 != 0) {
                s = second % 60;
            }
            if(s < 10)
                strs = "0" + s;
            else
                strs = String.valueOf(s);
        }
        return strh + ":" + strd + ":" + strs;
    }
}
