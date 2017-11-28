package com.ryx.interfacedemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ryx.interfacedemo.R;
import com.ryx.interfacedemo.activity.Bean.Person;
import com.ryx.interfacedemo.activity.callBack.HelperInterface;
/**
 *
 * 匿名内部类的简化实现（很常用）
 * Desc：匿名内部类：没有类名的类。
        匿名内部类的好处：简化书写。
        匿名内部类的使用前提：必须存在继承或者实现关系才能使用。
 */
public class NiMingNeiBuleiActivity extends AppCompatActivity {
        private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ni_ming_nei_bulei);
        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NiMingNeiBuleiActivity.this,NiMingNeiBuLei2Activity.class);
                startActivity(intent);
            }
        });
        //匿名内部类的做法：匿名内部类只是没有类名，其它的一概成员都是具备的
        //匿名内部类与HelperInterface是继承的关系。 目前是创建HelperInterface子类的对象
        //注意：内部类是不可以进行new的（没有方法体自己都不知道干什么，所以new没什么意义），但是可以利用多态，面向父类编程
       //接口不能创建对象。这里是创建了接口实现类的对象。
        HelperInterface helperInterface = new HelperInterface() {//多态
            @Override
            public void execute(String name) {
                Person p = new Person();
                p.setAge(8);
                p.setName(name);
                Toast.makeText(getApplicationContext(),p.getName()+p.getAge(),Toast.LENGTH_SHORT).show();
            }
        };
        helperInterface.execute("孙凯");
    }
}
