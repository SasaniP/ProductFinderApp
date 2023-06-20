package com.example.internetsearch;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList name_id;

    public MyHistoryAdapter(Context context, ArrayList name_id) {
        this.context = context;
        this.name_id = name_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.userdata,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id=itemView.findViewById(R.id.name_text);
        }
    }
}

