package com.example.internetsearch;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NoprofileFragment extends Fragment {

    Button login_signin;
    Button go_back_search;

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
        View view = inflater.inflate(R.layout.fragment_noprofile, container, false);

        login_signin=view.findViewById(R.id.login_signin);
        go_back_search=view.findViewById(R.id.go_back_search);



        login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_sign_in();
            }
        });
        return view;
    }

     void go_to_sign_in() {
         Intent intent = new Intent(getContext(), signup_page.class);
         startActivity(intent);
     }
}


