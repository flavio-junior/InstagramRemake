package br.com.instagramremake.common.model;

import android.net.Uri;
import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Set<User> users;
    private static Set<Uri> storeges;
    private static Database INSTANCE;

    private OnSucessListener onSucessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();
        users = new HashSet<>();
        storeges = new HashSet<>();

        //usersAuth.add(new UserAuth("user1@gmail.com", "12345"));
        //usersAuth.add(new UserAuth("user2@gmail.com", "123456"));
        //usersAuth.add(new UserAuth("user3@gmail.com", "1234567"));
        //usersAuth.add(new UserAuth("user4@gmail.com", "12345678"));
        //usersAuth.add(new UserAuth("user5@gmail.com", "123456789"));
    }

    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
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
            user.setPassword(password);
            user.setUuid(userAuth.getUUID());

            boolean added = users.add(user);
            if (added) {
                this.userAuth = userAuth;
                onSucessListener.onSucess(userAuth);
            } else {
                this.userAuth = null;
                onFailureListener.onFailure(new IllegalArgumentException("Usuário já existe"));
            }
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
        new Handler().postDelayed(r, 2000);
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