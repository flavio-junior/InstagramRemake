package br.com.instagramremake.register.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.main.presentation.MainActivity;
import br.com.instagramremake.register.datasource.RegisterDataSource;
import br.com.instagramremake.register.datasource.RegisterLocalDataSource;
import butterknife.BindView;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    @BindView(R.id.register_scrollView)
    ScrollView scrollView;

    private RegisterPresenter presenter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();
    }

    @Override
    protected void onInject() {
        RegisterDataSource dataSource = new RegisterLocalDataSource();
        presenter = new RegisterPresenter(dataSource);
        presenter.setRegisterView(this);

        showNextView(RegisterSteps.EMAIL);
    }

    @Override
    public void showNextView(RegisterSteps step) {
        Fragment frag = null;

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollView.getLayoutParams();

        switch (step) {
            case EMAIL:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                layoutParams.gravity = Gravity.BOTTOM;
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                layoutParams.gravity = Gravity.TOP;
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }
        scrollView.setLayoutParams(layoutParams);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (manager.findFragmentById(R.id.register_fragment) == null) {
            transaction.add(R.id.register_fragment, frag, step.name());
        } else {
            transaction.replace(R.id.register_fragment, frag, step.name());
            transaction.addToBackStack(step.name());
        }
        transaction.commit();
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this);
    }

    @Override
    public void showCamera() {

    }

    @Override
    public void showGallery() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}