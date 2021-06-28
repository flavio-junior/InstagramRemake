package br.com.instagramremake.main.search.datasource;

import java.util.List;

import br.com.instagramremake.common.model.User;
import br.com.instagramremake.common.presenter.Presenter;

public interface SearchDataSource {

    void findUser(String query, Presenter<List<User>> presenter);

}