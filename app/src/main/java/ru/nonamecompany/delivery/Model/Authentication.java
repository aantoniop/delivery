package ru.nonamecompany.delivery.Model;

import com.google.firebase.auth.FirebaseAuth;

class Authentication {

    private static final Authentication ourInstance = new Authentication();

    static Authentication getInstance() {
        return ourInstance;
    }

    private final int RC_SIGN_IN = 100;

    private FirebaseAuth auth;

    private Authentication() {
        auth = FirebaseAuth.getInstance();
    }

    public boolean isSigned() {
        return auth.getCurrentUser() != null;
    }
}
