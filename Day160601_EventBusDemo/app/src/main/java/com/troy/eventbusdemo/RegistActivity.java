package com.troy.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class RegistActivity extends AppCompatActivity {

    private EditText et_username;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        et_username=(EditText)findViewById(R.id.et_username);
        progress=(ProgressBar) findViewById(R.id.progress);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=et_username.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegistActivity.this, "用户名不许为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                progress.setVisibility(View.VISIBLE);

                et_username.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegistActivity.this, "注册成功，即将跳转！", Toast.LENGTH_LONG).show();
                        EventBus.getDefault().post(new LoginSuccessdEvent(username));
                        RegistActivity.this.finish();
                    }
                },3000);
            }
        });
    }
}
