
package com.xmht.lock.core.data.time.observe;

import com.xmht.lock.core.data.time.TimeLevel;


public interface TimeLevelObservable {
    void registerObserver(TimeLevelObserver observer);

    void unregisterObserver(TimeLevelObserver observer);

    void notifyObservers(TimeLevel level);
}
