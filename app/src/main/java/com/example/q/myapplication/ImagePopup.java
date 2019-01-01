package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.Arrays;

public class ImagePopup extends FragmentActivity implements View.OnClickListener {
    private Context mContext = null;
    private final int imgWidth = 320;
    private final int imgHeight = 372;
    String imgName = null;
    String imgPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("wrong", "onto onCreate of ImagePopup");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepopup);
        mContext = this;

        /* transmitted message */
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        imgPath = extras.getString("filepath");
        imgName = extras.getString("filename");

        /* show completed image */
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(imgPath, bfo);
        Bitmap resized = Bitmap.createScaledBitmap(bm, imgWidth, imgHeight, true);
        photoView.setImageBitmap(resized);

        // button for return
        Button btn = findViewById(R.id.btn_back);
        btn.setOnClickListener(this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("wrong", "onClick of setOnClickListener");
                shareImage(); //공유 이미지 함수를 호출 합니다.
            }

            private void shareImage() {
                Log.d("wrong", "begin shareImage");
                String[] parseDirectory = imgPath.split("/");
                String directory = TextUtils.join("/", Arrays.copyOfRange(parseDirectory, 0, parseDirectory.length - 1));
                Log.d("wrong", "directory: " + directory);
                Log.d("wrong", "image name: " + imgName);

                File file = new File(directory, imgName); // 파일 경로 설정 + imgName 은 파일 이름
                Uri uri = FileProvider.getUriForFile(mContext, "com.bignerdranch.android.test.fileprovider", file);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpg"); // set jpg type
                intent.putExtra(Intent.EXTRA_STREAM, uri); // put img w/ uri
                startActivity(Intent.createChooser(intent, "Choose")); // bring up sharing activity
            }
        });
        Log.d("wrong", "successful in ImagePopup onCreate");
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:
                finish();
        }
    }


}
