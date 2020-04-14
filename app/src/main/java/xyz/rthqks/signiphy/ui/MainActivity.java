package xyz.rthqks.signiphy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import xyz.rthqks.signiphy.R;
import xyz.rthqks.signiphy.data.Gif;
import xyz.rthqks.signiphy.inject.ViewModelFactory;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    ViewModelFactory viewModelFactory;
    private MainViewModel viewModel;
    private GifAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);

        RecyclerView gifList = findViewById(R.id.gif_list);
        gifList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GifAdapter();
        gifList.setAdapter(adapter);
        viewModel.gifs.observe(this, gifs -> {
            Log.d(TAG, gifs.toString());
            adapter.submitList(gifs);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit " + query);
                searchView.clearFocus();
                viewModel.setQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange " + newText);
                if (TextUtils.isEmpty(newText)) {
                    viewModel.clearQuery();
                    return true;
                }
                return false;
            }
        });
        return true;
    }
}

class GifAdapter extends PagedListAdapter<Gif, GifViewHolder> {
    protected GifAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_gif_item, parent, false);

        return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        Gif gif = getItem(position);
        if (gif != null) {
            holder.bind(gif);
        } else {
            holder.clear();
        }
    }

    private static DiffUtil.ItemCallback<Gif> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Gif>() {
                @Override
                public boolean areItemsTheSame(Gif old, Gif mew) {
                    return old.id.equals(mew.id);
                }

                @Override
                public boolean areContentsTheSame(Gif old, @NotNull Gif mew) {
                    return old.equals(mew);
                }
            };
}

class GifViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;
    private String id;

    public GifViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        textView = itemView.findViewById(R.id.text_view);
        itemView.setOnClickListener(v -> {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ID, id);
            context.startActivity(intent);
        });
    }

    public void bind(Gif gif) {
        id = gif.id;
        textView.setText(gif.title);
        Glide.with(imageView).load(gif.url).into(imageView);
    }

    public void clear() {
    }
}