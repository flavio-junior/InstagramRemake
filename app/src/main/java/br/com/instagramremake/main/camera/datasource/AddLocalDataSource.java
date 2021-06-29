package br.com.instagramremake.main.camera.datasource;

import android.net.Uri;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.presenter.Presenter;

public class AddLocalDataSource implements AddDataSource {

    @Override
    public void savePost(Uri uri, String caption, Presenter presenter) {
        Database db = Database.getInstance();
        db.createPost(db.getUser().getUUID(), uri, caption)
                .addOnSucessListener((Database.OnSucessListener<Void>) presenter::onSuccess)
                .addOnFailureListener(e -> presenter.onError(e.getMessage()))
                .addOnCompleteListener(presenter::onComplete);
    }

}