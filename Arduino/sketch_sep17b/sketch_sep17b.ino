#include <SoftwareSerial.h>
#include "Servo.h"

SoftwareSerial bluetooth(2, 3);
Servo myservo;
 
void setup(){
  myservo.attach(12); // 서보 핀 설정
  myservo.write(90);// 초기
  Serial.begin(9600);
  bluetooth.begin(9600);
}
 
void loop(){
  char val = bluetooth.read();
  if (val == 'o') {
    myservo.write(0);
    Serial.write("o");
  }

  if(val == 'b')
  { 
    myservo.write(90);
    Serial.write("b");
  }
  if (Serial.available()) {
    bluetooth.write(Serial.read());
  }
}
