package com.christianstowers.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.di.ViewModelKey;
import com.christianstowers.daggerpractice.ui.main.posts.PostsViewModel;
import com.christianstowers.daggerpractice.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    // ViewModel Bindings

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);

}
