package com.cl.papa;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baseproject.utils.Logger;
import com.cl.papa.adapter.VideoAdapter;
import com.cl.papa.bean.Video;
import com.cl.papa.util.HeadName;
import com.cl.papa.util.HttpUtils;
import com.cl.papa.util.LogUtil;
import com.cl.papa.util.ToastUtils;
import com.cl.papa.util.UsedUtils;
import com.youku.player.ApiManager;
import com.youku.player.VideoQuality;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;
import com.youku.service.download.DownloadManager;
import com.youku.service.download.OnCreateDownloadListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 播放器播放界面，
 * 
 */
public class PlayerActivity extends BaseActivity {

	private YoukuBasePlayerManager basePlayerManager;
	// 播放器控件
	private YoukuPlayerView mYoukuPlayerView;

	// 需要播放的视频id
	private String vid;

	// 清晰度相关按钮

	// 下载视频按钮
	private Button btn_download;

	// 需要播放的本地视频的id
	private String local_vid;

	// 标示是否播放的本地视频
	private boolean isFromLocal = false;


	// YoukuPlayer实例，进行视频播放控制
	private YoukuPlayer youkuPlayer;

	@ViewInject(R.id.recyclerview)
	private RecyclerView recyclerView;

	private VideoAdapter videoAdapter;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		x.view().inject(this);

		context = this;
		basePlayerManager = new YoukuBasePlayerManager(this) {

			@Override
			public void setPadHorizontalLayout() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onInitializationSuccess(YoukuPlayer player) {
				// TODO Auto-generated method stub
				// 初始化成功后需要添加该行代码
				addPlugins();

				// 实例化YoukuPlayer实例
				youkuPlayer = player;

				// 进行播放
				goPlay();
			}

			@Override
			public void onSmallscreenListener() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFullscreenListener() {
				// TODO Auto-generated method stub

			}
		};
		basePlayerManager.onCreate();

		// 通过上个页面传递过来的Intent获取播放参数
		getIntentData(getIntent());

		if (TextUtils.isEmpty(vid)) {
			vid = "XODQwMTY4NDg0"; // 默认视频
		}/* else {
			//vid = id;
		}*/

