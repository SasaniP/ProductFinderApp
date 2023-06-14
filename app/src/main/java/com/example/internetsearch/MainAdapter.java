package com.example.internetsearch;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> mContacts;
    ArrayList<String> mClasses;
    ArrayList<Bitmap> mIcons;

    public MainAdapter(ArrayList<String> contacts, ArrayList<String> classes, ArrayList<Bitmap> private_iconList) {

        mContacts = contacts;
        mClasses = classes;
        mIcons = private_iconList;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.mFullName.setText (mContacts.get(position));
        holder.mClassName.setText (mClasses.get(position));
        holder.mIcon.setImageBitmap(mIcons.get(position));
    }
    @Override
    public int getItemCount() {
        return mContacts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFullName;
        public TextView mClassName;
        public ImageView mIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mFullName = (TextView) itemView.findViewById(R.id.full_name);
            mClassName = itemView.findViewById(R.id.class_name);
            mIcon = itemView.findViewById(R.id.image_icon);
        }
    }
}

