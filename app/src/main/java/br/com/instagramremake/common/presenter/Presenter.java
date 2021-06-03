package br.com.instagramremake.common.presenter;

import br.com.instagramremake.common.model.UserAuth;

public interface Presenter {

    void onSucess(UserAuth response);

    void onError(String message);

    void onComplete();

}
