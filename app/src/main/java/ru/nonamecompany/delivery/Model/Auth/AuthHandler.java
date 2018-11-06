package ru.nonamecompany.delivery.Model.Auth;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import ru.nonamecompany.delivery.R;


public class AuthHandler {

    private final int RC_SIGN_IN = 100;

    private static AuthHandler instance;

    private FirebaseAuth firebaseAuth;

    private AuthHandler(){
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public static AuthHandler getInstance(){
        if(instance == null) instance = new AuthHandler();
        return instance;
    }

    public boolean isSigned(){
        return firebaseAuth.getCurrentUser() != null;
    }

    public void authenticate(Fragment activity) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.LoginTheme)
                        .build(),
                RC_SIGN_IN);
    }

    public void  signOut(Context context) {
        signOut(context, null);
    }

    public void signOut(Context context, OnCompleteListener onCompleteListener) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(onCompleteListener);
    }

    public String getUserId(){
        return firebaseAuth.getCurrentUser().getUid();
    }

    public String getUserName() {
        return firebaseAuth.getCurrentUser().getDisplayName();
    }

    public Uri getProfilePic() {
        return firebaseAuth.getCurrentUser().getPhotoUrl();
    }
}
