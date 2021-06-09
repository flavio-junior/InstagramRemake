package br.com.instagramremake.main.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.instagramremake.R;
import br.com.instagramremake.common.view.AbstractActivity;
import br.com.instagramremake.login.presentation.LoginActivity;
import br.com.instagramremake.main.camera.presentation.CameraFragment;
import br.com.instagramremake.main.home.presentation.HomeFragment;
import br.com.instagramremake.main.profile.presentation.ProfileFragment;
import br.com.instagramremake.main.search.presentation.SearchFragment;
import br.com.instagramremake.register.presentation.RegisterActivity;

public class MainActivity extends AbstractActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String ACT_SOURCE = "act_source";
    public static final int LOGIN_ACTIVITY = 0;
    public static final int REGISTER_ACTIVITY = 1;

    Fragment homeFragment;
    Fragment searchFragment;
    Fragment cameraFragment;
    Fragment profileFragment;
    Fragment active;

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
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        cameraFragment = new CameraFragment();
        profileFragment = new ProfileFragment();

        active = homeFragment;

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.main_fragment, homeFragment).hide(homeFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, searchFragment).hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, cameraFragment).hide(cameraFragment).commit();
        fm.beginTransaction().add(R.id.main_fragment, profileFragment).hide(profileFragment).commit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int source = extras.getInt(ACT_SOURCE);
            if (source == REGISTER_ACTIVITY) {
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bv = findViewById(R.id.main_bottom_nav);
        bv.setOnNavigationItemSelectedListener(this);
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
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                return true;

            case R.id.menu_botton_search:
                fm.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                return true;

            case R.id.menu_botton_add:
                fm.beginTransaction().hide(active).show(cameraFragment).commit();
                active = cameraFragment;
                return true;

            case R.id.menu_botton_profile:
                fm.beginTransaction().hide(active).show(profileFragment).commit();
                active = profileFragment;
                return true;
        }
        return false;
    }
}