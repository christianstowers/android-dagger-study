package com.christianstowers.daggerpractice.ui.auth;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.christianstowers.daggerpractice.models.User;
import com.christianstowers.daggerpractice.network.auth.AuthAPI;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    // inject
    private final AuthAPI authAPI;

    //TODO: watch tuts on REST API / Live Data stuff: REST API with MVVM and Retrofit2; Local Database Cache with REST API
    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthAPI authAPI) {
        this.authAPI = authAPI;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");
    }

    public void authenticateWithId(int userId){
        authUser.setValue(AuthResource.loading((User)null));

        // responsible for doing the api call, returns a live data object
        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(

                // returns flowable User object
                authAPI.getUser(userId)
                        //instead of calling onError (error happens)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        // either receives errorUser if there was an error or returns the User object
                        // from the API and then applies a function to it using the map operator.
                        // If the user id is -1 or an error, returns an AuthResource object of type
                        // error, making it basically the error case for the AuthResource class.
                        // If no error, returns an AuthResource object with an authenticated status,
                        // basically meaning everything worked, so then .addSource is hit, attaching
                        // the observer, setting the authUser value, and the observers are updated in
                        // the UI.
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1){
                                    return AuthResource.error("Could not authenticate", (User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })

                        .subscribeOn(Schedulers.io()));

        // returns the data using an RXJava call, converts to live data, sets to mediator live data, and updates observers using the setValue method
        authUser.addSource(source, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> user) {
                // if the user is authenticated successfully, the value of that user will get set to the authUser mediator live data object,
                // the observers will be immediately updated and the onChanged method will print out that user.
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }

    // on creation of AuthActivity, starts observing the live data via this method
    public LiveData<AuthResource<User>> observeUser(){
        return authUser;
    }
}