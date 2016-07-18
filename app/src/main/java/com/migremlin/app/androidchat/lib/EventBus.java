package com.migremlin.app.androidchat.lib;

/**
 * Created by ozehs on 14/07/2016.
 */
public interface EventBus {
    void register(Object suscriber);
    void unregister(Object suscriber);
    void post(Object event);
}
