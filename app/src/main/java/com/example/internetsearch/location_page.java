package com.example.internetsearch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class location_page extends MainActivity {

    TextView textView;
    ArrayList<String> arrayList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_page);

        //Assign variable
        textView = findViewById(R.id.text_view);

        //Initialize array list
        arrayList = new ArrayList<>();
        arrayList.add("Akkaraipattu");
        arrayList.add("Ampara");
        arrayList.add("Anuradhapura");
        arrayList.add("Badulla");
        arrayList.add("Balangoda");
        arrayList.add("Bandarawela");
        arrayList.add("Batticaloa");
        arrayList.add("Chavakachcheri");
        arrayList.add("Chilaw");
        arrayList.add("Colombo");
        arrayList.add("Dambulla");
        arrayList.add("Dehiwela-Mount Lavinia");
        arrayList.add("Embilipitiya");
        arrayList.add("Eravur");
        arrayList.add("Galle");
        arrayList.add("Gampaha");
        arrayList.add("Hambantota");
        arrayList.add("Happutalle");
        arrayList.add("Homagama");
        arrayList.add("Jaffna");
        arrayList.add("Kalmunai");
        arrayList.add("Kalutata");
        arrayList.add("Kandy");
        arrayList.add("Kattankudy");
        arrayList.add("Kegalle");
        arrayList.add("Kinniya");
        arrayList.add("Kuliyapitiya");
        arrayList.add("Kurunegala");
        arrayList.add("Mannar");
        arrayList.add("Matale");
        arrayList.add("Matara");
        arrayList.add("Negombo");
        arrayList.add("Nuwara Eliya");
        arrayList.add("Point Pedro");
        arrayList.add("Polonnaruwa");
        arrayList.add("Ratnapura");
        arrayList.add("Sri Jayewardenepura Kotte");
        arrayList.add("Trincomalee");
        arrayList.add("Valvettithurai");
        arrayList.add("Vavuniya");


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize dialog
                dialog = new Dialog(location_page.this);
                //Set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //Set custom height and width
                dialog.getWindow().setLayout( 650, 800);
                //Set transparent background
                dialog.getWindow().setBackgroundDrawable (new ColorDrawable(Color.TRANSPARENT));
                //Show dialog
                dialog.show();
                //Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                //Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>( location_page.this
                        ,android.R.layout.simple_list_item_1, arrayList);
                //Set adapter
                listView.setAdapter (adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        //Filter array List
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                        textView.setText(adapter.getItem(i));
                        dialog.dismiss();
                    }
                });

            }
            });


    }



}

