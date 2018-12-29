package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;

public class ImagePopup extends FragmentActivity implements View.OnClickListener {
    private Context mContext = null;
    private final int imgWidth = 320;
    private final int imgHeight = 372;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("wrong", "onto onCreate of ImagePopup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepopup);
        mContext = this;

        /* transmitted message */
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String imgPath = extras.getString("filename");

        /* show completed image */
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(imgPath, bfo);
        Bitmap resized = Bitmap.createScaledBitmap(bm, imgWidth, imgHeight, true);
        photoView.setImageBitmap(resized);

        // button for return
        Button btn = (Button)findViewById(R.id.btn_back);
        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);
                Fragment mFragment = null;
                mFragment = new Tab2();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, mFragment).commit();
        }
    }


}
