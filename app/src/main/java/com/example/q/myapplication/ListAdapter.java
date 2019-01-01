package com.example.q.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    ArrayList<item> contactList;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name_View;
        public TextView num_View;
        public ImageView photo_view;
        public MyViewHolder(View view)
        {
            super(view);
            name_View = view.findViewById(R.id.name);
            num_View =  view.findViewById(R.id.number);
            photo_view =  view.findViewById(R.id.photo);
        }
    }

    public ListAdapter(ArrayList<item> contactList)
    {
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.name_View.setText(contactList.get(position).getName());
        holder.num_View.setText(contactList.get(position).getNumber());
        holder.photo_view.setImageBitmap(contactList.get(position).getPhoto());
    }

   @Override
    public int getItemCount()
    {
        return contactList.size();
    }

    public void filterList(ArrayList<item> filteredList) {
        contactList = filteredList;
        notifyDataSetChanged();
    }
}
