package br.com.instagramremake.register.presentation;

import android.net.Uri;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.common.util.Strings;
import br.com.instagramremake.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private RegisterView registerView;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;
    private RegisterView.PhotoView photoView;

    private final RegisterDataSource datasource;

    private String email;
    private String name;
    private Uri uri;

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

    public void setPhotoView(RegisterView.PhotoView photoView) {
        this.photoView = photoView;
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

        namePasswordView.showProgressBar();
        datasource.createUser(name, email, password, this);
    }

    public String getName() {
        return name;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
        if (photoView != null)
            photoView.onImageCropped(uri);
        photoView.showProgressBar();

        datasource.addPhoto(uri, new updatePhotoCallBack());
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void showCamera() {
        registerView.showCamera();
    }

    public void showGallery() {
        registerView.showGallery();
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

    private class updatePhotoCallBack implements Presenter<Boolean> {

        @Override
        public void onSucess(Boolean response) {
            registerView.onUserCreated();
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onComplete() {

        }

    }

}