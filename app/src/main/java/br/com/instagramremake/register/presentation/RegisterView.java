package br.com.instagramremake.register.presentation;

import android.content.Context;
import android.net.Uri;

import br.com.instagramremake.common.view.View;

public interface RegisterView extends View {

    void showNextView(RegisterSteps steps);

    void onUserCreated();

    void showCamera();

    void showGallery();

    interface EmailView {

        Context getContext();

        void onFailureForm(String emailError);

    }

    interface NamePasswordView extends View {

        Context getContext();

        void onFailureForm(String nameError, String passwordError);

        void onFailureCreateUser(String message);

    }

    interface WelcomeView {

    }

    interface PhotoView {
        void onImageCropped(Uri uri);
    }

}