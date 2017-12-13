package com.ryx.neweventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Main3Activity extends AppCompatActivity {
        private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        // 注册时最好放到各变量初始化最后，否则可能会出现一些变量空指针异常。同时要注意，取消注册后就收不到了，这就是有些情况你会发现莫名其妙的收不到订阅的原因。
        tv = findViewById(R.id.tv);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
      //  tv.setText(name);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //  @Subscribe(threadMode = ThreadMode.MAIN)
     @Subscribe(sticky = true)
    public void onMessageEvent(Message2Event event) {
        String msg = event.getName();
        tv.setText(msg);
    }
}
