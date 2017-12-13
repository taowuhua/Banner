package com.ryx.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_post;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_post = findViewById(R.id.bt_post);
        tv = findViewById(R.id.tv);
        bt_post.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*註冊*/
        RxBus.getDefault().register(this);
    }

    /*常規接受事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(User changeText) {
        tv.setText(changeText.getName());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*註銷*/
        RxBus.getDefault().unRegister(this);
        /*注销所有的sticky消息*/
        RxBus.getDefault().removeAllStickyEvents();
    }
    @Override
    public void onClick(View v) {
        RxBus.getDefault().post(new User("我修改了-Main"));
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }
}
