package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spenditure.R;
import com.spenditure.presentation.transaction.CreateTransactionActivity;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        setUpConfirmButton();

        Intent data = getIntent();
        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            ImageView imageView = (ImageView) findViewById(R.id.imageview_image);
            imageView.setImageBitmap(bitmap);
            imageView.setRotation(270);
        } else {
            Toast.makeText(ImageViewActivity.this, "Error retrieving image.", Toast.LENGTH_LONG).show();
        }
    }

    private void setUpConfirmButton(){
        Button button = (Button) findViewById(R.id.button_back);

        button.setOnClickListener(view -> {
            finish();
        });
    }
}