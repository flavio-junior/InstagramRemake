package br.com.instagramremake.main.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.instagramremake.R;
import br.com.instagramremake.common.model.Database;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.main.camera.presentation.AddActivity;
import br.com.instagramremake.main.home.datasource.HomeDataSource;
import br.com.instagramremake.main.home.datasource.HomeFireDataSource;
import br.com.instagramremake.main.home.datasource.HomeLocalDataSource;
import br.com.instagramremake.main.home.presentation.HomeFragment;
import br.com.instagramremake.main.home.presentation.HomePresenter;
import br.com.instagramremake.main.profile.datasource.ProfileDataSource;
import br.com.instagramremake.main.profile.datasource.ProfileFireDataSource;
import br.com.instagramremake.main.profile.datasource.ProfileLocalDataSource;
import br.com.instagramremake.main.profile.presentation.ProfileFragment;
import br.com.instagramremake.main.profile.presentation.ProfilePresenter;
import br.com.instagramremake.main.search.datasource.SearchDataSource;
import br.com.instagramremake.main.search.datasource.SearchLocalDataSource;
import br.com.instagramremake.main.search.presentation.SearchFragment;
import br.com.instagramremake.main.search.presentation.SearchPresenter;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView {

    public static final String ACT_SOURCE = "act_source";
    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;

    private HomePresenter homePresenter;
    private SearchPresenter searchPresenter;
    private ProfilePresenter profilePresenter;

    Fragment homeFragment;
    Fragment searchFragment;
    //Fragment cameraFragment;
    Fragment profileFragment;
    Fragment active;

    ProfileFragment profileDetailFragment;

    public static void launch(Context context, int source) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ACT_SOURCE, source);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Drawable drawable = getResources().getDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onInject() {
        HomeDataSource homeDataSource = new HomeFireDataSource();
        ProfileDataSource profileDataSource = new ProfileFireDataSource();

        homePresenter = new HomePresenter(homeDataSource);
        profilePresenter = new ProfilePresenter(profileDataSource);

        homeFragment = HomeFragment.newInstance(this, homePresenter);
        profileFragment = ProfileFragment.newInstance(this, profilePresenter);

        SearchDataSource searchDataSource = new SearchLocalDataSource();
        searchPresenter = new SearchPresenter(searchDataSource);

//    cameraFragment = new CameraFragment();
        searchFragment = SearchFragment.newInstance(this, searchPresenter);

        active = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        //fm.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.main_progress).setVisibility(View.GONE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BottomNavigationView bv = findViewById(R.id.main_bottom_nav);
        bv.setOnNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                getSupportFragmentManager().beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                profilePresenter.findUser();
            }
        }
    }

    @Override
    public void scrollToolbarEnabled(boolean enabled) {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.main_appBar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

        if (enabled) {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }
    }

    @Override
    public void showProfile(String user) {
        ProfileDataSource profileDataSource = new ProfileFireDataSource();
        ProfilePresenter profilePresenter = new ProfilePresenter(profileDataSource, user);

        profileDetailFragment = ProfileFragment.newInstance(this, profilePresenter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_fragment, profileDetailFragment, "detail");
        transaction.hide(active);
        transaction.commit();

        scrollToolbarEnabled(true);

        if (getSupportActionBar() != null) {
            Drawable drawable = findDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void disposeProfileDetail() {
        if (getSupportActionBar() != null) {

            Drawable drawable = findDrawable(R.drawable.ic_insta_camera);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(profileDetailFragment);
        transaction.show(active);
        transaction.commit();

        profileDetailFragment = null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentManager fm = getSupportFragmentManager();
        switch (menuItem.getItemId()) {
            case R.id.menu_botton_home:
                if (profileDetailFragment != null)
                    disposeProfileDetail();

                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                //       homePresenter.findFeed();
                scrollToolbarEnabled(false);
                return true;

            case R.id.menu_botton_search:
                if (profileDetailFragment == null) {
                    fm.beginTransaction().hide(active).show(searchFragment).commit();
                    active = searchFragment;
                    scrollToolbarEnabled(false);
                }
                return true;

            case R.id.menu_botton_add:
                // fm.beginTransaction().hide(active).show(cameraFragment).commit();
                // active = cameraFragment;
                AddActivity.launch(this);
                return true;

            case R.id.menu_botton_profile:
                if (profileDetailFragment != null)
                    disposeProfileDetail();

                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                scrollToolbarEnabled(true);
                profilePresenter.findUser();
                return true;
        }
        return false;
    }
}