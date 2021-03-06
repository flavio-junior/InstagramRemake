package br.com.instagramremake.main.camera.presentation;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.instagramremake.R;
import br.com.instagramremake.common.component.CameraPreview;
import br.com.instagramremake.common.component.MediaHelper;
import br.com.instagramremake.common.view.AbstractFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class CameraFragment extends AbstractFragment {

    @BindView(R.id.camera_progress)
    ProgressBar progressBar;

    @BindView(R.id.camera_surface)
    FrameLayout frameLayout;

    @BindView(R.id.container_preview)
    LinearLayout conteinerPreview;

    @BindView(R.id.camera_image_view_picture)
    Button buttonCamera;

    private MediaHelper mediaHelper;
    private Camera camera;
    private AddView addView;

    public CameraFragment() {

    }

    public static CameraFragment newInstance(AddView addView) {
        CameraFragment cameraFragment = new CameraFragment();
        cameraFragment.setAddView(addView);
        return cameraFragment;
    }

    private void setAddView(AddView addView) {
        this.addView = addView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        if (getContext() != null) {
            mediaHelper = MediaHelper.getInstance(this);
            if (mediaHelper.checkCameraHardware(getContext())) {
                camera = mediaHelper.getCameraInstance(this, getContext());
                if (camera != null) {
                    CameraPreview cameraPreview = new CameraPreview(getContext(), camera);
                    frameLayout.addView(cameraPreview);
                }
            }
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        addView.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null)
            camera.release();
    }

    @OnClick(R.id.camera_image_view_picture)
    public void onCameraButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        buttonCamera.setVisibility(View.GONE);
        camera.takePicture(null, null, (data, camera) -> {
            progressBar.setVisibility(View.GONE);
            buttonCamera.setVisibility(View.VISIBLE);
            Uri uri = mediaHelper.saveCameraFile(getContext(), data);
            if (uri != null)
                addView.onImageLoaded(uri);
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_camera;
    }

}