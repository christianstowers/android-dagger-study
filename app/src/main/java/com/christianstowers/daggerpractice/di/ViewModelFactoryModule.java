package com.christianstowers.daggerpractice.di;

import androidx.lifecycle.ViewModelProvider;

import com.christianstowers.daggerpractice.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

//TODO: responsible for generating the dependency injection for the viewmodel factory class.
//** for the dependency of the ViewModelFactory
@Module
public abstract class ViewModelFactoryModule {

    // a more efficient way to provide a dependency than using @Provides, in this case providing an instance of viewmodelproviderfactory
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);

}
