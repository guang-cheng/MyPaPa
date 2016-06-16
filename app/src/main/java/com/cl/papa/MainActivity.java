package com.cl.papa;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

import com.cl.papa.fragment.ImageFragment;
import com.cl.papa.fragment.ScriptFragment;
import com.cl.papa.fragment.VideoFragment;
import com.cl.papa.util.DeviceInfo;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private final String TagScript = "script";
    private final String TagImage = "image";
    private final String TagVideo = "video";

    private  String mLastFragmentTag = "script";


    //private LayoutInflater layoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        x.view().inject(this);
        LogUtil.e("rb_bottom_script");

       // layoutInflater = LayoutInflater.from(this);
        DeviceInfo.initDeviceInfo(this);
        com.cl.papa.util.LogUtil.e("width========="+DeviceInfo.screenWidth+",height="+DeviceInfo.screenHeight);
        Fragment indexFragment = new ScriptFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.id_content, indexFragment,
                        mLastFragmentTag).addToBackStack(null).commit();

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Event(R.id.rb_bottom_script)
    private void  rb_bottom_scriptClick(View v){
      //  setSelect(mTab01);
        change(TagScript);
    }
    @Event(R.id.rb_bottom_image)
    private void  rb_bottom_imageClick(View v){
       // setSelect(mTab02);
        change(TagImage);
    }
    @Event(R.id.rb_bottom_video)
    private void  rb_bottom_videoClick(View v){
        // setSelect(mTab02);
        change(TagVideo);
    }

   /* @Event(R.id.rb_bottom_message)
    private void  rb_bottom_messageClick(View v){
       // setSelect(mTab03);
        change(TagMessage);
    }

    @Event(R.id.rb_bottom_mine)
    private void  rb_bottom_mineClick(View v){
        //setSelect(mTab04);
        change(TagMine);
    }*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    /**
     * 选择页卡
     */
/*    private void setSelect(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.id_content, fragment);
        transaction.commit();
        transaction.add(R.id.id_content, fragment
                ).addToBackStack(null).commit();
    }*/
    // 切换fragment
    private void change(String nowTag) {

        if (!nowTag.equals(mLastFragmentTag)) {
            FragmentManager manager= getSupportFragmentManager();
            Fragment lastFragment = manager.findFragmentByTag(
                    mLastFragmentTag);

            if (manager.findFragmentByTag(nowTag) != null) {

                Fragment fragmentNow = manager.findFragmentByTag(
                        nowTag);
                manager.beginTransaction().show(fragmentNow)
                        .hide(lastFragment).commit();
            } else {
                Fragment fragment = null;
                if(TagScript.equals(nowTag)){
                     fragment = new ScriptFragment();
                }else if(TagImage.equals(nowTag)){
                     fragment = new ImageFragment();
                }else if(TagVideo.equals(nowTag)){
                    fragment = new VideoFragment();
                }else{
                    fragment = new ImageFragment();
                }

                manager.beginTransaction()
                        .add(R.id.id_content, fragment, nowTag)
                        .addToBackStack(null).hide(lastFragment).commit();
            }
            mLastFragmentTag = nowTag;
        }
    }

}
