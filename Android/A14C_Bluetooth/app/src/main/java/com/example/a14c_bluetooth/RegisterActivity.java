package com.example.a14c_bluetooth;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView register_go_to_login_btn = (ImageView) findViewById(R.id.register_go_to_login_btn);
        ImageView register_finish_btn = (ImageView) findViewById(R.id.register_finish_btn);
        EditText register_edt_name = (EditText) findViewById(R.id.register_edt_name);
        EditText register_edt_email = (EditText) findViewById(R.id.register_edt_email);
        EditText register_edt_pwd = (EditText) findViewById(R.id.register_edt_pwd);

        // move to main
        Intent moveRegisterFinishActivity = new Intent(this, RegisterFinishActivity.class);

        // 뒤로가기 버튼
        register_go_to_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterFinishActivity.class);
                startActivity(intent);
            }
        });
    }
}