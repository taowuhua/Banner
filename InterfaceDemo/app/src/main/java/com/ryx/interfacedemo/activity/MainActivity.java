package com.ryx.interfacedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryx.interfacedemo.R;
import com.ryx.interfacedemo.activity.callBack.HelperInterface;

public class MainActivity extends AppCompatActivity {
    private TextView tv ;
    private Button bt;
    String fieldName = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        /***
         *
         * 接口回调（局部内部类的做法）
         */
        //创建对象
        SecondActivity.Ask ask = new SecondActivity.Ask();
        ask.setHelperInterface(new HelperInterface() {
            @Override
            public void execute(String name) {
                fieldName = name;
            }
        });
        ask.resultForAsk();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fieldName!=null) {
            tv.setText(fieldName);
        }
    }
}
