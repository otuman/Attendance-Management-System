/*
 * PSFunctions.c
 *
 * Created: 10/12/2015 05:12:10 PM
 *  Author: Otoman
 */ 
//*******************include files *************************

#include <avr/io.h>
#include <stdbool.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include "lcd_headerFile.h"
#include "PSFunctions.h"
#include "usart.h"
#include "PortInit.h"

//*******************end of include files ******************
#define FALSE 0
#define TRUE  1
#define BUFFER_ID01 0x01
#define BUFFER_ID02 0x02

void  Clear_Display();
void  LCD_Display();


unsigned char fingerTapped = FALSE;
unsigned char count        = 0;
unsigned char i            = 0;
unsigned char match        = FALSE;
unsigned char returnedValue[];
char buffer[30];
unsigned char UDR_Value, UDR_ValueFinal;
unsigned char PSRegFigureCommands[12]          = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x03,0x01,0x00,0x05};
unsigned char PSTurnOnPortCommands[13]         = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x04,0x17,0x01,0x00,0x1D};
unsigned char PSVrfPassCommands[16]            = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x07,0x13,0xFF,0xFF,0xFF,0xFF,0x04,0x17};
unsigned char PSGeneCharFileCommands_1[13]     = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x04,0x02,BUFFER_ID01,0x00,0x08};
unsigned char PSGeneCharFileCommands_2[13]     = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x04,0x02,BUFFER_ID02,0x00,0x09};
unsigned char PSCombineTwoCharFileCommands[12] = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x03,0x05,0x00,0x09};
unsigned char PSUpImageCommands[12]            = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x03,0x0A,0x00,0x0E};
unsigned char PSUpCharCommands[13]             = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x04,0x08,BUFFER_ID01,0x00,0x0E};
//unsigned char PSPreciselyMatchTwoCharFileCommands[12]     = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x03,0x03,0x00,0x07};
//unsigned char PSStoreCharFileCommands[15]     = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x06,0x06,0x01,0x00,pageID,0x00,sum};
//unsigned char PSSearchForAFingerB1Commands[14]    = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x08,0x04,BUFFER_ID01,startPage, pageNum, sum};	
//unsigned char PSSearchForAFingerB2Commands[14]    = {0xEF,0x01,0xFF,0xFF,0xFF,0xFF,0x01,0x00,0x08,0x04,BUFFER_ID02,startPage, pageNum, sum};	
	
const char  portIsReady[]   PROGMEM       = "Connection is established";
const char  noReturnedVal[] PROGMEM       = "No returned Value";
const char  tapYourFinger[] PROGMEM       = "Please tap your finger to the sensor";
const char  scanned[]   PROGMEM           = "Scanning .......";
const char  errorMsg[]  PROGMEM           = "Error when receiving package";
const char  errorMsg1[] PROGMEM           = "Fail due to over disorderly fingerprint Image";
const char  errorMsg2[] PROGMEM           = "Fail due to over smallness of fingerprint Image";
const char  errorMsg3[] PROGMEM           = "Fail due to lack of valid primary Image";
const char  rspMsg[]    PROGMEM           = "Finger with ID: ";
const char  rspMsg1[]   PROGMEM           = " was stored";
const char  rspMsg8[]   PROGMEM           = " was found";
const char  rspMsg2[]   PROGMEM           = "Template of two buffer matched";
const char  errorMsg4[] PROGMEM           = "Template do not match";
const char  rspMsg3[]   PROGMEM           = "The ID : ";
const char  rspMsg4[]   PROGMEM           = " is beyond the scope";
const char  errorMsg5[] PROGMEM           = "Error when writing flash";
const char  rspMsg5[]   PROGMEM           = "Found the matching finer";
const char  rspMsg6[]   PROGMEM           = "No matching finger found";
const char  rspMsg7[]   PROGMEM           = "Flash was emptied";
const char  rspMsg9[]   PROGMEM           = "Please enter the ID : ";
const char  rspMsg10[]   PROGMEM          = "There are : ";
const char  rspMsg11[]   PROGMEM          = " valid templates";
const char  rspMsg12[]   PROGMEM          = " Template loaded to buffer ";
const char  errorMsg6[]   PROGMEM         = " Template loaded to buffer ";
const char  errorMsg7[] PROGMEM           = "Fail to receive the following";

