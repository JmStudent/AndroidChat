package com.migremlin.app.androidchat.login;

/**
 * Created by ozehs on 13/07/2016.
 */
public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
