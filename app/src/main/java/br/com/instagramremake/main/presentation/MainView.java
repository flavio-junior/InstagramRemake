package br.com.instagramremake.main.presentation;

import android.net.Uri;

import java.util.List;

import br.com.instagramremake.common.model.Feed;
import br.com.instagramremake.common.model.Post;
import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.view.View;

public interface MainView extends View {

    void scrollToolbarEnabled(boolean enabled);

    void showProfile(String user);

    public interface ProfileView extends View {

        void showPhoto(Uri photo);

        void showData(String name, String following, String followers, String posts, boolean editProfile);

        void showPosts(List<Post> posts);

    }

    public interface HomeView extends View {

        void showFeed(List<Feed> response);

    }

    public interface SearchView {

        void showUsers(List<User> users);

    }

}