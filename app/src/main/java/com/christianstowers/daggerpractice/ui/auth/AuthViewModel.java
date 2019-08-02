package com.christianstowers.daggerpractice.ui.auth;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.christianstowers.daggerpractice.SessionManager;
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
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthAPI authAPI, SessionManager sessionManager) {
        this.authAPI = authAPI;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");
    }

    public void authenticateWithId(int userId){
        Log.d(TAG, "authenticateWithId: attempting to log in...");
        sessionManager.authenticateWithId(queryUserId(userId));
    }
    // only responsible for querying the user id. if error, returns error. if not, returns AuthResource authenticated user object.
    private LiveData<AuthResource<User>> queryUserId(int userId){

        // returns flowable User object
        return LiveDataReactiveStreams.fromPublisher(authAPI.getUser(userId)

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
    }

    // on creation of AuthActivity, starts observing the live data via this method
    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }
}