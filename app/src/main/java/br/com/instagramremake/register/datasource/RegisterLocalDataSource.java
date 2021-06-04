package br.com.instagramremake.register.datasource;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name, email, password)
                .addOnSucessListener((Database.OnSucessListener<UserAuth>) presenter::onSucess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}
