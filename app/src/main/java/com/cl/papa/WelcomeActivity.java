package com.cl.papa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Administrator on 2016/5/11.
 */
//@ContentView(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
       // x.view().inject(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(WelcomeActivity.this,SplashActivity.class));
            }
        },1500);
    }


}
