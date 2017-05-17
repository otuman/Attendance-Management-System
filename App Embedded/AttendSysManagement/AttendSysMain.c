/*
 * AttendenceSysMgt.c
 *
 * Created: 10/12/2015 02:31:46 PM
 *  Author: Otoman
 */
 
//*******************defining various variables ************
//#define FOSC 1843200UL
//#define Mybaud (F_CPU/16/BAUD-1) // define baud  (FOSC/16/BAUD-1)  
//*******************end of defining various variables ***********

#ifndef F_CPU
#define F_CPU 8000000UL
#endif
#define BAUD 9600
#define BAUDRATE (((( F_CPU / 16) + ( BAUD/2)) / (BAUD)) - 1)  // set baudrate value for UBRR
#define _DELAY_BACKWARD_COMPATIBLE_
//*******************include files *************************

#include <avr/io.h>
#include <util/delay.h>
#include <stdio.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include "PortInit.h"
#include "usart.h"
#include "lcd_headerFile.h"
#include "PSFunctions.h"
//*******************end of include files ******************
//**********************Store to flash***************************//
const char  initMsg[] PROGMEM    = "Welcome to Attendance System";
const char  statusMsg[] PROGMEM  = "You are successfully registered ";
const char  statusMsg1[] PROGMEM  = "Your attendance record is stored";

PGM_P const string_table[] PROGMEM ={initMsg, statusMsg, statusMsg1};


//******************* variables declaralation*********************
unsigned char buffer[10];
unsigned char UDR_Value;
void Clear_Display();
void LCD_Display(char *strg);

//*******************end of declaralation of variables ***********
int main(){ 
	
	PortInit();
	LCDinit();
	Clear_Display();
	USART_Init(BAUDRATE);
	sei();
	Clear_Display();
	strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_table[0]))); //Retrieving string from the memory
	LCD_Display(buffer);
	delay_ms(1000);
	while(1)
		{
		
	  PORTA = PORTA & 0b11111110;		// Lower PB0
	  PORTA = PORTA | 0b00000010;		// Raise PB1
	  delay_ms(5);
	  if (((PINA & 0b00000100)==0)){   //Activate switch 1
			int count =0;
			unsigned char  data[count];
					 Clear_Display();
				    
					 PORTD = PORTD & 0b00000000;		    // Lower PD3
					 PORTD = PORTD | 0b00010010;        // Raise PD3
			         for(count =0; count<11; count++ )
			         {
						  //data[count] = USART_Receive();
						  LCD_Display("The ");
						  LCD_Display(itoa(count, buffer, 10));
						  LCD_Display("  ");
						  LCD_Display(itoa(data[count], buffer, 10));
						  delay_ms(10);
						  Clear_Display();
						  
			         }
					 PSDownChar();
					 for(count =0; count<118; count++ )
					  {
						 PSDownLoadData(data[count]);
					 }
					 
					 LCD_Display("Stopped sending");
				     Clear_Display();
					 strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_table[0])));
					 LCD_Display(buffer);
				     delay_ms(1000);
					 Clear_Display();
					for(int i=0;  i < PSTempleteNum(); i++){
							  PSLoadChar(i);
							  PSUpCharFile();
							  delay_ms(10);
					}
						 
		 }
	 		
		if (((PINA & 0b00001000)==0)){  //Activate switch 2
			    //PSEmptyFPLib();          //Empty the Finger print library
				PSTurnOnPortCom(); 
		}
	
	  PORTA = PORTA & 0b11111101;		// Lower PB1
	  PORTA = PORTA | 0b00000001;       // Raise PB0 
	  delay_ms(5);
		
		if((PINA & 0b00000100)==0){      //Activate switch 3
				Clear_Display();
				PSTurnOnPortCom();      //Turn  on the PS port, this function is resided in the PSFunctions.c file
				do{
				 PSRegisterFingerOne();
				 PSRegisterFingerTwo();
			    }while(PSMatchTwoBuffer()!=1);
				Clear_Display();
				strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_table[1]))); //Retrieving string from the memory
				LCD_Display(buffer);
				delay_ms(1000);
				
					
		}			
     	if ((PINA & 0b00001000)==0){        //Activate switch 4
				do{
					Clear_Display();
					PSRegisterFingerOne();
					}while((PSSearchForAFingerB1(0, 40)!=1));
				strcpy_P(buffer, (PGM_P)pgm_read_word(&(string_table[2]))); //Retrieving string from the memory
				LCD_Display(buffer);
				delay_ms(1000);
				Clear_Display();
			} 
    }
 return 0;
}

