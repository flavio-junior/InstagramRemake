package br.com.instagramremake.main.camera.presentation;

import android.net.Uri;

import java.util.List;

import br.com.instagramremake.common.view.View;

public interface GalleryView extends View {

    void onPicturesLoaded(List<Uri> uris);

}