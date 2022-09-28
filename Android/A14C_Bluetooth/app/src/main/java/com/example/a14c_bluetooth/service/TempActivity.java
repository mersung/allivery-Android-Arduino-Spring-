package com.example.a14c_bluetooth.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a14c_bluetooth.PopupActivity1;
import com.example.a14c_bluetooth.PopupActivity2;
import com.example.a14c_bluetooth.ProductGiveActivity1;
import com.example.a14c_bluetooth.ProductGiveActivity2;
import com.example.a14c_bluetooth.R;

public class TempActivity extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        ImageView imageView5 = (ImageView) findViewById(R.id.imageView5);

        Intent get = getIntent();
        token = get.getStringExtra("token");

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempActivity.this, PopupActivity1.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });
    }


}