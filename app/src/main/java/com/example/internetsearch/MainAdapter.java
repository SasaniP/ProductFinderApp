package com.example.internetsearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> mContacts;

    public MainAdapter(ArrayList<String> contacts) {
        mContacts = contacts;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.mFullName.setText (mContacts.get(position));
    }
    @Override
    public int getItemCount() {
        return mContacts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFullName;
        //public TextView mClassName;

        public ViewHolder(View itemView) {
            super(itemView);
            mFullName = (TextView) itemView.findViewById(R.id.full_name);
            //mClassName = itemView.findViewById(R.id.class_name);
        }
    }
}

