package com.ryx.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzgiceman.rxbuslibrary.rxbus.RxBus;
import com.wzgiceman.rxbuslibrary.rxbus.Subscribe;
import com.wzgiceman.rxbuslibrary.rxbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_receive;
    private TextView tv;
    private String name;
    private Subscription rxSbscription;
    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        bt_receive = findViewById(R.id.bt_receive);
        tv = findViewById(R.id.tv);
        bt_receive.setOnClickListener(this);
       /* rxBusObservers();
        rxBusPost();*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        /*註冊*/
        RxBus.getDefault().register(this);
    }
    @Override
    public void onClick(View v) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void event(User eventStickText) {
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            tv.setText(eventStickText.getName());
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }
}
