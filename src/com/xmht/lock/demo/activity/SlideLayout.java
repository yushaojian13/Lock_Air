
package com.xmht.lock.demo.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.ysj.tools.debug.LOG;

public class SlideLayout extends RelativeLayout {

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LOG.e(ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(android.view.MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;
                LOG.e(moveY);
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getRawX();
                upY = event.getRawY();
                break;
        }

        return super.onTouchEvent(event);
    
    };
}
