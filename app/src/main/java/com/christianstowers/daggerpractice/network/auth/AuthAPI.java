package com.christianstowers.daggerpractice.network.auth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthAPI {

    @GET
    Call<ResponseBody> getFakeStuff();

}
