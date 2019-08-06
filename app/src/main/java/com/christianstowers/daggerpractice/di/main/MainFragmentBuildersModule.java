package com.christianstowers.daggerpractice.di.main;

import com.christianstowers.daggerpractice.ui.main.posts.PostsFragment;
import com.christianstowers.daggerpractice.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    // Fragment Declarations

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();



}
