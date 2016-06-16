package com.cl.papa;

import android.app.Activity;
import android.app.Application;

import com.cl.papa.util.Constants;
import com.umeng.socialize.PlatformConfig;
import com.youku.player.YoukuPlayerBaseConfiguration;

import org.xutils.x;

/**
 * Created by Administrator on 2016/4/27.
 */
public class MyApp extends Application{
    public static YoukuPlayerBaseConfiguration configuration;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

        //友盟分享初始化
        PlatformConfig.setWeixin(Constants.WeiXin_Appid, Constants.WeiXin_Appsecret);
        //微信 appid appsecret
        PlatformConfig.setQQZone(Constants.QQ_Appid, Constants.QQ_appkey);

        configuration = new YoukuPlayerBaseConfiguration(this) {


            /**
             * 通过覆写该方法，返回“正在缓存视频信息的界面”，
             * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
             * 用户需要定义自己的缓存界面
             */
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                // TODO Auto-generated method stub
                return null;
            }

            /**
             * 通过覆写该方法，返回“已经缓存视频信息的界面”，
             * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
             * 用户需要定义自己的已缓存界面
             */

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                // TODO Auto-generated method stub
                return null;
            }

            /**
             * 配置视频的缓存路径，格式举例： /appname/videocache/
             * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/
             *
             */
            @Override
            public String configDownloadPath() {
                // TODO Auto-generated method stub

                //return "/myapp/videocache/";			//举例
                return null;
            }
        };
    }
}
