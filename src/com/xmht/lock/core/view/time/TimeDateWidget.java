package com.xmht.lock.core.view.time;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xmht.lock.core.data.time.observe.UpdateLevel;
import com.xmht.lock.core.data.time.observe.UpdateLevelObserver;
import com.xmht.lock.core.view.common.Widget;
import com.xmht.lock.core.view.listener.HorizontalSlideListener;

public abstract class TimeDateWidget extends Widget implements UpdateLevelObserver {
    
    public TimeDateWidget(Context context) {
        this(context, null);
    }

    public TimeDateWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
        setFont();
    }
    
    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;
    
    private HorizontalSlideListener horizontalSlideListener;
    
    public void setHorizontalSlideListner(HorizontalSlideListener slideListener) {
        horizontalSlideListener = slideListener;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getRawX();
                upY = event.getRawY();
                
                if (horizontalSlideListener != null && upX - downX > getWidth() * 0.2f) {
                    horizontalSlideListener.rightSlide();
                } else if (horizontalSlideListener != null && upX - downX < -getWidth() * 0.2f) {
                    horizontalSlideListener.leftSlide();
                } 
                break;
        }
        
        return true;
    }
    
    protected abstract void setFont();

    @Override
    public abstract void onUpdate(UpdateLevel level);
    
}
