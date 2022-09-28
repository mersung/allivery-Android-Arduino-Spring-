package com.example.a14c_bluetooth;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finish);

        ImageView login_login_btn = (ImageView) findViewById(R.id.login_login_btn);

        login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterFinishActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}