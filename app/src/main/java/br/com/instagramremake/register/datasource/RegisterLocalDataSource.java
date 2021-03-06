package br.com.instagramremake.register.datasource;

import android.net.Uri;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance().createUser(name, email, password)
                .addOnSucessListener((Database.OnSucessListener<UserAuth>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

    @Override
    public void addPhoto(Uri uri, Presenter presenter) {
        Database db = Database.getInstance();
        db.addPhoto(db.getUser().getUUID(), uri)
                .addOnSucessListener((Database.OnSucessListener<Boolean>) presenter::onSuccess);
    }
}
