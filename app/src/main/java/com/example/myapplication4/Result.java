package com.example.myapplication4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Result extends AppCompatActivity {

    ImageView imgResult;
    Bitmap myBit;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imgResult = (ImageView)findViewById(R.id.imageView2);

        myBit = BitmapFactory.decodeFile("/storage/emulated/0/Output/_Out.png");
        imgResult.setImageBitmap(myBit);
        Toast toast = makeText(getApplicationContext(),"Shadow has been Removed!",Toast.LENGTH_LONG);
        toast.show();

    }


    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_Save) {
            SaveImage(myBit);
            Toast.makeText(getApplicationContext(), "Saved !!!", LENGTH_LONG).show();
        }
        else if (id == R.id.menu_item_Share){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("/storage/emulated/0/Output/_Out.png"));
            //share.putExtra(Intent.EXTRA_STREAM, Uri.parse("/Internal storage/DCIM/_Out.png"));
            startActivity(Intent.createChooser(share,"Share via"));
            Toast.makeText(getApplicationContext(), "Shared !!!", LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
