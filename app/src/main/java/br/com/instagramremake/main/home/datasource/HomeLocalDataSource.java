package br.com.instagramremake.main.home.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.Feed;
import br.com.instagramremake.common.presenter.Presenter;

public class HomeLocalDataSource implements HomeDataSource {

    @Override
    public void findFeed(Presenter<List<Feed>> presenter) {
        Database db = Database.getInstance();
        db.findFeed(db.getUser().getUUID())
                .addOnSucessListener(presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}