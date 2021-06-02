package br.com.instagramremake.login.presentation;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.common.view.LoadingButton;
import butterknife.BindView;

public class LoginActivity extends AbstractActivity implements LoginView {

    @BindView(R.id.login_edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.login_edit_text_password)
    EditText editTextPassword;

    @BindView(R.id.login_edit_text_email_input)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.login_edit_text_password_input)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.login_button_enter)
    LoadingButton buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        final EditText editTextEmail = findViewById(R.id.login_edit_text_email);
        final EditText editTextPassword = findViewById(R.id.login_edit_text_password);

        editTextEmail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);

        buttonEnter = findViewById(R.id.login_button_enter);
        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);
            }, 4000);
        });
    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @Override
    public void onUserLogged() {
        // TODO: 6/2/2021
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty())
                buttonEnter.setEnabled(true);
            else
                buttonEnter.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

}