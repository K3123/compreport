/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools @ Templates
 * and open the template in the editor.
 */
package javaapplication4;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #2
 * EN.695.744.82.FA17 Reverse Engineering and Vulnerability Analysis  
 */

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class main {

static final String fileName = "D:\\John Hopkins School\\examples1.o"; 
static final String debugCode = "debug7"; 

/**
 * The following function will import the machine code from the binary file into
 * a string to be decoded by the main program.
 * @param args - String array of command line arguments enter by user
 * @return - String array of byte codes for all instructions read from a binary 
 * file
 */
public static String[] importFileData(String[] args){

    int byteCt = 0;
    int value = 0;
    
    StringBuilder mcH = new StringBuilder();
    StringBuilder mcResults = new StringBuilder();
    
    if (args.length >= 1){
    try{
        InputStream machineCode = new FileInputStream(args[0]);
        while(( value = machineCode.read()) != -1 ){
        mcH.append(String.format("%02x ", value));
        byteCt++;
        
    }
        machineCode.close();
    }catch(FileNotFoundException e){
        System.out.println("The specified file does not exist. " + args[0]
                + "\nThe program will end. Please try executing "
                + "the program with a file that exists. ");
    }catch(IOException e){
        e.printStackTrace();
    }
    }else{
    
    try{
        InputStream machineCode = new FileInputStream(fileName);
        while(( value = machineCode.read()) != -1 ){
        mcH.append(String.format("%02x ", value));
        byteCt++;
        
    }
        machineCode.close();
    }catch(FileNotFoundException e){
        System.out.println("The specified file does not exist. " + fileName 
                + "\nThe program will end. Please try executing "
                + "the program with a file that exists. ");
    }catch(IOException e){
        e.printStackTrace();
    }
    }
    if (byteCt != 0 ){
        mcResults.append(mcH).append(" ").append("\n");
    }
    
return mcResults.toString().split(" ");
} 
/**
 * String array that contains the small set of small subset of the Intel 
 * Instruction Set that are supported for this disassembler  
 */
static final String[] allOpCodes = {"add","and","call","cmp","dec",
                "idiv","imul","inc","jmp","jz","jnz","lea","mov","movsd",
                "mul","neg","nop","not","or","pop","push","repne","cmpsd",
                "retf","retn","sal","sar","sbb","shr","test","xor"};
/**
 * String array of all prefixes that were identified by ISA document. 
 * Instruction prefixes are divided into four groups, each with a set of 
 * allowable prefix codes. For each instruction, it is only useful to include 
 * up to one prefix code from each of the four groups (Groups 1, 2, 3, 4). 
 * Groups 1 through 4 may be placed in any order relative to each other. 
 */
static final String[] allPrefix = {"f0","f2","f3","2e","36","0f",
                                    "3e","26","64","65","66","67"};
/**
 * String array of Op/En codes that will produce a ModRM value in the 
 * instruction set
 */
static final String[] allMRMCODE = {"A", "B", "C", "M", "RM", "MR", "MI", "RMI",
                       "RVM", "M1", "MC" };
/**
 * String array of opcodes that requires additional instructions 
 */
static final String[] allMRMCODE2 = {"ff","FF","80","81","83","C7","FE","F6",
                     "F7","C6","8F", "D0", "D1", "D2", "D3", "C0", "C1","C7" };

/**
 * Function that changes a hex value to a binary representation
 * @param opCode - Byte code of instruction codes extracted from the binary file
 * @return String - Binary translation of the byte code of the instructions 
 * codes extracted from the binary file
 */
static String hextoBin(String opCode){
    String i="";
    try{
        i = new BigInteger(opCode,16).toString(2);
    }catch(NumberFormatException ex){
        System.out.println("\"" + opCode + "\"" + "is not valid");
    }
    return i;
}
/**
 * Function that decodes the value ModRM bit and determines the length of extra 
 * bytes that will need to be extracted from the instruction set
 * @param mcCodeUpdateCT - ModRM byte code string array extracted from the 
 * instruction set
 * @return integer - the value added to the extra byte required for the 
 * instruction set
 */
static int opCodeCTUpdate(String[] mcCodeUpdateCT){
      int updateCT1 = 0;
      
            switch(mcCodeUpdateCT[0]){
            case "00" : 
                        switch(mcCodeUpdateCT[2]){
                            case "000" : updateCT1 = 0;
                                         break;
                            case "001" : updateCT1 = 0;
                                         break;
                            case "010" : updateCT1 = 0;
                                         break;
                            case "011" : updateCT1 = 0;
                                         break;
                            case "100" : updateCT1 = 1;
                                         break;
                            case "101" : updateCT1 = 4;
                                         break;
                            case "110" : updateCT1 = 0;
                                         break;
                            case "111" : updateCT1 = 0;
                                         break;
                            default : updateCT1 = 0;
                        }
                        break;
            case "01" :  
                       switch(mcCodeUpdateCT[2]){
                            case "000" : updateCT1 = 1;
                                         break;
                            case "001" : updateCT1 = 1;
                                         break;
                            case "010" : updateCT1 = 1;
                                         break;
                            case "011" : updateCT1 = 1;
                                         break;
                            case "100" : updateCT1 = 2;
                                         break;
                            case "101" : updateCT1 = 1;
                                         break;
                            case "110" : updateCT1 = 1;
                                         break;
                            case "111" : updateCT1 = 1;
                                         break;
                            default : updateCT1 = 1;
                        }
                        break;
            case "10" :  
                       switch(mcCodeUpdateCT[2]){
                            case "000" : updateCT1 = 4;
                                         break;
                            case "001" : updateCT1 = 4;
                                         break;
                            case "010" : updateCT1 = 4;
                                         break;
                            case "011" : updateCT1 = 4;
                                         break;
                            case "100" : updateCT1 = 5;
                                         break;
                            case "101" : updateCT1 = 4;
                                         break;
                            case "110" : updateCT1 = 4;
                                         break;
                            case "111" : updateCT1 = 4;
                                         break;
                            default : updateCT1 = 0;
                        }
                        break;
            case "11" :  
                       switch(mcCodeUpdateCT[2]){
                            case "000" : updateCT1 = 0;
                                         break;
                            case "001" : updateCT1 = 0;
                                         break;
                            case "010" : updateCT1 = 0;
                                         break;
                            case "011" : updateCT1 = 0;
                                         break;
                            case "100" : updateCT1 = 0;
                                         break;
                            case "101" : updateCT1 = 0;
                                         break;
                            case "110" : updateCT1 = 0;
                                         break;
                            case "111" : updateCT1 = 0;
                                         break;
                            default : updateCT1 = 0;
                        }
                        break;
        }
   return updateCT1;         
}
/**
 * Function that finds the required opcode instruction set based on the byte 
 * instruction hex value. This function will search the instruction set list 
 * to determine which opcode instruction set should be returned. 
 * @param opCD -  list of all supported instructionSet opcodes  
 * @param mcCOD - current opcode read from instruction set
 * @param byteType - string representation of current platform
 * @return - instructionSet found based on opcode
 */
public static instructionSet findOpCode(List<instructionSet> opCD, 
        String[] mcCOD,  String byteType)
{  
      instructionSet opCOD3 = null;
      String[] modCodes;
      String[] sibCodes;
      String[] opCodesPush = new String[1]; 
      int updateCT = 0;
      modCodes = modBuilder(String.format("%8s",hextoBin(mcCOD[1])).
                replace(" ", "0"));
      sibCodes = sibBuilder(String.format("%8s",hextoBin(mcCOD[2])).
                replace(" ", "0")); 
      if ( validPreFix(mcCOD[0].toLowerCase()) && ! mcCOD[0].toLowerCase().
              equalsIgnoreCase("ff")){
                modCodes = modBuilder(String.format("%8s",hextoBin(mcCOD[2])).
                replace(" ", "0"));
                sibCodes = sibBuilder(String.format("%8s",hextoBin(mcCOD[3])).
                replace(" ", "0")); 

          switch(mcCOD[0].toLowerCase()){
              case "f2" :
              case "0f" :
                         opCOD3 = findCodeAndCode2(opCD ,mcCOD,updateCT);   
                           if (opCOD3 != null){
                            if ( validMODRM(opCOD3.getMRMCODE())){ 
                             opCOD3.setCurrentBytes(opCOD3.
                                     getAdditionalBytes() +
                                  opCodeCTUpdate(modCodes)); 
                            }else{
                               opCOD3.setCurrentBytes(opCOD3.
                                       getAdditionalBytes()); 
                            }   
                            if (debugCode.equalsIgnoreCase("debug")){
                             System.out.
                                printf("1 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                                    opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                                    opCOD3.getAdditionalBytes(), updateCT,
                                    opCodeCTUpdate(modCodes),
                                    modCodes[0],modCodes[1],modCodes[2]);
                            } 
                     }
                     break;
        }
      }  
     if ( ! validPreFix(mcCOD[0].toLowerCase()) && mcCOD[0].charAt(0) != '5'
             && mcCOD[0].charAt(0) != '4'
             && mcCOD[0].charAt(0) != 'B' && ! validMODRM2(mcCOD[0])){
            
            opCOD3 = findCode(opCD ,mcCOD);
            
        if (opCOD3 != null){
            
           if ( validMODRM(opCOD3.getMRMCODE())){
               switch(opCOD3.getOpcodeCD().toUpperCase()){
                   case "E8" : opCOD3.setCurrentBytes(opCOD3.
                               getAdditionalBytes());
                               break;
                   default : opCOD3.setCurrentBytes(opCOD3.
                             getAdditionalBytes() +
                             opCodeCTUpdate(modCodes));
                              break;
                }
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
         if (debugCode.equalsIgnoreCase("debug")){  
         System.out.printf("3 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
         }
        }
     }
     if ( validMODRM2(cleaner(mcCOD[0],0).toUpperCase()) == true ){
         String nameS = "";
         updateCT = 0;
         switch(mcCOD[0].toUpperCase()){
           case "FF" :  switch(Integer.parseInt(modCodes[1],2)){
                                case 0 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "inc r/m16";
                                          }else{
                                            nameS = "inc r/m32";
                                         }
                                         break;
                                case 1 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "dec r/m16";
                                          }else{
                                            nameS = "dec r/m32";
                                         }
                                         break;
                                case 2 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "call r/m16";
                                          }else{
                                            nameS = "call r/m32";
                                         }
                                         break;
                                case 3 :  if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "call m16:16";
                                          }else{
                                            nameS = "call m16:32";
                                         }
                                         break;
                                case 4 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "jmp r/m16";
                                          }else{
                                            nameS = "jmp r/m32";
                                         }
                                         break;
                                case 5 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "jmp m16:16";
                                          }else{
                                            nameS = "jmp m16:32";
                                         }
                                         break;
                                case 6 : if (! byteType.equalsIgnoreCase("32")){
                                            nameS = "push r/m16";
                                          }else{
                                            nameS = "push r/m32";
                                         }
                                         break;
                                default : nameS = "not available";
                                         break;
                               }            
                               break;
             case "D0" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "not available";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : nameS = "SAL r/m8, 1";
                                    break;
                           case 5 : nameS = "SHR r/m8, 1";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "SAR r/m8, 1";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "D2" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "not available";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : nameS = "SAL r/m8, CL";
                                    break;
                           case 5 : nameS = "SHR r/m8, CL";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "SAR r/m8, CL";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "C0" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "not available";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "SHL r/m8, imm8";
                                    break;
                           case 4 : nameS = "SAL r/m8, imm8";
                                    break;
                           case 5 : nameS = "SHR r/m8, imm8";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "SAR r/m8, imm8";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "D1" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "not available";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAL r/m16,1";
                                    }else{
                                      nameS = "SAL r/m32,1";
                                    }
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SHR r/m16, 1";
                                    }else{
                                      nameS = "SHR r/m32, 1";
                                    }
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAR r/m16,1";
                                    }else{
                                      nameS = "SAR r/m32,1";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "D3" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "not available";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAL r/m16, CL";
                                    }else{
                                      nameS = "SAL r/m32, CL";
                                    }
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SHR r/m16, CL";
                                    }else{
                                      nameS = "SHR r/m32, CL";
                                    }
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAR r/m16, CL";
                                    }else{
                                      nameS = "SAR r/m32, CL";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "C1" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "POP r/m16";
                                    }else{
                                      nameS = "POP r/m32";
                                    }
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAL r/m16, imm8";
                                    }else{
                                      nameS = "SAL r/m32, imm8";
                                    }
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SHR r/m16, imm8";
                                    }else{
                                      nameS = "SHR r/m32, imm8";
                                    }
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SAR r/m16, imm8";
                                    }else{
                                      nameS = "SAR r/m32, imm8";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "8F" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "POP r/m16";
                                    }else{
                                      nameS = "POP r/m32";
                                    }
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : nameS = "not available";
                                    break;
                           case 5 : nameS = "not available";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "not available";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "C6" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "MOV r/m8, imm8";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : nameS = "not available";
                                    break;
                           case 5 : nameS = "not available";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "not available";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "C7" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "MOV r/m16, imm16";
                                    }else{
                                      nameS = "MOV r/m32, imm32";
                                    }
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "XRSTORS mem";
                                    break;
                           case 4 : nameS = "XRSTORSC mem";
                                    break;
                           case 5 : nameS = "not available";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "not available";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "F7" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "TEST r/m16, imm16";
                                    }else{
                                      nameS = "TEST r/m32, imm32" ;
                                    }
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "NOT r/m16";
                                    }else{
                                      nameS = "NOT r/m32";
                                    }
                                    break;
                           case 3 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "NEG r/m16";
                                    }else{
                                      nameS = "NEG r/m32";
                                    }
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "MUL r/m16";
                                    }else{
                                      nameS = "MUL r/m32";
                                    } 
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "IMUL r/m16";
                                    }else{
                                      nameS = "IMUL r/m32";
                                    }
                                    break;
                           case 6 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "DIV r/m16";
                                    }else{
                                      nameS = "DIV r/m32";
                                    }
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "IDIV r/m16";
                                    }else{
                                      nameS = "IDIV r/m32";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "F6" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "TEST r/m8, imm8";
                                    break;
                           case 1 : nameS = "not available";
                                    break;
                           case 2 : nameS = "NOT r/m8";
                                    break;
                           case 3 : nameS = "NEG r/m8";
                                    break;
                           case 4 : nameS = "MUL r/m8";
                                    break;
                           case 5 : nameS = "IMUL r/m8";
                                    break;
                           case 6 : nameS = "DIV r/m8";
                                    break;
                           case 7 : nameS = "IDIV r/m8";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "80" :   
                        switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "ADD r/m8, imm8";
                                    break;
                           case 1 : nameS = "OR r/m8, imm8";
                                    break;
                           case 2 : nameS = "ADC r/m8, imm8";
                                    break;
                           case 3 : nameS = "SBB r/m8, imm8";
                                    break;
                           case 4 : nameS = "AND r/m8, imm8";
                                    break;
                           case 5 : nameS = "SUB r/m8, imm8";
                                    break;
                           case 6 : nameS = "XOR r/m8, imm8";
                                    break;
                           case 7 : nameS = "CMP r/m8, imm8";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "81" : 
                          switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "ADD r/m16,imm16";
                                    }else{
                                      nameS = "ADD r/m32,imm32";
                                    }
                                    break;
                           case 1 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "OR r/m16,imm16";
                                    }else{
                                      nameS = "OR r/m32,imm32";
                                    }
                                    break;
                           case 2 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "ADC r/m16,imm16";
                                    }else{
                                      nameS = "ADC r/m32,imm32";
                                    }
                                    break;
                           case 3 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SBB r/m16,imm16";
                                    }else{
                                      nameS = "SBB r/m32,imm32";
                                    }
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "AND r/m16,imm16";
                                    }else{
                                      nameS = "AND r/m32,imm32";
                                    }
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SUB r/m16,imm16";
                                    }else{
                                      nameS = "SUB r/m32,imm32";
                                    }
                                    break;
                           case 6 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "XOR r/m16,imm16";
                                    }else{
                                      nameS = "XOR r/m32,imm32";
                                    }
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "CMP r/m16,imm16";
                                    }else{
                                      nameS = "CMP r/m32,imm32";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "83" : 
                          switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "ADD r/m16,imm8";
                                    }else{
                                      nameS = "ADD r/m32,imm8";
                                    }
                                    break;
                           case 1 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "OR r/m16,imm8";
                                    }else{
                                      nameS = "OR r/m32,imm8";
                                    }
                                    break;
                           case 2 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "ADC r/m16,imm8";
                                    }else{
                                      nameS = "ADC r/m32,imm8";
                                    }
                                    break;
                           case 3 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SBB r/m16,imm8";
                                    }else{
                                      nameS = "SBB r/m32,imm8";
                                    }
                                    break;
                           case 4 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "AND r/m16,imm8";
                                    }else{
                                      nameS = "AND r/m32,imm8";
                                    }
                                    break;
                           case 5 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "SUB r/m16,imm8";
                                    }else{
                                      nameS = "SUB r/m32,imm8";
                                    }
                                    break;
                           case 6 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "XOR r/m16,imm8";
                                    }else{
                                      nameS = "XOR r/m32,imm8";
                                    }
                                    break;
                           case 7 : if (! byteType.equalsIgnoreCase("32")){
                                      nameS = "CMP r/m16,imm8";
                                    }else{
                                      nameS = "CMP r/m32,imm8";
                                    }
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;
             case "FE" : 
                          switch(Integer.parseInt(modCodes[1],2)){
                           case 0 : nameS = "inc r/m8";
                                    break;
                           case 1 : nameS = "dec r/m8";
                                    break;
                           case 2 : nameS = "not available";
                                    break;
                           case 3 : nameS = "not available";
                                    break;
                           case 4 : nameS = "not available";
                                    break;
                           case 5 : nameS = "not available";
                                    break;
                           case 6 : nameS = "not available";
                                    break;
                           case 7 : nameS = "not available";
                                    break;
                           default : nameS = "not available";
                                    break;
                       }
                        break;    
                        
                  }
         
         opCOD3 = findCodeAndName(opCD ,mcCOD,cleaner(nameS.toLowerCase(),0),
                 updateCT);
         if (opCOD3 != null){
         
           if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
     if (debugCode.equalsIgnoreCase("debug")){      
        System.out.printf("4B %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
     }
     }

    }
     if ((mcCOD[0].charAt(0) == '5')&&((int) mcCOD[0].charAt(1) >= (int)'0') && 
              ((int) mcCOD[0].charAt(1) <= (int)'7')){    
              opCodesPush[0] = "50";
              opCOD3 = findCode(opCD,opCodesPush);
           if (opCOD3 != null){
         if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
     if (debugCode.equalsIgnoreCase("debug")){
         System.out.printf("5 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
     }   
     }
        
     }

      if ((mcCOD[0].charAt(0) == '4')&&((int) mcCOD[0].charAt(1) >= (int)'0') && 
              ((int) mcCOD[0].charAt(1) <= (int)'7')){    
              opCodesPush[0] = "40";
              opCOD3 = findCode(opCD,opCodesPush);
           if (opCOD3 != null){
         if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
     if (debugCode.equalsIgnoreCase("debug")){
         System.out.printf("5.1 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
     }   
     }
        
     }
        

     if ((mcCOD[0].charAt(0) == '5')&&((int) mcCOD[0].charAt(1) >= (int)'8')){ 
              opCodesPush[0] = "58";   
         opCOD3 = findCode(opCD,opCodesPush);
           if (opCOD3 != null){
                    if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
      if (debugCode.equalsIgnoreCase("debug")){
         System.out.printf("6 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
      }
     }
   
     }
     
     if ((mcCOD[0].toLowerCase().charAt(0) == 'b')&&((int) mcCOD[0].
             charAt(1) >= (int)'8') && 
              ((int) mcCOD[0].charAt(1) <= (int)'F')){    
                opCodesPush[0] = "B8";   
                opCOD3 = findCode(opCD,opCodesPush);
          if (opCOD3 != null){
          if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
       if (debugCode.equalsIgnoreCase("debug")){
         System.out.printf("7 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
       }
     }
           
     }

   if ((mcCOD[0].charAt(0) == '4')&&((int) mcCOD[0].charAt(1) >= (int)'8') && 
              ((int) mcCOD[0].charAt(1) <= (int)'F')){    
                opCodesPush[0] = "48";   
                opCOD3 = findCode(opCD,opCodesPush);
          if (opCOD3 != null){
          if ( validMODRM(opCOD3.getMRMCODE())){ 
             opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes() +
                 opCodeCTUpdate(modCodes)); 
           }else{
              opCOD3.setCurrentBytes(opCOD3.getAdditionalBytes()); 
           }   
      if (debugCode.equalsIgnoreCase("debug")){
         System.out.printf("8 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
      }
     }
           
     }
          
     if (opCOD3 != null){
        if (debugCode.equalsIgnoreCase("debug")){ 
         System.out.printf("8 %30s %5s %3d %3d %3d %3s %4s %4s %n",
                opCOD3.getOpcodeIns(),opCOD3.getMRMCODE(),
                opCOD3.getAdditionalBytes(), updateCT,opCodeCTUpdate(modCodes),
                modCodes[0],modCodes[1],modCodes[2]);
        } 
     }
   
  return opCOD3;
}
/**
 * Function that searches the instruction set for the listed OpCode and returns 
 * the required instruction set object
 * @param opCD -  list of all supported instructionSet opcodes  
 * @param mcCOD - current opcode read from instruction set
 * @return - instructionSet found based on opcode
 */
public static instructionSet findCode(List<instructionSet> opCD, String[] mcCOD)
{  
      instructionSet opCOD4 = null;
      for(instructionSet opCOD2 : opCD)
      {
          if (cleaner(mcCOD[0],0).toLowerCase().equalsIgnoreCase(cleaner(opCOD2.
                  getOpcodeCD(),0).
                  toLowerCase())){
              opCOD4 = opCOD2;
          }
      }
  return opCOD4;
}
/**
 * Function that searches the instruction set for the listed OpCode and 
 * Instruction and returns the required instruction set object
 * @param opCD -  list of all supported instructionSet opcodes  
 * @param mcCODFCN - current opcode read from instruction set
 * @param nameOP - instruction read from instruction set
 * @param countUpdate - value to add to number of bytes required from the 
 * instruction set
 * @return - instructionSet found based on opcode 
 */
public static instructionSet findCodeAndName(List<instructionSet> opCD, 
        String[] mcCODFCN, String nameOP, int countUpdate)
{  
      instructionSet opCOD4 = null;
       for(instructionSet opCOD2 : opCD)
      {  if (cleaner(mcCODFCN[0],0).toLowerCase().
                  equalsIgnoreCase(opCOD2.getOpcodeCD().
                  toLowerCase()) && cleaner(nameOP,0).toLowerCase().
                  equalsIgnoreCase(cleaner(opCOD2.getOpcodeIns(),0).
                  toLowerCase())){
              opCOD4 = opCOD2;
              opCOD4.setCurrentBytes(opCOD2.getAdditionalBytes() + 
                      countUpdate);
          }
      }
       
  return opCOD4;
}
/**
 * Function that searches the instruction set for the listed OpCode and 
 * Secondary OpCode byte and returns the required instruction set object
 * @param opCD - list of all supported instructionSet opcodes 
 * @param mcCODCAC  - current opcode read from instruction set
 * @param countUpdate - value to add to number of bytes required from the 
 * instruction set
 * @return - instructionSet found based on opcode
 */
public static instructionSet findCodeAndCode2(List<instructionSet> opCD, 
        String[] mcCODCAC, int countUpdate)
{  
      instructionSet opCOD4 = null;
      String operation = "16";
      String excluding = "r/m"+operation;
      String excluding1 = "r"+operation;
       for(instructionSet opCOD2 : opCD)
      {
          if (mcCODCAC[0].toLowerCase().equalsIgnoreCase(opCOD2.getOpcodeCD().
                  toLowerCase()) && cleaner(mcCODCAC[1],0).toLowerCase().
                  equalsIgnoreCase(cleaner(opCOD2.getOpcodeCD1(),0).toLowerCase()) 
                  && ! opCOD2.getOpcodeIns().contains(excluding)
                  && ! opCOD2.getOpcodeIns().contains(excluding1)){
              opCOD4 = opCOD2;
          }
      }
  return opCOD4;
}

/**
 * The following function will be a list of available register values that are 
 * decoded by the ModMR value
 * @param regValue - integer representation of register value 
 * @param regValueByte - binary representation of the register value
 * @param regNameByte - the byte name value for the register
 * @param regNameWord - the word name value for the register
 * @param regNameDWord - the dword name value for the register
 * @return - return a list of register values
 */
public static List<regCode> addRegCode(int regValue, String regValueByte, 
        String regNameByte,String regNameWord, String regNameDWord)
{
    List<regCode> regCodeList = new ArrayList<regCode>();
        regCode newRegCode = new regCode();
        newRegCode.setRegNameByte(regNameByte);
        newRegCode.setRegNameDWord(regNameDWord);
        newRegCode.setRegValue(regValue);
        newRegCode.setRegNameWord(regNameWord);
        newRegCode.setRegValueByte(regValueByte);
        regCodeList.add(newRegCode);
    return regCodeList;
}
/**
 * The following function returns a list of all segment address values
 * @param sregValue - integer representation of the segment address value
 * @param sregValueByte - binary representation of the segment address value
 * @param sregNameByte - byte name of the segment address value
 * @return - returns list of all segment address values objects
 */
public static List<segRegister> addSRegCode(int sregValue, String sregValueByte, 
        String sregNameByte)
{
    List<segRegister> sregCodeList = new ArrayList<segRegister>();
        segRegister snewRegCode = new segRegister();
        snewRegCode.setsRegNameByte(sregNameByte);
        snewRegCode.setsRegValue(sregValue);
        snewRegCode.setsRegValueByte(sregValueByte);
        sregCodeList.add(snewRegCode);
    return sregCodeList;
}
/**
 * The following function returns a list of all supported opcode instruction 
 * sets supported by the program
 * @param codeID - integer place value of the opcode instruction set in the list
 * @param opCode1 - opcode byte value of the instruction set
 * @param name - opcode instruction name 
 * @param codeCD -opcode instruction byte hex value 
 * @param codeIns - opcode instruction details 
 * @param codeDes - opcode instruction description
 * @param byteCT - opcode instruction count value
 * @param MRC - opcode ModRM encoding value
 * @return - return list of all supported opcode instruction objects
 */
public static List<instructionSet> addOpCodes(int codeID, int opCode1, String name, 
        String codeCD, String codeIns, String codeDes, int byteCT, String MRC ){
        List<instructionSet>  opcodeList = new ArrayList<instructionSet>();
            instructionSet newOpCode = new instructionSet();
            newOpCode.setOpcodeID(codeID);
            newOpCode.setOpcode(opCode1);
            newOpCode.setOpcodeName(name);
            newOpCode.setOpcodeCD(codeCD);
            newOpCode.setOpCodeIns(codeIns);
            newOpCode.setOpcodeDes(codeDes);
            newOpCode.setAdditionalBytes(byteCT);
            newOpCode.setMRMCODE(MRC);
            opcodeList.add(newOpCode);
   return opcodeList; 
}
/**
 * The following function returns a list of all supported opcode instruction 
 * sets supported by the program
 * @param codeID - Instruction set integer position value
 * @param MRC - Instruction set details
 * @return - return a list of all instructions that should placed in instruction 
 * stack
 */
public static List<instructStack> addInstructStack(int codeID, String MRC ){
            List<instructStack>  instuctList = new ArrayList<instructStack>();

            String[] newInData;
            
            instructStack newInstuct = new instructStack();
            
            newInstuct.setInstructCD(codeID);
            newInData = MRC.split("@");
            newInstuct.setInstructHex(Integer.toHexString(codeID));
            switch(newInData.length){
                case 0 :   newInstuct.setInstructData("");
                           break;
                case 1 :    newInstuct.setInstructData(String.
                             format("%-8s %-22s %-4s %-5s ",
                             newInData[0],"","",""));
                            break;
                case 2 :    newInstuct.setInstructData(String.
                            format("%-8s %-22s %-4s %-5s %n",
                             newInData[0],newInData[1],"",""));
                            break;
                case 3 :    newInstuct.setInstructData(String.
                             format("%-8s %-22s %-4s %-5s %n",
                            newInData[0],newInData[1],newInData[2],""));
                            break;
                case 4 :    newInstuct.setInstructData(String.
                            format("%-8s %-22s %-4s %-5s %n",
                            newInData[0],newInData[1],newInData[2],
                            newInData[3]));
                            break;

                default :  newInstuct.setInstructData(MRC);
                           break;
            }        
            instuctList.add(newInstuct);
            
   return instuctList; 
}
/**
 * The following function returns a list of all supported opcode instruction 
 * sets supported by the program
 * @param codeID - integer place value of the opcode instruction set in the list
 * @param opCode1 - opcode byte value of the instruction set
 * @param name - opcode instruction name 
 * @param codeCD -opcode instruction byte hex value 
 * @param codeIns - opcode instruction details 
 * @param codeDes - opcode instruction description
 * @param opCode2 - secondary opcode byte value of the instruction set
 * @param codeCD2 - secondary opcode instruction byte hex value 
 * @param byteCT - opcode instruction count value
 * @param MRC - opcode ModRM encoding value
 * @return - return list of all supported opcode instruction objects
 */
public static List<instructionSet> addOpCodes2(int codeID, int opCode1, 
        String name, String codeCD, String codeIns, String codeDes, 
        int opCode2, String codeCD2, int byteCT, String MRC){
        List<instructionSet>  opcodeList = new ArrayList<instructionSet>();
            instructionSet newOpCode = new instructionSet();
            newOpCode.setOpcodeID(codeID);
            newOpCode.setOpcode(opCode1);
            newOpCode.setOpcode1(opCode2);
            newOpCode.setOpcodeName(name);
            newOpCode.setOpcodeCD(codeCD);
            newOpCode.setOpcodeCD1(codeCD2);
            newOpCode.setOpCodeIns(codeIns);
            newOpCode.setOpcodeDes(codeDes);
            newOpCode.setAdditionalBytes(byteCT);
            newOpCode.setMRMCODE(MRC);
            opcodeList.add(newOpCode);
   return opcodeList; 
}
/**
 * The following function returns a list of all supported opcode instruction 
 * sets supported by the program
 * @param codeID - integer place value of the opcode instruction set in the list
 * @param opCode1 - opcode byte value of the instruction set
 * @param name - opcode instruction name 
 * @param codeCD -opcode instruction byte hex value 
 * @param codeIns - opcode instruction details 
 * @param codeDes - opcode instruction description
 * @param opCode2 - secondary opcode byte value of the instruction set
 * @param codeCD2 - secondary opcode instruction byte hex value 
 * @param opCode3 - third opcode byte value of the instruction set
 * @param codeCD3 - third opcode instruction byte hex value
 * @param byteCT - opcode instruction count value
 * @param MRC - opcode ModRM encoding value
 * @return - return list of all supported opcode instruction objects
 */
public static List<instructionSet> addOpCodes3(int codeID, int opCode1, 
        String name, String codeCD, String codeIns, String codeDes, 
        int opCode2, String codeCD2, int opCode3, String codeCD3, 
        int byteCT, String MRC){
        List<instructionSet>  opcodeList = new ArrayList<instructionSet>();
            instructionSet newOpCode = new instructionSet();
            newOpCode.setOpcodeID(codeID);
            newOpCode.setOpcode(opCode1);
            newOpCode.setOpcode1(opCode2);
            newOpCode.setOpcode2(opCode3);
            newOpCode.setOpcodeName(name);
            newOpCode.setOpcodeCD(codeCD);
            newOpCode.setOpcodeCD1(codeCD2);
            newOpCode.setOpcodeCD2(codeCD3);
            newOpCode.setOpCodeIns(codeIns);
            newOpCode.setOpcodeDes(codeDes);
            newOpCode.setAdditionalBytes(byteCT);
            newOpCode.setMRMCODE(MRC);
            opcodeList.add(newOpCode);
   return opcodeList; 
}
/**
 * Function that builds the register list
 * @return - returns a list of register objects
 */
public static List<regCode> buildRegCode(){
    List<regCode> regCodeListing = new ArrayList<regCode>();
    
    regCodeListing.addAll(addRegCode(0b000, "000","al","ax","eax"));
    regCodeListing.addAll(addRegCode(0b001, "001","cl","cx","ecx"));
    regCodeListing.addAll(addRegCode(0b010, "010","dl","dx","edx"));
    regCodeListing.addAll(addRegCode(0b011, "011","bl","bx","ebx"));
    regCodeListing.addAll(addRegCode(0b100, "100","ah","sp","esp"));
    regCodeListing.addAll(addRegCode(0b101, "101","ch","bp","ebp"));
    regCodeListing.addAll(addRegCode(0b110, "110","dh","si","esi"));
    regCodeListing.addAll(addRegCode(0b111, "111","bh","di","edi"));
    
    
    return regCodeListing;
}
/**
 * Function that builds a list of segment address objects
 * @return - returns a list of all segment address objects
 */
public static List<segRegister> buildSRegCode(){
    List<segRegister> sregCodeListing = new ArrayList<segRegister>();
    
    sregCodeListing.addAll(addSRegCode(0x0000, "0000","ds"));
    sregCodeListing.addAll(addSRegCode(0x0001, "0001","es"));
    sregCodeListing.addAll(addSRegCode(0x0002, "0002","fs"));
    sregCodeListing.addAll(addSRegCode(0x0003, "0003","gs"));
    
    return sregCodeListing;
}
/**
 * Function that builds a list of all support opcode instruction sets based on 
 * the requirement from the project deliverable 
 * @return - returns list of support opcode instruction sets
 */
public static List<instructionSet> buildMachie(){
    List<instructionSet>  word2 = new ArrayList<instructionSet>();
    int count = 0;
    
    word2.addAll(addOpCodes(count,0x04,"ADD","04","ADD AL, imm8",
            "Add imm8 to AL",1,"I"));
    word2.addAll(addOpCodes(count++,0x05,"ADD","05","ADD AX, imm16",
            "Add imm16 to AX",2,"I"));
    word2.addAll(addOpCodes(count++,0x05,"ADD","05","ADD EAX, imm32",
            "Add imm16 to EAX",4,"I"));
    word2.addAll(addOpCodes(count++,0x80,"ADD","80","ADD r/m8, imm8",
            "Add imm8 to r/m8",2,"MI"));   
    word2.addAll(addOpCodes(count++,0x81,"ADD","81","ADD r/m16, imm16",
            "Add imm16 to r/m16",3,"MI"));  
    word2.addAll(addOpCodes(count++,0x81,"ADD","81","ADD r/m32, imm32",
            "Add imm32 to r/m32",5,"MI"));  
    word2.addAll(addOpCodes(count++,0x83,"ADD","83","ADD r/m16, imm8",
            "Add signed-extend imm8 to r/m16",2,"MI")); 
    word2.addAll(addOpCodes(count++,0x83,"ADD","83","ADD r/m32, imm8",
            "Add signed-extend imm8 to r/m32",2,"MI"));

    word2.addAll(addOpCodes(count++,0x00,"ADD","00","ADD r/m8, r8",
            "Add r8 to r/m8",1,"MR"));
    
    word2.addAll(addOpCodes(count++,0x01,"ADD","01","ADD r/m16, r16",
            "Add r16 to r/m16",1,"MR"));
    
    word2.addAll(addOpCodes(count++,0x01,"ADD","01","ADD r/m32, r32",
            "Add r32 to r/m32",1,"MR"));
    
    word2.addAll(addOpCodes(count++,0x02,"ADD","02","ADD r8, r/m8",
            "Add r/m8 to r8",1,"RM"));
    
    word2.addAll(addOpCodes(count++,0x03,"ADD","03","ADD r16, r/m16",
            "Add r16, r/m16",1,"RM"));
    
    word2.addAll(addOpCodes(count++,0x03,"ADD","03","ADD r32, r/m32",
            "Add r/m32 to r32",1,"RM"));
    
    word2.addAll(addOpCodes(count++,0x24,"AND","24","AND AL, imm8",
            "AL AND imm8",1,"I"));
    
    word2.addAll(addOpCodes(count++,0x25,"AND","25","AND AX, imm16",
            "AX AND imm16",2,"I"));
    
    word2.addAll(addOpCodes(count++,0x25,"AND","25","AND EAX, imm32",
            "EAX AND imm32",4,"I"));
    
    word2.addAll(addOpCodes(count++,0x80,"AND","80","AND r/m8, imm8","r/m8 "
            + "AND imm8",2,"MI"));
   
    
    word2.addAll(addOpCodes(count++,0x81,"AND","81","AND r/m16, imm16","r/m16 "
            + "AND imm16",3,"MI"));
     
    
    word2.addAll(addOpCodes(count++,0x81,"AND","81","AND r/m32, imm32","r/m32 "
            + "AND imm32",5,"MI"));
     
    
    word2.addAll(addOpCodes(count++,0x83,"AND","83","AND r/m16, imm8","r/m16 "
            + "AND imm8 (sign-extended)",3,"MI"));
     
    
    word2.addAll(addOpCodes(count++,0x83,"AND","83","AND r/m32, imm8","r/m32 "
            + "AND imm8 (sign-extended)",2,"MI"));
     
    
    word2.addAll(addOpCodes(count++,0x20,"AND","20","AND r/m8, imm8","r/m8 "
            + "AND r8",2,"MR"));
     
    
    word2.addAll(addOpCodes(count++,0x21,"AND","21","AND r/m16, imm16","r/m16 "
            + "AND r16",3,"MR"));
     
    
    word2.addAll(addOpCodes(count++,0x21,"AND","21","AND r/m32, r32","r/m32 "
            + "AND r32",1,"MR"));
     
    
    word2.addAll(addOpCodes(count++,0x22,"AND","22","AND r8, r/m8","r8 AND "
            + "r/m8",1,"RM"));
     
    
    word2.addAll(addOpCodes(count++,0x23,"AND","23","AND r16, r/m16","r16 AND "
            + "r/m16",1,"RM"));
     
    
    word2.addAll(addOpCodes(count++,0x23,"AND","23","AND r32, r/m32","r32 AND "
            + "r/m32",1,"RM"));
     
    
    word2.addAll(addOpCodes(count++,0xE8,"CALL","E8","Call rel16","Call near, "
            + "relative, displacement relative to next instruction",2,"M"));
     
    
    word2.addAll(addOpCodes(count++,0xE8,"CALL","E8","Call rel32","Call near, "
            + "relative, displacement relative to next instruction. 32-bit "
            + "displacement sign extended to 64-bits in 64-bit mode",4,"M"));
    
    
    word2.addAll(addOpCodes(count++,0xFF,"CALL","FF","Call r/m16","Call near, "
            + "absolute indirect, address given in r/m16",1,"M"));
     
 
    word2.addAll(addOpCodes(count++,0xFF,"CALL","FF","Call r/m32","Call near, "
            + "absolute indirect, address given in r/m32",1,"M"));
    
 
    word2.addAll(addOpCodes(count++,0x9A,"CALL","9A","Call ptr16:16",
            "Call near, absolute, address given in operand",0,"D"));
    
    
    word2.addAll(addOpCodes(count++,0x9A,"CALL","9A","Call ptr16:32",
            "Call near, absolute, address given in operand",0,"D"));
       
    
    word2.addAll(addOpCodes(count++,0xFF,"CALL","FF","Call m16:16",
            "Call far, absolute indirect address given in m16:16."
            + " In 32-bit mode: if selector points to a gate, then RIP = 32-bit"
            + " zero extended displacement taken frm gate; else RIP = zero "
            + " extended 16 bit offset from gate; else RIP = zero extended "
            + " 16-bit offset from far pointer reference in the instruction ",1,
            "M"));
   
        
    word2.addAll(addOpCodes(count++,0xFF,"CALL","FF","Call m16:32"," In "
            + "64-bit mode: if selector points to a gate, "
            + " then RIP = 64-bit zero extended displacement taken frm gate; "
            + " else RIP = zero extended 32-bit offset from far pointer "
            + " reference in the instruction ",1,"M"));
          
    word2.addAll(addOpCodes(count++,0x3C,"CMP","3C","CMP AL, imm8"," Compare "
            + "imm8 with AL ",1,"I"));    
        
    word2.addAll(addOpCodes(count++,0x3D,"CMP","3D","CMP AX, imm16"," Compare "
            + "imm16 with AX ",2,"I")); 
        
    word2.addAll(addOpCodes(count++,0x3D,"CMP","3D","CMP EAX, imm32"," Compare "
            + "imm32 with EAX ",4,"I")); 
        
    word2.addAll(addOpCodes(count++,0x80,"CMP","80","CMP r/m8, imm8"," Compare "
            + "imm8 with r/m8 ",2,"MI")); 
        
    word2.addAll(addOpCodes(count++,0x81,"CMP","81","CMP r/m16, imm16",
            " Compare imm16 with r/m16 ",3,"MI")); 
              
    word2.addAll(addOpCodes(count++,0x81,"CMP","81","CMP r/m32, imm32",
            " Compare imm32 with r/m32 ",5,"MI"));
        
    word2.addAll(addOpCodes(count++,0x83,"CMP","83","CMP r/m16, imm8",
            " Compare imm8 with r/m16 ",2,"MI"));
        
    word2.addAll(addOpCodes(count++,0x83,"CMP","83","CMP r/m32, imm8",
            " Compare imm8 with r/m32 ",2,"MI"));
        
    word2.addAll(addOpCodes(count++,0x38,"CMP","38","CMP r/m8, imm8",
            " Compare imm8 with r/m8 ",2,"MR"));
        
    word2.addAll(addOpCodes(count++,0x39,"CMP","39","CMP r/m16, r16",
            " Compare r16 with r/m16 ",1,"MR"));
        
    word2.addAll(addOpCodes(count++,0x39,"CMP","39","CMP r/m32, r32",
            " Compare r32 with r/m32 ",1,"MR"));
        
    word2.addAll(addOpCodes(count++,0x3A,"CMP","3A","CMP r8, r/m8",
            " Compare r/m8 with r8 ",1,"RM"));
       
    word2.addAll(addOpCodes(count++,0x3B,"CMP","3B","CMP r16, r/m16",
            " Compare r/m16 with r16 ",1,"RM"));
       
    word2.addAll(addOpCodes(count++,0x3B,"CMP","3B","CMP r32, r/m32",
            " Compare r/m32 with r32 ",1,"RM"));
       
    word2.addAll(addOpCodes(count++,0xFE,"DEC","FE","DEC r/m8",
            " Decrement r/m8 by 1 ",1,"M"));
       
    word2.addAll(addOpCodes(count++,0xFF,"DEC","FF","DEC r/m16",
            " Decrement r/m16 by 1 ",1,"M"));
    
    word2.addAll(addOpCodes(count++,0xFF,"DEC","FF","DEC r/m32",
            " Decrement r/m32 by 1 ",1,"M"));

    word2.addAll(addOpCodes(count++,0x48,"DEC","48","DEC r16",
            " Decrement r16 by 1 ",0,"O"));

    word2.addAll(addOpCodes(count++,0x48,"DEC","48","DEC r32",
            " Decrement r32 by 1 ",0,"O"));

    word2.addAll(addOpCodes(count++,0xF6,"IDIV","F6","IDIV r/m8",
            " Signed divide AX by r/m8, with result store in:"
            + " AL <-- Quotient, AH <-- Remainder ",1,"M"));    
    
    word2.addAll(addOpCodes(count++,0xF7,"IDIV","F7","IDIV r/m16",
            " Signed divide DX:AX by r/m16, with result store "
            + " in: AX <-- Quotient, DX <-- Remainder ",1,"M"));    
    
    word2.addAll(addOpCodes(count++,0xF7,"IDIV","F7","IDIV r/m32",
            " Signed divide EDX:EAX by r/m32, with result "
            + " store in: EAX <-- Quotient, EDX <-- Remainder ",1,"M"));       
    
    word2.addAll(addOpCodes(count++,0xF6,"IMUL","F6","IMUL r/m8",
            " AX <-- AL * r/m byte ",1,"M"));       

    
    word2.addAll(addOpCodes(count++,0xF7,"IMUL","F7","IMUL r/m16",
            " DX:AX <-- AX * r/m word ",1,"M"));       

    
    word2.addAll(addOpCodes(count++,0xF7,"IMUL","F7","IMUL r/m32",
            " EDX:EAX <-- EAX * r/m32 ",1,"M"));       

    
    word2.addAll(addOpCodes2(count++,0x0F,"IMUL","0F","IMUL r16, r/m16",
            " word register <-- word register * r/m16 ",0xAF,"AF",2,"RM"));       

     
    word2.addAll(addOpCodes2(count++,0x0F,"IMUL","0F","IMUL r32, r/m32",
            " doubleword register <-- doubleword register * "
            + " r/m32 ",0xAF,"AF",2,"RM"));       

     
    word2.addAll(addOpCodes(count++,0x6B,"IMUL","6B","IMUL r16, r/m16, imm8",
            " word register <-- r/m16 * sign-extended"
            + " immediate byte ",2,"RMI"));       

        
    word2.addAll(addOpCodes(count++,0x6B,"IMUL","6B","IMUL r32, r/m32, imm8",
            " doubleword register <-- r/m32 * sign-extended"
            + " immediate byte ",2,"RMI"));       


        
    word2.addAll(addOpCodes(count++,0x69,"IMUL","69","IMUL r16, r/m16, imm16",
            " word register <-- r/m16 * immediate word ",3,"RMI"));       

        
    word2.addAll(addOpCodes(count++,0x69,"IMUL","69","IMUL r32, r/m32, imm32",
            " doubleword register <-- r/m32 * immediate "
            + "doubleword ",5,"RMI"));       

    word2.addAll(addOpCodes(count++,0xFE,"INC","FE","INC r/m8",
            " Increment r/m byte by 1 ",1,"M"));       


    word2.addAll(addOpCodes(count++,0xFE,"INC","FE","INC r/m8",
            " Increment r/m byte by 1 ",1,"M"));       


    word2.addAll(addOpCodes(count++,0xFF,"INC","FF","INC r/m16",
            " Increment r/m word by 1 ",1,"M"));       

  
    word2.addAll(addOpCodes(count++,0xFF,"INC","FF","INC r/m32",
            " Increment r/m doubleword by 1 ",1,"M"));       

 
    word2.addAll(addOpCodes(count++,0x40,"INC","40","INC r16",
            " Increment word register by 1 ",0,"O"));       


    word2.addAll(addOpCodes(count++,0x40,"INC","40","INC r32",
            " Increment doubleword register by 1 ",0,"O"));       


    word2.addAll(addOpCodes(count++,0xEB,"JMP","EB","JMP rel8",
            " Jump short, RIP = RIP + 8-bit displacement sign"
            + " extended to 64-bits ",1,"D"));       


    word2.addAll(addOpCodes(count++,0xE9,"JMP","E9","JMP rel16",
            " Jump near, relative, displacement relative to"
            + " next instruction. Not supported in 64-bit mode",2,"D"));       


    word2.addAll(addOpCodes(count++,0xE9,"JMP","E9","JMP rel32",
            " Jump near, relative, RIP = RIP + 32-bit"
            + " displacement sign extended to 64-bits",4,"D"));       


    word2.addAll(addOpCodes(count++,0xFF,"JMP","FF","JMP r/m16",
            " Jump near, absolute indirect, address = zero-"
            + "extended r/m16. Not supported in 64-bit mode",1,"M"));       


    word2.addAll(addOpCodes(count++,0xFF,"JMP","FF","JMP r/m32",
            " Jump near, absolute indirect, address given in"
            + " r/m32. Not supported in 64-bit mode",1,"M"));       


    word2.addAll(addOpCodes(count++,0xEA,"JMP","EA","JMP ptr16:16",
            " Jump far, absolute, address given in operand",2,"D"));       


    word2.addAll(addOpCodes(count++,0xEA,"JMP","EA","JMP ptr16:32",
            " Jump far, absolute, address given in operand",4,"D"));       


    word2.addAll(addOpCodes(count++,0xFF,"JMP","FF","JMP m16:16",
            " Jump far, absolute indirect, address given in"
            + " m16:16",2,"D"));       

 
    word2.addAll(addOpCodes(count++,0xFF,"JMP","FF","JMP m16:32",
            " Jump far, absolute indirect, address given in"
            + " m16:32",4,"D"));       


    word2.addAll(addOpCodes(count++,0x74,"JZ","74","JZ rel8",
            " Jump short if zero (ZF = 1)",1,"D"));       

     
    word2.addAll(addOpCodes2(count++,0x0F,"JZ","0F","JZ rel16",
            " Jump near if 0 (ZF = 1). Not supported  in 64-bit mode",
            0x84,"84",2,"D"));       

     
    word2.addAll(addOpCodes2(count++,0x0F,"JZ","0F","JZ rel32",
            " Jump near if 0 (ZF = 1). ",0x84,"84",5,"D"));       


    word2.addAll(addOpCodes(count++,0x75,"JNZ","75","JNZ rel8",
            " Jump short if zero (ZF = 0)",1,"D"));    
    
     
    word2.addAll(addOpCodes2(count++,0x0F,"JNZ","0F","JZ rel16",
            " Jump near if 0 (ZF = 1). Not supported  in 64-bit mode",
            0x85,"85",3,"D"));       

     
    word2.addAll(addOpCodes2(count++,0x0F,"JNZ","0F","JZ rel32",
            " Jump near if 0 (ZF = 1).",
            0x85,"85",5,"D"));       


    word2.addAll(addOpCodes(count++,0x8D,"LEA","8D","LEA r16,m",
            " Store effective address for m in register r16",1,"RM"));    
    

    word2.addAll(addOpCodes(count++,0x8D,"LEA","8D","LEA r32,m",
            " Store effective address for m in register r32",1,"RM"));    
    

    word2.addAll(addOpCodes(count++,0x88,"MOV","88","MOV r/m8,r8",
            " Move r8 to r/m8",1,"MR"));    
    


    word2.addAll(addOpCodes(count++,0x89,"MOV","89","MOV r/m16,r16",
            " Move r16 to r/m16",1,"MR"));    
    
  
    word2.addAll(addOpCodes(count++,0x89,"MOV","89","MOV r/m32,r32",
            " Move r32 to r/m32",1,"MR"));    
    
 
    word2.addAll(addOpCodes(count++,0x8A,"MOV","8A","MOV r8,r/m8",
            " Move r/m8 to r8",1,"RM"));    
    
 
 
    word2.addAll(addOpCodes(count++,0x8B,"MOV","8B","MOV r16,r/m16",
            " Move r/m16 to r16",1,"RM"));    
    
 
    word2.addAll(addOpCodes(count++,0x8B,"MOV","8B","MOV r32,r/m32",
            " Move r/m32 to r32",1,"RM"));    
    
 
    word2.addAll(addOpCodes(count++,0x8C,"MOV","8C","MOV r/m16, Sreg**",
            " Move segment register to r/m16",1,"MR"));    
    
 
    word2.addAll(addOpCodes(count++,0x8E,"MOV","8E","MOV Sreg,r/m16**",
            " Move r/m16 to segment register",1,"RM"));    
    
 
    word2.addAll(addOpCodes(count++,0xA0,"MOV","A0","MOV AL,moffs8*",
            " Move byte at (seg:offset) to AL",1,"FD"));    
    
 
    word2.addAll(addOpCodes(count++,0xA1,"MOV","A1","MOV AX,moffs16*",
            " Move word at (seg:offset) to AX ",2,"FD"));    
    
 
    word2.addAll(addOpCodes(count++,0xA1,"MOV","A1","MOV EAX,moffs32*",
            " Move doubleword at (seg:offset) to EAX",4,"FD"));    
    
 
    word2.addAll(addOpCodes(count++,0xA2,"MOV","A2","MOV moffs8, AL",
            " Move AL to (seg:offset)",1,"TD"));    
    
 
    word2.addAll(addOpCodes(count++,0xA3,"MOV","A3","MOV moffs16*, AX",
            " Move AX to (seg:offset) ",2,"TD"));    
    
 
    word2.addAll(addOpCodes(count++,0xA3,"MOV","A3","MOV moffs32*, EAX",
            " Move EAX to (seg:offset) ",4,"TD"));    
    
 
    word2.addAll(addOpCodes(count++,0xB0,"MOV","B0","MOV r8,imm8",
            " Move imm8 to r8",1,"OI"));    
    
 
    word2.addAll(addOpCodes(count++,0xB8,"MOV","B8","MOV r16,imm16",
            " Move imm16 to r16",2,"OI"));    
    
 
    word2.addAll(addOpCodes(count++,0xB8,"MOV","B8","MOV r32,imm32",
            " Move imm32 to r32",4,"OI"));    
    
 
    word2.addAll(addOpCodes(count++,0xC6,"MOV","C6","MOV r/m8,imm8",
            " Move imm8 to r8",2,"MI"));    
    
 
    word2.addAll(addOpCodes(count++,0xC7,"MOV","C7","MOV r/m16,imm16",
            " Move imm16 to r/m16",3,"MI"));    
    
    word2.addAll(addOpCodes(count++,0xC7,"MOV","C7","MOV r/m32,imm32",
            " Move imm32 to r/m32",5,"MI"));    
    
 
    word2.addAll(addOpCodes2(count++,0x0F,"MOV","0F","MOV r32,CR0-CR7",
            " Move control register to r32",0x20,"20",2,"MR"));    
    
    word2.addAll(addOpCodes2(count++,0x0F,"MOV","0F","MOV CR0-CR7,r32",
            " Move r32 to control register",0x22,"22",2,"RM"));    
    
    word2.addAll(addOpCodes2(count++,0x0F,"MOV","0F","MOV r32,DR0-DR7",
            " Move debug register to r32",0x21,"21",2,"MR"));    
    
    word2.addAll(addOpCodes2(count++,0x0F,"MOV","0F","MOV DR0-DR7,r32",
            " Move r32 to debug register",0x23,"23",2,"RM"));    
     
    word2.addAll(addOpCodes3(count++,0xF2,"MOVSD","F2","MOVSD xmm1,xmm2",
            " Move scalar double-precision floating-point"
            + " value from xmm2 to xmm1 register ",0x0F,"0F",0x10,"10",3,"V"));    
    
 
    word2.addAll(addOpCodes3(count++,0xF2,"MOVSD","F2","MOVSD xmm1,m64",
            " Move scalar double-precision floating-point"
            + " value from m64 to xmm1 register ",0x0F,"0F",0x10,"10",3,"V"));    
    
 
    word2.addAll(addOpCodes3(count++,0xF2,"MOVSD","F2","MOVSD xmm1/m64,xmm2",
            " Move scalar double-precision floating-point"
            + " value from xmm2 to xmm1/m64 register ",0x0F,"0F",0x11,"11",3,
            "V"));    
    
    word2.addAll(addOpCodes(count++,0xA5,"MOVSD","A5","MOVSD",
            " For legacy mode, move word from address DS:(E)SI to ES:(E)DI. "
            + " For 64-bit mode move word at address (R@E)SI to (R@E)DI ",
            0,"ZO"));   
 
    word2.addAll(addOpCodes(count++,0xF6,"MUL","F6","MUL r/m8",
            " Unsigned multiply (AX <-- AL * r/m8) ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0xF7,"MUL","F7","MUL r/m16",
            " Unsigned multiply (DX:AX <-- AX *"
            + " r/m16 ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0xF7,"MUL","F7","MUL r/m32",
            " Unsigned multiply (EDX:EAX <-- EAX *"
            + " r/m32 ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0xF6,"NEG","F6","NEG r/m8",
            " Two'2 complement negate r/m8 ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0xF7,"NEG","F7","NEG r/m16",
            " Two's complement negate r/m16 ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0xF7,"NEG","F7","NEG r/m32",
            " Two's complement negate r/m32 ",1,"M"));    
    
 
    word2.addAll(addOpCodes(count++,0x90,"NOP","90","NOP",
            " One byte no-operation instruction ",0,"ZO"));    
    
 
    word2.addAll(addOpCodes2(count++,0x0F,"NOP","0F","NOP r/m16",
            " Multi-byte no-operation instruction ",0x1F,"1F",2,"M"));    
    
      
    word2.addAll(addOpCodes2(count++,0x0F,"NOP","0F","NOP r/m32",
            " Multi-byte no-operation instruction ",0x1F,"1F",2,"M"));    
    
      
    word2.addAll(addOpCodes(count++,0xF6,"NOT","F6","NOT r/m8",
            " Reverse each bit of r/m8 ",1,"M"));    
    
      
    word2.addAll(addOpCodes(count++,0xF7,"NOT","F7","NOT r/m16",
            " Reverse each bit of r/m16 ",1,"M"));    
    
      
    word2.addAll(addOpCodes(count++,0xF7,"NOT","F7","NOT r/m32",
            " Reverse each bit of r/m32 ",1,"M"));    
    
      
    word2.addAll(addOpCodes(count++,0x0C,"OR","0C","OR AL, imm8",
            " AL OR imm8 ",1,"I"));    
    
    word2.addAll(addOpCodes(count++,0x0D,"OR","0D","OR AX, imm16",
            " AX OR imm16 ",2,"I"));    
        
    word2.addAll(addOpCodes(count++,0x0D,"OR","0D","OR EAX, imm32",
            " EAX OR imm32 ",4,"I"));    
    
    word2.addAll(addOpCodes(count++,0x80,"OR","80","OR r/m8, imm8",
            " r/m8 OR imm8 ",2,"MI"));    
      
    word2.addAll(addOpCodes(count++,0x81,"OR","81","OR r/m16, imm16",
            " r/m16 OR imm16 ",3,"MI"));    
    
    word2.addAll(addOpCodes(count++,0x81,"OR","81","OR r/m32, imm32",
            " r/m32 OR imm32 ",5,"MI"));    
      
    word2.addAll(addOpCodes(count++,0x83,"OR","83","OR r/m16, imm8",
            " r/m16 OR imm8 (sign-extended) ",2,"MI"));    
      
    word2.addAll(addOpCodes(count++,0x83,"OR","83","OR r/m32, imm8",
            " r/m32 OR imm8 (sign-extended) ",2,"MI"));    
      
    word2.addAll(addOpCodes(count++,0x08,"OR","08","OR r/m8, r8",
            " r/m8 OR r8 ",1,"MR"));    
      
    word2.addAll(addOpCodes(count++,0x09,"OR","09","OR r/m16, r16",
            " r/m16 OR r16 ",1,"MR"));    
    
    word2.addAll(addOpCodes(count++,0x09,"OR","09","OR r/m32, r32",
            " r/m32 OR r32 ",1,"MR"));    
 
    word2.addAll(addOpCodes(count++,0x0A,"OR","0A","OR r8, r/m8",
            " r8 OR r/m8 ",1,"RM"));    
    
    word2.addAll(addOpCodes(count++,0x0B,"OR","0B","OR r16, r/m16",
            " r16 OR r/m16 ",1,"RM"));    
    
    word2.addAll(addOpCodes(count++,0x0B,"OR","0B","OR r32, r/m32",
            " r32 OR r/m32 ",1,"RM"));    
   
    word2.addAll(addOpCodes(count++,0x8F,"POP","8F","POP r/m16",
            " Pop top of stack into m16; increment stack pointer. ",1,"M"));    
   
    word2.addAll(addOpCodes(count++,0x8F,"POP","8F","POP r/m32",
            " Pop top of stack into m32; increment stack pointer. ",1,"M"));    
   
    word2.addAll(addOpCodes(count++,0x58,"POP","58","POP r16",
            " Pop top of stack into r16; increment stack pointer. ",0,"O"));    
    word2.addAll(addOpCodes(count++,0x58,"POP","58","POP r32",
            " Pop top of stack into r32; increment stack pointer. ",0,"O"));    
 
    word2.addAll(addOpCodes(count++,0x1F,"POP","1F","POP DS",
            " Pop top of stack into DS; increment stack pointer. ",0,"ZO"));    
    word2.addAll(addOpCodes(count++,0x07,"POP","07","POP ES",
            " Pop top of stack into ES; increment stack pointer. ",0,"ZO"));    
    word2.addAll(addOpCodes(count++,0x17,"POP","17","POP SS",
            " Pop top of stack into SS; increment stack pointer. ",0,"ZO"));    
    word2.addAll(addOpCodes2(count++,0x0F,"POP","0F","POP FS",
            " Pop top of stack into FS; increment stack pointer by 16-bits ",
            0xA1,"A1",1,"ZO"));    
    word2.addAll(addOpCodes2(count++,0x0F,"POP","0F","POP FS",
            " Pop top of stack into FS; increment stack pointer by 32-bits ",
            0xA1,"A1",1,"ZO"));    
    word2.addAll(addOpCodes2(count++,0x0F,"POP","0F","POP GS",
            " Pop top of stack into GS; increment stack pointer by 16-bits ",
            0xA9,"A9",1,"ZO"));    
    word2.addAll(addOpCodes2(count++,0x0F,"POP","0F","POP GS",
            " Pop top of stack into GS; increment stack pointer by 32-bits ",
            0xA9,"A9",1,"ZO"));    
    word2.addAll(addOpCodes(count++,0xFF,"PUSH","FF","PUSH r/m16",
            " Push r/m16 ",1,"M"));    
    word2.addAll(addOpCodes(count++,0xFF,"PUSH","FF","PUSH r/m32",
            " Push r/m32 ",1,"M"));    
    word2.addAll(addOpCodes(count++,0x50,"PUSH","50","PUSH r16",
            " Push r16 ",0,"O"));    
    word2.addAll(addOpCodes(count++,0x50,"PUSH","50","PUSH r32",
            " Push r32 ",0,"O"));    
    word2.addAll(addOpCodes(count++,0x6A,"PUSH","6A","PUSH imm8",
            " Push imm8 ",1,"I"));    
    word2.addAll(addOpCodes(count++,0x68,"PUSH","68","PUSH imm16",
            " Push imm16 ",2,"I"));    
    word2.addAll(addOpCodes(count++,0x68,"PUSH","68","PUSH imm32",
            " Push imm32 ",4,"I"));    
    word2.addAll(addOpCodes(count++,0x0E,"PUSH","0E","PUSH CS",
            " Push CS ",0,"ZO"));    
    word2.addAll(addOpCodes(count++,0x16,"PUSH","16","PUSH SS",
            " Push SS ",0,"ZO"));    
    word2.addAll(addOpCodes(count++,0x1E,"PUSH","1E","PUSH DS",
            " Push DS ",0,"ZO"));    
    word2.addAll(addOpCodes(count++,0x06,"PUSH","06","PUSH ES",
            " Push ES ",0,"ZO"));
    word2.addAll(addOpCodes2(count++,0x0F,"PUSH","0F","PUSH FS",
            " Push FS ",0xA0,"A0",1,"ZO"));
    word2.addAll(addOpCodes2(count++,0x0F,"PUSH","0F","PUSH GS",
            " Push GS ",0xA8,"A8",1,"ZO"));    
  
    word2.addAll(addOpCodes2(count++,0xF2,"REPNE CMPSB","F2","REPNE CMPS m8,m8",
            " Find matching bytes in ES:[(E)DI] and DS:[(E)SI] ",
            0xA6,"A6",1,"ZO"));    
    
    word2.addAll(addOpCodes2(count++,0xF2,"REPNE CMPSW","F2",
            "REPNE CMPS m16,m16"," Find matching words in ES:[(E)DI] and "
                    + "DS:[(E)SI] ",0xA7,"A7",1,"ZO"));    
        
    word2.addAll(addOpCodes2(count++,0xF2,"REPNE CMPSD","F2","REPNE CMPS m32,m32",
            " Find matching doublewords in ES:[(E)DI] and DS:[(E)SI] ",
            0xA7,"A7",1,"ZO"));    
     word2.addAll(addOpCodes(count++,0xC3,"RETN","C3"," RET ",
            " Near return to calling procedure ",0,"ZO")); 
     word2.addAll(addOpCodes(count++,0xCB,"RETF","CB"," RET ",
            " Far return to calling procedure ",0,"ZO")); 
     word2.addAll(addOpCodes(count++,0xC2,"RETN","C2"," RET imm16",
            " Near return to calling procedure and pop imm16 bytes from "
                    + "stack ",2,"I")); 
     word2.addAll(addOpCodes(count++,0xCA,"RETF","CA"," RET imm16",
            " Far return to calling procedure and pop imm16 bytes from "
                    + "stack ",2,"I"));   
     word2.addAll(addOpCodes(count++,0xD0,"SAL","D0"," SAL r/m8 ",
            " Multiply r/m8 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD2,"SAL","D2"," SAL r/m8, CL",
            " Multiply r/m8 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC0,"SAL","C0"," SAL r/m8, imm8",
            " Multiply r/m8 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SAL","D1"," SAL r/m16,1 ",
            " Multiply r/m16 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SAL","D3"," SAL r/m16, CL ",
            " Multiply r/m16 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SAL","C1"," SAL r/m16, imm8",
            " Multiply r/m16 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SAL","D1"," SAL r/m32,1",
            "  Multiply r/m32 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SAL","D3"," SAL r/m32, CL",
            " Multiply r/m32 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SAL","C1"," SAL r/m32, imm8",
            " Multiply r/m32 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD0,"SAR","DO"," SAR r/m8,1",
            " Signed divide* r/m8 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD2,"SAR","D2"," SAR r/m8, CL ",
            " Signed divide* r/m8 by 2, CL times",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC0,"SAR","C0"," SAR r/m8, imm8",
            " Signed divide* r/m8 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SAR","D1"," SAR r/m16, 1",
            " Signed divide* r/m16 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SAR","D3"," SAR r/m16, CL ",
            " Signed divide* r/m16 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SAR","C1"," SAR r/m16, imm8",
            " Signed divide* r/m16 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SAR","D1"," SAR r/m32, 1",
            " Signed divide* r/m32 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SAR","D3"," SAR r/m32,CL",
            " Signed divide* r/m32 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SAR","C1"," SAR r/m32, imm8",
            " Signed divide* r/m32 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD0,"SHL","D0"," SHL r/m8, 1",
            " Multiply r/m8 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD2,"SHL","D2"," SHL r/m8, CL ",
            " Multiply r/m8 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC0,"SHL","C0"," SHL r/m8, imm8 ",
            " Multiply r/m8 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SHL","D1"," SHL r/m16, 1",
            " Multiply r/m16 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SHL","D3"," SHL r/m16, CL",
            " Multiply r/m16 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SHL","C1"," SHL r/m16, imm8",
            " Multiply r/m16 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SHL","D1"," SHL r/m32, 1",
            " Multiply r/m32 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SHL","D3"," SHL r/m32, CL",
            " Multiply r/m32 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SHL","C1"," SHL r/m32, imm8",
            " Multiply r/m32 by 2, imm8 times ",2,"MI"));   
     
     word2.addAll(addOpCodes(count++,0xD0,"SHR","D0"," SHR r/m8, 1",
            " Unsigned divide r/m8 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD2,"SHR","D2"," SHR r/m8, CL",
            " Unsigned divide r/m8 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC0,"SHR","C0"," SHR r/m8, imm8",
            " Unsigned divide r/m8 by 2, imm8 times ",2,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xD1,"SHR","D1"," SHR r/m16, 1",
            " Unsigned divide r/m16 by 2, once ",1,"M1"));   
     
     word2.addAll(addOpCodes(count++,0xD3,"SHR","D3"," SHR r/m16, CL",
            " Unsigned divide r/m16 by 2, CL times ",1,"MC"));   
     
     word2.addAll(addOpCodes(count++,0xC1,"SHR","C1"," SHR r/m16, imm8",
            " Unsigned divide r/m16 by 2, imm8 times ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0xD1,"SHR","D1"," SHR r/m32, 1",
            " Unsigned divide r/m32 by 2, once ",1,"M1"));   
     
     
     word2.addAll(addOpCodes(count++,0xD3,"SHR","D3"," SHR r/m32, CL",
            " Unsigned divide r/m32 by 2, CL times ",1,"MC"));   
     
     
     word2.addAll(addOpCodes(count++,0xC1,"SHR","C1"," SHR r/m32, imm8",
            " Unsigned divide r/m32 by 2, imm8 times ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x1C,"SBB","1C"," SBB AL, imm8",
            " Subtract with borrow imm8 from AL ",1,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x1D,"SBB","1D"," SBB AX, imm16",
            " Subtract with borrow imm16 from AX ",2,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x1D,"SBB","1D"," SBB EAX, imm32",
            " Subtract with borrow imm32 from EAX ",4,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x80,"SBB","80"," SBB r/m8, imm8",
            " Subtract with borrow imm8 from r/m8 ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x81,"SBB","81"," SBB r/m16, imm16",
            " Subtract with borrow imm16 from r/m16 ",3,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x81,"SBB","81"," SBB r/m32, imm32",
            " Subtract with borrow imm32 from r/m32 ",5,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x83,"SBB","83"," SBB r/m16, imm8",
            " Subtract with borrow signed extended imm8 from r/m16 ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x83,"SBB","83"," SBB r/m32, imm8",
            " Subtract with borrow signed extended imm8 from r/m32 ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x18,"SBB","18"," SBB r/m8, r8",
            " Subtract with borrow r8 from r/m8 ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x19,"SBB","19"," SBB r/m16, r16",
            " Subtract with borrow r16 from r/m16 ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x19,"SBB","19"," SBB r/m32, r32",
            " Subtract with borrow r32 from r/m32 ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x1B,"SBB","1B"," SBB r16, r/m16",
            " Subtract with borrow r/m16 from r16 ",1,"RM"));   
     
     
     word2.addAll(addOpCodes(count++,0x1B,"SBB","1B"," SBB r32, r/m32",
            " Subtract with borrow r/m32 from r32 ",1,"RM"));   
     
     
     word2.addAll(addOpCodes(count++,0xA8,"TEST","A8"," TEST AL, imm8 ",
            " AND imm8 with AL; set SF, ZF, PF according to results ",1,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0xA9,"TEST","A9"," TEST AX, imm16",
            " AND imm16 with AX; set SF, ZF, PF according to result ",2,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0xA9,"TEST","A9"," TEST EAX, imm32",
            " AND imm32 with EAX; set SF, ZF, PF according to result ",4,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0xF6,"TEST","F6"," TEST r/m8, imm8",
            " AND imm8 with r/m8; set SF, ZF, PF according to result ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0xF7,"TEST","F7"," TEST r/m16, imm16",
            " AND imm16 with r/m16; set SF, ZF, PF according to result ",3,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0xF7,"TEST","F7"," TEST r/m32, imm32",
            " AND imm32 with r/m32; set SF,ZF,PF according to result ",5,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x84,"TEST","84"," TEST r/m8, r8",
            " AND r8 with r/m8; set SF, ZF, PF according to result ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x85,"TEST","85"," TEST r/m16, r16",
            " AND r16 with r/m16; set SF, ZF, PF according to result ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x85,"TEST","85"," TEST r/m32, r32",
            " AND r32 with r/m32;  set SF, ZF, PF according to result ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x34,"XOR","34"," XOR AL, imm8",
            " AL XOR imm8 ",1,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x35,"XOR","35"," XOR AX, imm16",
            " AX XOR imm16 ",2,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x35,"XOR","35"," XOR EAX, imm32",
            " EAX XOR imm32 ",4,"I"));   
     
     
     word2.addAll(addOpCodes(count++,0x80,"XOR","80"," XOR r/m8, imm8",
            " r/m8 XOR imm8 ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x81,"XOR","81"," XOR r/m16, imm16",
            " r/m16 XOR imm16 ",3,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x81,"XOR","81"," XOR r/m32, imm32",
            " r/m32 XOR imm32 ",5,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x83,"XOR","83"," XOR r/m16, imm8",
            " r/m16 XOR imm8 (signed-extended) ",2,"MI"));   
     
     
     word2.addAll(addOpCodes(count++,0x83,"XOR","83"," XOR r/m32, imm8",
            " r/m32 XOR imm8 (signed-extended) ",2,"MI"));   
          
     word2.addAll(addOpCodes(count++,0x30,"XOR","30"," XOR r/m8, r8",
            " r/m8 XOR r8 ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x31,"XOR","31"," XOR r/m16, r16",
            " r/m16 XOR r16 ",1,"MR"));   
     
     
     word2.addAll(addOpCodes(count++,0x31,"XOR","31"," XOR r/m32, r32",
            " r/m32 XOR r32 ",1,"MR"));   
     
     word2.addAll(addOpCodes(count++,0x32,"XOR","32"," XOR r8, r/m8",
            " r8 XOR r/m8 ",1,"RM"));   
     
     
     word2.addAll(addOpCodes(count++,0x33,"XOR","33"," XOR r16, r/m16",
            " r16 XOR r/m16 ",1,"RM"));   
     
     
     word2.addAll(addOpCodes(count++,0x33,"XOR","33"," XOR r32, r/m32",
            " r32 XOR r/m32 ",1,"RM"));   
     
     
    return word2;
}
/**
 * Function compares the presented value to determine if the value is a valid 
 * opcode from the list of opcodes. If not valid then returns false
 * @param checkCode - String hex value opcode
 * @return - boolean 
 */
public static boolean validInstruction(String checkCode){
    for(String value :allOpCodes){
        if(checkCode.equalsIgnoreCase(value)){
            return true;
        }
    }
    return false;
}
/**
 * Function compares the presented value to determine if the value is a valid 
 * prefix from the list of prefixes. If not valid then returns false
 * @param checkOp - String hex value opcode
 * @return - boolean
 */
public static boolean validPreFix(String checkOp){
     for(String value : allPrefix){
        if(checkOp.equalsIgnoreCase(value)){
            return true;
        }
    }
    return false;
}
/**
 * Function that valid dates the type of instruction to determine if a MoDRM 
 * value proceeds after the instruction set hex value 
 * @param checkOp - ModRM type value
 * @return - boolean
 */
public static boolean validMODRM(String checkOp){
        for(String value : allMRMCODE){
        if(checkOp.equalsIgnoreCase(value)){
            return true;
        }
    }
    return false; 
}
/**
 * Function determine if the instruction set hex value will require additional 
 * instruction details or if the instruction details may contain data
 * @param checkOp2 - String hex value opcode
 * @return - boolean
 */
public static boolean validMODRM2(String checkOp2){
        for(String value2 : allMRMCODE2){
        if(cleaner(checkOp2.toUpperCase(),0).
                equalsIgnoreCase(cleaner(value2,0).toUpperCase())){
            return true;
        }
        }
    return false; 
}
/**
 * Function builds a string array of the ModRM value 
 * @param modCodeDa - String MODRM value
 * @return - String array of the ModRM value
 */
public static String[] modBuilder(String modCodeDa){
    String[] newModCode = new String[3];
    
    newModCode[0] = modCodeDa.substring(0, 2);
    newModCode[1] = modCodeDa.substring(2, 5);
    newModCode[2] = modCodeDa.substring(5, 8);
    
    return newModCode;
}
/**
 * Function builds a scale index base array based on the SIB opcode binary value
 * @param sibCodeDa - String SIB binary value
 * @return - returns string array of the scale index base value
 */
public static String[] sibBuilder(String sibCodeDa){
    String[] newSibCode = new String[3];
    
    newSibCode[0] = sibCodeDa.substring(0, 2);
    newSibCode[1] = sibCodeDa.substring(2, 5);
    newSibCode[2] = sibCodeDa.substring(5, 8);
    
    return newSibCode;
}
/**
 * Function decodes the reg and r/m values for an instruction set
 * @param modActual - Mod/RM value array
 * @param addCode1 - Hex code values after main instruction set
 * @param r_mData - String Mod/RM type
 * @param sData - integer value Mod/RM code 
 * @param mcCode - complete instruction set list
 * @param cposition - current position in instruction set
 * @return - String decoded instruction set
 */
public static String instructMRMDecoder(String[] modActual, String[] addCode1, 
        String r_mData, int sData, String[] mcCode, int cposition){
    regCode currentReg, currentReg1;
    StringBuilder instructPart2 = new StringBuilder();
    String[] sibCOD = new String[1];
    String[] sidCODES = new String[8];
    int count1 = 0;
    switch(r_mData){
        case "r/m" :
            switch(modActual[0]){

                case "00" : if (modActual[sData].equalsIgnoreCase("100")){
                                  sibCOD[count1] = addCode1[1].toString();
                                     instructPart2.append("[");
                                      sidCODES = sibBuilder(String.format("%8s",
                                              binCodeBuilder(sibCOD)).
                                              replace(" ", "0"));
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"index",1,mcCode,cposition)); 
                               instructPart2.append("]");
                               instructPart2.append("[");
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"base",2,mcCode,cposition));
                               instructPart2.append("]");
                             }
                             else if(modActual[sData].equalsIgnoreCase("101")){
                                 switch(addCode1.length){
                                 case 4 :   instructPart2.append("[dword 0x").
                                            append(getReverseNum3(addCode1,4)).
                                            append("h]");
                                            break;
                                 case 5 :   instructPart2.append("[dword 0x").
                                            append(getReverseNum3(addCode1,4)).
                                            append("h]");
                                            break;
                                 case 6 :   instructPart2.append("[dword 0x").
                                            append(getReverseNum3(addCode1,4)).
                                            append("h]");
                                            break;           
                                 case 7 :   instructPart2.append("[dword 0x").
                                            append(getReverseNum3(addCode1,4)).
                                            append("h]");
                                            break;   
                                            
                                 default :  instructPart2.append("[dword 0x").
                                            append(getReverseNum2(addCode1,4,4)).
                                            append("h]");
                                            break;           
                                 }          
                             }else{
                                currentReg = searchRegTable(modActual[sData]);
                                instructPart2.append("[");
                                instructPart2.append(currentReg.getRegNameDWord());
                                instructPart2.append("]");                                
                            }
                            break;

                case "01" : if (
                                modActual[sData].equalsIgnoreCase("100")){
                                sibCOD[count1] = addCode1[1].toString();
                                instructPart2.append("[");
                                sidCODES = sibBuilder(String.format("%8s",
                                              binCodeBuilder(sibCOD)).
                                              replace(" ", "0"));
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"index",1,mcCode,cposition)); 
                               instructPart2.append("]");
                               instructPart2.append("[");
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"base",2,mcCode,cposition));
                               instructPart2.append("]");
                                switch(addCode1.length){
                                case 4 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,0));
                                           break;
                                case 5 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,1));
                                           break;
                                }  
                    

                            }else{
                            currentReg = searchRegTable(modActual[sData]);
                            instructPart2.append("[");
                            instructPart2.append(currentReg.getRegNameDWord());
                            
                            switch(addCode1.length){
                            case 1 :   instructPart2.append("+0x").
                                       append(getReverseNum(addCode1,0));
                                       break;
                            case 2 :   instructPart2.append("+0x").
                                       append(getReverseNum(addCode1,1));
                                       break;
                            default :  instructPart2.append("+0x").
                                       append(addCode1[1]);
                                       break;
                            }  
                            instructPart2.append("]");
                            }
                            break;
                case "10" : if (modActual[sData].equalsIgnoreCase("100")){
                                 sibCOD[count1] = addCode1[1].toString();
                                     instructPart2.append("[");
                                      sidCODES = sibBuilder(String.format("%8s",
                                              binCodeBuilder(sibCOD)).
                                              replace(" ", "0"));
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"index",1,mcCode,cposition)); 
                               instructPart2.append("]");
                               instructPart2.append("[");
                               instructPart2.append(instructSIBDecoder(sidCODES,
                                       addCode1,"base",2,mcCode,cposition));
                               instructPart2.append("]");
                                switch(addCode1.length){
                                case 4 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,0));
                                           break;
                                case 5 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,1));
                                           break;
                                 default :  instructPart2.append("+0x").
                                       append(addCode1[1]);
                                       break;
                                      
                                }  
                    
                            }else{
                                currentReg = searchRegTable(modActual[sData]);
                                instructPart2.append("[");
                                instructPart2.append(currentReg.getRegNameDWord());
                                
                                switch(addCode1.length){
                                case 4 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,0));
                                           break;
                                case 5 :   instructPart2.append("+0x").
                                           append(getReverseNum(addCode1,1));
                                           break;
                                 default :  instructPart2.append("+0x").
                                       append(addCode1[1]);
                                       break;
                                                                                 
                                }  
                              instructPart2.append("]");  
                            }
                            break;
                case "11" : currentReg = searchRegTable(modActual[sData]);
                            instructPart2.append(currentReg.getRegNameDWord());            
                            break;
            }
            break;
     case "reg" :        
             currentReg1 = searchRegTable(modActual[sData]);
             instructPart2.append(currentReg1.getRegNameDWord());
             break;
    }       
    
    return instructPart2.toString();
}

