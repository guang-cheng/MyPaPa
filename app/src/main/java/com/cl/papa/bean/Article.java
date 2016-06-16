package com.cl.papa.bean;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Article {
    /**
     * 内容
     */
    private String moodcontent;
    /**
     * 文章id
     */
    private String moodid;
    /**
     * 图片地址
     */
    private String moodimg;
    /**
     * 时间
     */
    private String moodtime;
    /**
     * 发帖人头像
     */
    private String userhead;
    private int usericon;
    /**
     * 发帖人id
     */
    private String userid;
    /**
     * 发帖人昵称
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMoodtime() {
        return moodtime;
    }

    public void setMoodtime(String moodtime) {
        this.moodtime = moodtime;
    }

    public String getMoodid() {
        return moodid;
    }

    public void setMoodid(String moodid) {
        this.moodid = moodid;
    }

    public String getUserhead() {
        return userhead;
    }

    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }

    public String getMoodcontent() {
        return moodcontent;
    }

    public void setMoodcontent(String moodcontent) {
        this.moodcontent = moodcontent;
    }

    public String getMoodimg() {
        return moodimg;
    }

    public void setMoodimg(String moodimg) {
        this.moodimg = moodimg;
    }

    public Article() {
    }

    public int getUsericon() {
        return usericon;
    }

    public void setUsericon(int usericon) {
        this.usericon = usericon;
    }

    public Article(String username, String userid, int usericon, String userhead, String moodtime, String moodimg, String moodid, String moodcontent) {
        this.username = username;
        this.userid = userid;
        this.usericon = usericon;
        this.userhead = userhead;
        this.moodtime = moodtime;
        this.moodimg = moodimg;
        this.moodid = moodid;
        this.moodcontent = moodcontent;
    }

    @Override
    public String toString() {
        return "Article{" +
                "moodcontent='" + moodcontent + '\'' +
                ", moodid='" + moodid + '\'' +
                ", moodimg='" + moodimg + '\'' +
                ", moodtime='" + moodtime + '\'' +
                ", userhead='" + userhead + '\'' +
                ", usericon=" + usericon +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
