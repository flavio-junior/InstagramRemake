package br.com.instagramremake.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.instagramremake.common.util.Drawables;
import butterknife.ButterKnife;

public abstract class AbstractActivity extends AppCompatActivity implements View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        onInject();
    }

    public Drawable findDrawable(@DrawableRes int drawableId) {
        return Drawables.getDrawable(this, drawableId);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void onInject();
}