package br.com.instagramremake.register.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.theartofdev.edmodo.cropper.CropImageView;

import br.com.instagramremake.R;
import br.com.instagramremake.common.component.MediaHelper;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.main.presentation.MainActivity;
import br.com.instagramremake.register.datasource.RegisterDataSource;
import br.com.instagramremake.register.datasource.RegisterLocalDataSource;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends AbstractActivity implements RegisterView, MediaHelper.OnImageCroppedListener {

    @BindView(R.id.register_root_container)
    FrameLayout rootContainer;

    @BindView(R.id.register_scrollView)
    ScrollView scrollView;

    @BindView(R.id.register_crop_image_view)
    CropImageView cropImageView;

    @BindView(R.id.register_button_crop)
    Button buttonCrop;

    private RegisterPresenter presenter;
    private MediaHelper mediaHelper;

    public static void launch(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarDark();

        mediaHelper = MediaHelper.getInstance(this)
                .cropView(cropImageView)
                .listener(this);
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
        switch (step) {
            case EMAIL:
                frag = RegisterEmailFragment.newInstance(presenter);
                break;
            case NAME_PASSWORD:
                frag = RegisterNamePasswordFragment.newInstance(presenter);
                break;
            case WELCOME:
                frag = RegisterWelcomeFragment.newInstance(presenter);
                break;
            case PHOTO:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) scrollView.getLayoutParams();
                layoutParams.gravity = Gravity.TOP;
                scrollView.setLayoutParams(layoutParams);
                frag = RegisterPhotoFragment.newInstance(presenter);
                break;
        }

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
    public void onImageCropped(Uri uri) {
        presenter.setUri(uri);
    }

    @Override
    public void onImagePicked(Uri uri) {
        cropImageView.setImageUriAsync(uri);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cropViewEnabled(true);

        MediaHelper mediaHelper = MediaHelper.getInstance(this);
        mediaHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUserCreated() {
        MainActivity.launch(this, MainActivity.REGISTER_ACTIVITY);
    }

    @Override
    public void showCamera() {
        mediaHelper.chooserCamera();
    }

    @Override
    public void showGallery() {
        mediaHelper.chooserGallery();
    }

    @OnClick(R.id.register_button_crop)
    public void onButtonCropClick() {
        cropViewEnabled(false);
        MediaHelper.getInstance(this).cropImage(cropImageView);
    }

    private void cropViewEnabled(boolean enabled) {
        cropImageView.setVisibility(enabled ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(enabled ? View.GONE : View.VISIBLE);
        buttonCrop.setVisibility(enabled ? View.VISIBLE : View.GONE);
        rootContainer.setBackgroundColor(enabled ? findColor(android.R.color.black) : findColor(android.R.color.white));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

}