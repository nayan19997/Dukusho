package com.example.dukusho_nv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.dukusho_nv.view.RepoActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class LogInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        findViewById(R.id.btn_gmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIngmail();
            }
        });


        findViewById(R.id.btn_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInface();
            }
        });
        findViewById(R.id.btn_twiter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIntwitter();
            }
        });
        findViewById(R.id.btn_github).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIngithub();
            }
        });


        comeIn();
    }

    void comeIn(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(LogInActivity.this, RepoActivity.class));
            finish();
        }
    }


    void signIngmail(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GitHubBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build(),

//                                            new AuthUI.IdpConfig.TwitterBuilder().build(),
                                new AuthUI.IdpConfig.FacebookBuilder().build()  ))
                        .build(),

                RC_SIGN_IN);
        //    https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md

    }


    void signInface(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.FacebookBuilder().build()  ))
                        .build(),

                RC_SIGN_IN);
        //    https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md

    }



    void signIntwitter(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                                new AuthUI.IdpConfig.GoogleBuilder().build()))

//                                            new AuthUI.IdpConfig.TwitterBuilder().build()

                                        .build(),

                RC_SIGN_IN);
        //    https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md

    }

    void signIngithub(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GitHubBuilder().build()))
                        .build(),

                RC_SIGN_IN);
        //    https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                comeIn();
            }
        }
    }
}