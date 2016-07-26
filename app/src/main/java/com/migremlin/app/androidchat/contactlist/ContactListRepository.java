package com.migremlin.app.androidchat.contactlist;

/**
 * Created by JM Peña on 26/07/2016.
 */
public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
