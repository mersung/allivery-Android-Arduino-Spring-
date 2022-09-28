package com.example.a14c_bluetooth;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.a14c_bluetooth.client.ServiceGenerator;
import com.example.a14c_bluetooth.dto.QRIdDTO;
import com.example.a14c_bluetooth.service.PurchaseService;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRSCannerActivity extends AppCompatActivity {

    String token, queryValue;

    PurchaseService purchaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, "PopupActivity1 토큰 : " + token);

        purchaseService = ServiceGenerator.createService(PurchaseService.class, token);

        // qr 스캔
        new IntentIntegrator(this).setOrientationLocked(false).initiateScan();
    }

    // qr 결과값
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                // Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(QRSCannerActivity.this, MainActivity.class);

                queryValue = result.getContents();

                if (purchaseService != null) {
                    purchaseService.getQR(queryValue)
                            .enqueue(new Callback<QRIdDTO>() {
                                @Override
                                public void onResponse(Call<QRIdDTO> call, Response<QRIdDTO> response) {
                                    QRIdDTO data = response.body();
                                    if (response.isSuccessful() == true) {
                                        Log.d(TAG, "onResponse: 성공, 결과\n" + data);
                                        String code = data.getCode();
                                        intent.putExtra("code",code);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), data.getCode(), Toast.LENGTH_LONG).show();
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
                    // todo
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}