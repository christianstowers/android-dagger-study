package com.christianstowers.daggerpractice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.SessionManager;
import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;


    @Inject
    public ProfileViewModel(SessionManager sessionManager) {

        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: viewmodel is ready...");
    }

    // leverage sessionmanager to gain access to the authenticated user's information and
    // return live data and observe it inside of ProfileFragment.
    public LiveData<AuthResource<User>> getAuthenticatedUser(){
        return sessionManager.getAuthUser();
    }

}
