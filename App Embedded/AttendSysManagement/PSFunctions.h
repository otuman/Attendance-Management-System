/*
 * PSFunctions.h
 *
 * Created: 10/12/2015 07:45:46 PM
 *  Author: Otoman
 */ 

#ifndef PSFUNCTIONS_H_
#define PSFUNCTIONS_H_
extern int itoa(int __val, char *__s, int __radix);
extern void PSEmptyFPLib();
extern void PSUpImageFile();
extern void PSDownChar();
extern void PSDownLoadData(unsigned data);
extern void PSUpCharFile();
extern void PSLoadChar(int iPageID);
extern int  PSTempleteNum();
extern void PSTurnOnPortCom();
extern void PSVrfyPassword();
extern void PSRegisterFingerOne();
extern void PSRegisterFingerTwo();
extern void PSTestingFunction();
extern void PSGeneCharFileForBuffer1();
extern void PSGeneCharFileForBuffer2();
extern void PSStoreCharFile();
extern unsigned char PSSearchForAFingerB1(int sPage, int pNum);
extern void PSSearchForAFingerB2(int sPage, int pNum);
extern void PSCombineTwoCharFile();
extern unsigned char PSMatchTwoBuffer();

#endif    /* PSFUNCTIONS_H_ */