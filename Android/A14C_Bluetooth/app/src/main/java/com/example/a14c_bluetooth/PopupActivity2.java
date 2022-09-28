package com.example.a14c_bluetooth;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PopupActivity2 extends AppCompatActivity {

    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup2);

        ImageView exit_btn = (ImageView) findViewById(R.id.exit_btn);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = getIntent();
                token = getIntent.getStringExtra("token");
                Intent intent = new Intent(PopupActivity2.this, QRSCannerActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
    }
}