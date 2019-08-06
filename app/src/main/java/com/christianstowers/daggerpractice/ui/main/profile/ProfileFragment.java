package com.christianstowers.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.christianstowers.daggerpractice.R;
import com.christianstowers.daggerpractice.SessionManager;
import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.ui.auth.AuthResource;
import com.christianstowers.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;
    // widgets for screen
    private TextView email, username, website;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onViewCreated: ProfileFragment was created...");
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);

        subscribeObservers();

    }

    private void subscribeObservers(){
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null){
                    switch (userAuthResource.status){
                        case AUTHENTICATED:{
                            setUserDetails(userAuthResource.data);
                            break;
                        }
                        case ERROR:{
                            setErrorDetails(userAuthResource.message);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        username.setText("error");
        website.setText("error");
    }

    private void setUserDetails(User data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());
    } 


}
