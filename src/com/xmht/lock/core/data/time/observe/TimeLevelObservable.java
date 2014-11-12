
package com.xmht.lock.core.data.time.observe;


public interface TimeLevelObservable {
    void registerObserver(TimeLevelObserver observer);

    void unregisterObserver(TimeLevelObserver observer);

    void notifyObservers(TimeLevel level);
}
