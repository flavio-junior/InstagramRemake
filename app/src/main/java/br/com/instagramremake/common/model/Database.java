package br.com.instagramremake.common.model;

import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storeges;
    private static HashMap<String, HashSet<Post>> posts;
    private static HashMap<String, HashSet<Feed>> feed;
    private static HashMap<String, HashSet<String>> followers;

    private static Database INSTANCE;

    private OnSucessListener onSucessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private static UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storeges = new HashSet<>();
        posts = new HashMap<>();
        feed = new HashMap<>();
        followers = new HashMap<>();

       // init();

        //usersAuth.add(new UserAuth("user1@gmail.com", "12345"));
        //usersAuth.add(new UserAuth("user2@gmail.com", "123456"));
        //usersAuth.add(new UserAuth("user3@gmail.com", "1234567"));
        //usersAuth.add(new UserAuth("user4@gmail.com", "12345678"));
        //usersAuth.add(new UserAuth("user5@gmail.com", "123456789"));
    }

    public static Database getInstance() {
        return new Database();
        //   if (INSTANCE == null) {
        //       INSTANCE = new Database();
        //     INSTANCE.init();
        // }
        //return INSTANCE;
    }

    public static void init() {

        String email = "juniorFlavio@gmail.com";
        String password = "12345";
        String name = "Flávio Júnior";

        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(email);
        userAuth.setPassword(password);

        usersAuth.add(userAuth);

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUuid(userAuth.getUUID());

        users.add(user);
        Database.userAuth = userAuth;

    }

    public <T> Database addOnSucessListener(OnSucessListener<T> listener) {
        this.onSucessListener = listener;
        return this;
    }

    public Database addOnFailureListener(OnFailureListener listener) {
        this.onFailureListener = listener;
        return this;
    }

    public Database addOnCompleteListener(OnCompleteListener listener) {
        this.onCompleteListener = listener;
        return this;
    }

    public Database findFeed(String uuid) {
        timeout(() -> {
            HashMap<String, HashSet<Feed>> feed = Database.feed;
            HashSet<Feed> res = feed.get(uuid);

            if (res == null)
                res = new HashSet<>();

            if (onSucessListener != null)
                onSucessListener.onSucess(new ArrayList<>(res));

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    // select * from posts p inner join users u on p.user_id = u.id where u.uuid = ?
    public Database findPosts(String uuid) {
        timeout(() -> {
            HashMap<String, HashSet<Post>> posts = Database.posts;
            HashSet<Post> res = posts.get(uuid);

            if (res == null)
                res = new HashSet<>();

            if (onSucessListener != null)
                onSucessListener.onSucess(new ArrayList<>(res));

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    // select * from users where uuid = ?
    public Database findUser(String uuid) {
        timeout(() -> {
            Set<User> users = Database.users;
            User res = null;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    res = user;
                    break;
                }
            }

            if (onSucessListener != null && res != null) {
                onSucessListener.onSucess(res);
            } else if (onFailureListener != null) {
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    public Database addPhoto(String uuid, Uri uri) {
        timeout(() -> {
            Set<User> users = Database.users;
            for (User user : users) {
                if (user.getUuid().equals(uuid)) {
                    user.setUri(uri);
                }
            }
            storeges.add(uri);
            onSucessListener.onSucess(true);
        });
        return this;
    }

    public Database createPost(String uuid, Uri uri, String caption) {
        timeout(() -> {
            HashMap<String, HashSet<Post>> postMap = Database.posts;

            HashSet<Post> posts = postMap.get(uuid);

            if (posts == null) {
                posts = new HashSet<>();
                postMap.put(uuid, posts);
            }

            Post post = new Post();
            post.setUri(uri);
            post.setCaption(caption);
            post.setTimestamp(System.currentTimeMillis());
            post.setUuid(String.valueOf(post.hashCode()));
            posts.add(post);

            HashMap<String, HashSet<String>> followersMap = Database.followers;

            HashSet<String> followers = followersMap.get(uuid);

            if (followers == null) {
                followers = new HashSet<>();
                followersMap.put(uuid, followers);
            } else {

                HashMap<String, HashSet<Feed>> feedMap = Database.feed;

                for (String follower : followers) {
                    HashSet<Feed> feeds = feedMap.get(follower);

                    if (feeds != null) {
                        Feed feed = new Feed();
                        feed.setUri(post.getUri());
                        feed.setCaption(post.getCaption());

                        // feed.publisher
                        feed.setTimestamp(post.getTimestamp());

                        feeds.add(feed);
                    }
                }

                HashSet<Feed> feedMe = feedMap.get(uuid);

                if (feedMe != null) {
                    Feed feed = new Feed();
                    feed.setUri(post.getUri());
                    feed.setCaption(post.getCaption());

                    // feed.publisher
                    feed.setTimestamp(post.getTimestamp());
                    feedMe.add(feed);
                }
            }

            if (onSucessListener != null)
                onSucessListener.onSucess(null);

            if (onCompleteListener != null)
                onCompleteListener.onComplete();
            
        });
        return this;
    }

    //insert into user ()
    public Database createUser(String name, String email, String password) {
        timeout(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            usersAuth.add(userAuth);

            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setUuid(userAuth.getUUID());

            boolean added = users.add(user);
            if (added) {
                Database.userAuth = userAuth;

                HashMap<String, HashSet<String>> followersMap = Database.followers;
                followersMap.put(userAuth.getUUID(), new HashSet<>());

                HashMap<String, HashSet<Feed>> feedMap = Database.feed;
                feedMap.put(userAuth.getUUID(), new HashSet<>());

                if (onSucessListener != null)
                    onSucessListener.onSucess(userAuth);
            } else {
                Database.userAuth = null;
                if (onFailureListener != null)
                    onFailureListener.onFailure(new IllegalArgumentException("Usuário já existe"));
            }
            if (onCompleteListener != null)
                onCompleteListener.onComplete();
        });
        return this;
    }

    //select * from user where email = ? and password = ?
    public Database login(String email, String password) {
        timeout(() -> {
            UserAuth userAuth = new UserAuth();
            userAuth.setEmail(email);
            userAuth.setPassword(password);

            if (usersAuth.contains(userAuth)) {
                Database.userAuth = userAuth;
                onSucessListener.onSucess(userAuth);
            } else {
                Database.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário não encontrado"));
            }
            onCompleteListener.onComplete();
        });
        return this;
    }

    public UserAuth getUser() {
        return userAuth;
    }

    private void timeout(Runnable r) {
        new Handler().postDelayed(r, 1000);
    }

    public interface OnSucessListener<T> {
        void onSucess(T response);
    }

    public interface OnFailureListener {
        void onFailure(Exception e);
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}