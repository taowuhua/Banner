package com.ryx.credit.tnh.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.base.BaseAct;
import com.ryx.credit.tnh.ui.creditcard.CreditCardAddAct;
import com.ryx.credit.tnh.ui.creditcard.CreditCardContentAct;
import com.ryx.credit.tnh.ui.home.adapter.HomeCardAdapter;
import com.ryx.credit.tnh.ui.pay.RechargePlanAct;
import com.ryx.quickadapter.inter.NoDoubleClickListener;
import com.ryx.quickadapter.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 首页
 */
public class HomeAct extends BaseAct {
    @BindView(R.id.home_recyclerview)
    RecyclerView homeRecyclerview;
    @Override
    public void initView(Bundle bundle) {
        super.initView(bundle);
        LinearLayoutManager linearLayoutManager=  new LinearLayoutManager(HomeAct.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeRecyclerview.setLayoutManager(linearLayoutManager);

        List<Map<String,String>> dataList=new ArrayList<>();
        dataList.add(new HashMap<String, String>());
        dataList.add(new HashMap<String, String>());

        HomeCardAdapter homecardadapter=new HomeCardAdapter(dataList,HomeAct.this);
        homeRecyclerview.setAdapter(homecardadapter);
        homeRecyclerview.addItemDecoration(new RecyclerViewDivider(HomeAct.this,1,30,R.color.bg_f5f5f5));
        homecardadapter.setItemOnclick(new HomeCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position==0){
                    Intent intent=new Intent(HomeAct.this, CreditCardContentAct.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(HomeAct.this, RechargePlanAct.class);
                    startActivity(intent);
                }
            }
        });
        homecardadapter.setAddcreditLayoutOnclick(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent=new Intent(HomeAct.this, CreditCardAddAct.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
