package br.com.instagramremake.main.profile.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.Post;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;

public class ProfileLocalDataSource implements ProfileDataSource {

    @Override
    public void findUser(String user, Presenter<UserProfile> presenter) {
        Database db = Database.getInstance();
        db.findUser(user)
                .addOnSucessListener((Database.OnSucessListener<User>) user1 -> {
                    db.findPosts(user1.getUuid())
                            .addOnSucessListener((Database.OnSucessListener<List<Post>>) posts -> {
                                presenter.onSucess(new UserProfile(user1, posts));
                                presenter.onComplete();
                            });
                });
    }

}