/**
 * Function decodes the scale, index and base of the SIB values for an 
 * instruction set
 * @param sibActual - SIB value array
 * @param addCode - Hex code values after main instruction set
 * @param r_mData - String Sib type
 * @param sData - integer value of scale
 * @param mcCode - complete instruction set list
 * @param cposition - current position in instruction set
 * @return - String decode instruction set for SIB
 */
public static String instructSIBDecoder(String[] sibActual, String[] addCode, 
        String r_mData, int sData, String[] mcCode, int cposition){
    regCode currentReg, currentReg1;
    String indexCode = "";
    StringBuilder instructPart2 = new StringBuilder();
    switch(r_mData){
        case "index" :     
    
                    switch(sibActual[0]){
                        case "00" : indexCode = "]";
                                    break;
                        case "01" : indexCode = "*2]";
                                    break;
                        case "10" : indexCode = "*4]";
                                    break;
                        case "11" : indexCode = "*8]";
                                    break;
                    }       
                    if(sibActual[1].equalsIgnoreCase("100")){
                            currentReg = searchRegTable(sibActual[sData]);
                            instructPart2.append("[");
                            instructPart2.append(currentReg.getRegNameDWord());
                            instructPart2.append(indexCode);

                        }else{
                            instructPart2.append("none");
                     }
                    break;
        case "base" :             
                if(sibActual[sData].equalsIgnoreCase("101")){
                          currentReg1 = searchRegTable(sibActual[sData]);
                          instructPart2.append(",");
                          instructPart2.append(currentReg1.getRegNameDWord());
                      }else{ 
                          switch(addCode.length){
                              case 1 :   instructPart2.append(
                                         getReverseNum(addCode,0));
                                         instructPart2.append(" + [EBP] "); 
                                         break;
                              case 2 :   instructPart2.append(
                                         getReverseNum(addCode,1));
                                         instructPart2.append(" + [EBP] "); 
                                         break;    
                              case 4 :   instructPart2.append(
                                         getReverseNum(addCode,0));
                                         instructPart2.append(" + [EBP] "); 
                                         break;
                              case 5 :   instructPart2.append(
                                         getReverseNum(addCode,1));
                                         instructPart2.append(" + [EBP] "); 
                                         break;
                          }   
                 }
                 break;
    }             
    return instructPart2.toString();
}
/**
 * Function that search the register list for a valid register object
 * @param regFind - String register value
 * @return - returns the correct register object
 */
