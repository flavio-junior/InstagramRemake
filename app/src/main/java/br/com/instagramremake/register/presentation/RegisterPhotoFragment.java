package br.com.instagramremake.register.presentation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import br.com.instagramremake.R;
import br.com.instagramremake.common.component.CustomDialog;
import br.com.instagramremake.common.view.AbstractFragment;
import br.com.instagramremake.common.component.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_button_photo)
    LoadingButton buttonNext;

    @BindView(R.id.register_camera_icon)
    ImageView imageViewCropper;

    public RegisterPhotoFragment() {

    }

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        presenter.setPhotoView(fragment);
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
    }

    @Override
    public void onImageCropped(Uri uri) {
        Log.e("Teste", uri.toString());
        try {
            if (getContext() != null && getContext().getContentResolver() != null) {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                imageViewCropper.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            Log.e("Teste", e.getMessage(), e);
        }
    }

    @OnClick(R.id.register_button_photo)
    public void onButtonNextClick() {
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            presenter.showCamera();
                            break;
                        case R.string.search_gallery:
                            presenter.showGallery();
                            break;
                    }
                }, R.string.take_picture, R.string.search_gallery)
                .build();

        customDialog.show();
    }

    @OnClick(R.id.register_button_jump)
    public void onButtonJumpClick() {
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }

}