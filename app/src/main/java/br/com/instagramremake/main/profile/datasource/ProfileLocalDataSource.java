package br.com.instagramremake.main.profile.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.Post;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(String uuid, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(uuid)
                .addOnSucessListener((Database.OnSucessListener<User>) user1 -> {
                    db.findPosts(uuid)
                            .addOnSucessListener((Database.OnSucessListener<List<Post>>) posts -> {
                                db.following(db.getUser().getUUID(), uuid)
                                        .addOnSucessListener((Database.OnSucessListener<Boolean>) following -> {
                                            presenter.onSucess(new UserProfile(user1, posts, following));
                                            presenter.onComplete();
                                        });
                            });
                });
    }

}