public static regCode searchRegTable(String regFind){
    List<regCode> curregCode = buildRegCode();
    regCode reg2 = null;
    for(regCode reg1 : curregCode){
        if(reg1.getRegValueByte().equalsIgnoreCase(regFind)){
            return reg1;   
        }
    }
    
    return reg2;
}
/**
 * Function that search the segment address list for a valid segment object
 * @param sregFind - String segment address
 * @return - returns the correct segment address value
 */
public static segRegister searchSRegTable(String sregFind){
    List<segRegister> curregCode = buildSRegCode();
    segRegister sreg2 = null;
    for(segRegister sreg1 : curregCode){
        if(sreg1.getsRegValue() == Integer.parseInt(sregFind, 2)){
            return sreg1;   
        }
    }
    
    return sreg2;
}
/**
 * The following function will build a binary representation of a hex 
 * instruction set
 * @param addCode2 - String array list of require instruction set details
 * @return - String binary representation of the instruction set 
 */
public static String binCodeBuilder(String[] addCode2)
{
    StringBuilder regBin = new StringBuilder();
    for(String value : addCode2){
           regBin.append(hextoBin(value));
    }
    switch(addCode2.length){
        case 3 : return String.format("%3s",regBin.toString()).
                 replace(" ", "0");
        case 5 : return String.format("%8s",regBin.toString()).
                 replace(" ", "0");
        case 6 : return String.format("%8s",regBin.toString()).
                 replace(" ", "0");
        case 8 : return String.format("%8s",regBin.toString()).
                 replace(" ", "0");
        default : return String.format("%8s",regBin.toString()).
                 replace(" ", "0");
    }
  
}
/**
 * Function converts integer values to binary of specific length
 * @param modCodeInt - integer value that must be translated into binary
 * @param length - integer length of binary code
 * @return - String binary representation of the integer
 */
