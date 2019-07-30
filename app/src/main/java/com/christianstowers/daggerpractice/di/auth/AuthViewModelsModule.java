package com.christianstowers.daggerpractice.di.auth;


import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.di.ViewModelKey;
import com.christianstowers.daggerpractice.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

//** for the dependencies of the AuthViewModel
@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);

}
