package br.com.instagramremake.common.presenter;

public interface Presenter {

    void onSucess();

    void onError(String message);

    void onComplete();

}
