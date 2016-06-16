package com.cl.papa.bean;

/**
 * Created by Administrator on 2016/5/26.
 *
 *    "dataid": "10",
 "videoid": "XMTU3MzMxODAwMA==",
 "url": "http://v.youku.com/v_show/id_XMTU3MzMxODAwMA==.html?from=s1.8-1-1.2",
 "title": "性感美女热舞 美女沙滩性感比基尼视频6",
 "thumbnail": "http://r1.ykimg.com/054204085739CBC56A0A4E04D6EAF3A5",
 "duration": "264
 */
public class Video {
    private String title;
    private String dataid;
    private String videoid; //视频播放id 用来添加到播放器
    private String url;
    private String thumbnail;
    private String duration;  //视频长度 单位：秒
    /**
     * 发帖人头像
     */
    private String userhead;
    private int usericon;

    public String getUserhead() {
        return userhead;
    }

    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }

    public int getUsericon() {
        return usericon;
    }

    public void setUsericon(int usericon) {
        this.usericon = usericon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 发帖人昵称
     */
    private String username;

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Video() {
    }

    public Video(String title, String dataid, String videoid, String url, String thumbnail, String duration, String userhead, int usericon, String username) {
        this.title = title;
        this.dataid = dataid;
        this.videoid = videoid;
        this.url = url;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.userhead = userhead;
        this.usericon = usericon;
        this.username = username;
    }
}
