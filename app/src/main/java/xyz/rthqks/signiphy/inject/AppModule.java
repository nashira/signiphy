package xyz.rthqks.signiphy.inject;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.rthqks.signiphy.BuildConfig;
import xyz.rthqks.signiphy.ui.DetailActivity;
import xyz.rthqks.signiphy.ui.MainActivity;
import xyz.rthqks.signiphy.SigniphyApp;
import xyz.rthqks.signiphy.data.SigDao;
import xyz.rthqks.signiphy.data.SigDb;
import xyz.rthqks.signiphy.net.ApiInterceptor;
import xyz.rthqks.signiphy.net.GiphyApi;

@Module
abstract class AppModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract MainActivity contributeMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract DetailActivity contributeDetailActivity();

    @Singleton
    @Binds
    public abstract Context provideContext(SigniphyApp app);

    @Singleton
    @Provides
    @Named("apiKey")
    public static String provideGiphyApiKey() {
        return BuildConfig.API_KEY;
    }

    @Singleton
    @Provides
    @Named("host")
    public static String provideGiphyHost() {
        return BuildConfig.HOST;
    }

    @Singleton
    @Provides
    public static GiphyApi provideGiphyApi(
            @Named("apiKey") String apiKey,
            @Named("host") String host) {
        return new Retrofit.Builder()
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new ApiInterceptor(apiKey))
                        .build())
                .build().create(GiphyApi.class);
    }

    @Singleton
    @Provides
    public static SigDb provideDb(Context context) {
        return Room.databaseBuilder(context, SigDb.class, "sig.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public static SigDao provideDao(SigDb db) {
        return db.dao();
    }
}