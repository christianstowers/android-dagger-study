package com.christianstowers.daggerpractice.network.auth;

import com.christianstowers.daggerpractice.models.User;

import dagger.Provides;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthAPI {

    @GET("users/{id}")
    Flowable<User> getUser(
            @Path("id") int id
    );

}
