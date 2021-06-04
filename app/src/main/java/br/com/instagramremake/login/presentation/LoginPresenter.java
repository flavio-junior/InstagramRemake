package br.com.instagramremake.login.presentation;

import android.os.Handler;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.common.util.Strings;
import br.com.instagramremake.login.datasource.LoginDataSource;

class LoginPresenter implements Presenter<UserAuth> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    void login(String email, String password) {
        if (!Strings.emailValid(email)) {
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }
        view.showProgressBar();
        dataSource.login(email, password, this);
    }

    @Override
    public void onSucess(UserAuth userAuth) {
        view.onUserLogged();
    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null, message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}