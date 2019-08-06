package com.christianstowers.daggerpractice.network.main;

import com.christianstowers.daggerpractice.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainAPI {

    // what the request looks like:
    // /posts?userId=1/
    @GET("posts")
    Flowable<List<Post>> getPostsFromUser(
            @Query("userId") int id
    );

}
