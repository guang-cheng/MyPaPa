package com.cl.papa.adapter;

/**
 * Created by Administrator on 2016/4/29.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cl.papa.R;
import com.cl.papa.bean.Article;
import com.cl.papa.util.DeviceInfo;
import com.cl.papa.util.LogUtil;
import com.cl.papa.util.UsedUtils;
import com.cl.papa.view.CircularImage;
import com.cl.papa.view.ImageViewPlus;


import org.xutils.common.Callback;
import org.xutils.x;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 1/17/15.
 */
public class ArticleAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
    private LayoutInflater mInflater;

    private int width = 0;

    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    public static final int N0_MORE = 2;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position ,String content,String imageURL);
        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = ArticleAdapter.class.getSimpleName();
    private List<Article> list;



    public ArticleAdapter(List<Article> list, Context context) {
        this.list = list;
        this.mInflater =LayoutInflater.from(context);
        this.width = DeviceInfo.screenWidth - DeviceInfo.dip2px(15);
       /*  options= new ImageOptions.Builder()
                //设置加载过程中的图片
         //.setLoadingDrawableId(R.drawable.f1)
                // 设置加载失败后的图片
         .setFailureDrawableId(R.drawable.f1)
        //设置使用缓存
        .setUseMemCache(true)
        //设置显示圆形图片
         .setCircular(true)//设置支持gif.setIgnoreGif(false)
         .build();
*/
    }
    public void addAll(List<Article> list){
        int size = this.list.size();
       this.list.clear();
        this.list.addAll(list);
       // notifyDataSetChanged();
        LogUtil.e("size="+size+",getItemCount="+getItemCount());
        if(getItemCount() > size)
            notifyItemRangeInserted(size,getItemCount() -size-1);
            //notifyItemRangeChanged(size,getItemCount() -size-1);// 从size 刷新数量
        else
            notifyDataSetChanged();
        //notifyItemRangeInserted(size,getItemCount() -size-1);//从size插入 数量

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LogUtil.e(TAG, "onCreateViewHolder, i: " + viewType);
        if(viewType==TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.adapter_item_artice, null);
          LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ArticleViewHolder(view);
        }else if(viewType==TYPE_FOOTER){
            View view = mInflater.inflate(R.layout.recyclertview_footer, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new FootViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
       // LogUtil.e("onBindViewHolder, i: " + i + ", viewHolder: " + viewHolder);
        if(viewHolder instanceof ArticleViewHolder) {
           final  ArticleViewHolder holder = (ArticleViewHolder) viewHolder;
            holder.position = i;
           final Article article = list.get(i);
            if(!TextUtils.isEmpty(article.getUsername()))
                holder.user_nice.setText(article.getUsername());
            if(!TextUtils.isEmpty(article.getMoodcontent())) {
                holder.artice_content.setText(article.getMoodcontent());
                holder.content = article.getMoodcontent();
            }
            if(!TextUtils.isEmpty(article.getMoodimg())) {
                holder.imageURL = article.getMoodimg();
                x.image().bind(holder.imageContent, article.getMoodimg(), UsedUtils.getImageOptions(), new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable drawable) {
                       holder.imageContent.setLayoutParams(new LinearLayout.LayoutParams(width, width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth()));
                       /* if(article.getMoodimg().contains(".gif")) {
                            LogUtil.e("onSuccess-height=" + drawable.getIntrinsicHeight() + ",width=" + drawable.getIntrinsicWidth());
                             LogUtil.e("onSuccess-height="+drawable.getMinimumHeight()+",width="+drawable.getMinimumWidth());

                             holder.imageContent.setImageDrawable(drawable);
                        }*/
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {

                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {
                       // LogUtil.e("onFinished");
                    }
                });
            }
            //if(!TextUtils.isEmpty(article.getUsericon() ))
           // x.image().bind(holder.user_icon, headNameClass.getIcon(), options);
            holder.user_icon.setImageResource(article.getUsericon());
        }else if(viewHolder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            if(i == 0)
                footViewHolder.xlistview_footer_content.setVisibility(View.INVISIBLE);
            else
                footViewHolder.xlistview_footer_content.setVisibility(View.VISIBLE);
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
                case N0_MORE:
                    footViewHolder.foot_view_item_tv.setText("没有更多了...");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public int getItemViewType(int position) {
        // 最后一个item设置为footerVie

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
       // notifyDataSetChanged();
        notifyItemChanged(list.size() + 1 - 1);//刷新最后一位
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        public View rootView;
        public TextView user_nice;
        public TextView artice_content;
        public ImageViewPlus user_icon;
        public ImageView imageContent;
        public LinearLayout share_layout;
        public int position;
        public String content;
        public String imageURL;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            user_nice = (TextView) itemView.findViewById(R.id.tv_user_nice);
            user_icon = (ImageViewPlus) itemView.findViewById(R.id.iv_user_icon);
            imageContent = (ImageView) itemView.findViewById(R.id.iv_content);

            //imageContent.setLayoutParams(new RelativeLayout.LayoutParams(DeviceInfo.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            artice_content = (TextView) itemView.findViewById(R.id.tv_art_content);
            share_layout = (LinearLayout) itemView.findViewById(R.id.share_layout);
            share_layout.setOnClickListener(this);
           // rootView = itemView.findViewById(R.id.recycler_view_test_item_person_view);
           // rootView.setOnClickListener(this);
           // rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position, content,imageURL);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(null != onRecyclerViewListener){
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private TextView foot_view_item_tv;
        private LinearLayout xlistview_footer_content;
        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv=(TextView)view.findViewById(R.id.xlistview_footer_hint_textview);
            xlistview_footer_content = (LinearLayout) view.findViewById(R.id.xlistview_footer_content);
        }
    }
}