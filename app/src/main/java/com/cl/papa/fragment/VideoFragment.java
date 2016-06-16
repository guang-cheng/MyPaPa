package com.cl.papa.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cl.papa.PlayerActivity;
import com.cl.papa.R;
import com.cl.papa.adapter.ArticleAdapter;
import com.cl.papa.adapter.VideoAdapter;
import com.cl.papa.bean.Video;
import com.cl.papa.util.Constants;
import com.cl.papa.util.DeviceInfo;
import com.cl.papa.util.HeadName;
import com.cl.papa.util.HttpUtils;
import com.cl.papa.util.LogUtil;
import com.cl.papa.util.ToastUtils;
import com.cl.papa.util.UsedUtils;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener ,VideoAdapter.OnRecyclerViewListener{

    @ViewInject(R.id.title_layout_tv)
    private TextView title_layout_tv;

    @ViewInject(R.id.refresh_layout)
    private SwipeRefreshLayout swipeRefresh_layout;

    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;

    private Activity activity;

    @ViewInject(R.id.bannerContainer)
    private ViewGroup bannerContainer;

    private BannerView bv;


    private VideoAdapter videoAdapter = null;

    private int lastVisibleItem;

    private int page = 1;

    @ViewInject(R.id.tv_error)
    private TextView tv_error;

    private Cache cache;
    private View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.frg_main_recylertview,null);

        x.view().inject(this,rootview);
        activity = this.getActivity();
        swipeRefresh_layout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        LogUtil.e("ScriptFragment");
        swipeRefresh_layout.setProgressViewOffset(false, 0, DeviceInfo.dip2px(30));
        swipeRefresh_layout.setOnRefreshListener(this);//设置下拉的监听
        title_layout_tv.setText(getString(R.string.app_name));
        cache = new Cache();
        initRecyclerView();
        initBanner();
        return rootview;
    }

    /**
     * banner 广告
     */

    private void initBanner() {
        this.bv = new BannerView(activity, ADSize.BANNER, Constants.AppId, Constants.BannerID);
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                LogUtil.e("tag", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                LogUtil.e("tag", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);
        bv.loadAD();
    }
    /**
     * 打开随机页数
     */
 /*   private void initPage(){
        page = (int) (Math.random()*1100 +1);
        LogUtil.e("page="+page);
    }*/


    @Event(value = R.id.tv_error,type = View.OnClickListener.class)
    private void errorOnclick(View v){

        page(101);
    }

    private void initRecyclerView(){
       // recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        videoAdapter = new VideoAdapter(cache.generateLocalList(),activity);
        videoAdapter.setOnRecyclerViewListener(this);
        recyclerView.setAdapter(videoAdapter);
        swipeRefresh_layout.setRefreshing(true);
        //initPage();
        page(101);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.e("newState=="+newState);
              /*
              * newState ==RecyclerView.SCROLL_STATE_IDLE &&
              * */

                if (newState ==RecyclerView.SCROLL_STATE_DRAGGING && lastVisibleItem > videoAdapter.getItemCount() - 2) {
                    videoAdapter.changeMoreStatus(ArticleAdapter.LOADING_MORE);
                    page(102);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                lastVisibleItem =layoutManager.findLastVisibleItemPosition();
               // LogUtil.e("lastVisibleItem======"+lastVisibleItem);
            }
        });
    }

    private void page(int other){
        RequestParams params = new RequestParams(HttpUtils.VideoURL);
        params.addQueryStringParameter("reqParam",String.valueOf(page));
        httpGet(params,other);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    if(videoAdapter != null)
                        videoAdapter.addAll(cache.generateLocalList());

                   // articleAdapter.notifyDataSetChanged();
                    break;
                case 101:
                    if(tv_error != null)
                        tv_error.setVisibility(View.GONE);
                    swipeRefresh_layout.setRefreshing(false);
                    cache.clear();
                    cache.update((ArrayList<Video>) msg.obj);
                   // sendEmptyMessage(100);
                    if(videoAdapter != null)
                        videoAdapter.addAll(cache.generateLocalList());
                    videoAdapter.notifyDataSetChanged();
                    break;
                case 102:
                    cache.update((ArrayList<Video>) msg.obj);
                    sendEmptyMessage(100);
                    break;
                case 103:
                    videoAdapter.changeMoreStatus(videoAdapter.N0_MORE);
                    break;
                case 104:
                    if(tv_error != null)
                        tv_error.setVisibility(View.VISIBLE);

                    ToastUtils.showShortToast(activity, getResources().getString(R.string.service_error));
                    swipeRefresh_layout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    private void httpGet(RequestParams params, final int other) {

        // params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    LogUtil.e(result);
                    JSONObject jsonObj = JSON.parseObject(result);

                   String status = jsonObj.getString("state");

                    if (status.equals("1")) {
                    List<Video> dataArry = jsonObj.parseArray(jsonObj.getString("data"), Video.class);

                    if(dataArry != null  && dataArry.size() > 0) {
                                    for(int i = 0;i < dataArry.size();i++){
                                        dataArry.get(i).setUsericon(HeadName.getHeadNameClass().getIcon());
                                        dataArry.get(i).setUsername(HeadName.getHeadNameClass().getHeadName());
                                        dataArry.get(i).setDuration(UsedUtils.getTime(Integer.parseInt(dataArry.get(i).getDuration())));
                                    }

                            if (other == 101) {
                                mHandler.sendMessage(mHandler.obtainMessage(101, dataArry));
                                // Toast.makeText(x.app(), result,
                                // Toast.LENGTH_LONG).show();
                            } else if (other == 102) {
                                page++;
                                mHandler.sendMessage(mHandler.obtainMessage(102, dataArry));

                            }
                        }else{
                            ToastUtils.showShortToast(activity, getResources().getString(R.string.recyclerview_footer_hint_no_more));
                            mHandler.sendMessage(mHandler.obtainMessage(103));
                        }
                    }else{
                        if(other == 101)
                            mHandler.sendMessage(mHandler.obtainMessage(104));
                        else
                            mHandler.sendMessage(mHandler.obtainMessage(103));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    mHandler.sendMessage(mHandler.obtainMessage(104));
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if(other == 101)
                    mHandler.sendMessage(mHandler.obtainMessage(104));
                else
                    mHandler.sendMessage(mHandler.obtainMessage(103));
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {
        //initPage();
    LogUtil.e("on-----------Refresh");
        page(101);
    }


    /**
     * 分享方法
     */
    private void shares(String content,String imageURL) {
        //String sharUrl = null;
        UMImage image;
        if(TextUtils.isEmpty(imageURL))
              image = new UMImage(activity, R.mipmap.icon_haha);
        else
              image = new UMImage(activity,imageURL);
        //LogUtil.e("sharUrl=" + sharUrl+"content="+content+",title="+title);

        /**
         * shareboard need the platform all you want and callbacklistener,then
         * open it
         **/
        new ShareAction(activity)
                .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ)
                .withTargetUrl(Constants.ShareURL).withTitle(getString(R.string.app_name)).withText(content).withMedia(image)
                .setListenerList(umShareListener, umShareListener).open();
    }

    /**
     *分享成功回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

            ToastUtils.showLongToast(activity, platform + getResources().getString(R.string.share_success));


        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showLongToast(activity, platform + getResources().getString(R.string.share_faile));
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showLongToast(activity, platform + getResources().getString(R.string.share_cancel));
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this **/
        LogUtil.e("requestCode="+requestCode+",resultCode="+resultCode);
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);


    }




    @Override
    public void onItemClick(String vid) {
        LogUtil.e("vid="+vid);
      Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra("vid",vid);
        startActivity(intent);
    }

    @Override
    public void onShareItemClick(int position, String content, String imageURL) {
        shares(content,imageURL);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    private static final class Cache {
        ArrayList<Video> cacheList;

        public Cache() {
            cacheList = new ArrayList<Video>();
        }

        synchronized ArrayList<Video> generateLocalList() {
            ArrayList<Video> local = new ArrayList<Video>();
            local.addAll(cacheList);
            return local;
        }

        synchronized void update(ArrayList<Video> list) {
            for (Video info : list) {
                cacheList.add(info);
            }
        }

        synchronized void update_DowntPullto(ArrayList<Video> list) {
            for (Video info : list) {
                // cacheList.add(info);
                cacheList.add(0, info);
            }
        }

        synchronized void clearAndUpdate(ArrayList<Video> list) {
            cacheList.clear();
            for (Video info : list) {
                cacheList.add(info);
            }
        }

        synchronized void clear() {
            cacheList.clear();
        }
    }



}
