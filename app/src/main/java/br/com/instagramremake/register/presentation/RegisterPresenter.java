package br.com.instagramremake.register.presentation;

import br.com.instagramremake.R;
import br.com.instagramremake.common.util.Strings;

public class RegisterPresenter {

    private RegisterView.EmailView emailView;

    private String email;

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setEmail(String email) {
        if (Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
    }

}