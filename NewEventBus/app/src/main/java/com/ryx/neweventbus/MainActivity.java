package com.ryx.neweventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
        private Button bt;
        TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        bt = findViewById(R.id.bt);
        tv = findViewById(R.id.tv);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      //  EventBus.getDefault().post(new MessageEvent("陶务华"));
      //  EventBus.getDefault().postSticky(new MessageEvent("sticky"));
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
         String msg = event.getName();
        tv.setText(msg);
    }

}
