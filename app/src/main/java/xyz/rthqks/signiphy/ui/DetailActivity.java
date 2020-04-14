package xyz.rthqks.signiphy.ui;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import xyz.rthqks.signiphy.R;
import xyz.rthqks.signiphy.inject.ViewModelFactory;

public class DetailActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_ID = "gif_id";

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imageView = findViewById(R.id.image_view);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
        viewModel.gif.observe(this, gif -> {
            Glide.with(imageView).load(gif.url).into(imageView);
        });

        String id = getIntent().getStringExtra(EXTRA_ID);
        viewModel.setGifId(id);
    }
}
