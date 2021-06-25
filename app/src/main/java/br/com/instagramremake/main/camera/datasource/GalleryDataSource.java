package br.com.instagramremake.main.camera.datasource;

import android.content.Context;

import br.com.instagramremake.common.presenter.Presenter;

public interface GalleryDataSource {

    void findPictures(Context context, Presenter presenter);

}