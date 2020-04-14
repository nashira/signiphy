package xyz.rthqks.signiphy.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

import xyz.rthqks.signiphy.data.Gif;
import xyz.rthqks.signiphy.data.SigDao;
import xyz.rthqks.signiphy.logic.SigLogic;

public class MainViewModel extends ViewModel {
    private final SigDao dao;
    private final SigLogic logic;
    private int offset = 0;
    private int pageSize = 20;
    private String query;
    public LiveData<PagedList<Gif>> gifs;

    private final PagedList.BoundaryCallback<Gif> pageCallback = new PagedList.BoundaryCallback<Gif>() {
        @Override
        public void onItemAtEndLoaded(@NonNull Gif itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
            Log.d("ui", "onItemAtEndLoaded " + itemAtEnd);
            if (query == null) {
                logic.fetchTrending(offset, pageSize);
            } else {
                logic.fetchSearch(query, offset, pageSize);
            }
            offset += pageSize;
        }

        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            Log.d("ui", "onZeroItemsLoaded");
        }
    };

    @Inject
    public MainViewModel(SigLogic logic, SigDao dao) {
        this.logic = logic;
        this.dao = dao;
        gifs = new LivePagedListBuilder<>(
                dao.getGifs(), pageSize)
                .setBoundaryCallback(pageCallback)
                .build();

        logic.refreshTrending(pageSize);
        offset = pageSize;
    }

    public void setQuery(String query) {
        this.query = query;
        logic.refreshSearch(query, pageSize);
        offset = pageSize;
    }

    public void clearQuery() {
        this.query = null;
        logic.refreshTrending(pageSize);
        offset = pageSize;
    }
}