PGM_P const string_response_table[] PROGMEM =
{
	portIsReady,
	noReturnedVal,
	tapYourFinger,
	scanned,
	errorMsg,
    errorMsg1,
	errorMsg2,
	errorMsg3,
	rspMsg,
	rspMsg2,
	errorMsg4,
	rspMsg1,
	rspMsg3,
	rspMsg4,
	errorMsg5,
	rspMsg5,
	rspMsg6,
	rspMsg7,
	rspMsg8,
	rspMsg9,
	rspMsg10,
	rspMsg11,
	rspMsg12,
	errorMsg6,
	errorMsg7
};

//The function to turn on the PF 

void PSTurnOnPortCom(){
    
	OpenPortToFP();
   
   for(count=0; count<13; count++){
	   
	   USART_Transmit(PSTurnOnPortCommands[count]);
	 if(count==12){
		    
		  for(count=0; count<12; count++){
			    
			 returnedValue[count] = USART_Receive();
			    
		    }
	
	//OpenPortToPC();
	/*
	int i=0;
	for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
	 }*/
     if(returnedValue[9] == 00){
			    strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[0]))); //Retrieving string from the memory
			    LCD_Display(buffer);
			    delay_ms(100);
			    Clear_Display();
			    
		    }
		    else{
			    strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[1]))); //Retrieving string from the memory
			    LCD_Display(buffer);
			    delay_ms(100);
			    Clear_Display();
		    }

	   }
   }
 		
}
 void PSRegisterFingerOne(){

	OpenPortToFP();
    Clear_Display();
	strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[2]))); //please tap your finger
	LCD_Display(buffer);
	while(fingerTapped == FALSE){

		for(count=0; count<12; count++){
			USART_Transmit(PSRegFigureCommands[count]);
			if(count==11){
				for(count=0; count<12; count++){
				  returnedValue[count] = USART_Receive();
				}
			}
		}

	OpenPortToPC();
    /*
	for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	OpenPortToFP();
	       if(returnedValue[9]==0x00){
				
				PORTC|=(1<<0);
				Clear_Display();
				strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[3]))); //Scanned
				LCD_Display(buffer);
				delay_ms(1000);
				Clear_Display();
			    PSGeneCharFileForBuffer1();
			    	
			}
			else if(returnedValue[9]==0x02){
				fingerTapped=FALSE;
				//Can't detect finger
			}
			else if(returnedValue[9]==0x01){
				fingerTapped = FALSE;
				Clear_Display();
				strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
				LCD_Display(buffer);
				delay_ms(100);
				Clear_Display();
			}
	  
	  if(fingerTapped==TRUE) break;
	 }
	fingerTapped=FALSE;
	
}

void PSRegisterFingerTwo(){
	
	OpenPortToFP();
    Clear_Display();
	strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[2]))); //please tap your finger
	LCD_Display(buffer);
	while(fingerTapped == FALSE){

		for(count=0; count<12; count++){
			USART_Transmit(PSRegFigureCommands[count]);
			if(count==11){
				for(count=0; count<12; count++){
				   
				   returnedValue[count] = USART_Receive();
				}
			}
		}
     OpenPortToPC();

		/*int i=0;
	for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	OpenPortToFP();
	  if(returnedValue[9]==00){
     		PORTC|=(1<<0);
			fingerTapped=TRUE;
            delay_ms(5);
			PSGeneCharFileForBuffer2();
	   }
	    else if(returnedValue[9]==0x02){
			fingerTapped=FALSE;
			
		}
		else if(returnedValue[9]==0x01){
			fingerTapped = FALSE;
			Clear_Display();
			strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
			LCD_Display(buffer);
			delay_ms(100);
			Clear_Display();
		}
		
		if(fingerTapped==TRUE) break;
		
    }
    fingerTapped =FALSE;
   
	
}


void PSGeneCharFileForBuffer1(){
 
        OpenPortToFP();
        int count;
		for(count=0; count<13; count++){
				
			USART_Transmit(PSGeneCharFileCommands_1[count]);
			
			if(count==12){
				 for(count=0; count<12; count++){
				
				 returnedValue[count]=USART_Receive();
			   }
			}
		}
		
	OpenPortToPC();

		/*int i=0;
	for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	 
	OpenPortToFP();
	if(returnedValue[9]==0x00)
		{
			fingerTapped = TRUE;
			PORTC|=(1<<1);
		}
		else if(returnedValue[9]==0x01){
			fingerTapped = FALSE;
			Clear_Display();
			strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
			LCD_Display(buffer);
			delay_ms(1000);
			
		}
		else if(returnedValue[9]==0x06){
			fingerTapped = FALSE;
			Clear_Display();
			strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[5]))); 
			LCD_Display(buffer);
			delay_ms(1000);
			
				
		}
		else if(returnedValue[9]==0x07){
			fingerTapped = FALSE;
			Clear_Display();
			strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[6]))); 
			LCD_Display(buffer);
			delay_ms(1000);
			
				
		}
		else if(returnedValue[9]==0x15){
			fingerTapped = FALSE;
			Clear_Display();
			strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[7]))); 
			LCD_Display(buffer);
			delay_ms(1000);
			Clear_Display();
				
		}
	OpenPortToFP();
}

void PSGeneCharFileForBuffer2(){
	 
	OpenPortToFP();
	 for(count=0; count<13; count++){
		
		USART_Transmit(PSGeneCharFileCommands_2[count]);
		
		if(count==12){
			for(count=0; count<12; count++){
				
				returnedValue[count]=USART_Receive();
			}
		}
	}
	
	OpenPortToPC();

	/*int i=0;
	for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	 
	 OpenPortToFP();
	
	if(returnedValue[9]==0x00)
	{
		fingerTapped = TRUE;
		PORTC|=(1<<1);
	}
	else if(returnedValue[9]==0x01){
		fingerTapped = FALSE;
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		
		
	}
	else if(returnedValue[9]==0x06){
		fingerTapped = FALSE;
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[5]))); 
		LCD_Display(buffer);
		delay_ms(1000);
		
		
	}
	else if(returnedValue[9]==0x07){
		fingerTapped = FALSE;
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[6]))); 
			LCD_Display(buffer);
		delay_ms(1000);
		
		
	}
	else if(returnedValue[9]==0x15){
		fingerTapped = FALSE;
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[7]))); 
		LCD_Display(buffer);
		delay_ms(1000);
		
		
	}
    OpenPortToFP();
	
}
unsigned char PSMatchTwoBuffer(){
	 OpenPortToFP();
  
  	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x03);
	USART_Transmit(0x03);
	USART_Transmit(0x00);
	USART_Transmit(0x07);
	
	
   for(count=0; count<14; count++){
			returnedValue[count]=USART_Receive();
	}

   /* OpenPortToPC();
	for(i=0; i<14; i++){
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	OpenPortToFP();
	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[9])));  //Template of two buffer matched
		LCD_Display(buffer);
		/*unsigned char ID1 = returnedValue[10];
		unsigned char ID2 = returnedValue[11];
		USART_Transmit(ID1);
		USART_Transmit(ID2);*/
		match        = TRUE;
		delay_ms(1000);
		Clear_Display();
		PSCombineTwoCharFile();
		
	}
	else if(returnedValue[9]==0x01){
		match        = FALSE;
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
	}
	else if(returnedValue[9]==0x08){
		match        = FALSE;
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[10]))); //LCD_Display("Template do not match");
		LCD_Display(buffer);                                                
		delay_ms(1000);
		
	}
   
    OpenPortToFP();
	return match; 
}
void PSCombineTwoCharFile(){
	OpenPortToFP();
	int count;
	for(count=0; count<12; count++){
		
		USART_Transmit(PSCombineTwoCharFileCommands[count]);
		
		if(count==11){
			for(count=0; count<12; count++){
				
				returnedValue[count]=USART_Receive();
			}
		}
	}
	if(returnedValue[9]==0x00)
	{
		PORTC|=(1<<1);
	    Clear_Display();                                                       //LCD_Display("Template Combined Successfully");
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[19]))); //LCD_Display("Please enter the ID : ");
		LCD_Display(buffer); 
		OpenPortToPC();                                    
		int ID = USART_Receive();
		itoa(ID, buffer, 10);
		LCD_Display(buffer);
		delay_ms(1000);
		PSStoreCharFile(ID);
	}
	else if(returnedValue[9]==0x01){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		
		
	}
	else if(returnedValue[9]==0x0A){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[5]))); 
	    LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	
	OpenPortToFP();
}
void PSStoreCharFile(int pID){
	
	OpenPortToFP();
	int sum    = 14 + pID;
	
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x06);
	USART_Transmit(0x06);
	USART_Transmit(BUFFER_ID01);
	USART_Transmit(0x00);
	USART_Transmit(pID);
	USART_Transmit(0x00);//C
	USART_Transmit(sum);//C
	
    for(count=0; count<12; count++){
				
		returnedValue[count]=USART_Receive();
    }
	
	
	OpenPortToPC();

	/*for (i=0; i<12; i++){
		
		USART_Transmit(returnedValue[i]);
		delay_ms(2);
		
	}*/
	
	if(returnedValue[9]==0x00)
	{
		PORTC|=(1<<1);
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[8]))); //LCD_Display("The finger with ID : ");
		LCD_Display(buffer);
		LCD_Display(itoa(pID, buffer, 10));
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[11]))); //LCD_Display(" was stored.");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
	}
	else if(returnedValue[9]==0x01){
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x0B){
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[12]))); //The ID : 
		LCD_Display(buffer);
		LCD_Display(pID);
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[13]))); //is beyond the scope
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x18){
		fingerTapped = FALSE;
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[14]))); //LCD_Display("Error when writing flash");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	
	OpenPortToFP();
}
unsigned char PSSearchForAFingerB1(int sPage, int pNum){
	int PSfound;
	OpenPortToFP();
	
	int sum = 14 + sPage + pNum;
			
		USART_Transmit(0xEF);
		USART_Transmit(0x01);
		USART_Transmit(0xFF);
		USART_Transmit(0xFF);
		USART_Transmit(0xFF);
		USART_Transmit(0xFF);
		USART_Transmit(0x01);
		USART_Transmit(0x00);
		USART_Transmit(0x08);
		USART_Transmit(0x04);
		USART_Transmit(BUFFER_ID01);
		USART_Transmit(0x00);
		USART_Transmit(sPage);
		USART_Transmit(0x00);
		USART_Transmit(pNum);
		USART_Transmit(0x00);
		USART_Transmit(sum);

	for(count=0; count<16; count++){
		
		returnedValue[count]=USART_Receive();
	}

	
	
	OpenPortToPC();
    //for (i=0; i<16; i++){
	     
	    // USART_Transmit(returnedValue[i]);
	    // delay_ms(2);
	     
    // }
	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		int ID1 = returnedValue[10];
		int ID2 = returnedValue[11];
		//USART_Transmit(ID1);
		USART_Transmit(ID2);
		strcpy_P(buffer,(PGM_P)pgm_read_word(&(string_response_table[8]))); //LCD_Display("Finger with ID : ");
		LCD_Display(buffer);
		LCD_Display(itoa(ID2,buffer, 10));
		strcpy_P(buffer,(PGM_P)pgm_read_word(&(string_response_table[18]))); //LCD_Display("was found");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		PSfound = TRUE;
	}
	else if(returnedValue[9]==0x01){
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //LCD_Display("Error when receiving package");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
        //PSSearchForAFingerB2(sPage, pNum);
		
	}
	else if(returnedValue[9]==0x09){
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[16]))); //LCD_Display("No matching found b1");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		PSfound = FALSE;
		//PSSearchForAFingerB2(sPage, pNum);  
	}
	OpenPortToFP();
	return PSfound;
}
void PSSearchForAFingerB2(int sPage, int pNum){
	
	OpenPortToFP();
	int sum = 14 + sPage + pNum;
	
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x08);
	USART_Transmit(0x04);
	USART_Transmit(BUFFER_ID02);
	USART_Transmit(0x00);
	USART_Transmit(sPage);
	USART_Transmit(0x00);
	USART_Transmit(pNum);
	USART_Transmit(0x00);
	USART_Transmit(sum);
	
	for(count=0; count<16; count++){
		
		returnedValue[count]=USART_Receive();
	}

    OpenPortToPC();

	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		int ID1 = returnedValue[10];
		int ID2 = returnedValue[11];
		USART_Transmit(ID1);
		USART_Transmit(ID2);
		strcpy_P(buffer,(PGM_P)pgm_read_word(&(string_response_table[8]))); //LCD_Display("Finger with ID : ");
		LCD_Display(buffer);
		LCD_Display(itoa(ID2,buffer, 10));
		strcpy_P(buffer,(PGM_P)pgm_read_word(&(string_response_table[18]))); //LCD_Display("was found");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
	}
	else if(returnedValue[9]==0x01){
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x09){
		
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[16]))); //LCD_Display("No matching found");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	
	OpenPortToFP();
}

