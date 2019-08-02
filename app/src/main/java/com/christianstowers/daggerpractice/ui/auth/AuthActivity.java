package com.christianstowers.daggerpractice.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.christianstowers.daggerpractice.R;
import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.ui.main.MainActivity;
import com.christianstowers.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


// ** PRO TIP: holding ctrl and hovering over elements provides an explanation of what it does.
// ** since I'm using the ContributesAndroidInjector annotation, must extend by DaggerAppCompatActivity instead of AppCompatActivity.
public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText userId;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    Drawable logo;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);

        // instantiate viewModel
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        //Demonstration Injection Logs
//        Log.d(TAG, "onCreate: " + testytest);
//        Log.d(TAG, "is app null? " + isAppNull);

        setLogo();
        subscribeObservers();
    }

    // on activity creation, starts observing the live data through the public LiveData<User> observeUser() method
    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch(userAuthResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message
                            + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_LONG).show();
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private  void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressBar(boolean isVisible){
        if(isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:{
                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }


}
