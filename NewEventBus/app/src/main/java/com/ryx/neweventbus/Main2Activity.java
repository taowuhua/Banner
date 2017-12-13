package com.ryx.neweventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
        private TextView tv;
        private Button bt;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       //  EventBus.getDefault().register(this);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        bt.setOnClickListener(this);
        EventBus.getDefault().post(new MessageEvent("三千弱水，不饮一瓢！"));

    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().postSticky(new Message2Event("我从山上来！"));
        Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
        startActivity(intent);
    }
}
