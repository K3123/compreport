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
 */
public class segRegister {
    
    private int sRegValue;
    private String sRegValueByte;
    private String sRegNameByte;
    private String sRegNameWord;
    private String sRegNameDWord;
   
    /**
     * Constructor used to build segRegister object
     */
    public segRegister() {
    }
    /**
     * Constructor used to build segRegiset object
     * @param sRegValue - segment register value
     * @param sRegValueByte - segment address register binary value
     * @param sRegNameByte - segment address register byte name
     * @param sRegNameWord - segment address register word name
     * @param sRegNameDWord - segment address register dword name
     */
    public segRegister(int sRegValue, String sRegValueByte, String 
            sRegNameByte, String sRegNameWord, String sRegNameDWord) {
        this.sRegValue = sRegValue;
        this.sRegValueByte = sRegValueByte;
        this.sRegNameByte = sRegNameByte;
        this.sRegNameWord = sRegNameWord;
        this.sRegNameDWord = sRegNameDWord;
    }
   /**
     * Constructor used to build segRegiset object
     * @param sRegValue - segment register value
     * @param sRegValueByte - segment address register binary value
     * @param sRegNameByte - segment address register byte name
    */
  public segRegister(int sRegValue, String sRegValueByte, String sRegNameByte) {
        this.sRegValue = sRegValue;
        this.sRegValueByte = sRegValueByte;
        this.sRegNameByte = sRegNameByte;

    }

    /**
     * Function return sregvalue
     * @return - integer for sregvalue
     */
    public int getsRegValue() {
        return sRegValue;
    }
    /**
     * Function sets sregvalue
     * @param sRegValue - integer value for sregvalue
     */
    public void setsRegValue(int sRegValue) {
        this.sRegValue = sRegValue;
    }
    /**
     * Function return sregvaluebyte name
     * @return - String sregvaluebyte name
     */
    public String getsRegValueByte() {
        return sRegValueByte;
    }
    /**
     * Function sets sregvaluebyte name
     * @param sRegValueByte - String sregvaluebyte name
     */
    public void setsRegValueByte(String sRegValueByte) {
        this.sRegValueByte = sRegValueByte;
    }
    /**
     * Function returns sregnamebyte value
     * @return - String value for sregnamebyte
     */
    public String getsRegNameByte() {
        return sRegNameByte;
    }
    /**
     * Function sets sregnamebyte value
     * @param sRegNameByte - String value for sregnamebyte
     */
    public void setsRegNameByte(String sRegNameByte) {
        this.sRegNameByte = sRegNameByte;
    }
    /**
     * Function returns sregnameword value
     * @return 
     */
    public String getsRegNameWord() {
        return sRegNameWord;
    }
    /**
     * Function sets regnameword value
     * @param sRegNameWord - String regnameword value
     */
    public void setsRegNameWord(String sRegNameWord) {
        this.sRegNameWord = sRegNameWord;
    }
    /**
     * Function returns segment address register regnameword
     * @return - String segment address register
     */
    public String getsRegNameDWord() {
        return sRegNameDWord;
    }
    /**
     * Function sets segment address register regnamedword
     * @param sRegNameDWord - segment address register regnamedword
     */
    public void setsRegNameDWord(String sRegNameDWord) {
        this.sRegNameDWord = sRegNameDWord;
    }
    /**
     * Function returns the string representation of all the fields for 
     * the segment register object
     * @return - String all fields 
     */
    @Override
    public String toString() {
        return "segRegister{" + "sRegValue=" + sRegValue + ", sRegValueByte=" + 
                sRegValueByte + ", sRegNameByte=" + sRegNameByte + 
                ", sRegNameWord=" + sRegNameWord + ", sRegNameDWord=" + 
                sRegNameDWord + '}';
    }
    
    
    
}
