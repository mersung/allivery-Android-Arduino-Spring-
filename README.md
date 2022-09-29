
### Server의 경우 압축파일로 저장해놓았습니다.

## 프로젝트명 
***
    파블로항공 - allivery 기업 프로젝트
####
    파블로항공은 가평지역에서 자사의 드론배송서비스인 
    ALLIVERY를 운영하고 있으며, 프로젝트에선 배송물품을 
    특정 스테이션에 보관할 때 물품을 주문한 사람만
    해당 물품에 접근할 수 있도록 어플리케이션을 만든다.
    사용자마다 가지는 고유 시리얼 넘버를 사용해 
    물품을 주문한 사람만 해당 물품에 접근할 수 있도록
    개발 한다.
#
## 팀원소개
***
    심대성(PM), 천현우, 이지현
#
## 프로젝트 기간
***
    2022.09.04 ~ 2022.09.29
#
## 프로젝트 목적
***
   유저가 갖는 고유의 시리얼 넘버를 사용해 QR 코드 검증을 거쳐 물품을 주문한 사용자가 정상적으로 배송받은 물품    을 가져가는 Android 기반의 어플리케이션 개발
#
## 프로젝트 설명
***
    <<사용자 인증 도어락 시스템>>
    배송 물품을 특정 스테이션에 보관할 때 물품을 주문한 사람만 해당 물품에 접근할 수 있도록 해야 한다.
    
    1) Android 기반의 어플리케이션으로 개발되어야 한다.

    2) 사용자마다 가지고 있는 독립된 시리얼넘버(영문/숫자 40자리 이내)를 DBMS를 통해 관리할 수 있어야 한다.

    3) 어플리케이션에서 QR Code로 만들어진 시리얼 넘버를 인식할 수 있도록 한다.

    4) 인식된 시리얼 넘버가 서버와의 검증을 통해 “등록되어 있으면서 허가된” 사용자인 경우 외부 도어락 장비에 명령을 통해 잠금 장치를 해제한다.
####

### 서비스 구조

## Android Studio
  * UI/UX 화면구성
  * QR체크인(소프트웨어 잠금/해제)
  * 아두이노 블루투스 통신
  
## Spring Boot
  * 회원가입(Security, JWT토큰 인증 사용)
  * 로그인(Security, JWT토큰 인증 사용)
  * QR생성
  * QR인증(USER 시리얼 넘버 인증)

## Arduino
  * 모터 제어(하드웨어 잠금/해제)
  * HC-06(블루투스 모듈)

## Design
  * 피그마
   
#
## 서비스 로직
***
![image](https://user-images.githubusercontent.com/86938974/193068890-13ed745e-218e-4074-b546-cd02c94856fe.png)

#
## QR 로직
***
![image](https://user-images.githubusercontent.com/86938974/193069061-ba87b39a-d4f7-4448-8e94-e05d5d68ab30.png)

#
## ERD
***
![image](https://user-images.githubusercontent.com/86938974/193069139-ade7d75e-3ad5-463a-b0fd-71fa1adb1fa4.png)


#
## UI 설계서
***
[로그인/회원가입 화면]
![image](https://user-images.githubusercontent.com/86938974/193069298-11a109d4-e44b-4be0-9793-7ecec85ec5d9.png)

***
[홈/구매 화면]
![image](https://user-images.githubusercontent.com/86938974/193069370-7475f618-b1da-4020-bfab-4b23823507a0.png)

***
[QR코드/장바구니 화면]
![image](https://user-images.githubusercontent.com/86938974/193069655-75fa973e-aea4-41c3-a67b-9ced1ae95e4d.png)

***
[QR 수령 화면]
![image](https://user-images.githubusercontent.com/86938974/193069692-9fe73409-948e-4c74-a4c3-f3614e2e99d2.png)


#
## 하드웨어(3D 프린터)
***
![image](https://user-images.githubusercontent.com/86938974/193070093-5b1157ac-5964-4981-b4a0-cb02c04b6de5.png)


![image](https://user-images.githubusercontent.com/86938974/193070417-1c28b28c-226e-480f-b35a-84f6c7b4dc64.png)
![image](https://user-images.githubusercontent.com/86938974/193070157-bc787687-a5a4-4941-ab97-27a74f5ea0f7.png)
![image](https://user-images.githubusercontent.com/86938974/193070169-6f0ed00b-6239-4040-8cc0-496640ca98f8.png)

#
## 구현영상
***

https://user-images.githubusercontent.com/86938974/193071304-b25167b2-cdcc-4cfc-81d7-1a5df3b1efc2.mp4






