/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * EN.695.744.82.FA17 Reverse Engineering and Vulnerability Analysis  
 * This class will be used to store details about the register codes used 
 * in assembler.  This codes correlate to the register codes found in the 
 * ModR/M table
 */
public class regCode implements Serializable {
    
    private int regValue;
    private String regValueByte;
    private String regNameByte;
    private String regNameWord;
    private String regNameDWord;
    
    /** 
     * Constructor for the register object
     */
    public regCode() {
    }
    /**
     * Constructor for the register object
     * @param regValue - register integer value
     * @param regValueByte - register binary of integer value
     * @param regNameByte - register byte name
     * @param regNameWord - register word name
     * @param regNameDWord - register dword name
     */
    public regCode(int regValue, String regValueByte, String regNameByte, 
            String regNameWord, String regNameDWord) {
        this.regValue = regValue;
        this.regValueByte = regValueByte;
        this.regNameByte = regNameByte;
        this.regNameWord = regNameWord;
        this.regNameDWord = regNameDWord;

    }

    /**
     * Function returns the regvaluebyte 
     * @return - String 
     */ 
    public String getRegValueByte() {
        return regValueByte;
    }
    /**
     * Function sets the regvaluebyte
     * @param regValueByte - String value for the regvaluebyte
     */
    public void setRegValueByte(String regValueByte) {
        this.regValueByte = regValueByte;
    }

    /**
     * Function returns the regvalue
     * @return - integer register value
     */
    public int getRegValue() {
        return regValue;
    }
    /**
     * Function sets the register value
     * @param regValue - integer register value
     */
    public void setRegValue(int regValue) {
        this.regValue = regValue;
    }
    /**
     * Function returns the regnamebyte value
     * @return - String name of the register byte value
     */
    public String getRegNameByte() {
        return regNameByte;
    }
    /**
     * Function sets the regnamebyte value
     * @param regNameByte - String value of the register name
     */
    public void setRegNameByte(String regNameByte) {
        this.regNameByte = regNameByte;
    }
    /**
     * Function returns the regnameword string value
     * @return - String value of the register word name
     */
    public String getRegNameWord() {
        return regNameWord;
    }
    /**
     * Function sets the regnameword string name
     * @param regNameWord - String value of
     */
    public void setRegNameWord(String regNameWord) {
        this.regNameWord = regNameWord;
    }
    /**
     * Function returns the regnamedword string name
     * @return - String value of regnamedword
     */
    public String getRegNameDWord() {
        return regNameDWord;
    }
    /**
     * Function sets the regnamedword value
     * @param regNameDWord - String name of the dword 
     */
    public void setRegNameDWord(String regNameDWord) {
        this.regNameDWord = regNameDWord;
    }
    /**
     * Function returns the complete value of the register
     * @return - String value of all register fields
     */
    @Override
    public String toString() {
        return "regCode{" + "regValue=" + regValue + ", regNameByte=" + 
                regNameByte + ", regNameWord=" + regNameWord + 
                ", regNameDWord=" + regNameDWord + '}';
    }

  
    
}
