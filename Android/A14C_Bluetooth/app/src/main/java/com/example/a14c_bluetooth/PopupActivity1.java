package com.example.a14c_bluetooth;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a14c_bluetooth.client.ServiceGenerator;
import com.example.a14c_bluetooth.dto.LoginDTO;
import com.example.a14c_bluetooth.dto.QRDTO;
import com.example.a14c_bluetooth.service.AuthService;
import com.example.a14c_bluetooth.service.PurchaseService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupActivity1 extends AppCompatActivity {

    private PurchaseService purchaseService;
    private MainActivity2 mainActivity2;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup1);

        ImageView buy_btn = (ImageView) findViewById(R.id.buy_btn);
        ImageView exit_btn = (ImageView) findViewById(R.id.exit_btn);


        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, "PopupActivity1 토큰 : " + token);

        purchaseService = ServiceGenerator.createService(PurchaseService.class, token);

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopupActivity1.this, PopupActivity2.class);
                if (purchaseService != null) {
                    purchaseService.postQR()
                            .enqueue(new Callback<QRDTO>() {
                                @Override
                                public void onResponse(Call<QRDTO> call, Response<QRDTO> response) {
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "onResponse: 성공" + ", \n헤더 :" + response.headers());
                                        Toast.makeText(getApplicationContext(), "true", Toast.LENGTH_LONG).show();
                                        intent.putExtra("token", token);
                                        startActivity(intent);
                                    } else {
                                        try {
                                            Log.d(TAG, "onResponse: 실패 \n response.body(): " +"false" + ", \n응답코드: " + response.code() + ", \nresponse.errorBody(): " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<QRDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        }); // 구매 버튼 클릭
    }

    public String getToken() {
        return this.token;
    }
}
