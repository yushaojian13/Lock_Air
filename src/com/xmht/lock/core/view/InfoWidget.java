package com.xmht.lock.core.view;

import android.content.Context;
import android.util.AttributeSet;

import com.xmht.lock.core.data.info.InfoCenter;
import com.xmht.lock.core.data.info.InfoCenter.InfoType;
import com.xmht.lock.core.data.time.observe.InfoObserver;
import com.xmht.lock.core.view.common.Widget;

public abstract class InfoWidget extends Widget implements InfoObserver {
    
    public InfoWidget(Context context) {
        this(context, null);
    }

    public InfoWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public InfoWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    public void onStart() {
        InfoCenter.registerWifiRssiObserver(this);
    }

    @Override
    public void onStop() {
        InfoCenter.unregisterWifiRssiObserver(this);
    }
    
    @Override
    protected void setView() {
    }

    @Override
    public abstract void onInfoLevelChanged(InfoType infoType, int level);

}
