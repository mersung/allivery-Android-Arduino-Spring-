package com.example.a14c_bluetooth.service;

import com.example.a14c_bluetooth.dto.LoginDTO;
import com.example.a14c_bluetooth.dto.QRDTO;
import com.example.a14c_bluetooth.dto.QRIdDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PurchaseService {

    // /qr 생성
    @POST("/order/")
    Call<QRDTO> postQR();

    @GET("/order/open")
    Call<QRIdDTO> getQR(@Query("qr") String userid);
}
