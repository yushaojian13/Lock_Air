package com.xmht.lock.core.data.time.observe;

import com.xmht.lock.core.data.time.TimeLevel;


public interface TimeLevelObserver {
    void onTimeChanged(TimeLevel level);
}
