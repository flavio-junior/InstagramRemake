package br.com.instagramremake.main.home.presentation;

import java.util.List;

import javax.sql.DataSource;

import br.com.instagramremake.common.model.Feed;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.main.home.datasource.HomeDataSource;
import br.com.instagramremake.main.presentation.MainView;

public class HomePresenter implements Presenter<List<Feed>> {

    private final HomeDataSource dataSource;
    private MainView.HomeView view;

    public HomePresenter(HomeDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.HomeView view) {
        this.view = view;
    }

    public void findFeed() {
        view.showProgressBar();
        dataSource.findFeed(this);
    }

    @Override
    public void onSucess(List<Feed> response) {
        view.showFeed(response);
    }

    @Override
    public void onError(String message) {
        // TODO: 6/9/2021
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }

}