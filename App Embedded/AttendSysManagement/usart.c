
                          
/*************************************************************************/
#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>
#include "usart.h"
  
  
/*
 *  constants and macros
 */
  
/* size of RX/TX buffers */
#define UART_RX_BUFFER_MASK ( UART_RX_BUFFER_SIZE - 1)
#define UART_TX_BUFFER_MASK ( UART_TX_BUFFER_SIZE - 1)
  
#if ( UART_RX_BUFFER_SIZE & UART_RX_BUFFER_MASK )
#error RX buffer size is not a power of 2
#endif
#if ( UART_TX_BUFFER_SIZE & UART_TX_BUFFER_MASK )
#error TX buffer size is not a power of 2
#endif
  
/* ATmega with one USART */
 #define ATMEGA_USART
 #define UART0_RECEIVE_INTERRUPT   USART_RXC_vect
 #define UART0_TRANSMIT_INTERRUPT  USART_UDRE_vect
 #define UART0_STATUS   UCSRA
 #define UART0_CONTROL  UCSRB
 #define UART0_DATA     UDR
 #define UART0_UDRIE    UDRIE
  
/*
 *  module global variables
 */
static volatile unsigned char UART_TxBuf[UART_TX_BUFFER_SIZE];
static volatile unsigned char UART_RxBuf[UART_RX_BUFFER_SIZE];
static volatile unsigned char UART_TxHead;
static volatile unsigned char UART_TxTail;
static volatile unsigned char UART_RxHead;
static volatile unsigned char UART_RxTail;
static volatile unsigned char UART_LastRxError;
  
ISR (UART0_RECEIVE_INTERRUPT)
/*************************************************************************
Function: UART Receive Complete interrupt
Purpose:  called when the UART has received a character
**************************************************************************/
{
	unsigned char tmphead;
	unsigned char data;
	unsigned char usr;
	unsigned char lastRxError;
	
	
	/* read UART status register and UART data register */
	usr  = UART0_STATUS;
	data = UART0_DATA;
	
	/* */
	#if defined(ATMEGA_USART)
	lastRxError = (usr & ((1<<(FE))|(1<<(DOR)) ));
	#endif
	
	/* calculate buffer index */
	tmphead = ( UART_RxHead + 1) & UART_RX_BUFFER_MASK;
	
	if ( tmphead == UART_RxTail ) {
		/* error: receive buffer overflow */
		lastRxError = UART_BUFFER_OVERFLOW >> 8;
		}else{
		/* store new index */
		UART_RxHead = tmphead;
		/* store received data in buffer */
		UART_RxBuf[tmphead] = data;
	}
	UART_LastRxError |= lastRxError;
}
  
ISR(UART0_TRANSMIT_INTERRUPT)
/*************************************************************************
Function: UART Data Register Empty interrupt
Purpose:  called when the UART is ready to transmit the next byte
**************************************************************************/
{
    unsigned char tmptail;
  
      
    if ( UART_TxHead != UART_TxTail) {
        /* calculate and store new buffer index */
        tmptail = (UART_TxTail + 1) & UART_TX_BUFFER_MASK;
        UART_TxTail = tmptail;
        /* get one byte from buffer and write it to UART */
        UART0_DATA = UART_TxBuf[tmptail];  /* start transmission */
    }else{
        /* Tx buffer empty, disable UDRE interrupt */
        UART0_CONTROL &= ~(1<<UART0_UDRIE);
    }
}
  
  
/*************************************************************************
Function: uart_init()
Purpose:  initialize UART and set baudrate
Input:    baudrate using macro UART_BAUD_SELECT()
Returns:  none
**************************************************************************/
void USART_Init(unsigned int baudrate)
{
    UART_TxHead = 0;
    UART_TxTail = 0;
    UART_RxHead = 0;
    UART_RxTail = 0;
	
	

#if defined (ATMEGA_USART)
    /* Set baud rate */
	
    if ( baudrate & 0x8000 )
    {
	     UART0_STATUS = (1<<U2X);  //Enable 2x speed 
         baudrate &= ~0x8000;
    }
    UBRRH = (unsigned char)baudrate>>8;
    UBRRL = (unsigned char)baudrate;
     
    /* Enable USART receiver and transmitter and receive complete interrupt */
    UART0_CONTROL = (1<<RXCIE)|(1<<RXEN)|(1<<TXEN);
	    
    /* Set frame format: asynchronous, 8data, no parity, 1stop bit */
    #ifdef URSEL
    UCSRC =  (1<<URSEL)|(3<<UCSZ0); //(1<<URSEL)|(3<<UCSZ0); ///*This is (frame format)asynchronous, 8data, no parity, 1stop bit */
    #else
    UCSRC = (3<<UCSZ0);
    #endif
#endif 
}/* uart_init */
  
  
/*************************************************************************
Function: uart_getc()
Purpose:  return byte from ringbuffer  
Returns:  lower byte:  received byte from ringbuffer
          higher byte: last receive error
**************************************************************************/
unsigned int USART_Receive(void)
{    
    unsigned char tmptail;
    unsigned char data;
  
  
  while(UART_RxHead == UART_RxTail){;}  //Wait for data availability 
  
   /* calculate /store buffer index */
    tmptail = (UART_RxTail + 1) & UART_RX_BUFFER_MASK;
    UART_RxTail = tmptail; 
      
    /* get data from receive buffer */
    data = UART_RxBuf[tmptail];
	data = (UART_LastRxError << 8) + data;
	
	UART_LastRxError = 0;
      
    return data;
  
}/* uart_getc */
  
  
/*************************************************************************
Function: uart_putc()
Purpose:  write byte to ringbuffer for transmitting via UART
Input:    byte to be transmitted
Returns:  none          
**************************************************************************/
void USART_Transmit(unsigned char data)
{
    unsigned char tmphead;
  
      
    tmphead  = (UART_TxHead + 1) & UART_TX_BUFFER_MASK;
      
    while ( tmphead == UART_TxTail ){
        ;/* wait for free space in buffer */
    }
      
    UART_TxBuf[tmphead] = data;
    UART_TxHead = tmphead;
  
    /* enable UDRE interrupt */
    UART0_CONTROL    |= 1<<(UART0_UDRIE);
  
}/* uart_putc */
  
  
/*************************************************************************
Function: uart_puts()
Purpose:  transmit string to UART
Input:    string to be transmitted
Returns:  none          
**************************************************************************/
void  USART_Transmit_String(const char *s )
{
    while (*s) 
      USART_Transmit(*s++);
  
}/* uart_puts */
  
  
/*************************************************************************
Function: uart_puts_p()
Purpose:  transmit string from program memory to UART
Input:    program memory string to be transmitted
Returns:  none
**************************************************************************/
void USART_Transmit_String_p(const char *progmem_s )
{
    register char c;
      
    while ( (c = pgm_read_byte(progmem_s++)) ) 
      USART_Transmit(c);
  
}/* uart_puts_p */
  
  
