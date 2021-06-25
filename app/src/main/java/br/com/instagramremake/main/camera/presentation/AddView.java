package br.com.instagramremake.main.camera.presentation;

import android.net.Uri;

public interface AddView {

    void onImageLoaded(Uri uri);

    void dispose();

}