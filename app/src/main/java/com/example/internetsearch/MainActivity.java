package com.example.internetsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView txt_main;
    ImageButton startBtn;
    //FirebaseAuth mAuth;
    private AuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_main=findViewById(R.id.txt_main);
        startBtn=findViewById(R.id.startBtn);
        Animation animation_l_to_r=AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        //Animation animation_r_to_l=AnimationUtils.loadAnimation(this,R.anim.righttoleft);
        txt_main.startAnimation(animation_l_to_r);
        //startBtn.startAnimation(animation_r_to_l);
        //Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();

        authManager = new AuthManager();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direct_user();
            }
        });
    }

    public void direct_user() {
        finish();
        startActivity(new Intent(MainActivity.this, search_page.class));
        /*if (authManager.isLoggedIn()) {
            finish();
            startActivity(new Intent(MainActivity.this, search_page.class));
        } else {
            finish();
            startActivity(new Intent(MainActivity.this, signup_page.class));
        }*/

        /*mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, search_page.class));
        }
        else if (user == null){
            finish();
            startActivity(new Intent(MainActivity.this, signup_page.class));
        }*/
    }
}
