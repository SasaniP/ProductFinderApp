package com.example.internetsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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


public class signup_page extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    EditText email_enter1;
    EditText password1;
    Button register;
    TextView Not_for_now1;
    ImageView google_btn1;
    ImageView facebook_btn1;


    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        google_btn1=findViewById(R.id.google_btn1);
        Not_for_now1=findViewById(R.id.Not_for_now1);
        facebook_btn1=findViewById(R.id.facebook_btn1);
        register=findViewById(R.id.register);
        email_enter1=findViewById(R.id.email_enter1);
        password1=findViewById(R.id.password1);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view ->{
            createUser();
        });

        google_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Not_for_now1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSearchPage();
            }
        });
    }

    private void createUser(){
        String email= email_enter1.getText().toString();
        String password= password1.getText().toString();

        if (TextUtils.isEmpty(email)){
            email_enter1.setError("Email cannot be empty");
            email_enter1.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            password1.setError("Password cannot be empty");
            password1.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(signup_page.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signup_page.this,login_page.class));
                    }else{
                        Toast.makeText(signup_page.this,"Registered Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(signup_page.this,search_page.class);
        //correct this from location_page ----> to search_page
        startActivity(intent);
    }

    public void go_to_login(View view){
        Intent intent_go_to_login=new Intent(this,login_page.class);
        startActivity(intent_go_to_login);
    }


    /*public void go_to_search_page() {
        finish();
        Intent intent=new Intent(signup_page.this,search_page.class);
        startActivity(intent);
    }*/
}