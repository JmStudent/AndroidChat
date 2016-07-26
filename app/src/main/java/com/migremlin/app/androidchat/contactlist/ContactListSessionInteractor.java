package com.migremlin.app.androidchat.contactlist;

/**
 * Created by JM Peña on 26/07/2016.
 */
public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
