package com.migremlin.app.androidchat.login;

/**
 * Created by ozehs on 13/07/2016.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
