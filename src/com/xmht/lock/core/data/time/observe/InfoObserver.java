package com.xmht.lock.core.data.time.observe;

import com.xmht.lock.core.data.info.InfoCenter.InfoType;


public interface InfoObserver {
    public static final int MAX_LEVEL = 5;
    public void onInfoLevelChanged(InfoType infoType, int level);
}
