package com.example.myapplication4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class BeforeRemove extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java3");
    }


    public final static String IMAGE_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/temp/Image.jpg";

    SelectEdge img = new SelectEdge();
    Bitmap processImage;
    File file;


    ImageView imageView;
    Button remove;

    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_remove);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Removing a Shadow..");


        imageView = (ImageView)findViewById(R.id.imageView4);
        remove = (Button) findViewById(R.id.remove);

        processImage = img.getResult();

        imageView.setImageBitmap(processImage);

        SaveImage(processImage);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                new BackgroundJob().execute();
            }
        });

    }

    private void SaveImage (Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/temp");
        myDir.mkdirs();
        String fname = "Image.jpg";
        file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearTempImages() {
        try {
            File tempFolder = new File(IMAGE_PATH);
            for (File f : tempFolder.listFiles())
                f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public native void removeShadow (String path);

   private class BackgroundJob extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids){
            progressDialog.setCancelable(false);
            NativeClass nativemethod = new NativeClass();
            nativemethod.removeShadow(IMAGE_PATH);

            return null;
        }
        protected void onPostExecute(Void avoid){
            progressDialog.cancel();
            clearTempImages();
            Intent resultPage = new Intent(BeforeRemove.this, Result.class);
            startActivity(resultPage);

        }
    }
}
