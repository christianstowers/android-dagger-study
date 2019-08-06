package com.christianstowers.daggerpractice.di;

import com.christianstowers.daggerpractice.di.auth.AuthModule;
import com.christianstowers.daggerpractice.di.auth.AuthViewModelsModule;
import com.christianstowers.daggerpractice.di.main.MainFragmentBuildersModule;
import com.christianstowers.daggerpractice.di.main.MainViewModelsModule;
import com.christianstowers.daggerpractice.ui.auth.AuthActivity;
import com.christianstowers.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// builder modules are places for dependencies to live and then add to components
@Module
public abstract class ActivityBuildersModule {

    // ** any classes that use the ContributeAndroidInjector annotation must be abstract!
    // ** add all activities in this module!

    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class})
    // lets Dagger know that AuthActivity is a potential client
    // creates AuthActivity subcomponent
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class}
    )
    // creates MainActivity subcomponent
    abstract MainActivity contributeMainActivity();

}
