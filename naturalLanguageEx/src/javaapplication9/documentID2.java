/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;
/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class documentID2 implements Comparator<documentID2>, Serializable {
    private String docID;
    private int termCount;
    final private List<Integer> position = new ArrayList<Integer>();

    /**
     * Constructor for the documentID1 object class
     */
    public documentID2() {
        
    }
    /**
     * Constructor for the documentID1 object class that includes parameters to 
     * initialize fields for the object
     * @param docID - string document ID
     * @param termCount - integer term count value
     */
    public documentID2(String docID, int termCount, int position1) {
        this.docID = docID;
        this.termCount = termCount;
        this.position.add(position1);
    }
    
    
    public void addPosition(int position){
        this.position.add(position);
    }
    public List<Integer> getPosition(){
        return this.position;
    }
    public void setPosition(List<Integer> position1){
        this.position.addAll(position1);
    }
    /**
     * Function that returns the document id
     * @return - string document ID
     */
    public String getDocID() {
        return docID;
    }
    /**
     * Function that sets the document id
     * @param docID - string document ID
     */
    public void setDocID(String docID) {
        this.docID = docID;
    }
    /**
    * Function that returns the integer term count value
    * @return - integer term count value
    */
    public int getTermCount() {
        return termCount;
    }
    /**
     * Function that sets the integer term count value
     * @param termCount - integer term count value
     */
    public void setTermCount(int termCount) {
        this.termCount = termCount;
    }
    /**
     * Function that returns the string value of all terms for the documentID1
     * object
     * @return - String representation of the documentID1 object 
     */
    @Override
    public String toString() {
        return this.docID + " " + this.termCount; 
    }
    /**
     * Function that compares to documentID1 objects using the docID field
     * @param d2 - documentID1 object
     * @param d3 - documentID1 object
     * @return - integer value result of comparison of objects
     */
    @Override
    public int compare(documentID2 d2, documentID2 d3) {
       return Integer.parseInt(d2.docID) - Integer.parseInt(d3.docID); 
    }
    /**
     * Comparator constructor for the documentID1 object
     */
    public static Comparator<documentID2> DocIDComparator = 
            new Comparator<documentID2>() {
        /**
         * Function that compares to documentID1 objects using the docID field 
         * @param s1 - documentID1 object
         * @param s2 - documentID1 object
         * @return - integer value result of comparison of objects
         */
	public int compare(documentID2 s1, documentID2 s2) {
	   String DocID1 = s1.getDocID().toUpperCase();
	   String DocID2 = s2.getDocID().toUpperCase();

	   //ascending order
	   return DocID1.compareTo(DocID2);

    }};
    /**
     * Comparator constructor for the documentID1 object
     */
    public static Comparator<documentID2> DocIDComparator2 = 
            new Comparator<documentID2>() {
        /**
         * 
         * @param s1 - documentID1 object
         * @param s2 - documentID1 object
         * @return - integer value result of comparison of objects
         */
	public int compare(documentID2 s1, documentID2 s2) {
	   //ascending order
	   return Integer.parseInt(s1.docID) - Integer.parseInt(s2.docID);

    }};
       /**
     * Comparator constructor for the documentID1 object
     */
    public static Comparator<documentID2> termCountComparator2 = 
            new Comparator<documentID2>() {
        /**
         * 
         * @param s1 - documentID1 object
         * @param s2 - documentID1 object
         * @return - integer value result of comparison of objects
         */
	public int compare(documentID2 s1, documentID2 s2) {
	   //ascending order
	   return s1.getTermCount() - s2.getTermCount();

    }};
   
    
    
}
