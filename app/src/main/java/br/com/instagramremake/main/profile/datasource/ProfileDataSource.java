package br.com.instagramremake.main.profile.datasource;

import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;

public interface ProfileDataSource {

    void findUser(String user, Presenter<UserProfile> presenter);

    void follow(String user);

    void unfollow(String user);

}