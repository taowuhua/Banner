package com.troy.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private TextView tv1,tv_status,tv_getevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        tv1=(TextView)findViewById(R.id.tv);
        tv_getevent = (TextView) findViewById(R.id.tv_getevent);
        tv_status=(TextView)findViewById(R.id.tv_status);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Subscribe          //订阅事件FirstEvent
    public  void onEventMainThread(LoginSuccessdEvent event){
        String msg=event.getMsg();
        tv_status.setText("已登录，当前账号"+msg);
        tv_getevent.setText(msg);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
