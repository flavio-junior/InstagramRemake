package br.com.instagramremake.main.home.datasource;

import java.util.List;

import br.com.instagramremake.common.model.Feed;
import br.com.instagramremake.common.presenter.Presenter;

public interface HomeDataSource {

    void findFeed(Presenter<List<Feed>> presenter);

}