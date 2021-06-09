package br.com.instagramremake.login.presentation;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.model.UserAuth;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.common.component.LoadingButton;
import br.com.instagramremake.login.datasource.LoginDataSource;
import br.com.instagramremake.login.datasource.LoginLocalDataSource;
import br.com.instagramremake.main.presentation.MainActivity;
import br.com.instagramremake.register.presentation.RegisterActivity;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarDark();

        UserAuth user = Database.getInstance().getUser();
        if (user != null)
            onUserLogged();
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        presenter = new LoginPresenter(this, dataSource);
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            inputLayoutEmail.setError(emailError);
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
        if (passwordError != null) {
            inputLayoutPassword.setError(passwordError);
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background_error));
        }
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.login_button_enter)
    public void onButtonEnterClick() {
        presenter.login(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    @OnClick(R.id.login_text_view_register)
    public void OnTextViewRegisterClick() {
        RegisterActivity.launch(this);
    }

    @OnTextChanged({R.id.login_edit_text_email, R.id.login_edit_text_password})
    public void onTextChanged(CharSequence s) {
        buttonEnter.setEnabled(
                !editTextEmail.getText().toString().isEmpty() &&
                        !editTextPassword.getText().toString().isEmpty());

        if (s.hashCode() == editTextEmail.getText().hashCode()) {
            editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutEmail.setError(null);
            inputLayoutEmail.setErrorEnabled(false);
        } else if (s.hashCode() == editTextPassword.getText().hashCode()) {
            editTextPassword.setBackground(findDrawable(R.drawable.edit_text_background));
            inputLayoutPassword.setError(null);
            inputLayoutPassword.setErrorEnabled(false);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
}