package br.com.instagramremake.main.camera.presentation;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.main.camera.datasource.GalleryDataSource;

public class GalleryPresenter implements Presenter<List<String>> {

    private final GalleryDataSource dataSource;
    private GalleryView view;

    public GalleryPresenter(GalleryDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(GalleryView view) {
        this.view = view;
    }

    public void findPictures(Context context) {
        view.showProgressBar();
        dataSource.findPictures(context, this);
    }

    @Override
    public void onSuccess(List<String> response) {
        ArrayList<Uri> uris = new ArrayList<>();
        for (String res : response) {
            Uri uri = Uri.parse(res);
            uris.add(uri);
        }
        view.onPicturesLoaded(uris);
    }

    @Override
    public void onError(String message) {
        // TODO: 6/14/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}