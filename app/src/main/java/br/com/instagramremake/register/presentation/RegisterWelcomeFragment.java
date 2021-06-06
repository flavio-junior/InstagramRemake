package br.com.instagramremake.register.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractFragment;
import br.com.instagramremake.common.component.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

    @BindView(R.id.register_button_next_welcome)
    LoadingButton buttonNext;

    @BindView(R.id.register_text_view_welcome)
    TextView textViewWelcome;

    public RegisterWelcomeFragment() {

    }

    public static RegisterWelcomeFragment newInstance(RegisterPresenter presenter) {
        RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
        textViewWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }

    @OnClick(R.id.register_button_next_welcome)
    public void onButtonNextClick() {
        presenter.showPhotoView();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }

}