void PSEmptyFPLib(){
	
	OpenPortToFP();
	
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x03);
	USART_Transmit(0x0D);
	USART_Transmit(0x00);
	USART_Transmit(0x11);
	
	
	for(count=0; count<12; count++){
		
		returnedValue[count]=USART_Receive();
	}

	
	OpenPortToPC();

	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		unsigned char ID1 = returnedValue[10];
		unsigned char ID2 = returnedValue[11];
		
		USART_Transmit(ID1);
		USART_Transmit(ID2);
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[17]))); //LCD_Display("Flash was emptied");
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
	}
	else if(returnedValue[9]==0x01){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	
	OpenPortToFP();
}
int PSTempleteNum(){
	
	OpenPortToFP();
	int numTemp;
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x03);
	USART_Transmit(0x1D);
	USART_Transmit(0x00);
	USART_Transmit(0x21);
	
	
	for(count=0; count<14; count++){
		
		returnedValue[count]=USART_Receive();
	}

	
	OpenPortToPC();
	
	OpenPortToFP();
	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		unsigned char ID1 = returnedValue[10];
		unsigned char ID2 = returnedValue[11];
		numTemp = ID1 +ID2;
		//USART_Transmit(ID1);
		//USART_Transmit(ID2);
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[20]))); //Error when receiving package
		LCD_Display(buffer);
		LCD_Display(itoa(ID1, buffer, 10));
		LCD_Display(itoa(ID2, buffer, 10));
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[21]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(1000);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x01){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	
	OpenPortToFP();
	
	return numTemp;
}
void PSUpCharFile(){
	
	OpenPortToFP();
	
	for (count = 0; count<13; count++){
		USART_Transmit(PSUpCharCommands[count]);
				if(count==12){
					for(count=0; count<129; count++){
						
						returnedValue[count] = USART_Receive();
						 			
						
					}
					
				OpenPortToPC();
					
				if(returnedValue[9] == 00){
					    for(count=12; count<129; count++){
							
						    USART_Transmit(returnedValue[count]);
							 delay_ms(1);
							
					    }
						strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[0]))); //Retrieving string from the memory
						LCD_Display(buffer);
						delay_ms(100);
						Clear_Display();
						
					}
					else{
						strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[1]))); //Retrieving string from the memory
						LCD_Display(buffer);
						delay_ms(100);
						Clear_Display();
					}
			}
	}
	
	
}

