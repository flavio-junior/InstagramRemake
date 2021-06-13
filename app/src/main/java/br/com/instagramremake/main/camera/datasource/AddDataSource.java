package br.com.instagramremake.main.camera.datasource;

import android.net.Uri;

import br.com.instagramremake.common.presenter.Presenter;

public interface AddDataSource {

    void savePost(Uri uri, String caption, Presenter presenter);

}