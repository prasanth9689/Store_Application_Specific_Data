package com.skyblue.storeapplicationspecificdata;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //https://developers.google.com/drive/api/guides/appdata
    public static final String APP_DATA = "/.Skyblue";
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create folder
            File dir = getExternalFilesDir(APP_DATA);
        if(!dir.exists()){
            if (!dir.mkdir()) {
                Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
            }
        }

        // Example store bitmap, to create empty bitmap
        int w = 200, h = 200;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        bmp.eraseColor(Color.RED);
        Canvas canvas = new Canvas(bmp);

        // Convert bitmap to jpg and save
        convertAndSaveImage();

        // get stored image
        String root = getExternalFilesDir("/").getPath() + "/" + ".Skyblue/";
        File file = new File(root, "test_image" + ".jpg");
        Bitmap testBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        String debug = "";
    }

    private void convertAndSaveImage() {
        String dir = null;
        dir = getExternalFilesDir("/").getPath() + "/" + ".Skyblue/";

        Toast.makeText(this, dir, Toast.LENGTH_LONG).show();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        byte[] bitmapdata = bos.toByteArray();

        File file = new File(dir, "test_image" + ".jpg");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}