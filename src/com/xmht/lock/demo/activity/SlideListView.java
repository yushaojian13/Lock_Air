package com.xmht.lock.demo.activity;

import com.ysj.tools.debug.LOG;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class SlideListView extends ListView {

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context) {
        super(context);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LOG.v(ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }
    
    float downX = 0;
    float downY = 0;
    float moveX;
    float moveY;
    float upX;
    float upY;
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LOG.v(event.getAction() + " " + event.getRawY());
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;
                LOG.e(moveY);
                if (scrollListener != null) {
                    scrollListener.onScrollY(moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getRawX();
                upY = event.getRawY();
                break;
        }
        
        return super.onTouchEvent(event);
    }
    
    private ScrollListener scrollListener;
    
    public void setScrollListener(ScrollListener listener) {
        this.scrollListener = listener;
    }
    
    public interface ScrollListener {
        public void onScrollY(float y);
    }

}
