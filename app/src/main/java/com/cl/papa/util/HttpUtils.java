package com.cl.papa.util;

/**
 * Created by Administrator on 2016/4/29.
 */
public class HttpUtils {
    //private static final  String Server = "http://papa.applinzi.com";
    private static final  String Server = "http://apis.baidu.com/showapi_open_bus/showapi_joke";

    /**
     * 文章列表 接口
     */
    public static String ArticeURL = Server + "/joke_text";

    /**
     * 图片列表接口
     */
    public static String ImageURL = Server + "/joke_pic";

    /**
     * 视频接口
     */
    public static String VideoURL = "http://papa.applinzi.com/hahaba/videolist.php";

    /**
     * 视频播放页下发列表接口
     */
    public static String VideoPlayerURL = "https://openapi.youku.com/v2/videos/by_related.json";
}