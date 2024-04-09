package com.example.lucky_number_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    TextView text ;
    TextView randomno;
    Button share;

    ImageView image;
    int random;

    String username;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        text = findViewById(R.id.textView2);
        randomno = findViewById(R.id.random_number);
        share = findViewById(R.id.share_button);
        image = (ImageView) findViewById(R.id.imageview2);
        Bundle i = getIntent().getExtras();
        if(i!=null) {
            username = i.getString("nameOfUser");
            image.setImageResource(i.getInt("image"));
        }

        //Random no. generator
        random = generateRandomNumber();
        randomno.setText(""+random);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        bitmap = bitmapDrawable.getBitmap();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(username,random,image);
            }
        });
    }

    private void shareData(String username, int random,ImageView image) {
        //Implicit Intent
        //ACTION_SEND is predefined constants that represents an action to send data. Typically used to share content with other applications or components on the device.
        //Its part of Android systems intent mechanism, which enables communication b/w different components of an app..
        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");  // sharing data of type string and its a plain text.
        intent.setType("image/jpeg");  // sharing image.
//        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"prakharyadav0302@gmail.com"}); // sending data to specific email address .
//        intent.putExtra(Intent.EXTRA_SUBJECT,username + " got lucky today"); // EXTRA_SUB and EXTRA_TEXT are constant used as keys to attach additional data to an intent.
        //These extras are commonly used when you want to pre-populate the subject and text fields when sharing contents like emails or messages.
//        intent.putExtra(Intent.EXTRA_TEXT,"His lucky no. is " + random);

        Uri bmpUri ;
        bmpUri = saveImage(bitmap,getApplicationContext());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM,bmpUri);
        intent.putExtra(Intent.EXTRA_SUBJECT,"New App");
        intent.putExtra(Intent.EXTRA_TEXT,"Your lucky no :"+random);

        startActivity(Intent.createChooser(intent,"Choose a platform"));
        //createChooser() is a utility provided by the intent class that allows you to create a dialog that displays a list of app that can handle a specific intent.
    }

    private int generateRandomNumber(){
        Random random = new Random();
        int upper_limit = 100;

        int getRandomNo = random.nextInt(upper_limit);
        return getRandomNo;
    }

    private Uri saveImage(Bitmap image, Context mcontext){
        File imagefolder = new File(mcontext.getCacheDir(),"images");
        Uri uri = null;
        try{
            imagefolder.mkdirs();
            File file = new File(imagefolder,"shared_images.jpg");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG,90,stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(Objects.requireNonNull(mcontext.getApplicationContext()),
                    BuildConfig.APPLICATION_ID+".provider",file);
        }
        catch (IOException e){
            Log.d("Tag","Exception"+e.getMessage());
        }

        return uri;
    }
}