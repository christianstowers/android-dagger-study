package com.christianstowers.daggerpractice.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    // ** this is where I'm going to put all of the app lvl dependencies for the project like the retrofit and glide instances (things that don't change through the app lifetime).

    // @Provides is used to declare a dependency
    @Provides
    static String someString() {
        return "this is a test string.";
    }

    @Provides
    static boolean getApp(Application application) {
        //** the application object is available b/c it's bound to AppComponent via @BindsInstance
        // if the application object is null, this will return true.
        return application == null;
    }

}
