package com.ryx.credit.tnh.base;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryx.baselib.mvpframe.base.MvpBaseAct;
import com.ryx.baselib.mvpframe.base.MvpBaseModel;
import com.ryx.baselib.mvpframe.base.MvpBasePresenter;
import com.ryx.credit.tnh.BuildConfig;
import com.ryx.credit.tnh.R;
import com.ryx.credit.tnh.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 子项目父Activity类
 */
public abstract class BaseAct<P extends MvpBasePresenter, M extends MvpBaseModel> extends MvpBaseAct<P,M>  {
    private Unbinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = ButterKnife.bind(this);
        initView(savedInstanceState);
    }
    public void initView(Bundle savedInstanceState){}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBinder!=null){
            mBinder.unbind();
        }
    }


    /**
     * 设置当前页面的标题
     *
     * @param title           标题
     * @param leftRightisShow 左侧右侧是否显示,
     * @author xucc
     */
    public void setTitleLayout(String title, boolean... leftRightisShow) {
        try {
            TextView tv_title = (TextView) findViewById(R.id.tv_title);
            tv_title.setText(title);
            ImageView leftImageView = (ImageView) findViewById(R.id.tileleftImg);
            ImageView rightImageView = (ImageView) findViewById(R.id.tilerightImg);
            if (leftRightisShow.length > 0) {
                //第一个代表左侧返回图标
                boolean leftIshow = leftRightisShow[0];
                if (leftIshow) {
                    leftImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            backUpImgOnclickListen();
                        }
                    });
                    leftImageView.setVisibility(View.VISIBLE);
                } else {
                    leftImageView.setVisibility(View.INVISIBLE);
                }
                //第二个代表右侧帮助图标
                boolean rightIshow = leftRightisShow[leftRightisShow.length - 1];
                if (rightIshow) {
                    rightImageView.setVisibility(View.VISIBLE);
                } else {
                    rightImageView.setVisibility(View.INVISIBLE);
                }
            }
        }catch (Exception e){

        }
    }

    public void backUpImgOnclickListen() {finish();}


}