public static String intConvertBinMod(int modCodeInt, int length){
    switch(length){
        case 3 : return String.format("%3s", 
           Integer.toBinaryString(modCodeInt)).replace(' ', '0');
        case 6 : return String.format("%6s", 
           Integer.toBinaryString(modCodeInt)).replace(' ', '0');
        case 8 : return String.format("%8s", 
           Integer.toBinaryString(modCodeInt)).replace(' ', '0');
        case 16 : return String.format("%8s:", 
           Integer.toHexString(modCodeInt)).replace(' ', '0');
        

    }
    return "None";
}
/**
 * Function reveres the hex values of a instruction set
 * @param reversed - String array of instruction set hex values
 * @return - String of revered instruction set bytes, will also remove leading 
 * zeros if present
 */
public static String getReverse(String[] reversed)
{
    String zeroCt;
    StringBuilder reveresed = new StringBuilder();
     for(int z = reversed.length - 1; z >= 0 ; z--){
        reveresed.append(reversed[z]);
    }
     if (reveresed.toString().startsWith("00")){
         zeroCt = reveresed.toString().replaceAll("^0*", "");
         reveresed.delete(0, reveresed.length());
         reveresed.append("0"+zeroCt);
     } 
    return reveresed.toString();
}
/**
 * Function reveres the hex values of a instruction set based on the count
 * @param reversed - String array of instruction set hex values
 * @param count - Integer value that stops the reverse process
 * @return - String of revered instruction set bytes, will also remove leading 
 * zeros if present 
 */
