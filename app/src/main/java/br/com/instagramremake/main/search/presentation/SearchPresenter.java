package br.com.instagramremake.main.search.presentation;

import java.util.List;

import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.presenter.Presenter;
import br.com.instagramremake.main.presentation.MainView;
import br.com.instagramremake.main.search.datasource.SearchDataSource;

public class SearchPresenter implements Presenter<List<User>> {

    private final SearchDataSource dataSource;
    private MainView.SearchView view;

    public SearchPresenter(SearchDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setView(MainView.SearchView view) {
        this.view = view;
    }

    public void findUsers(String newText) {
        dataSource.findUser(newText, this);
    }

    @Override
    public void onSuccess(List<User> response) {
        view.showUsers(response);
    }

    @Override
    public void onError(String message) {
        // TODO: 6/28/2021
    }

    @Override
    public void onComplete() {

    }
}