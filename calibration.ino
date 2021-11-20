#include "MQ135.h"
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

}

void loop() {
  // put your main code here, to run repeatedly:
  MQ135 gas = MQ135(A0); // Attach sensor to pin A0
  float rzero = gas.getRZero();
  Serial.println (rzero);
  delay(1000);
}
