package br.com.instagramremake.register.presentation;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractFragment;
import br.com.instagramremake.common.component.LoadingButton;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_button_photo)
    LoadingButton buttonNext;


    public RegisterPhotoFragment() {

    }

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);

        //CustomDialog customDialog = new CustomDialog.Builder(getContext())
        //      .setTitle(R.string.define_photo_profile)
        //      .addButton((v) -> {
        //          switch (v.getId()) {
        //              case R.string.take_picture:
        //                  Log.i("Teste", "take pic");
        //                  break;
        //              case R.string.search_gallery:
        //                  Log.i("Teste", "gallery");
        //                  break;
        //          }
        //      }, R.string.take_picture, R.string.search_gallery)
        //      .build();
        //
        //      customDialog.show();
    }

    @Override
    public void onImageCropped(Uri uri) {
        
    }

    @OnClick(R.id.register_button_photo)
    public void onButtonNextClick() {
        // TODO: 6/4/2021
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