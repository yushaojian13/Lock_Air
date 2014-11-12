
package com.xmht.lock.core.data.time.observe;


public interface UpdateLevelObservable {
    void registerObserver(UpdateLevelObserver observer);

    void unregisterObserver(UpdateLevelObserver observer);

    void notifyObservers(UpdateLevel level);
}
