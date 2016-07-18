package com.migremlin.app.androidchat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ozehs on 12/07/2016.
 */
public class AndroidChatApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
