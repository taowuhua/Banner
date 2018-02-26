package com.ryx.baselib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;

/**
 * 
 * @author muxin
 * @time 2016-11-16 11:26
 */
public class RyxSpinKitView extends ProgressBar {
    private Style mStyle;
    private int mColor;
    private Sprite mSprite;

    public RyxSpinKitView(Context context) {
        this(context, (AttributeSet)null);
    }

    public RyxSpinKitView(Context context, AttributeSet attrs) {
        this(context, attrs, com.github.ybq.android.spinkit.R.attr.SpinKitViewStyle);
    }

    public RyxSpinKitView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, com.github.ybq.android.spinkit.R.style.SpinKitView);
    }

    @TargetApi(21)
    public RyxSpinKitView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, com.github.ybq.android.spinkit.R.styleable.SpinKitView, defStyleAttr, defStyleRes);
        this.mStyle = Style.values()[a.getInt(com.github.ybq.android.spinkit.R.styleable.SpinKitView_SpinKit_Style, 0)];
        this.mColor = a.getColor(com.github.ybq.android.spinkit.R.styleable.SpinKitView_SpinKit_Color, -1);
        a.recycle();
        this.init();
        this.setIndeterminate(true);
    }

    private void init() {
        Sprite sprite = SpriteFactory.create(this.mStyle);
        this.setIndeterminateDrawable(sprite);
    }

    @Override
    public void setIndeterminateDrawable(Drawable d) {
        if(!(d instanceof Sprite)) {
            throw new IllegalArgumentException("this d must be instanceof Sprite");
        } else {
            this.setIndeterminateDrawable((Sprite)d);
        }
    }

    public void setIndeterminateDrawable(Sprite d) {
        super.setIndeterminateDrawable(d);
        this.mSprite = d;
        if(this.mSprite.getColor() == 0) {
            this.mSprite.setColor(this.mColor);
        }

        this.onSizeChanged(this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        if(this.getVisibility() == VISIBLE) {
            this.mSprite.start();
        }

    }

    @Override
    public Sprite getIndeterminateDrawable() {
        return this.mSprite;
    }

    @Override
    public void unscheduleDrawable(Drawable who) {
        super.unscheduleDrawable(who);
        if(who instanceof Sprite) {
            ((Sprite)who).stop();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus && this.mSprite != null && this.getVisibility() == VISIBLE) {
            this.mSprite.start();
        }

    }

//    public void onScreenStateChanged(int screenState) {
//        super.onScreenStateChanged(screenState);
//        if(screenState == 0 && this.mSprite != null) {
//            this.mSprite.stop();
//        }
//    }

}
