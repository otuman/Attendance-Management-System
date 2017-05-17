/*
 * lcd_codeFile.c
 *
 * Created: 10/12/2015 02:35:48 PM
 *  Author: Otoman
 */ 

//************************ MACROS AND FUNCTIONS ********************
#include <avr/pgmspace.h>
#include <avr/io.h>
#include <util/delay.h>
#include "lcd_headerFile.h"



void delay_ms(int d){ 
	int i = 0;
	while(i<d)
	{
		_delay_ms(2);
		i++;
	}
}
//********************************************************************
//************************** LCD INITIALIZATION **********************

void LCDinit(void) 
{
DDRB  = 0xFF; // Port C is output
PORTB = 0x00; // E = 0
delay_ms(5); // Wait for LCD
PORTB = PORTB & 0x0F; // mask away high nibble from Port D
PORTB = PORTB | 0x20; // enable 4-bit data transfer
Strobe();
Unpack_To_PortB(0x28); // enable 4-bit data transfer
Unpack_To_PortB(0x0C); // display on, no cursor
Unpack_To_PortB(0x06); // cursor shifts right
Clear_Display();
}

//******************************************************************

//*************************** LCD_Display ******************************

void LCD_Display(char *strg){ 
	
char character;            // Exits loop when character = NULL
		PORTB = PORTB | 0b00000100; // E = 1
		PORTB = PORTB & 0b11111101; // RW = 0,
		PORTB = PORTB | 0b00000001; // RS = 1 (Data)
		while (character = *strg)
			{
			Unpack_To_PortB(character); // write data to LCD
			*strg++;
			}
}
//************************** Strobe *********************************
void Strobe(){ 
	
    PORTB = PORTB | 0b00000100; // E = 1
	Short_Delay();
	PORTB = PORTB & 0b11111011; // E = 0
	delay_ms(5);
}
//*******************************************************************
//************************ Clear_Display ****************************
void Clear_Display(){

		PORTB = PORTB | 0b00000100; // E = 1
		PORTB = PORTB & 0b11111100; // RW = 0, RS = 0 (Cmd)
		Short_Delay(); // Wait for LCD
		Unpack_To_PortB(0x01); // clear LCD
		Unpack_To_PortB(0x02); // cursor home
}
//*************************** Short_Delay ****************************
void Short_Delay(){ 
	char i;
	for (i=0; i<10; i++){}; 

}
//*******************************************************************     
//************************ Unpack_To_PortC **************************   
void Unpack_To_PortB(char data){
	
		char temp;
		temp = data;
		PORTB = PORTB & 0x0F; // mask away high nibble from Port D
		data = data & 0xF0; // mask away low nibble from data
		PORTB = PORTB | data; // data low nibble to Port D high nibble
		Strobe();
		PORTB = PORTB & 0x0F; // mask away high nibble from Port D
		temp = temp <<4; // move data low nibble to high nibble
		PORTB = PORTB | temp; // data low nibble to Port D high nibble
		Strobe();
}
//**********************************************************************


