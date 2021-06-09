package br.com.instagramremake.main.profile.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.Post;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(db.getUser().getUUID())
                .addOnSucessListener((Database.OnSucessListener<User>) user -> {
                    db.findPosts(user.getUuid())
                            .addOnSucessListener((Database.OnSucessListener<List<Post>>) posts -> {
                                presenter.onSucess(new UserProfile(user, posts));
                                presenter.onComplete();
                            });
                });
    }

}