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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

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
                shareImage(); //공유 이미지 함수를 호출 합니다.
            }

            private void shareImage() {
                File dirName = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Download");  //디렉토리를 지정합니다.
                String filename = imgName; //공유할 이미지 파일 명
                File file = new File(dirName, filename); //image 파일의 경로를 설정합니다.
                Uri mSaveImageUri = Uri.fromFile(file); //file의 경로를 uri로 변경합니다.
                Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                intent.setType("image/jpg"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                intent.putExtra(Intent.EXTRA_STREAM, mSaveImageUri); //사진의 Uri를 가지고 옵니다.
                startActivity(Intent.createChooser(intent, "Choose")); //Activity를 이용하여 호출 합니다.
            }
        });
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_back:
                finish();
        }
    }


}
