package com.example.a14c_bluetooth.dto;

import com.google.gson.annotations.SerializedName;

// DTO 모델 - PostResult Class 선언
public class RegisterDTO {
    private String name;
    private String email;
    private String pw;
    /*private String gender;
    private int height;
    private int weight;
    private int age;*/

    public RegisterDTO(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    /*public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }*/



    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "PostResult {" +
                "name = " + name +
                ", email = " + email +
                '}';
    }
}
