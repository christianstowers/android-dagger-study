package com.christianstowers.daggerpractice.di;


import android.app.Application;

import com.christianstowers.daggerpractice.BaseApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

// persists across the entire lifetime of the application
// when creating a component class with dagger, annotate it with @Component. This tells the code gen this class is labeled as a component.
// extends AndroidInjector<BaseApplication> b/c this app uses the Android specific Dagger dependencies. Cuts out a bunch of code (manual injection methods, interfaces, etc.).


@Component(
        modules = {
                // if using convenience classes, only have to declare once, within the app level component
                AndroidSupportInjectionModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    // ** AndroidInjector is a convenience class
    // think of this app component class as a service and the base application class as a client.

    @Component.Builder
    interface Builder{

        // use to bind an object to the component at the time of its construction
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