		// 播放器控件
		mYoukuPlayerView = (YoukuPlayerView) this
				.findViewById(R.id.full_holder);
		//控制竖屏和全屏时候的布局参数。这两句必填。
		mYoukuPlayerView
				.setSmallScreenLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
		mYoukuPlayerView
				.setFullScreenLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT));
		// 初始化播放器相关数据
		mYoukuPlayerView.initialize(basePlayerManager);
		initRecyclerView();
	}

	@Override
	public void onBackPressed() { // android系统调用
		Logger.d("sgh", "onBackPressed before super");
		super.onBackPressed();
		Logger.d("sgh", "onBackPressed");
		basePlayerManager.onBackPressed();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		basePlayerManager.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		basePlayerManager.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean managerKeyDown = basePlayerManager.onKeyDown(keyCode, event);
		if (basePlayerManager.shouldCallSuperKeyDown()) {
			return super.onKeyDown(keyCode, event);
		} else {
			return managerKeyDown;
		}

	}

	@Override
	public void onLowMemory() { // android系统调用
		super.onLowMemory();
		basePlayerManager.onLowMemory();
	}

	@Override
	public void onPause() {
		super.onPause();
		basePlayerManager.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		basePlayerManager.onResume();
	}

	@Override
	public boolean onSearchRequested() { // android系统调用
		return basePlayerManager.onSearchRequested();
	}

	@Override
	protected void onStart() {
		super.onStart();
		basePlayerManager.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		basePlayerManager.onStop();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);

		// 通过Intent获取播放需要的相关参数
		getIntentData(intent);

		// 进行播放
		goPlay();
	}

	/**
	 * 获取上个页面传递过来的数据
	 */
	private void getIntentData(Intent intent) {

		if (intent != null) {
			// 判断是不是本地视频
			//isFromLocal = intent.getBooleanExtra("isFromLocal", false);

			if (isFromLocal) { // 播放本地视频
				local_vid = intent.getStringExtra("video_id");
			} else { // 在线播放
				vid = intent.getStringExtra("vid");
			}
		}

	}

	private void goPlay() {
		if (isFromLocal) { // 播放本地视频
			youkuPlayer.playLocalVideo(local_vid);
		} else { // 播放在线视频
			youkuPlayer.playVideo(vid);
		}

		// XNzQ3NjcyNDc2
		// XNzQ3ODU5OTgw
		// XNzUyMzkxMjE2
		// XNzU5MjMxMjcy 加密视频
		// XNzYxNzQ1MDAw 万万没想到
		// XNzgyODExNDY4 魔女范冰冰扑倒黄晓明
		// XNDcwNjUxNzcy 姐姐立正向前走
		// XNDY4MzM2MDE2 向着炮火前进
		// XODA2OTkwMDU2 卧底韦恩突出现 劫持案愈发棘手
		// XODUwODM2NTI0 会员视频
		// XODQwMTY4NDg0 一个人的武林
	}




	/**
	 * 更改视频的清晰度
	 * 
	 * @param quality
	 *            VideoQuality有四种枚举值：{STANDARD,HIGHT,SUPER,P1080}，分别对应：标清，高清，超清，
	 *            1080P
	 */

	private void change(VideoQuality quality) {
		try {
			// 通过ApiManager实例更改清晰度设置，返回值（1):成功；（0): 不支持此清晰度
			// 接口详细信息可以参数使用文档
			int result = ApiManager.getInstance().changeVideoQuality(quality,
					basePlayerManager);
			if (result == 0)
				Toast.makeText(PlayerActivity.this, "不支持此清晰度", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(PlayerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 简单展示下载接口的使用方法，用户可以根据自己的 通过DownloadManager下载视频
	 */
	private void doDownload() {
		// 通过DownloadManager类实现视频下载
		DownloadManager d = DownloadManager.getInstance();
		/**
		 * 第一个参数为需要下载的视频id 第二个参数为该视频的标题title 第三个对下载视频结束的监听，可以为空null
		 */
		d.createDownload("XNzgyODExNDY4", "魔女范冰冰扑倒黄晓明",
				new OnCreateDownloadListener() {

					@Override
					public void onfinish(boolean isNeedRefresh) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void initRecyclerView(){
		//recyclerView.setHasFixedSize(true);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(OrientationHelper.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);

		//initPage();
		page(101);

	}
	private void page(int other){
		RequestParams params = new RequestParams(HttpUtils.VideoPlayerURL);
		params.addQueryStringParameter("client_id",getString(R.string.client_id));
		LogUtil.e("vid====="+vid);
		params.addQueryStringParameter("video_id",vid);
		params.addQueryStringParameter("count","20");
		httpGet(params,other);
	}


	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 101:

					ArrayList<Video>  videos = (ArrayList<Video>)msg.obj;
					LogUtil.e("videos="+videos.size());
					videoAdapter = new VideoAdapter(videos,context);
					recyclerView.setAdapter(videoAdapter);
					videoAdapter.changeMoreStatus(2);
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

					String total = jsonObj.getString("total");

					JSONArray videosArry = JSON.parseArray(jsonObj.getString("videos"));

					if (Integer.parseInt(total)>0) {
						List<Video> videoList = new ArrayList<Video>();
						if(videosArry != null  && videosArry.size() > 0) {

							for(int i = 0;i < videosArry.size();i++){
								Video video = new Video();
								String thumbnail = videosArry.getJSONObject(i).getString("thumbnail");
								if(thumbnail != null)
								video.setThumbnail(thumbnail);
								String url = videosArry.getJSONObject(i).getString("link");
								if(url != null)
								video.setUrl(url);
								String title = videosArry.getJSONObject(i).getString("title");
								if(title != null)
								video.setTitle(title);
								String id = videosArry.getJSONObject(i).getString("id");
								if(id != null)
								video.setVideoid(id);
								video.setUsericon(HeadName.getHeadNameClass().getIcon());
								video.setUsername(HeadName.getHeadNameClass().getHeadName());
								int time =  (int)videosArry.getJSONObject(i).getFloatValue("duration");
								video.setDuration(UsedUtils.getTime(time));
								videoList.add(video);
							}
							LogUtil.e("SIZE="+videosArry.size());
							if (other == 101) {
								mHandler.sendMessage(mHandler.obtainMessage(101, videoList));
								// Toast.makeText(x.app(), result,
								// Toast.LENGTH_LONG).show();
							}
						}else{
							ToastUtils.showShortToast(context, getResources().getString(R.string.recyclerview_footer_hint_no_more));
						}
					}else{

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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

}
