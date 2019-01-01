package com.example.q.myapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.InputStream;
import java.util.ArrayList;
import android.support.design.widget.Snackbar;
import android.view.WindowManager;
import android.widget.EditText;

public class Tab1  extends Fragment{
    View rootView;
    EditText editText;
    private ArrayList<item> arrayList;
    private ArrayList<item> filteredList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ListAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab1, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_DENIED) {
                arrayList = GetList();
                buildRecyclerView();
                editText = rootView.findViewById(R.id.search);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                       filter(s.toString());
                    }
                });
            }
            FloatingActionButton add = rootView.findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addContact = new Intent(ContactsContract.Intents.Insert.ACTION);
                    addContact.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    startActivity(addContact);
                    Snackbar.make(v, "Add New Contact", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        return rootView;
    }

    @Override
    public void onResume() {
        Log.d("cheeck","on resume tab1");
        super.onResume();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_DENIED) {
            ArrayList<item> arrayList;
            arrayList = GetList();
            RecyclerView recyclerView = rootView.findViewById(R.id.ContactView);
            recyclerView.setHasFixedSize(true);
            ListAdapter listAdapter = new ListAdapter(arrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.scrollToPosition(0);
            recyclerView.setAdapter(listAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    private ArrayList<item> GetList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        };

        String[] selectionArgs = null;

        //정렬
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        //조회해서 가져온다
        Cursor contactCursor = getContext().getContentResolver().query(uri,projection,null,selectionArgs,sortOrder);

        //정보를 담을 array 설정
        ArrayList <item> persons = new ArrayList();

        if(contactCursor.moveToFirst()){
            do{
                item temp = new item(contactCursor.getString(1) , contactCursor.getString(0));
                Uri photo_uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactCursor.getLong(2));
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(),photo_uri);

                if(input==null)
                {
                    temp.setPhoto(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_action_name));
                    Log.d("Don't have photo",contactCursor.getString(1));
                }
                else
                {
                   temp.setPhoto(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(input), 100, 100, true));
                }
                persons.add(temp);
            }while(contactCursor.moveToNext());
        }
        return persons;
    }


    private void filter(String text)
    {
       filteredList = new ArrayList<>();
        for(item i : arrayList)
        {
            if(i.getName().toLowerCase().contains(text.toLowerCase()) || i.getNumber().toLowerCase().contains(text.toLowerCase()) || check(i.getNumber()).contains(check(text.toLowerCase())))
            {
                filteredList.add(i);
            }
        }
        listAdapter.filterList(filteredList);
       recyclerView.setAdapter(listAdapter);
    }

    private void buildRecyclerView()
    {
        recyclerView = rootView.findViewById(R.id.ContactView);
        layoutManager = new LinearLayoutManager(getActivity());
        listAdapter = new ListAdapter(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.setAdapter(listAdapter);
       recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String check(String s)
    {
        s = s.toLowerCase();
        if(s.contains("-"))
        {
           s = s.replaceAll("-","");
        }
        Log.d("checkk",s);
        return s;
    }
}

