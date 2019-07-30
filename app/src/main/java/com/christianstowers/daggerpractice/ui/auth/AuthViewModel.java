package com.christianstowers.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.network.auth.AuthAPI;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthAPI authAPI;

    //TODO: if we want to inject viewmodels into our activities, this needs to be annotated with Inject
    @Inject
    public AuthViewModel(AuthAPI authAPI) {
        this.authAPI = authAPI;
//        Log.d(TAG, "AuthViewModel: viewmodel is working!!! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        authAPI.getUser(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getUsername() + " " + user.getEmail() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
