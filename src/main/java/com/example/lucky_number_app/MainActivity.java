package com.example.lucky_number_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText mNameOfPlayer;
    private Button getLuckyNumber;

    private ImageView diceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameOfPlayer = findViewById(R.id.name_edit_text);
        getLuckyNumber = findViewById(R.id.check_lucky_num);

        diceImage = findViewById(R.id.imageView);
        diceImage.setImageResource(R.drawable.dice);
        getLuckyNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("nameOfUser", mNameOfPlayer.getText().toString());
                intent.putExtra("image",R.drawable.dice);
                startActivity(intent);
            }
        });

        diceImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // Offsets are for centering the TextView on the touch location
                    v.setX(event.getRawX() - v.getWidth() / 2.0f);
                    v.setY(event.getRawY() - v.getHeight() / 2.0f);
                }

                return true;
            }

        });

//        mNameOfPlayer.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    // Offsets are for centering the TextView on the touch location
//                    v.setX(event.getRawX() - v.getWidth() / 2.0f);
//                    v.setY(event.getRawY() - v.getHeight() / 2.0f);
//                }
//
//                return true;
//            }
//        });
    }
}