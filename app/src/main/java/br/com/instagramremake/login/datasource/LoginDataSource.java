package br.com.instagramremake.login.datasource;

import br.com.instagramremake.common.presenter.Presenter;

public interface LoginDataSource {

    void login(String email, String password, Presenter presenter);

}