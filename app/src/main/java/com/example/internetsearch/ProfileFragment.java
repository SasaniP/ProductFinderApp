package com.example.internetsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button signout_Btn;
    TextView txt_userName,txt_userEmail;
    ShapeableImageView profile_picture;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txt_userName=view.findViewById(R.id.txt_userName);
        txt_userEmail=view.findViewById(R.id.txt_userEmail);
        profile_picture=view.findViewById(R.id.profile_picture);
        signout_Btn=view.findViewById(R.id.signout_btn);

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc=GoogleSignIn.getClient(getActivity(),gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct!=null){
            String personName= acct.getDisplayName();
            String personEmail= acct.getEmail();
            String profilePicture= acct.getPhotoUrl().toString();

            txt_userName.setText(personName);
            txt_userEmail.setText(personEmail);
            Glide.with(getActivity()).load(profilePicture).into(profile_picture);


        }

        signout_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                signOut();
            }
        });

        return view;
    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), login_page.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Signed out", Toast.LENGTH_LONG).show();

            }
        });
    }
}