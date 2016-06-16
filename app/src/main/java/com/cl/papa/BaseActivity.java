package com.cl.papa;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BaseActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
