package com.christianstowers.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.network.auth.AuthAPI;

import javax.inject.Inject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthAPI authAPI;

    //TODO: if we want to inject viewmodels into our activities, this needs to be annotated with Inject
    @Inject
    public AuthViewModel(AuthAPI authAPI) {
        this.authAPI = authAPI;
        Log.d(TAG, "AuthViewModel: viewmodel is working!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        if(this.authAPI == null){
            Log.d(TAG, "AuthViewModel: auth api is NULL.");
        } else {
            Log.d(TAG, "AuthViewModel: auth api is not NULL.");
        }
    }
}
