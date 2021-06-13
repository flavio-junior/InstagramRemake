package br.com.instagramremake.main.home.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.Feed;
import br.com.instagramremake.common.view.AbstractFragment;
import br.com.instagramremake.main.presentation.MainActivity;
import br.com.instagramremake.main.presentation.MainView;
import br.com.instagramremake.main.profile.presentation.ProfileFragment;
import butterknife.BindView;

public class HomeFragment extends AbstractFragment<HomePresenter> implements MainView.HomeView {

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;

    private FeedAdapter feedAdapter;

    private MainView mainView;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(MainView mainView, HomePresenter homePresenter) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setMainView(mainView);
        homeFragment.setPresenter(homePresenter);
        homePresenter.setView(homeFragment);
        return homeFragment;
    }

    private void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        feedAdapter = new FeedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(feedAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findFeed();
    }

    @Override
    public void showProgressBar() {
        mainView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        mainView.hideProgressBar();
    }

    @Override
    public void showFeed(List<Feed> response) {
        feedAdapter.setFeed(response);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private static class PostViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePost = itemView.findViewById(R.id.profile_image_grid);
        }

        public void bind(Feed feed) {
            // TODO: 6/9/2021
            this.imagePost.setImageURI(feed.getUri());
        }
    }

    private class FeedAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private List<Feed> feed = new ArrayList<>();

        public void setFeed(List<Feed> feed) {
            this.feed = feed;
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new PostViewHolder(getLayoutInflater().inflate(R.layout.item_post_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
            postViewHolder.bind(feed.get(i));
        }

        @Override
        public int getItemCount() {
            return feed.size();
        }
    }
}