public static String getReverseNum(String[] reversed, int count)
{   
    String zeroCt;
    StringBuilder reveresed = new StringBuilder();
     for(int z = reversed.length - 1; z >= count ; z--){
        reveresed.append(reversed[z]);
    }
     if (reveresed.toString().startsWith("00")){
         zeroCt = reveresed.toString().replaceAll("^0*", "");
         reveresed.delete(0, reveresed.length());
         reveresed.append("0"+zeroCt);
     }      
    return reveresed.toString();
}
/**
 * Function reveres the hex values of a instruction set based on the count
 * @param reversed - String array of instruction set hex values
 * @param count - Integer value that stops the reverse process
 * @param count2 - Integer value that provides starting position for reverse 
 * process
 * @return - String of revered instruction set bytes, will also remove leading 
 * zeros if present
 */
public static String getReverseNum2(String[] reversed, int count, int count2)
{
    String zeroCt;
    StringBuilder reveresed = new StringBuilder();
    int newLength =  (reversed.length - 1) - count2;
     for(int z = newLength; z > newLength - count ; z--){
        
        reveresed.append(reversed[z]);
    }
     if (reveresed.toString().startsWith("00")){
         zeroCt = reveresed.toString().replaceAll("^0*", "");
         reveresed.delete(0, reveresed.length());
         reveresed.append("0"+zeroCt);
     }    
     
     
    return reveresed.toString();
}
/**
 * Function reveres the hex values of a instruction set based on the count
 * @param reversed - String array of instruction set hex values
 * @param count - Integer value that stops the reverse process
 * @return - String of revered instruction set bytes, will also remove leading 
 * zeros if present 
 */
