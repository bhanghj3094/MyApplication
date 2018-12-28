package com.example.q.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder>{

    ArrayList<item> contactList;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name_View;
        public TextView num_View;
        public MyViewHolder(View view)
        {
            super(view);
            name_View = (TextView) view.findViewById(R.id.name);
            num_View = (TextView) view.findViewById(R.id.number);
        }
    }

    public ListAdapter(ArrayList<item> contactList)
    {
        this.contactList = contactList;
        Log.d("test point",String.valueOf(contactList.size()));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.name_View.setText(contactList.get(position).getName());
        holder.num_View.setText(contactList.get(position).getNumber());
    }

   @Override
    public int getItemCount()
    {
        return contactList.size();
    }
}
