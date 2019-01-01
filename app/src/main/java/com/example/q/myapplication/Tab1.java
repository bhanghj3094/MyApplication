package com.example.q.myapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.InputStream;
import java.util.ArrayList;
import com.google.android.material.snackbar.Snackbar;

public class Tab1  extends Fragment {
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab1, container, false);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
            Log.d("cheeck","on create tab1 finished");
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
                   temp.setPhoto(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(input), 50, 50, true));
                }
                persons.add(temp);
            }while(contactCursor.moveToNext());
        }
        return persons;
    }

}

