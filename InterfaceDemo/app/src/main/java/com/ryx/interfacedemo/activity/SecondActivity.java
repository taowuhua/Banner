package com.ryx.interfacedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ryx.interfacedemo.R;
import com.ryx.interfacedemo.activity.callBack.HelperInterface;

public class SecondActivity extends AppCompatActivity   {
private Button bt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
         bt = findViewById(R.id.bt);
         bt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         });

    }

    /***
     *
     * 接口回调
     */
        public static class Ask {
            private HelperInterface helperInterface;

            public void setHelperInterface(HelperInterface helperInterface){  //注册
                this.helperInterface = helperInterface;
            }

            public void resultForAsk(){
                helperInterface.execute("陶务华");
            }
        }
}
