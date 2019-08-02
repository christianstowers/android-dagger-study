package com.christianstowers.daggerpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;


//TODO: SESSION MANAGER: THE SINGLE SOURCE OF TRUTH

// this will take over management of the authenticated user, which was originally
// handled by AuthViewModel:  private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
// also, all of the setting of the authenticated user will be handled here as well.
@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    // using MediatorLiveData instead of saving this as a user object (private User user = ...)
    // so I can observe the authenticated user from any class that I inject the session manager into.
    // Then if the user logs out, I can react to that change immediately. It's not just holding a
    // raw object but an observable object, so that way if it is injected into any activity etc.
    // I can react to those changes.
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source){
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((User)null));
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        } else {
            Log.d(TAG, "authenticateWithId: previous session detected. Retrieving user from cache.");
        }
    }

    // resets the cachedUser value. basically removes the cached user object from the mediator live data
    public void logOut(){
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<User>logout());
    }

    // live data object for observing
    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUser;
    }

}
