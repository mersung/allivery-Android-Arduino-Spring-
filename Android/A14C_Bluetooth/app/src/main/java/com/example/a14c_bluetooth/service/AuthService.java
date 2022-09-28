package com.example.a14c_bluetooth.service;




import com.example.a14c_bluetooth.dto.LoginDTO;
import com.example.a14c_bluetooth.dto.RegisterDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    // /register 등록
    //@Headers("Content-Type: application/json")
    @POST("login/signup")
    Call<RegisterDTO> postRegisterData(@Body RegisterDTO registerDTO);

    // /login 여부
    @POST("/login/")
    Call<LoginDTO> postLoginData(@Body LoginDTO loginDTO);
}
