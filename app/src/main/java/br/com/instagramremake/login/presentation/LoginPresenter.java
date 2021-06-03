package br.com.instagramremake.login.presentation;

import android.os.Handler;

import br.com.instagramremake.login.datasource.LoginDataSource;

class LoginPresenter {

    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password) {
        view.showProgressBar();

        new Handler().postDelayed(() -> {
            view.hideProgressBar();
            view.onFailureForm("Error1", "Error2");
        }, 2000);
    }
}