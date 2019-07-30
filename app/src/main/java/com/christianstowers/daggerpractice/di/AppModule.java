package com.christianstowers.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.christianstowers.daggerpractice.R;
import com.christianstowers.daggerpractice.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    // ** this is where I'm going to put all of the app lvl dependencies for the project like the retrofit and glide instances (things that don't change through the app lifetime).

    // Demonstration Dependencies
//    // @Provides is used to declare a dependency
//    @Provides
//    static String someString() {
//        return "this is a test string.";
//    }
//
//    @Provides
//    static boolean getApp(Application application) {
//        //** the application object is available b/c it's bound to AppComponent via @BindsInstance
//        // if the application object is null, this will return true.
//        return application == null;
//    }


    //** retrofit instance/object declared in the AppModule b/c it will exist throughout the app lifetime
    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //TODO : remember!!: these dependencies are scoped to the same scope of AppComponent b/c these dependencies are passed as a module (AppModule) to AppComponent.

    // required for using Glide
    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    /* TODO: memorize this...
    by default dagger is going to look for whatever objects you pass through the constructors
    and see if that dependency exists anywhere inside the module or inside of the other
    modules in the component.
     */

    // application and requestOptions are available b/c AppComponent binds the instance using the Application object and b/c RequestOptions is provided above.
    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }

}
