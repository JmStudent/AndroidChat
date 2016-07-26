package com.migremlin.app.androidchat.contactlist;

import com.migremlin.app.androidchat.contactlist.events.ContactListEvent;

/**
 * Created by JM Peña on 26/07/2016.
 */
public interface ContactListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);
}
