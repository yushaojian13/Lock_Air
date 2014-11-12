package com.xmht.lock.core.view.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class Widget extends RelativeLayout {
    public Widget(Context context) {
        this(context, null);
    }

    public Widget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public Widget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    protected abstract void setView();
}
