package br.com.instagramremake.common.presenter;

public interface Presenter<T> {

    void onSuccess(T response);

    void onError(String message);

    void onComplete();

}
