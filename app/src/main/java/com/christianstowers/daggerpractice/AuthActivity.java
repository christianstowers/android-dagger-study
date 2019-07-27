package com.christianstowers.daggerpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


// ** PRO TIP: holding ctrl and hovering over elements provides an explanation of what it does.
// ** since I'm using the ContributesAndroidInjector annotation, must extend by DaggerAppCompatActivity instead of AppCompatActivity.
public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";
    
    @Inject
    String testytest;
    @Inject
    Boolean isAppNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Log.d(TAG, "onCreate: " + testytest);
        Log.d(TAG, "is app null? " + isAppNull);
    }
}
