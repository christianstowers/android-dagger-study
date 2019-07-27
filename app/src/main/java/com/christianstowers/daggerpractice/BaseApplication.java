package com.christianstowers.daggerpractice;

import com.christianstowers.daggerpractice.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    // ** Remember: client service client service client service. BaseApplication is a client of AppComponent.

    // ** DaggerApplication is a convenience class

    // returns app component
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        // binds the app to the appcomponent interface
        return DaggerAppComponent.builder().application(this).build();
        //after this binding, BaseApplication is now a client of AppComponent(now a server).
    }




}
