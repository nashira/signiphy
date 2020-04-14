package xyz.rthqks.signiphy.inject;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import xyz.rthqks.signiphy.SigniphyApp;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class})
public interface AppComponent extends AndroidInjector<SigniphyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(SigniphyApp app);

        AppComponent build();
    }
}