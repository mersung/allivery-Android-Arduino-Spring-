package com.example.a14c_bluetooth;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.a14c_bluetooth.client.ServiceGenerator;
import com.example.a14c_bluetooth.dto.LoginDTO;
import com.example.a14c_bluetooth.service.AuthService;
import com.example.a14c_bluetooth.service.TempActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;

    String email, password;

    private AuthService authService;
    private LoginDTO loginDTO;

    EditText ediTextId, ediTextPwd;
    ImageView loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        ImageView naverBtn = (ImageView) findViewById(R.id.login_naver_btn);
        ImageView kakaoBtn = (ImageView) findViewById(R.id.login_kakao_btn);
        ImageView googleBtn = (ImageView) findViewById(R.id.login_google_btn);

        Intent intentMainActivity = new Intent(this, TempActivity.class);

        authService = ServiceGenerator.createService(AuthService.class);

//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });

//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });

        naverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        kakaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
                loginDTO = new LoginDTO(email, password);

                // 데이터 post
                if (authService != null) {

                    authService.postLoginData(loginDTO)
                            .enqueue(new Callback<LoginDTO>() {
                                @Override
                                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                                    LoginDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "onResponse: 성공, 결과\n" + response.body() + "토큰 : " + data.getData().getToken());
                                        intentMainActivity.putExtra("token", data.getData().getToken());
                                        startActivity(intentMainActivity);
                                        Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG).show();
                                    } else {
                                        try {
                                            Log.d(TAG, "onResponse: 실패, response.body(): " + response.body() + ", 응답코드: " + response.code() + ", response.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 로그인 클릭

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }); // 회원가입 클릭
    }

    public void init() {
        ediTextId = findViewById(R.id.login_edit_email);
        ediTextPwd = findViewById(R.id.login_edit_pwd);
        loginBtn = findViewById(R.id.login_login_btn);
        registerBtn = findViewById(R.id.login_register_btn);
    } // 컴포넌트 인스턴스화

    public void inputData() {
        email = ediTextId.getText().toString();
        password = ediTextPwd.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

        }
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_LONG).show();
        }
    } // 콜백 함수 결과
}