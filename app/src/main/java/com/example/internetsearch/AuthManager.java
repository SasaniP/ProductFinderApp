package com.example.internetsearch;

import com.google.firebase.auth.FirebaseAuth;

public class AuthManager {
    private FirebaseAuth firebaseAuth;

    public AuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    // Additional methods for sign in, sign up, and sign out if required
}

