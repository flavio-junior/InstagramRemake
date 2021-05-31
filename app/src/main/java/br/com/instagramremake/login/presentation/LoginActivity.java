package br.com.instagramremake.login.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.instagramremake.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout inputLayoutTest = findViewById(R.id.login_edit_text_email_input);
        inputLayoutTest.setError("Esse email é inválido");

        EditText editText = findViewById(R.id.login_edit_text_email);
        editText.setBackground(ContextCompat.getDrawable(LoginActivity.this,
                R.drawable.edit_text_background_error));
    }

}