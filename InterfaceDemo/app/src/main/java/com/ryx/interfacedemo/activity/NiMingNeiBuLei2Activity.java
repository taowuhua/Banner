package com.ryx.interfacedemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ryx.interfacedemo.R;
import com.ryx.interfacedemo.activity.Bean.Person;
import com.ryx.interfacedemo.activity.callBack.HelperInterface;

/**
 * 这个类测试有问题，在查找不确定什么问题导致的，想去了解这样去实现，但是没得到回调结果
 *
 */
public class NiMingNeiBuLei2Activity extends AppCompatActivity implements HelperInterface{

    private Button bt;
    @Override
    public void execute(String name) {
        Person p = new Person();
        p.setName(name);
        p.setAge(999);
        Toast.makeText(getApplicationContext(),p.getName()+p.getAge(),Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ni_ming_nei_bu_lei2);
        bt = findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person p = new Person();
                Toast.makeText(getApplicationContext(),p.getName()+p.getAge(),Toast.LENGTH_SHORT).show();
            }
        });

    }



}
