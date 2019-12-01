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
 * The following class will be used as the structure for a opcode 
 * instruction set. This will list will contain all supported opcode 
 * instructions available for this project
 */
public class instructionSet implements Comparator<instructionSet>, 
        Serializable {
    
    private int opcodeID;
    private int opcode;
    private int opcode1;
    private int opcode2;
    private String opcodeName;
    private String opcodeDes;
    private String opcodeIns;
    private String opcodeCD;
    private String opcodeCD1;
    private String opcodeCD2;
    private int additionalBytes;
    private int currentBytes;
    private String MRMCODE;
    
    /**
     * Constructor for the instructionset object
     */
    public instructionSet() {
    }
    /**
     * Constructor for the instructionset object
     * @param opcode - integer value for the opcode hex value
     * @param opcodeName - String name for the opcode
     * @param opcodeDes - String description for the opcode
     * @param opcodeCD - String code for the primary hex value
     */
    public instructionSet(int opcode, String opcodeName, String opcodeDes, 
            String opcodeCD) {
        this.opcode = opcode;
        this.opcodeName = opcodeName;
        this.opcodeDes = opcodeDes;
        this.opcodeCD = opcodeCD;
    }
    /**
     * Function that returns the current size of the instruction set
     * @return - Integer value of the currentbyte size 
     */
    public int getCurrentBytes() {
        return currentBytes;
    }
    /**
     * Function that sets the currentbytes of the instruction set
     * @param currentBytes - Integer value of the current instruction set
     */
    public void setCurrentBytes(int currentBytes) {
        this.currentBytes = currentBytes;
    }
    /**
     * Function that returns  the ModRM encoding value
     * @return - String of the ModRM encoding value
     */
    public String getMRMCODE() {
        return MRMCODE;
    }
    /**
     * Function that sets the ModRM encoding value
     * @param MRMCODE - String of the ModRM encoding value
     */
    public void setMRMCODE(String MRMCODE) {
        this.MRMCODE = MRMCODE;
    }
    /**
     * Function returns the opCode instruction set
     * @return - String of the full instruction set provide by Intel® 64 and 
     * IA-32 architectures software developer's manual combined volumes 2A, 
     * 2B, 2C, and 2D: Instruction set reference, A-Z
     */
    public String getOpCodeIns(){
        return this.opcodeIns;
    }
    
    /**
     * Function that sets the opCode instruction set based on the Intel® 64 and
     * IA-32 architectures software developer's manual combined volumes 2A, 2B,
     * 2C, and 2D: Instruction set reference, A-Z 
     * @param value - String of the Intel instruction set 
     */
    public void setOpCodeIns(String value){
        this.opcodeIns =  value;
    }
    /**
     * Function that sets the position id in the list for the instruction 
     * object
     * @return - Integer value of the position of the opcode in the list
     */
    public int getOpCodeID(){
        return opcodeID;
    }
    /**
     * Function that returns the integer value of the hex code used to 
     * represent the instruction set 
     * @return - Integer of opcode primary byte value
     */
    public int getOpcode() {
        return opcode;
    }
   /**
    * Function that returns the integer value of the secondary code used 
    * represent the instruction set
    * @return - Integer of opcode secondary byte value
    */
    public int getOpcode1() {
        return opcode1;
    }
    /**
     * Function that returns the integer value of the third code used to 
     * represent the instruction set
     * @return - Integer of opcode third byte value
     */
    public int getOpcode2() {
        return opcode2;
    }
    /**
     * Function that returns the opcode name
     * @return - String value of the opcode name
     */
    public String getOpcodeName() {
        return opcodeName;
    }
    /**
     * Function that returns the opcode description 
     * @return - String opcode description as provided in Intel® 64 and IA-32 
     * architectures software developer's manual combined volumes 2A, 2B, 2C, 
     * and 2D: Instruction set reference, A-Z
     */
    public String getOpcodeDes() {
        return opcodeDes;
    }
    /**
     * Function returns the opcode primary byte value
     * @return - String of primary byte value
     */
    public String getOpcodeCD() {
        return opcodeCD;
    }
    /**
     * Function returns the opcode secondary byte value
     * @return - String of secondary byte value
     */
    public String getOpcodeCD1() {
        return opcodeCD1;
    }
    /**
     * Function returns the opcode third byte value
     * @return - String of third byte value
     */
    public String getOpcodeCD2() {
        return opcodeCD2;
    }
    /**
     * Function sets the ID list value for the instruction in the list of 
     * opcode objects
     * @param opcodID - Integer value of current position of instruction set
     * object
     */    
    public void setOpcodeID(int opcodID ){
        this.opcodeID = opcodID;
    }
    /**
     * Function sets the opcode primary byte integer value
     * @param opcode - Integer version of the primary byte value
     */
    public void setOpcode(int opcode) {
        this.opcode = opcode;
    }
    /**
     * Function sets the opcode secondary byte integer value
     * @param opcode1 - Integer version of the secondary byte value
     */
    public void setOpcode1(int opcode1) { 
        this.opcode1 = opcode1;
    }
    /**
     * Function sets the opcode third byte integer value
     * @param opcode2 - Integer version of the third byte value
     */
    public void setOpcode2(int opcode2) {
        this.opcode2 = opcode2;
    }
    /**
     * Function sets the opcode name
     * @param opcodeName - String name of the opcode
     */
    public void setOpcodeName(String opcodeName) {
        this.opcodeName = opcodeName;
    }
    /**
     * Function sets the opcode description, which should be based on Intel® 64 
     * and IA-32 architectures software developer's manual combined volumes 2A, 
     * 2B, 2C, and 2D: Instruction set reference, A-Z
     * @param opcodeDes - String description of opcode
     */
    public void setOpcodeDes(String opcodeDes) {
        this.opcodeDes = opcodeDes;
    }
    /**
     * Function sets the primary byte of the opcode
     * @param opcodeCD - String of primary byte 
     */
    public void setOpcodeCD(String opcodeCD) {
        this.opcodeCD = opcodeCD;
    }
    /**
     * Function sets the secondary byte of the opcode
     * @param opcodeCD1 - String of secondary byte
     */
    public void setOpcodeCD1(String opcodeCD1) {
        this.opcodeCD1 = opcodeCD1;
    }
   /**
    * Function sets the third byte of the opcode
    * @param opcodeCD2 - String of the third byte
    */
    public void setOpcodeCD2(String opcodeCD2) {
        this.opcodeCD2 = opcodeCD2;
    }
    /**
     * Function returns the opcode instruction details
     * @return - String opcode instruction details
     */
    public String getOpcodeIns() {
        return opcodeIns;
    }
    /**
     * Function sets the opcode instruction details
     * @param opcodeIns - String opcode instruction details
     */
    public void setOpcodeIns(String opcodeIns) {
        this.opcodeIns = opcodeIns;
    }
    /**
     * Function returns the initial number of bytes based on the instruction set
     * @return - Integer value of the initial number of bytes contain in
     * instruction set
     */
    public int getAdditionalBytes() {
        return additionalBytes;
    }
    /**
     * Function sets the initial number of bytes based on the instruction set
     * @param additionalBytes - Integer value of the initial number of bytes 
     * contain in instruction set
     */
    public void setAdditionalBytes(int additionalBytes) {
        this.additionalBytes = additionalBytes;
    }
    /**
     * Function returns the complete string of instruction set fields
     * @return - String of all instruction set fields
     */
    @Override
    public String toString() {
        return "instructionSet{" + "opcode=" + opcode + ", opcodeName=" + 
                opcodeName + ", opcodeDes=" + opcodeDes + ", opcodeCD=" +
                opcodeCD + '}';
    }
    /**
     * Function to generate hash value based on opcode and opcodeName. Will be 
     * used in future for faster searches
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.opcode;
        hash = 47 * hash + Objects.hashCode(this.opcodeName);
        return hash;
    }
    /** 
     * Function determine if instruction set objects are equivalent 
     * @param obj - Instruction set object
     * @return - boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final instructionSet other = (instructionSet) obj;
        if (this.opcode != other.opcode) {
            return false;
        }
        return true;
    }

    /**
     * Function compares the instruction position to determine correct sort size
     * @param o1 - Instruction set object1
     * @param o2 - Instruction set object2
     * @return - Integer value of the comparison 
     */
    @Override
    public int compare(instructionSet o1, instructionSet o2) {
        return o1.getOpCodeID() - o2.getOpCodeID();
    }

    /**
     * Function reverses the order Instruction set
     * @return 
     */
    @Override
    public Comparator<instructionSet> reversed() {
        return Comparator.super.reversed(); 
    }
}
