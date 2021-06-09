package br.com.instagramremake.main.profile.datasource;

import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;

public interface ProfileDataSource {

    void findUser(Presenter<UserProfile> presenter);

}