package com.migremlin.app.androidchat.contactlist;

/**
 * Created by JM Peña on 26/07/2016.
 */
public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);
}
