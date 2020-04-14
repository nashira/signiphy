package xyz.rthqks.signiphy.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import xyz.rthqks.signiphy.data.Gif;
import xyz.rthqks.signiphy.data.SigDao;

public class DetailViewModel extends ViewModel {
    private final SigDao dao;
    public MutableLiveData<Gif> gif = new MutableLiveData<>();

    @Inject
    public DetailViewModel(SigDao dao) {
        this.dao = dao;
    }

    public void setGifId(String id) {
        Disposable disposable = Single.fromCallable(() -> dao.getGit(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe((g) -> {
            gif.setValue(g);
        });
    }
}
