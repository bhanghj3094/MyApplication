package com.example.q.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EditorActivity extends AppCompatActivity {
    private CropImageView cropImageView;
    private Bitmap cropped;
    private Context mContext = this;
    private String imgName;
    private String imgDirectory;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Log.d("wrong", "opened onCreate ContentView for EditorActivity");

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            imgName = (String) getIntent().getExtras().get("filename");
            imgDirectory = (String) getIntent().getExtras().get("fileDirectory");
            Uri cropImageUri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
            cropImageView = findViewById(R.id.cropImageView);
            cropImageView.setImageUriAsync(cropImageUri);
        }
        Log.d("wrong", "done onCreate on EditorActivity");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
            Uri cropImageUri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
            cropImageView = findViewById(R.id.cropImageView);
            cropImageView.setImageUriAsync(cropImageUri);
        }
    }

    public void onCropImageClick(View view) {
        Log.d("wrong", "cropping process begin");
        cropped = cropImageView.getCroppedImage(500, 500);
        if (cropped != null) {
            Toast.makeText(mContext, "Image Cropped!", Toast.LENGTH_SHORT).show();
            cropImageView.setImageBitmap(cropped);
            Log.d("wrong", "cropped not NULL");
        }
    }

    public void onSaveImageClick(View view) {
        Log.d("wrong", "begin onSaveImageClick");
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/CAMERA");
        if (!myDir.isDirectory()) {
            myDir.mkdirs();
        }
        String fname = "edited-" + imgName;
        Log.d("wrong", "directory name is: " + myDir.toString());
        Log.d("wrong", "file name is: " + fname);
        File file = new File (myDir, fname);
        if (file.exists ()) {
            Toast.makeText(mContext, "file with name " + fname + " already exists!\n", Toast.LENGTH_SHORT).show();
            file.delete ();
        }
        Log.d("wrong", "made file successfully!");
        try {
            FileOutputStream out = new FileOutputStream(file);
            cropped.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(mContext, "Croped File saved successfully!\n => " + fname, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("wrong", e.getMessage());
            e.printStackTrace();
        }
        finish();
    }







}