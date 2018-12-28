package com.example.q.myapplication;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.InputStream;
import java.util.ArrayList;

public class Tab1  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1, container, false);
        // 권한 허가 요청
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            // pass with true;
        } else {
            // request for permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_DENIED)
        {

        }
        else {
            ArrayList<item> arrayList = new ArrayList<item>();
            arrayList = GetList();
            //Log.d("STATE",String.valueOf(arrayList.size()));
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.ContactView);
            recyclerView.setHasFixedSize(true);
            ListAdapter listAdapter = new ListAdapter(arrayList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.scrollToPosition(0);
            recyclerView.setAdapter(listAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        return rootView;
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
                //persons.add(new item(contactCursor.getString(1) , contactCursor.getString(0)));
                Uri photo_uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.valueOf(contactCursor.getString(2)));
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(),photo_uri);
                if(input==null)
                {
                    temp.setPhoto(R.drawable.ic_action_name);
                    Log.d("Don't have photo",contactCursor.getString(1));
                }
                else
                {
                    Log.d("Have photo","aa");
                    //temp.setPhoto(input.);
                }
                //Log.d("photo file",contactCursor.getString(2));
                persons.add(temp);
            }while(contactCursor.moveToNext());
        }

        return persons;
    }
}



