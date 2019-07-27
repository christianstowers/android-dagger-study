package com.christianstowers.daggerpractice.di;

import com.christianstowers.daggerpractice.AuthActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

// builder modules are places for dependencies to live and then add to components
@Module
public abstract class ActivityBuildersModule {

    // ** any classes that use the ContributeAndroidInjector annotation must be abstract!
    // ** add all activities in this module!

    @ContributesAndroidInjector
    // lets Dagger know that AuthActivity is a potential client
    abstract AuthActivity contributeAuthActivity();



}
