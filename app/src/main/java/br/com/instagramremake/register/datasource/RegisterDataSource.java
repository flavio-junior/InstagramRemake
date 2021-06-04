package br.com.instagramremake.register.datasource;

import br.com.instagramremake.common.presenter.Presenter;

public interface RegisterDataSource {

    void createUser(String name, String email, String password, Presenter presenter);

}