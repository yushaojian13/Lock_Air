package com.xmht.lock.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xmht.lock.core.data.time.format.TimeFormatter;
import com.xmht.lock.core.data.time.observe.TimeLevel;
import com.xmht.lock.core.data.time.observe.TimeLevelObserver;
import com.xmht.lock.core.view.listener.SwipeListener;

public abstract class TimeDateWidget extends WidgetBase implements TimeLevelObserver {
    
    public TimeDateWidget(Context context) {
        this(context, null);
    }

    public TimeDateWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimeDateWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @Override
    public void onStart() {
        TimeFormatter.register(this);
    }

    @Override
    public void onStop() {
        TimeFormatter.unregister(this);
    }
    
    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;
    
    private SwipeListener horizontalSlideListener;
    
    public void setHorizontalSlideListner(SwipeListener slideListener) {
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
                    horizontalSlideListener.rightSwipe();
                } else if (horizontalSlideListener != null && upX - downX < -getWidth() * 0.2f) {
                    horizontalSlideListener.leftSwipe();
                } 
                break;
        }
        
        return true;
    }
    
    protected abstract void setFont();

    @Override
    public abstract void onTimeChanged(TimeLevel level);
    
}
