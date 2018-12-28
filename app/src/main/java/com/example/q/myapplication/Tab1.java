package com.example.q.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Tab1  extends Fragment {
    public TextView outputText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<item> arrayList = new ArrayList<item>();
        arrayList.add(new item("yelin","010-5056-7464"));
        arrayList.add(new item("hj","000-00000000"));
        //Log.d("STATE",String.valueOf(arrayList.size()));
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ContactView);
        recyclerView.setHasFixedSize(true);
        ListAdapter listAdapter = new ListAdapter(arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }
}
