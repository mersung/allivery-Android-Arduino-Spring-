package com.example.a14c_bluetooth;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a14c_bluetooth.client.ServiceGenerator;
import com.example.a14c_bluetooth.dto.LoginDTO;
import com.example.a14c_bluetooth.dto.QRIdDTO;
import com.example.a14c_bluetooth.service.PurchaseService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends AppCompatActivity {

    PurchaseService purchaseService;

    String token, queryValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);
        EditText queryValueText = findViewById(R.id.queryValueText);

        purchaseService = ServiceGenerator.createService(PurchaseService.class, token);

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QrActivity.this, ProductGiveActivity1.class);
                startActivity(intent);

                queryValue = queryValueText.getText().toString();

                if (purchaseService != null) {
                    purchaseService.getQR(queryValue)
                            .enqueue(new Callback<QRIdDTO>() {
                                @Override
                                public void onResponse(Call<QRIdDTO> call, Response<QRIdDTO> response) {
                                    QRIdDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "onResponse: 성공, 결과\n" + response.body());
                                        startActivity(intent);
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
                                public void onFailure(Call<QRIdDTO> call, Throwable t) {
                                    Log.d(TAG, "onFailure: " + t.toString());
                                }
                            });
                }
            }
        });
    }
}