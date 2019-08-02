package com.christianstowers.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.ui.auth.AuthActivity;
import com.christianstowers.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

// this helps manage the authentication state. the only thing to do from here is to extend
// all other activities by this activity and the authentication state will be managed.
public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    // injected b/c we want to keep track of user authentication, everywhere and at all times.
    // this way, if you log out or something goes wrong, the user will be navigated back to
    // the login screen.
    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    // for observing the authentication state
    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch(userAuthResource.status) {
                        case LOADING: {

                            break;
                        }
                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: " + userAuthResource.message);
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            navLoginScreen();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
