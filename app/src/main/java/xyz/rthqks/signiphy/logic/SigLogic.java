package xyz.rthqks.signiphy.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import xyz.rthqks.signiphy.data.Gif;
import xyz.rthqks.signiphy.data.SigDao;
import xyz.rthqks.signiphy.net.GiphyApi;
import xyz.rthqks.signiphy.net.GiphyResponse;

@Singleton
public class SigLogic {
    private final SigDao dao;
    private final GiphyApi api;

    @Inject
    public SigLogic(SigDao dao, GiphyApi api) {
        this.dao = dao;
        this.api = api;
    }

    public void refreshTrending(int limit) {
        fetch(api.getTrending(0, limit), true);
    }

    public void fetchTrending(int offset, int limit) {
        fetch(api.getTrending(offset, limit), false);
    }

    public void refreshSearch(String query, int limit) {
        fetch(api.getSearch(query, 0, limit), true);
    }

    public void fetchSearch(String query, int offset, int limit) {
        fetch(api.getSearch(query, offset, limit), false);
    }

    private void fetch(Single<GiphyResponse> request, boolean delete) {
        Disposable unused = request
                .subscribeOn(Schedulers.io())
                .map(giphyResponse -> {
                    List<Gif> gifs = new ArrayList<>();
                    for (GiphyResponse.Gif g : giphyResponse.data) {
                        gifs.add(Gif.from(g));
                    }
                    return gifs;
                })
                .flatMapCompletable(gifs -> Completable.fromAction(() -> {
                    if (delete) {
                        dao.deleteAll();
                    }
                    dao.insert(gifs);
                }))
                .subscribe(() -> {
                    Log.d("logic", "fetch done");
                });
    }
}
