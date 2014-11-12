package com.xmht.lock.core.view.common;

import com.xmht.lock.core.view.listener.LockEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class Widget extends RelativeLayout implements LockEvent {
    public Widget(Context context) {
        this(context, null);
    }

    public Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }
    
    protected abstract void setView();
}
