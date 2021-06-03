package br.com.instagramremake.login.datasource;

import br.com.instagramremake.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        presenter.onError("Error1");
        presenter.onComplete();
    }
}