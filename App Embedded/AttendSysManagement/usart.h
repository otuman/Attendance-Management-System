#ifndef UART_H
#define UART_H

/** Size of the circular receive buffer, must be power of 2 */
#ifndef UART_RX_BUFFER_SIZE
#define UART_RX_BUFFER_SIZE 128
#endif

/** Size of the circular transmit buffer, must be power of 2 */
#ifndef UART_TX_BUFFER_SIZE
#define UART_TX_BUFFER_SIZE 128
#endif

/* test if the size of the circular buffers fits into SRAM */
#if ( (UART_RX_BUFFER_SIZE+UART_TX_BUFFER_SIZE) >= (RAMEND-0x60 ) )
#error "size of UART_RX_BUFFER_SIZE + UART_TX_BUFFER_SIZE larger than size of SRAM"
#endif

/* 
** high byte error return code of uart_getc()
*/
#define UART_FRAME_ERROR      0x1000              /* Framing Error by UART       */
#define UART_OVERRUN_ERROR    0x0800              /* Overrun condition by UART   */
#define UART_PARITY_ERROR     0x0400              /* Parity Error by UART        */ 
#define UART_BUFFER_OVERFLOW  0x0200              /* receive ringbuffer overflow */
#define UART_NO_DATA          0x0100              /* no receive data available   */


/*
** function prototypes
*/

/**
   @brief   Initialize UART and set baudrate 
   @param   baudrate 
   @return  none
*/
extern void USART_Init(unsigned int baudrate);


/**
*  @brief   Get received byte from ringbuffer
*
* Returns in the lower byte the received character and in the
* higher byte the last receive error.
* UART_NO_DATA is returned when no data is available.
*
*  @param   void
*  @return  lower byte:  received byte from ringbuffer
*  @return  higher byte: last receive status
*           - \b 0 successfully received data from UART
*           - \b UART_NO_DATA
*             <br>no receive data available
*           - \b UART_BUFFER_OVERFLOW
*             <br>Receive ringbuffer overflow.
*             We are not reading the receive buffer fast enough,
*             one or more received character have been dropped
*           - \b UART_OVERRUN_ERROR
*             <br>Overrun condition by UART.
*             A character already present in the UART UDR register was
*             not read by the interrupt handler before the next character arrived,
*             one or more received characters have been dropped.
*           - \b UART_FRAME_ERROR
*             <br>Framing Error by UART
*/
extern unsigned int USART_Receive(void);


/**
 *  @brief   Put byte to ringbuffer for transmitting via UART
 *  @param   data byte to be transmitted
 *  @return  none
 */
extern void USART_Transmit(unsigned char data);


/**
 *  @brief   Put string to ringbuffer for transmitting via UART
 *
 *  The string is buffered by the uart library in a circular buffer
 *  and one character at a time is transmitted to the UART using interrupts.
 *  Blocks if it can not write the whole string into the circular buffer.
 * 
 *  @param   s string to be transmitted
 *  @return  none
 */

extern void USART_Transmit_String(const char *s );


/**
 * @brief    Put string from program memory to ringbuffer for transmitting via UART.
 *
 * The string is buffered by the uart library in a circular buffer
 * and one character at a time is transmitted to the UART using interrupts.
 * Blocks if it can not write the whole string into the circular buffer.
 *
 * @param    s program memory string to be transmitted
 * @return   none
 * @see      uart_puts_P
 */
extern void USART_Transmit_String_p(const char *s );

/**
 * @brief    Macro to automatically put a string constant into program memory
 */
#define uart_puts_P(__s)       USART_Transmit_String_p(PSTR(__s))

#endif
