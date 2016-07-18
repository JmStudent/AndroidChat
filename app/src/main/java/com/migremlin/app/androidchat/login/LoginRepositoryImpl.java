package com.migremlin.app.androidchat.login;

import android.util.Log;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.migremlin.app.androidchat.domain.FirebaseHelper;
import com.migremlin.app.androidchat.entities.User;
import com.migremlin.app.androidchat.lib.EventBus;
import com.migremlin.app.androidchat.lib.GreenRobotEventBus;
import com.migremlin.app.androidchat.login.events.LoginEvent;

import java.util.Map;

/**
 * Created by ozehs on 14/07/2016.
 */
public class LoginRepositoryImpl implements LoginRepository {
    private final FirebaseAuth authReference;
    private FirebaseHelper helper;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.myUserReference = helper.getMyUserReference();
        this.authReference = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(final String email, final String password) {
        authReference.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        postEvent(LoginEvent.ON_SIGN_UP_SUCCESS);
                        signIn(email, password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.ON_SIGN_UP_ERROR, e.getMessage());
                    }
                });
    }

    @Override
    public void signIn(String email, String password) {
        authReference.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        initSignIn();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.ON_SIGN_IN_ERROR, e.getMessage());
                    }
                });
        //postEvent(LoginEvent.ON_SIGN_IN_SUCCESS);
    }

    @Override
    public void checkSession() {
        if(authReference.getCurrentUser() != null){
            initSignIn();
        } else {
            postEvent(LoginEvent.ON_FAILED_TO_RECOVER_SESSION);
        }
    }

    private void initSignIn() {
        myUserReference = helper.getMyUserReference();
        myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);

                if (currentUser == null) {
                    registerNewUser();
                }
                helper.changeUserConnectionStatus(User.ONLINE);
                postEvent(LoginEvent.ON_SIGN_IN_SUCCESS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void registerNewUser() {
        String email = helper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }
}
