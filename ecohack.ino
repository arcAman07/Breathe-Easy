#include "MQ135.h"             //library with calibrated value of R0
#include <Servo.h>
#include <ESP8266WiFi.h>        

#include <FirebaseArduino.h>  


  
#define FIREBASE_HOST ""  
#define FIREBASE_AUTH ""  
#define WIFI_SSID ""  
#define WIFI_PASSWORD ""  

#define sensorPin A0               //we read sensor values using A0 pin
int air_quality;
String string_quality;

Servo s1;
Servo s2;



void setup() {
 
 
 
  Serial.begin(115200);
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  pinMode(sensorPin, INPUT);
          
  s1.attach(D3);
  s2.attach(D4);
  
  s1.write(0);
  delay(100);
  
  s2.write(0);
  delay(100);
}

void loop() {

  MQ135 gasSensor = MQ135(A0);
  
  air_quality = gasSensor.getPPM();
  
  string_quality=quality(air_quality);
  
  Firebase.setFloat("AQI", air_quality);
  Firebase.setString("Quality", string_quality);

  
  if (Firebase.failed()) {  
    Serial.print("setting /number failed:");  
    Serial.println(Firebase.error());     
   } 
    
  mask(air_quality);
  
  delay(1500);
    
      
}

String quality(int num){
  switch(num){
    case 0 ... 50:
      return "Good";
    case 51 ... 100:
      return "Moderate";
    case 101 ... 150:
      return "Slightly Unhealthy";
    case 151 ... 200:
      return "Unhealthy";
    case 201 ... 300:
      return "Very Unhealthy";
    case 301 ... 500:
      return "Hazardous";
  }
  
}

void mask(int qual){
  if (qual>150){
    for(int i=0;i<=180;i++){
      s1.write(i);
      delay(100);
    }
    for(int i=0;i<=180;i++){
      s2.write(i);
      delay(100);
    }
  }
  else{
    s1.write(0);
    delay(100);
    s2.write(0);
    delay(100);
  }
}
