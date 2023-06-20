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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

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
    TextView txt_userName,txt_userEmail, searched_keys;
    TextView search_his, cart_text;
    ShapeableImageView profile_picture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txt_userName=view.findViewById(R.id.txt_userName);
        txt_userEmail=view.findViewById(R.id.txt_userEmail);
        searched_keys=view.findViewById(R.id.searched_keys);
        profile_picture=view.findViewById(R.id.profile_picture);
        signout_Btn=view.findViewById(R.id.signout_btn);
        search_his=view.findViewById(R.id.search_his);
        cart_text=view.findViewById(R.id.cart_text);

        getParentFragmentManager().setFragmentResultListener("dataFromHome", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data = result.getString("df1");
                searched_keys.setText(data);
            }
        });

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc=GoogleSignIn.getClient(getActivity(),gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct!=null){
            String personName= acct.getDisplayName();
            String personEmail= acct.getEmail();
            String profilePicture= acct.getPhotoUrl().toString();

            txt_userName.setText(personName);
            txt_userEmail.setText(personEmail);
            signout_Btn.setText("SIGN OUT");
            Glide.with(getActivity()).load(profilePicture).into(profile_picture);

        }


        signout_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                signOut();
            }
        });
        search_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { go_to_history();
            }
        });
        cart_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_cart();
            }
        });
        return view;
    }



    //------------------------------------------- Sign Out -----------------------------------------
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
    void signIn(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), login_page.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Sign In", Toast.LENGTH_LONG).show();

            }
        });
    }

    void go_to_history(){
        getActivity().finish();
        Intent intent = new Intent(getContext(), search_history_page.class);
        startActivity(intent);
    }

    void go_to_cart(){
        getActivity().finish();
        Intent intent = new Intent(getContext(), cart_page.class);
        startActivity(intent);
    }
}