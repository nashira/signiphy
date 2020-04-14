package xyz.rthqks.signiphy;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import xyz.rthqks.signiphy.inject.DaggerAppComponent;

public class SigniphyApp extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
