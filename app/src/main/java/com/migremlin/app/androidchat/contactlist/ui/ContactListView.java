package com.migremlin.app.androidchat.contactlist.ui;

import com.migremlin.app.androidchat.entities.User;

/**
 * Created by JM Peña on 26/07/2016.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
