package xyz.rthqks.signiphy.inject;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.annotation.Retention;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import xyz.rthqks.signiphy.ui.DetailViewModel;
import xyz.rthqks.signiphy.ui.MainViewModel;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module
public abstract class ActivityModule {
    @Binds
    @ActivityScope
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel provideViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel provideDetailViewModel(DetailViewModel viewModel);
}


@MapKey
@Retention(RUNTIME)
@interface ViewModelKey {
    Class<? extends ViewModel> value();
}