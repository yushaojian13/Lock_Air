package com.xmht.lock.core.view;

import com.xmht.lock.core.view.listener.LockEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class WidgetBase extends RelativeLayout implements LockEvent {
    public WidgetBase(Context context) {
        this(context, null);
    }

    public WidgetBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public WidgetBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }
    
    protected abstract void setView();
}
