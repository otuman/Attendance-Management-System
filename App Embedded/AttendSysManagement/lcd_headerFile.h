/*
 * lcd_headerFile.h
 *
 * Created: 10/12/2015 02:35:07 PM
 *  Author: Otoman
 */ 


#ifndef LCD_HEADERFILE_H_
#define LCD_HEADERFILE_H_

extern void delay_ms(int d);
extern void LCDinit(void);

extern void LCD_Display(char *strg);

extern void Strobe();

extern void Short_Delay();

extern void Unpack_To_PortA(char data);

#endif /* LCD_HEADERFILE_H_ */