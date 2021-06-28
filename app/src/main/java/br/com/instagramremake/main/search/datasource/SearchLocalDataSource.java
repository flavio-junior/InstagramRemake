package br.com.instagramremake.main.search.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.presenter.Presenter;

public class SearchLocalDataSource implements SearchDataSource {

    @Override
    public void findUser(String query, Presenter<List<User>> presenter) {
        Database db = Database.getInstance();
        db.findUsers(db.getUser().getUUID(), query)
                .addOnSucessListener(presenter::onSucess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}