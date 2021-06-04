package br.com.instagramremake.register.presentation;

import android.content.Context;

import br.com.instagramremake.common.view.View;

public interface RegisterView extends View {

    void showNextView(RegisterSteps steps);

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

}