/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * EN.695.744.82.FA17 Reverse Engineering and Vulnerability Analysis  
 */
public class instructStack implements Comparator<instructStack>, 
        Serializable{
    
    private long instructCD;
    private String instructHex;
    private String instructData;

    /**
     * Constructor used to create instructStack object
     */ 
    public instructStack() {
    }
    /**
     * Constructor used to create instructStack object
     * @param instructCD - Instruction index number
     * @param instructData - String instruction set
     */
    public instructStack(long instructCD, String instructData) {
        this.instructCD = instructCD;
        this.instructData = instructData;
    }
 
    /**
     * Function returns InstructHex string
     * @return - String InstructHex
     */
    public String getInstructHex() {
        return instructHex;
    }
    /**
     * Function sets InstructHex string
     * @param instructHex - String InstructHex
     */
    public void setInstructHex(String instructHex) {
        this.instructHex = instructHex;
    }
    /**
     * Function returns comparator objects
     * @return - comparator objects
     */
    public static Comparator<instructStack> getComparatorHex() {
        return ComparatorHex;
    }
    /**
     * Function sets the comparator objects
     * @param ComparatorHex - comparator objects
     */
    public static void setComparatorHex(Comparator<instructStack> 
            ComparatorHex)
    {
        instructStack.ComparatorHex = ComparatorHex;
    }

    /**
     * Function returns the InstructCD long
     * @return - long InsructCD value
     */
    public long getInstructCD() {
        return instructCD;
    }
    /**
     * Function sets the InsructCD long
     * @param instructCD - long instruct code value
     */
    public void setInstructCD(long instructCD) {
        this.instructCD = instructCD;
    }
    /**
     * Function returns the complete instruction details
     * @return - String of complete instruction details
     */
    public String getInstructData() {
        return instructData;
    }
    /**
     * Function sets the complete instruction details
     * @param instructData - String complete instruction set details
     */
    public void setInstructData(String instructData) {
        this.instructData = instructData;
    }
    
    /**
     * Function that compares instructStack objects
     * @param o1 - Instruction Stack Object1
     * @param o2 - Instruction Stack Object2
     * @return 
     */    
    @Override
    public int compare(instructStack o1, instructStack o2) {
         return (int) o1.instructCD - (int) o2.instructCD;
    }

    /**
     * Function returns comparator objects results
     */
    public static Comparator<instructStack> ComparatorHex = 
            new Comparator<instructStack>() {

	public int compare(instructStack s1, instructStack s2) {
	   String hex1 = s1.getInstructHex().toLowerCase();
	   String hex2 = s2.getInstructHex().toLowerCase();

	   //ascending order
	   return hex1.compareTo(hex2);

    }};   
    
     public static Comparator<instructStack> ComparatorBigInteger = 
            new Comparator<instructStack>() {

	public int compare(instructStack s1, instructStack s2) {
	   BigInteger hex1 = new BigInteger(s1.getInstructHex(),16);
	   BigInteger hex2 = new BigInteger(s2.getInstructHex(),16);
           
	   //ascending order
	   return hex1.compareTo(hex2);

    }};
     
    @Override
    public String toString() {
        return "instructStack{" + "instructCD=" + instructCD + 
                ", instructData=" + instructData + '}';
    }
    
    
    
}
