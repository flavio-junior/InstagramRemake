package br.com.instagramremake.main.profile.presentation;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.Post;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.model.UserProfile;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.main.presentation.MainView;
import br.com.instagramremake.main.profile.datasource.ProfileDataSource;

public class ProfilePresenter implements Presenter<UserProfile> {

    private final ProfileDataSource datasource;
    private final String user;
    private MainView.ProfileView view;

    public ProfilePresenter(ProfileDataSource dataSource) {
        this(dataSource, FirebaseAuth.getInstance().getUid());
    }

    public ProfilePresenter(ProfileDataSource dataSource, String user) {
        this.datasource = dataSource;
        this.user = user;
    }

    public void setView(MainView.ProfileView view) {
        this.view = view;
    }

    public String getUser() {
        return user;
    }

    public void findUser() {
        view.showProgressBar();
        datasource.findUser(user, this);
    }

    public void follow(boolean follow) {
        if (follow)
            datasource.follow(user);
        else
            datasource.unfollow(user);
    }

    @Override
    public void onSuccess(UserProfile userProfile) {
        User user = userProfile.getUser();
        List<Post> posts = userProfile.getPosts();

        boolean editProfile = user.getUuid().equals(FirebaseAuth.getInstance().getUid());

        view.showData(
                user.getName(),
                String.valueOf(user.getFollowing()),
                String.valueOf(user.getFollowers()),
                String.valueOf(user.getPosts()),
                editProfile,
                userProfile.isFollowing()
        );
        view.showPosts(posts);

        if (user.getPhotoUrl() != null)
            view.showPhoto(user.getPhotoUrl());
    }

    @Override
    public void onError(String message) {
        // TODO: 6/9/2021  
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}