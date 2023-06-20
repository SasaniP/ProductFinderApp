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

public class search_history_page extends AppCompatActivity {

    TextView textview;
    Button button;

    RecyclerView recyclerView;
    ArrayList<String> name;
    DBHepler db;
    androidx.recyclerview.widget.RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history_page);

        textview = findViewById(R.id.text_my_shist);
        button = findViewById(R.id.button);

        /*
        db=new DBHepler(this);
        name=new ArrayList<>();
        recyclerView=findViewById(R.id.history_recycler_view);
        adapter = new RecyclerView.Adapter(this,name) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        }
        adapter= new RecyclerView.Adapter(this,name);
        recyclerView.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData(); */


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_home();
            }
        });

    }

    private void displayData() {
        Cursor cursor=db.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No Data Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                name.add(cursor.getString(0));
            }
        }
    }

    void go_to_home(){
        finish();
        Intent intent=new Intent(search_history_page.this,search_page.class);
        startActivity(intent);
    }
}
