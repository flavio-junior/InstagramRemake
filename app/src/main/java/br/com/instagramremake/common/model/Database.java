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
    private static Database INSTANCE;

    private OnSucessListener onSucessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storeges = new HashSet<>();
        posts = new HashMap<>();

        //usersAuth.add(new UserAuth("user1@gmail.com", "12345"));
        //usersAuth.add(new UserAuth("user2@gmail.com", "123456"));
        //usersAuth.add(new UserAuth("user3@gmail.com", "1234567"));
        //usersAuth.add(new UserAuth("user4@gmail.com", "12345678"));
        //usersAuth.add(new UserAuth("user5@gmail.com", "123456789"));
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init() {

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
        this.userAuth = userAuth;

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
                this.userAuth = userAuth;
                if (onSucessListener != null)
                    onSucessListener.onSucess(userAuth);
            } else {
                this.userAuth = null;
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
                this.userAuth = userAuth;
                onSucessListener.onSucess(userAuth);
            } else {
                this.userAuth = null;
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