package br.com.instagramremake.register.presentation;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.common.util.Strings;
import br.com.instagramremake.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private RegisterView registerView;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;

    private final RegisterDataSource datasource;

    private String email;
    private String name;
    private String password;

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.datasource = dataSource;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
        this.namePasswordView = namePasswordView;
    }

    public void setEmail(String email) {
        if (!Strings.emailValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_email));
            return;
        }
        this.email = email;
        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            namePasswordView.onFailureForm(null, namePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }
        this.name = name;
        this.password = password;

        namePasswordView.showProgressBar();
        datasource.createUser(this.name, this.email, this.password, this);
    }

    public String getName() {
        return name;
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

    @Override
    public void onSucess(UserAuth response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        namePasswordView.onFailureCreateUser(message);
    }

    @Override
    public void onComplete() {
        namePasswordView.showProgressBar();
    }

}