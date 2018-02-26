package com.ryx.baselib.mvpframe.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ryx.baselib.utils.TUtil;
/**
 * Author：xucc
 * date: 2017/11/30 10:29
 * email：xuchenchen-jn@ruiyinxin.com
 * Description：所有Activity父类
 */
public abstract class  MvpBaseAct <P extends MvpBasePresenter, M extends MvpBaseModel>
        extends AppCompatActivity {
    public P mPresenter;
    public M mModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        //取出泛型中的业务处理类和数据获取类
        mPresenter = TUtil.getT(this, 0);//P
        mModel = TUtil.getT(this, 1);//M
        if (this instanceof MvpBaseView) {
            //presenter与view和model的关联
            mPresenter.setVM(this, mModel, this);
        }
    }

    /**
     * 获取布局文件接口,子类必须继承
     * @return
     */
    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //资源引用释放，防止内存溢出
        if(mPresenter!=null){
            mPresenter=null;
        }
        if(mModel!=null){
            mModel=null;
        }
    }
}
