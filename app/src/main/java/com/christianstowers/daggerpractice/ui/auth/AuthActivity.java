package com.christianstowers.daggerpractice.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.christianstowers.daggerpractice.R;
import com.christianstowers.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


// ** PRO TIP: holding ctrl and hovering over elements provides an explanation of what it does.
// ** since I'm using the ContributesAndroidInjector annotation, must extend by DaggerAppCompatActivity instead of AppCompatActivity.
public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;


    // Demonstration Injections
//    @Inject
//    String testytest;
//    @Inject
//    Boolean isAppNull;

    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // instantiate viewModel
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        //Demonstration Injection Logs
//        Log.d(TAG, "onCreate: " + testytest);
//        Log.d(TAG, "is app null? " + isAppNull);

        setLogo();
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }





}
