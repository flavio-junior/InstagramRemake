package br.com.instagramremake.common.model;

import android.os.Handler;

import java.util.HashSet;
import java.util.Set;

public class Database {

    private static Set<UserAuth> usersAuth;
    private static Database INSTANCE;

    private OnSucessListener onSucessListener;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UserAuth userAuth;

    static {
        usersAuth = new HashSet<>();

        usersAuth.add(new UserAuth("user1@gmail.com", "12345"));
        usersAuth.add(new UserAuth("user2@gmail.com", "123456"));
        usersAuth.add(new UserAuth("user3@gmail.com", "1234567"));
        usersAuth.add(new UserAuth("user4@gmail.com", "12345678"));
        usersAuth.add(new UserAuth("user5@gmail.com", "123456789"));
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