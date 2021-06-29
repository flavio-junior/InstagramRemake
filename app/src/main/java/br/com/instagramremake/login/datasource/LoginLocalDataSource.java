package br.com.instagramremake.login.datasource;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    @Override
    public void login(String email, String password, Presenter presenter) {
        Database.getInstance().login(email, password)
                .addOnSucessListener((Database.OnSucessListener<UserAuth>) response -> presenter.onSuccess(response))
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(() -> presenter.onComplete());
    }
}