package com.migremlin.app.androidchat.login;

import com.migremlin.app.androidchat.login.events.LoginEvent;

/**
 * Created by ozehs on 13/07/2016.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