void PSUpImageFile(){
	
	OpenPortToFP();
	for(count=0; count<12; count++){
		
		USART_Transmit(PSUpImageCommands[count]);
		if(count==11){
			for(count=0; count<100; count++){
				
			     returnedValue[count] = USART_Receive();
				
			}
	
	OpenPortToPC();
			
	if(returnedValue[9] == 00){
		for (count=0; count<100; count++){
			USART_Transmit(returnedValue[count]);
			delay_ms(1);
		}
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[0]))); //Retrieving string from the memory
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
				
		}
			else{
				strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[1]))); //Retrieving string from the memory
				LCD_Display(buffer);
				delay_ms(100);
				Clear_Display();
			}

		}
	}
}
void PSLoadChar(int iPageID){
	
	OpenPortToFP();
	int sum = 15 + iPageID;
	
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x06);
	USART_Transmit(0x07);
	USART_Transmit(BUFFER_ID01);
	USART_Transmit(0x00);
	USART_Transmit(iPageID);
	USART_Transmit(0x00);
	USART_Transmit(sum);
	
	
	for(count=0; count<12; count++){
		returnedValue[count]=USART_Receive();
	}

   OpenPortToPC();

	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[22]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x01){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x0C){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[23]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	
	OpenPortToFP();
}

void PSDownChar(){
	 OpenPortToFP();
	
	USART_Transmit(0xEF);
	USART_Transmit(0x01);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0xFF);
	USART_Transmit(0x01);
	USART_Transmit(0x00);
	USART_Transmit(0x04);
	USART_Transmit(0x09);
	USART_Transmit(BUFFER_ID01);
	USART_Transmit(0x00);
	USART_Transmit(0x0F);
	
	
	for(count=0; count<12; count++){
		returnedValue[count]=USART_Receive();
	}

	OpenPortToPC();

	if(returnedValue[9]==0x00){
		PORTC|=(1<<1);
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[22]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x01){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[4]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	else if(returnedValue[9]==0x0E){
		Clear_Display();
		strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_response_table[24]))); //Error when receiving package
		LCD_Display(buffer);
		delay_ms(100);
		Clear_Display();
		
	}
	OpenPortToFP();
}
void PSDownLoadData(unsigned data){
	OpenPortToFP();
	USART_Transmit(data);
	
}


