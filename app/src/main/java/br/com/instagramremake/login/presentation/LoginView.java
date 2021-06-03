package br.com.instagramremake.login.presentation;

import br.com.instagramremake.common.view.View;

public interface LoginView extends View {

    void onFailureForm(String emailError, String passwordError);

    void onUserLogged();

}