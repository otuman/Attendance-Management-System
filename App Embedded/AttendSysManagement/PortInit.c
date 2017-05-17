/*
 * PortInit.c
 *
 * Created: 24/01/2016 05:07:05 PM
 *  Author: Otoman
 */ 
#include <avr/io.h>
#include "PortInit.h"
#include "lcd_headerFile.h";

//****************************  Flash LED  ************************/
void OpenPortToPC(){
	PORTD = PORTD & 0b00000000;		    // Lower PD3
	PORTD = PORTD | 0b00010010;        // Raise PD3
	delay_ms(2);
}
void OpenPortToFP(){
	PORTD = PORTD & 0b00000000;		    // Lower PD3
	PORTD = PORTD | 0b00000010;        // Raise PD3
	delay_ms(2);
}


void FlashLED()
{
	PORTC = PORTC | 0b00000001;
	delay_ms(250);
	PORTC = PORTC & 0b11111110;	//	Flash LED once
	delay_ms(250);
}

//****************************  Port_Init  *************************/

void PortInit()
{
	DDRD  = DDRD | 0b00011110;	// PD1, PD2, and PD3 are outputs and PD0 is input
	PORTD = PORTD & 0b00000000;	// PD0 are 0
	DDRA  = DDRA | 0b00000011;	// PortB0 and PB1 are outputs
	PORTA = PORTA & 0b00000000;	// PortB0 and PB1 are 0
	DDRC  = DDRC| 0b11111111;	// LED
	PORTC = PORTC & 0b00000000;	// LED
}