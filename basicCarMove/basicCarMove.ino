#include <Stepper.h>
#include <Servo.h>
#include <SoftwareSerial.h>
 
SoftwareSerial mySerial(6, 5);
//#define   M_PI   3.14159265358979323846 /* pi */
//#include <math.h>

////////////////////Stepper Stuff/////////////////
//const int stepsPerRev = 512;
//Stepper myStepper(stepsPerRev, 10, 12, 11, 13);

////////////////////Joystick Stuff///////////////
/*const int x = 1;
const int y = 0;
const int key = 8;*/


///////////////////Servo Stuff/////////////////
//Servo myServo;
//int angle = 90;


//command input(bt)
char data;
void setup() {
  // put your setup code here, to run once:
  //myServo.attach(13); 
  //myStepper.setSpeed(60);
  Serial.begin(9600);
 //pinMode(LED_BUILTIN,OUTPUT);
  //right wheel
   pinMode(9, OUTPUT);
   pinMode(10, OUTPUT);

  //left wheel
  pinMode(11, OUTPUT);
  pinMode(12, OUTPUT);
  
}

void loop() {
  // put your main code here, to run repeatedly:

//read data from serial
if(Serial.available()>0){
  data = Serial.read();
  Serial.print(data);
 }

//if up button
if(data == '0'){
  //move car forward
  //myStepper.step(5);
  //digitalWrite(LED_BUILTIN, HIGH);
   digitalWrite(9, LOW);
   digitalWrite(10, HIGH);
   digitalWrite(11, LOW);
   digitalWrite(12, HIGH);
   //delay(50);
   

}


//if down button
if(data == '1'){
  //myStepper.step(-5);
  //digitalWrite(LED_BUILTIN, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(11, HIGH);
   digitalWrite(12, LOW);
  //delay(50);
}


//if left button
if(data == '2'){
  /*if(angle!= 0){
    myServo.write(angle - 20);
    //delay(1000);
  } */

  //right wheel
    digitalWrite(9, HIGH);
  digitalWrite(10, LOW);
  //left wheel
  digitalWrite(11, LOW);
   digitalWrite(12, HIGH);
  
  
}


//if right button 
if(data == '3'){
    /*if(angle!= 180){
    myServo.write(angle + 20);
    //delay(1000);
  }  */

    //right wheel
    digitalWrite(9, LOW);
  digitalWrite(10, HIGH);
  //left wheel
  digitalWrite(11, HIGH);
   digitalWrite(12, LOW);
}

//stop moving
if(data == 'z'){
  digitalWrite(9, LOW);
  digitalWrite(10, LOW);
  digitalWrite(11, LOW);
  digitalWrite(12, LOW);

  
  }

//stop turning
if(data == 'y'){
//  myServo.write(angle);
  }


}//end loop
