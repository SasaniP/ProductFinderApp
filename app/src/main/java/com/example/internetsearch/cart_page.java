package com.example.internetsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import org.w3c.dom.Text;

public class cart_page extends AppCompatActivity {

    TextView cart_textview;
    Button cart_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);

        cart_textview = findViewById(R.id.text_my_cart);
        cart_button = findViewById(R.id.cart_button);



        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_home();
            }
        });

    }


    void go_to_home(){
        finish();
        Intent intent=new Intent(cart_page.this,search_page.class);
        startActivity(intent);
    }
}

