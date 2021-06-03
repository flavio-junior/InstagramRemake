package br.com.instagramremake.register.presentation;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractFragment;
import br.com.instagramremake.common.view.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterEmailFragment extends AbstractFragment implements RegisterView.EmailView {

    @BindView(R.id.register_edit_text_email_input)
    TextInputLayout inputLayoutEmail;

    @BindView(R.id.register_edit_text_email)
    EditText editTextEmail;

    @BindView(R.id.register_button_next)
    LoadingButton buttonNext;

    public RegisterEmailFragment() {

    }

    @Override
    public void onFailureForm(String emailError) {

    }

    @OnClick(R.id.register_text_view_email_login)
    public void onTextViewLoginClick() {
        if (isAdded() && getActivity() != null) {
            getActivity().finish();
        }
    }

    @OnClick(R.id.register_button_next)
    public void onButtonNextClick() {

    }

    @OnTextChanged(R.id.register_edit_text_email)
    public void onTextChanged(CharSequence s) {
        buttonNext.setEnabled(!editTextEmail.getText().toString().isEmpty());

        editTextEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        inputLayoutEmail.setError(null);
        inputLayoutEmail.setErrorEnabled(false);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_email;
    }

}