package com.xmht.lock.core.data.time.observe;

public interface SecondObservable {
    void registerObservers(SecondObserver observer);
    void removeObservers(SecondObserver observer);
    void notifyObservers();
}
