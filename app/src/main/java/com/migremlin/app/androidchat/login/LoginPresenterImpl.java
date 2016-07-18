package com.migremlin.app.androidchat.login;

import com.migremlin.app.androidchat.lib.EventBus;
import com.migremlin.app.androidchat.lib.GreenRobotEventBus;
import com.migremlin.app.androidchat.login.events.LoginEvent;
import com.migremlin.app.androidchat.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by ozehs on 14/07/2016.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }

        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.ON_SIGN_IN_ERROR:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.ON_SIGN_IN_SUCCESS:
                onSignInSuccess();
                break;
            case LoginEvent.ON_SIGN_UP_ERROR:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.ON_SIGN_UP_SUCCESS:
                onSignUpSuccess();
                break;
            case LoginEvent.ON_FAILED_TO_RECOVER_SESSION:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    private void onSignInSuccess() {
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if (loginView != null) {
            loginView.newUserSuccess();
        }
    }

    private void onSignInError(String error) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }
}