public static String getReverseNum3(String[] reversed, int count)
{
    String zeroCt;
    StringBuilder reveresed = new StringBuilder();
     for(int z = reversed.length - 1; z > 
             ((reversed.length - 1) - count) ; z--){
         
        reveresed.append(reversed[z]);
    }
     
     if (reveresed.toString().startsWith("00")){
         zeroCt = reveresed.toString().replaceAll("^0*", "");
         reveresed.delete(0, reveresed.length());
         reveresed.append("0"+zeroCt);
     }    
    return reveresed.toString();
}

/**
 * Function reveres the hex values of a instruction set based on the count
 * @param single - String array of instruction set hex values
 * @param count - Integer value that stops the reverse process
 * @return 
 */
public static String getSingleNum(String[] single, int count)
{
    StringBuilder singled = new StringBuilder();
     for(int z = 0; z <= single.length - 1; z++){
        singled.append(single[z]);
    }
    return singled.toString();
}
/**
 * Function builds the Instruction code to be returned and added to the 
 * instruction stack
 * @param ccount - Integer value of the current position
 * @param finalCode - Instruction set object 
 * @param addCode - String array list of instruction set hex values
 * @param opCodeD - Current opcode hex value
 * @param mcCODE - Complete list of all instruction sets extracted from file
 * @param OpType - Operation type
 * @return - String of completed decode instruction set details
 */
