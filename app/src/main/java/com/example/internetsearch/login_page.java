package com.example.internetsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {


    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    EditText email_enter2;
    EditText password2;
    Button login2;
    //TextView Not_for_now2;
    ImageView google_btn2;
    ImageView facebook_btn2;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        login2=findViewById(R.id.login2);
        email_enter2=findViewById(R.id.email_enter2);
        password2=findViewById(R.id.password2);
        //Not_for_now2=findViewById(R.id.Not_for_now2);

        //google sign in
        google_btn2=findViewById(R.id.google_btn2);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);


        mAuth = FirebaseAuth.getInstance();

        login2.setOnClickListener(view ->{
            loginUser();
        });

        google_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        /*Not_for_now2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSearchPage();
            }
        });*/

        //facebook sign in
        facebook_btn2=findViewById(R.id.facebook_btn2);

        facebook_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void loginUser(){
        String email= email_enter2.getText().toString();
        String password= password2.getText().toString();

        if (TextUtils.isEmpty(email)){
            email_enter2.setError("Email cannot be empty");
            email_enter2.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password2.setError("Password cannot be empty");
            password2.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(login_page.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login_page.this,search_page.class));
                    }else{
                        Toast.makeText(login_page.this,"Registration Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    void signIn(){
        Intent signInIntent=gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSearchPage();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSearchPage(){
        finish();
        Intent intent=new Intent(login_page.this,search_page.class);
        startActivity(intent);
    }


    public void go_to_sign_in(View view) {
        Intent intent_go_to_sign_in = new Intent(this, signup_page.class);
        startActivity(intent_go_to_sign_in);
    }
}
