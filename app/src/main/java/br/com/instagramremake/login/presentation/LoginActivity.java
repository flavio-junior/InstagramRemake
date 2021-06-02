package br.com.instagramremake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.LoadingButton;

public class LoginActivity extends AppCompatActivity {

    private LoadingButton buttonEnter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                TextInputLayout inputLayoutEmail = findViewById(R.id.login_edit_text_email_input);
                inputLayoutEmail.setError("Esse email é inválido");
                editTextEmail.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_background_error));

                TextInputLayout inputLayoutPassword = findViewById(R.id.login_edit_text_password_input);
                inputLayoutPassword.setError("Senha incorreta!");
                editTextPassword.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                        R.drawable.edit_text_background_error));
            }, 4000);
        });
    }

}