public static String codeBuilder(int ccount, instructionSet finalCode, 
        String[] addCode, String opCodeD, String[] mcCODE, String OpType )
{
    
    StringBuilder currentCode = new StringBuilder();
    String[] modCodes = new String[3];
    regCode[] finalReg = new regCode[2];
    segRegister sReg = null;
    int codeDef, count1 = 0;
    String[] modCo = new String[1];
    String[] sibCo = new String[1]; 
    String[] displac1 = new String[4];
    
    
    int nextPos;
    int callPos;
    BigInteger number, number2, number3, number4;
     
    modCo[0] = mcCODE[ccount + 1];
    sibCo[0] = mcCODE[ccount + 2];
    
    switch(finalCode.getOpcodeName().toLowerCase())
    {
        case "push" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                      append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                            currentCode.append(value1.toUpperCase());
                       }
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "FF" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          case "50" :
                                        codeDef = Integer.parseInt(opCodeD,16)  
                                                  - finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                              (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                              (finalReg[0].getRegNameDWord());
                                        break;
                          case "68" :  
                                       modCo[count1] = addCode[0];
                                       modCodes = modBuilder(String.
                                               format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       if (! modCodes[0].equalsIgnoreCase("11"))
                                       {    
                                        if (OpType.equalsIgnoreCase("32")){
                                             currentCode.append("dword ");
                                          } else {
                                             currentCode.append("word ");   
                                          }
                                        }
                                        currentCode.append(" 0x");
                                        currentCode.append(getReverseNum3
                                        (addCode,4));
                                        currentCode.append("h ");
                                        break;
                          case "6A" :   currentCode.append("byte");    
                                        currentCode.append(" 0x");
                                        currentCode.append(getReverseNum3
                                        (addCode,1));
                                        currentCode.append("h ");
                                        break;            
                          case "0E" :   currentCode.append("CS");
                                        break;
                          case "16" :   currentCode.append("SS");
                                        break;
                          case "1E" :   currentCode.append("DS");
                                        break;
                          case "06" :   currentCode.append("ES");
                                        break;
                          case "0F" :   switch(finalCode.getOpcodeCD1()){
                                        case "A0" : currentCode.append("FS");
                                                    break;
                                        case "A8" : currentCode.append("GS");
                                                    break;          
                                        }
                                        break;
                                

                      }
                      
                      break;
        case "add" : 
                      currentCode.append(intConvertBinMod(ccount,16))
                              .append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                          currentCode.append(value1.toUpperCase());
                      }
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "04" :   currentCode.append(" al,0x");
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;
                          case "05" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;
                          case "80" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum
                                                (addCode,1));
                                        currentCode.append("h ");
                                      break;
                            case "81" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                            case "83" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                            case "01" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              

                           case "03" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "02" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "00" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;                        
                      }
    
                      break;              
        case "and" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "24" :   currentCode.append(" al,0x");
                                       currentCode.append(getReverse(addCode));
                                       currentCode.append("h ");
                                       break;
                        case "25" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "80" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                            case "81" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                            case "83" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                            case "20" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              

                           case "21" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "23" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;   
                          case "22" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
           
                      }
                      
                      modCodes = modBuilder(String.format("%8s",
                              binCodeBuilder(addCode)).replace(" ", "0"));
                      break;   
        case "call" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");  
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "E8" : 
                                      nextPos = ccount + 1 + 
                                                     addCode.length; 
                                      number2 = new BigInteger(Integer.
                                              toHexString(nextPos),16);
                                      if (OpType.equalsIgnoreCase("32")){      
                                        number = new BigInteger(getReverseNum3(
                                                addCode,4),16);
                                      }else{
                                        number = new BigInteger(getReverseNum3(
                                                addCode,2),16);  
                                      }    
                                       displac1[0] = number.add(number2).
                                               toString(2);
                                      
                                       if ( displac1[0].length() > 32){
                                             displac1[1] = displac1[0].
                                               substring(displac1[0].length() 
                                                       - 32, displac1[0].
                                                               length());
                                       }else{
                                           displac1[1] = displac1[0].
                                               substring(0, displac1[0].
                                                       length());

                                      }
                                             currentCode.append("0x").append
                                            (Integer.toHexString(Integer.
                                                     parseInt(displac1[1],2)));
                                      break;
                          case "FF" : 
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "cmp" :  
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.getOpcodeName()).
                              append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "3C" :   currentCode.append(" al,0x");
                                       currentCode.append(getReverse(addCode));
                                       currentCode.append("h ");
                                       break;
                        case "3D" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "80" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                            case "81" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                            case "83" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                            case "38" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "39" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                          case "3A" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "3B" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;   
           
                      }
                      
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "dec" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }   
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "FE" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          
                          case "FF" : 
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          case "48" :
                                        codeDef = Integer.parseInt(opCodeD,16) - 
                                                  finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                                  (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                                 (finalReg[0].getRegNameDWord());
                                        break;
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "idiv" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
         
                                      break;              

                      
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "imul" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                     currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,4));
                                        currentCode.append("h ");
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
         
                                      break;              
                         case "0F" :   switch(finalCode.getOpcodeCD1()){
                                        case "AF" : modCo[count1] = addCode[1];
                                                    modCodes = modBuilder(
                                                            String.format("%8s",
                                                    binCodeBuilder(modCo)).
                                                    replace(" ", "0"));
                                                    currentCode.append(
                                                            instructMRMDecoder( 
                                                    modCodes,addCode, "reg", 1, 
                                                            mcCODE, ccount));
                                                    currentCode.append(",");
                                                    currentCode.append(
                                                            instructMRMDecoder( 
                                                    modCodes,addCode, "r/m", 2, 
                                                            mcCODE, ccount));
                                                    break;
                                        default  :  currentCode.
                                                    append("Not Available");
                                                    break;          
                                        }
                                      break;
                         case "6B" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       currentCode.append(",");
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,4));
                                        currentCode.append("h ");
                                      break;
                           case "69" : 
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));

                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       currentCode.append(",");
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                   if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(",dword ");
                                       } else {
                                          currentCode.append(",word ");   
                                       }
                                     }
                                        currentCode.append(" 0x");
                                        if (! OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));
                                        }else{
                                         currentCode.append(getReverseNum3(
                                                 addCode,4));   
                                        }
                                        currentCode.append("h ");
                                      break;                 
                      
                      }
                       modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "inc" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                     
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "FE" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          
                          case "FF" : 
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          case "40" :
                                        codeDef = Integer.parseInt(opCodeD,16) - 
                                                  finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                                 (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                                (finalReg[0].getRegNameDWord());
                                        break;
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "jmp" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "EB" : 
                                      modCo[count1] = addCode[0];
                                      displac1[3] = binCodeBuilder(modCo); 
                                       nextPos =  ccount + 1 + addCode.length; 
                                       number2 = new BigInteger(Integer.
                                                toHexString(nextPos),16);
                                        if (displac1[3].charAt(0) == '1'){     
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                              (addCode,1)).replace(" ","F"),16);
                                        }else{
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                              (addCode,1)).replace(" ","0"),16);
                                            
                                        }
                                      
                                       displac1[0] = number.add(number2).
                                               toString(2);
                                       if ( displac1[0].length() > 32){
                                             displac1[1] = displac1[0].
                                               substring(displac1[0].length() 
                                                       - 32, displac1[0].
                                                               length());
                                       }else{
                                           displac1[1] = displac1[0].
                                               substring(0, displac1[0].
                                                       length());

                                       }
                                          currentCode.append("short 0x").
                                                  append(Integer.
                                                          toHexString(Integer.
                                              parseInt(displac1[1],2)));
                                   
                                      break;
                          case "E9" : 
                                      nextPos = ccount + 1 + 
                                                     addCode.length; 
                                      number2 = new BigInteger(Integer.
                                              toHexString(nextPos),16);
                                      if (OpType.equalsIgnoreCase("32")){ 
                                        currentCode.append("dword ");  
                                        number = new BigInteger(getReverseNum3(
                                                addCode,4),16);
                                      }else{
                                        number = new BigInteger(getReverseNum3(
                                                addCode,2),16);  
                                      }    
                                       displac1[0] = number.add(number2).
                                               toString(2);
                                      
                                       if ( displac1[0].length() > 32){
                                             displac1[1] = displac1[0].
                                               substring(displac1[0].length() 
                                                       - 32, displac1[0].
                                                               length());
                                       }else{
                                           displac1[1] = displac1[0].
                                               substring(0, displac1[0].
                                                       length());

                                      }
                                             currentCode.append("0x").append
                                            (Integer.toHexString(Integer.
                                                     parseInt(displac1[1],2)));
                                      break;
                                

                          case "FF" : 
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "jz" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "74" : 
                                       modCo[count1] = addCode[0];
                                      displac1[3] = binCodeBuilder(modCo); 
                                       nextPos =  ccount + 1 + addCode.length; 
                                       number2 = new BigInteger(Integer.
                                                toHexString(nextPos),16);
                                        if (displac1[3].charAt(0) == '1'){     
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                              (addCode,1)).replace(" ","F"),16);
                                        }else{
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                              (addCode,1)).replace(" ","0"),16);
                                            
                                        }
                                      
                                                                     
                                       displac1[0] = number.add(number2).
                                               toString(2);
                                       if ( displac1[0].length() > 32){
                                             displac1[1] = displac1[0].
                                               substring(displac1[0].length() 
                                                       - 32, displac1[0].
                                                               length());
                                       }else{
                                           displac1[1] = displac1[0].
                                               substring(0, displac1[0].
                                                       length());

                                       }
                                       if (displac1[0].charAt(0) == '1' && 
                                          displac1[0].length() > 32 && Integer.
                                              parseInt(displac1[1],2) <= 127 ){
                                          currentCode.append("0x").append
                                          (Integer.toHexString(Integer.
                                              parseInt(displac1[1],2)));
                                        }else{
                                          currentCode.append("0x").append
                                          (Integer.toHexString(Integer.
                                              parseInt(displac1[1],2)));                                          
                                        }
                              
                                      break;
                          case "0F" :   
                                       switch(finalCode.getOpcodeCD1()){
                                           case "84" : nextPos = ccount + 1 + 
                                                      addCode.length; 
                                            number2 = new BigInteger(Integer.
                                                    toHexString(nextPos),16);
                                            if (OpType.equalsIgnoreCase("32")){
                                                  currentCode.append("dword ");
                                                 number = new BigInteger
                                                (getReverseNum3(addCode,4),16);
                                            }else{
                                                  number = new BigInteger
                                                (getReverseNum3(addCode,2),16);  
                                            }    
                                             displac1[0] = number.add(number2).
                                                     toString(2);
                                             if ( displac1[0].length() > 32){
                                                   displac1[1] = displac1[0].
                                                     substring(displac1[0].
                                                             length() 
                                                             - 32, displac1[0].
                                                                     length());
                                             }else{
                                                 displac1[1] = displac1[0].
                                                     substring(0, displac1[0].
                                                             length());

                                            }
                                                   currentCode.append("0x").
                                                           append
                                                  (Integer.toHexString(Integer.
                                                     parseInt(displac1[1],2)));
                                              break;
                                       }      
                                      break;

                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "jnz" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "75" : 
                                       modCo[count1] = addCode[0];
                                      displac1[3] = binCodeBuilder(modCo); 
                                       nextPos =  ccount + 1 + addCode.length; 
                                       number2 = new BigInteger(Integer.
                                                toHexString(nextPos),16);
                                        if (displac1[3].charAt(0) == '1'){     
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                               (addCode,1)).
                                                replace(" ","F"),16);
                                        }else{
                                        number = new BigInteger(String.
                                                format("%8s",getReverseNum3
                                               (addCode,1)).
                                                replace(" ","0"),16);
                                            
                                        }
                                      
                                       displac1[0] = number.add(number2).
                                               toString(2);
                                       if ( displac1[0].length() > 32){
                                             displac1[1] = displac1[0].
                                               substring(displac1[0].length() 
                                                       - 32, displac1[0].
                                                               length());
                                       }else{
                                           displac1[1] = displac1[0].
                                               substring(0, displac1[0].
                                                       length());

                                       }
                                       if (displac1[0].charAt(0) == '1' && 
                                            displac1[0].length() > 8 && Integer.
                                              parseInt(displac1[1],2) <= 127 ){
                                          currentCode.append(" 0x1").append
                                          (Integer.toHexString(Integer.
                                              parseInt(displac1[1],2)));
                                        }else{
                                          currentCode.append("0x").append
                                          (Integer.toHexString(Integer.
                                              parseInt(displac1[1],2)));                                          
                                        }
                              
                                      break;
                          case "0F" :   
                                       switch(finalCode.getOpcodeCD1()){
                                           case "85" : nextPos = ccount + 1 + 
                                                      addCode.length; 
                                            number2 = new BigInteger(Integer.
                                                    toHexString(nextPos),16);
                                            if (OpType.equalsIgnoreCase("32")){
                                                  currentCode.append("dword ");
                                                    number = new BigInteger(
                                                  getReverseNum3(addCode,4),16);
                                            }else{
                                                      number = new BigInteger(
                                                 getReverseNum3(addCode,4),16);  
                                            }    
                                             displac1[0] = number.add(number2).
                                                     toString(2);

                                             if ( displac1[0].length() > 32){
                                                   displac1[1] = displac1[0].
                                                 substring(displac1[0].length() 
                                                             - 32, displac1[0].
                                                                     length());
                                             }else{
                                                 displac1[1] = displac1[0].
                                                     substring(0, displac1[0].
                                                             length());

                                            }
                                                currentCode.append("0x").append
                                                  (Integer.toHexString(Integer.
                                                      parseInt(displac1[1],2)));
                                              break;
                                       }      
                                      break;

                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "lea" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                     
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "8D" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      if( ! modCodes[0].contains("11")){
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      }else{
                                        currentCode.append("Illegal Operation");  
                                      }
                                      break;
                          
                       
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "mov" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }           
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "88" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                      break;
                          case "89" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                      break;
                          case "8A" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          
                          case "8B" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          
                          case "8C" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      sReg = searchSRegTable(modCodes[1]);
                                      if (sReg != null){
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(sReg.
                                              getsRegNameByte());
                                      }else{
                                        currentCode.append("Not Available");  
                                      }
                                      break;
                          
                       case "8E" : 
                                      
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      sReg = searchSRegTable(modCodes[1]);
                                      if (sReg != null){
                                         currentCode.append(sReg.
                                              getsRegNameByte());    
                                         currentCode.append(",");
                                         currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                    
                                      }else{
                                        currentCode.append("Not Available");  
                                      }
                                      break;
                          
                        case "B0" :
                                        codeDef = Integer.parseInt(opCodeD,16) - 
                                                  finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                                  (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                               (finalReg[0].getRegNameDWord());
                                        currentCode.append(",");                                        
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");

                                        break;

                        case "B8" :
                                        codeDef = Integer.parseInt(opCodeD,16) - 
                                                  finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                                  (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                                (finalReg[0].getRegNameDWord());
                                        currentCode.append(",0x");
                                        if (! OpType.equalsIgnoreCase("32") ){
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));   
                                        }
                                        currentCode.append("h ");

                                        break;
                        case "C6" :     modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");

                                        break;

                        case "C7" :     modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                    if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }

                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (! OpType.equalsIgnoreCase("32") ){
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));   
                                        }
                                        currentCode.append("h ");

                                        break;
                       
                      }                      
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "movsd" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                     
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).
                      append("@ ")        ;
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "mul" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                       currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,4));
                                        currentCode.append("h ");
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
         
                                      break;  
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "neg" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                        currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                 
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
         
                                      break;   
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "nop" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "90" : currentCode.append(" ");
                                      break;
                          
                          case "OF" :  switch(finalCode.getOpcodeCD()){
                                       case "1F" : 
                                           modCodes = modBuilder(String.
                                                   format("%8s",
                                                   binCodeBuilder(modCo)).
                                                   replace(" ", "0"));
                                           currentCode.append(
                                                   instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                           break;
                                       default : currentCode.
                                               append("Not available");
                                           break;
                                        }      
                                      break;
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "not" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                        currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                            
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
         
                                      break;              
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "or" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                     
                      currentCode.append("@ ").append(finalCode.getOpcodeName()).
                              append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "0C" :   currentCode.append(" al,0x");
                                       currentCode.append(getReverse(addCode).toString());
                                       currentCode.append("h ");
                                       break;
                        case "0D" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "80" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                            case "81" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11") ){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                            case "83" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                            case "08" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "09" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                          case "0A" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "0B" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;   
           
                      }
                      
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "pop" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "8F" : 
   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }                                   
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      break;
                          case "58" :
                                        codeDef = Integer.parseInt(opCodeD,16) - 
                                                  finalCode.getOpcode();
                                        finalReg[0] = searchRegTable
                                                  (intConvertBinMod(codeDef,3));
                                        currentCode.append(" ").append
                                               (finalReg[0].getRegNameDWord());
                                        break;
                          case "1F" :   currentCode.append("DS");
                                        break;
                          case "17" :   currentCode.append("SS");
                                        break;
                          case "07" :   currentCode.append("ES");
                                        break;
                          case "0F" :   switch(finalCode.getOpcodeCD1()){
                                        case "A1" : currentCode.append("FS");
                                                    break;
                                        case "A9" : currentCode.append("GS");
                                                    break;          
                                        }
                                        break;
                                

                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "repne cmpsd" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      } 
                       currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "retf" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@"); 
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName().toUpperCase().
                              replace("F", "")).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "CB" : currentCode.append(" ");
                                      break;
                          case "CA" : currentCode.append("0x");
                                      currentCode.append(getReverseNum3
                                       (addCode,2).toString());
                                      currentCode.append("h");
                                      break;
                                

                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "retn" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@"); 
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName().toUpperCase().
                              replace("N", "")).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "C3" : currentCode.append(" ");
                                      break;
                          case "C2" : currentCode.append("0x");
                                      currentCode.append(getReverseNum3
                                       (addCode,2).toString());
                                      currentCode.append("h");
                                      break;
                                

                      }
                      break;   
        case "sal" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "D0" : 
   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                                      break;
                          case "D2" :
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                                      break;
                          case "C0" :   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                                      
                                      break;
                          case "D1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                
                                        break;
                          case "D3" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                
                                        break;
                          case "C1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                
                                        break;
                                
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "sar" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "D0" : 
   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                                      break;
                          case "D2" :
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                                      break;
                          case "C0" :   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                                      
                                      break;
                          case "D1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                
                                        break;
                          case "D3" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                
                                        break;
                          case "C1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                
                                        break;
                                
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "sbb" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).
                              append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "1C" :   currentCode.append(" al,0x");
                                       currentCode.append(getReverse(addCode));
                                       currentCode.append("h ");
                                       break;
                        case "1D" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "80" : modCo[count1] = addCode[0];
                                    modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                         case "81" : modCo[count1] = addCode[0];
                                     modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                         case "83" : modCo[count1] = addCode[0];
                                     modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                         case "18" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                         case "19" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                          case "1A" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                           case "1B" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;   
           
                      }
                      
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "shr" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).append("@ ");
                      switch(finalCode.getOpcodeCD()){
                          case "D0" : 
   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                                      break;
                          case "D2" :
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                                      break;
                          case "C0" :   
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                                      
                                      break;
                          case "D1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",1");
                
                                        break;
                          case "D3" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",CL");
                
                                        break;
                          case "C1" :                       
                                      modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                      currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                      currentCode.append(",");
                                      currentCode.append(",0x");
                                      currentCode.append(getReverseNum3(
                                              addCode,1));
                                      currentCode.append("h ");
                
                                        break;
                                
                      }
                      modCodes = modBuilder(binCodeBuilder(addCode));

                      break;   
        case "test" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.getOpcodeName()).
                              append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "A8" :   currentCode.append(" al,0x");
                                      currentCode.append(getReverse(addCode));
                                      currentCode.append("h ");
                                      break;
                        case "A9" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "F6" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                         case "F7" : modCo[count1] = addCode[0];
                                     modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                       } else {
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));   
                                       }
                                     
                                
                                      break;              
                         case "84" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        break;              
                         case "85" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                      }
                      
                      modCodes = modBuilder(binCodeBuilder(addCode));
                      break;   
        case "xor" : 
                      currentCode.append(intConvertBinMod(ccount,16)).
                              append("@");
                      currentCode.append(" ").append(opCodeD.toUpperCase());
                      for(String value1 : addCode){
                         currentCode.append(value1.toUpperCase());
                      }                      
                      currentCode.append("@ ").append(finalCode.
                              getOpcodeName()).
                              append("@ ");
                      switch(finalCode.getOpcodeCD()){
                        case "34" :   currentCode.append(" al,0x");
                                       currentCode.append(getReverse(addCode));
                                       currentCode.append("h ");
                                       break;
                        case "35" :   if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(" eax,0x");
                                        } else {
                                          currentCode.append(" ax,0x");
                                        }
                                        currentCode.append(getReverse(addCode));
                                        currentCode.append("h ");
                                        break;                  
                        case "80" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;
                         case "81" : modCo[count1] = addCode[0];
                                        modCodes = modBuilder(String.
                                                format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                     if (! modCodes[0].equalsIgnoreCase("11")){    
                                     if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append("dword ");
                                       } else {
                                          currentCode.append("word ");   
                                       }
                                     }
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        if (OpType.equalsIgnoreCase("32")){
                                          currentCode.append(getReverseNum3(
                                                  addCode,4));
                                        }else{
                                          currentCode.append(getReverseNum3(
                                                  addCode,2));  
                                        }
                                        currentCode.append("h ");
                                      break;              
                         case "83" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                        currentCode.append("byte ");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",0x");
                                        currentCode.append(getReverseNum3(
                                                addCode,1));
                                        currentCode.append("h ");
                                      break;              
                          case "30" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              

                          case "31" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
                          case "33" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;
                           case "32" : modCo[count1] = addCode[0];
                                      modCodes = modBuilder(String.format("%8s",
                                              binCodeBuilder(modCo)).
                                              replace(" ", "0"));
                                       currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "reg", 1, 
                                              mcCODE, ccount));
                                        currentCode.append(",");
                                        currentCode.append(instructMRMDecoder( 
                                              modCodes,addCode, "r/m", 2, 
                                              mcCODE, ccount));
                                       ;
                                      break;              
            
                      }
                      break;   
        default : System.out.println("Sorry no code available.");
                  break;
    }
    
    return currentCode.toString();
    
}
/**
 * Function prints the details of the instruction set stack in order of 
 * instruction set line number
 * @param instructS - Instruction stack object
 */
  public static void printInstructionStack(List<instructStack>  instructS )
  {
       int count = 0, count2 = 0;
       Collections.sort(instructS, new instructStack());  
       
          for(instructStack data : instructS)
          {
              System.out.println(data.getInstructData());   
          } 
                 
          
      
  }
 /**
  * 
  * @param instructDS
  * @param selection
  * @return 
  */     
 public static instructStack SearchHexValues( List<instructStack>  instructDS, 
            String selection )
  {
        instructStack validateCheck = new instructStack();
        
        for(instructStack data : instructDS)
          {
            if( data.getInstructHex().contains(selection))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }
/**
 * 
 * @param instructDS
 * @param selection
 * @return 
 */
 public static boolean FindHexValues( List<instructStack>  instructDS, 
            String selection )
  {
        
        
        for(instructStack data : instructDS)
          {
            if( data.getInstructData().contains(selection))
            {
               return true;
             }          
          }   
        return false;
  }
/**
 * 
 * @param instructDS
 * @param selection
 * @return 
 */
 public static instructStack SearchInstructValues( List<instructStack>  instructDS, 
            String selection )
  {
        instructStack validateCheck = new instructStack();
        
        for(instructStack data : instructDS)
          {
            if( data.getInstructData().replaceAll("\\W", "").toLowerCase()
                    .contains(selection.replaceAll("\\W", "").toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }
  /**
   * 
   * @param inPut
   * @param typeCleaner
   * @return 
   */
   public static String cleaner(String inPut,int typeCleaner){
       switch(typeCleaner){
           case 0 : return inPut.replaceAll("\\W","");
           case 1 : return inPut.replaceAll("[\\p{Punct}]&&[\\W]","");
           default : return inPut;
       }        
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<instructionSet> newCode = buildMachie();
        List<instructStack>  instructListFinal = new ArrayList<instructStack>();
        List<instructStack>  instructListFinalOffSet = new ArrayList<instructStack>();
        
        instructionSet newOp = new instructionSet();
        String[] value = importFileData(args);
        String[] checkValue = new String[4];
        String[] finalInstrut;
        String bitType = "32", instructData;
        for(int i = 0; i < value.length-1; i++){
            if(! cleaner(value[i],1).isEmpty()){
                   checkValue[0] = cleaner(value[i],0);
                   checkValue[1] = cleaner(value[i+1],0);
                   checkValue[2] = cleaner(value[i+2],0);
                   checkValue[3] = cleaner(value[i+3],0);
                   
            newOp = findOpCode(newCode,checkValue,bitType);
            if(newOp != null){
            int count = 0;
            String[] addCode = new String[newOp.getCurrentBytes()];
            for (int z = i+1; z <= i + newOp.getCurrentBytes(); z++){
                    addCode[count] = value[z];
                    if (debugCode.equalsIgnoreCase("debug5")){
                        System.out.println(value[z] + " " + 
                            newOp.getCurrentBytes());
                    }
                    count++;
               }
            instructData = codeBuilder(i,newOp,addCode,value[i],value,bitType).
                    toString().replace("  "," ");
            finalInstrut = instructData.replace("@","").split(" ");
            int positionCT = 0;
            if (debugCode.equalsIgnoreCase("debug7")){
            for(String valueF : finalInstrut){
                if (positionCT == 0){
                   System.out.printf("%-8s ", valueF);
                }else if (positionCT == 1){
                   System.out.printf("%-22s ", valueF);
                }else if (positionCT == 2){
                   System.out.printf("%-4s ", valueF); 
                }else{
                  System.out.printf("%-5s ", valueF);  
                }
                positionCT++;
             }
           
            System.out.println();
            } 
            if ( ! FindHexValues(instructListFinal,
                    "offset_"+finalInstrut
                               [finalInstrut.length-1])){
        
            switch(newOp.getOpcodeName()){
                case "JMP" : if(newOp.getOpcodeCD().equalsIgnoreCase("EB")||
                              newOp.getOpcodeCD().equalsIgnoreCase("E9")){
                              instructListFinal.addAll
                            (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));
                              instructListFinalOffSet.addAll
                              (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));        

                            }
                            break;
            case "CALL" : if(newOp.getOpcodeCD().equalsIgnoreCase("E8")){
                              instructListFinal.addAll
                            (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));
                              instructListFinalOffSet.addAll
                              (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));        
                            }
                            break;
            case "JNZ" :                
            case "JZ" : instructListFinal.addAll
                            (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));
                              instructListFinalOffSet.addAll
                              (addInstructStack(Integer.
                                    parseInt(finalInstrut
                               [finalInstrut.length-1].
                               replace("0x", "").replace("short","").
                               replace("dword","")             
                                 ,16)-1,"offset_"+finalInstrut
                               [finalInstrut.length-1]+":@@@"));        
                          
                            break;
            
            }
            }
            instructListFinal.addAll(addInstructStack(i,instructData));
           switch(newOp.getOpcodeName()){
               case "JMP" : if(newOp.getOpcodeCD().equalsIgnoreCase("EB")||
                              newOp.getOpcodeCD().equalsIgnoreCase("E9")){
                              instructListFinalOffSet.
                              addAll(addInstructStack(i,
                                  instructData.replace("dword ","offset_").
                                   replace("short ","offset_")));
                            }
                          break; 
               case "CALL" : if(newOp.getOpcodeCD().equalsIgnoreCase("E8")){
                            instructListFinalOffSet.
                              addAll(addInstructStack(i,
                                  instructData.replace("dword ","offset_").
                                  replace("short ","offset_")));
                           }
              case "JNZ" : 
              case "JZ" :  if(newOp.getOpcodeCD().equalsIgnoreCase("0F")){  
                            instructListFinalOffSet.
                              addAll(addInstructStack(i,
                                  instructData.replace("dword ","offset_").
                                  replace("short ","offset_")));
                            }else{
                            instructListFinalOffSet.
                              addAll(addInstructStack(i,instructData.
                                      replace("0x","offset_0x")));
                  
                            }
                           break;
               default : instructListFinalOffSet.
                         addAll(addInstructStack(i,instructData));
                         break;
           }
            i = i + newOp.getCurrentBytes(); 
            newOp.setCurrentBytes(0);
            }else{
            System.out.println(value[i] + " is not a supported code " );
            }
           }
        }
        printInstructionStack(instructListFinalOffSet);

    }
    
}
