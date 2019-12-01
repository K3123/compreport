/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class docQueryStruct implements Comparator<docQueryStruct>, 
        Serializable{
    
    private int docIDInt;
    private String docID;
    private int termCount;
    private double dotProduct;
    private double docLength;
    private double simValue;
    private double sumSquares;
    private int wordCount;
    
    HashMap<String, Integer> docMapStruct;
    HashMap<String, Double> docMapStructTFIDF;
    
    /**
     * Constructor for the docQueryStruct object. This will initialize the 
     * object with new parameters set 
     */
    public docQueryStruct() {
    }
    /**
     * Constructor for the docQueryStruct object. This will initialize the 
     * object with the provide values
     * @param docID - document identification number
     * @param termCount - number of unique terms contain in the document
     * @param dotProduct - dot product for the docQueryStruct object
     * @param docLength - document length for the docQueryStruct object
     * @param sumSquares - sum of squares for the docQueryStruct object
     * @param newValue - new term that will be aligned to the docQueryStruct
     * @param TF - term frequency number
     * @param IDF - inverted document frequency number
     */
    public docQueryStruct(String docID, int termCount, int dotProduct1, 
         double docLength, int sumSquares, String newValue, int TF, double IDF
           ) {
        this.docID = docID;
        this.termCount = termCount;
        this.dotProduct = dotProduct1;
        this.docLength = docLength;
        this.sumSquares = sumSquares;
        
          if (this.docMapStructTFIDF.isEmpty()) {
            this.docMapStructTFIDF = new HashMap<String, Double>(){{
           put(newValue,IDF);
          }};
          }
          
          
          if (this.docMapStruct.isEmpty()) {
           this.docMapStruct = new HashMap<String, Integer>(){{
           put(newValue,TF);
          }};
          }
        
        
    }
    /**
     * Function to add new terms to the docMapStruct Hashmap
     * @param new_ID - String value of word to be added to docQueryStruct
     * object
     * @param TF - integer value of the term frequency for the word
     */
      public void addDocMapStruct(String new_ID, int TF ){
      
      if (this.docMapStruct == null) {
           this.docMapStruct = new HashMap<String, Integer>(){{
           put(new_ID,TF);
       }};
        }
      
      if (! this.docMapStruct.containsKey(new_ID)) {
           this.docMapStruct.putIfAbsent(new_ID,TF);
           
      }
      
  }
 /**
  * Function to add new terms to the DocMapStructTFIDF and include the TF-IDF 
  * value
  * @param new_ID - String value of word to be added to docQueryStruct
  * object 
  * @param IDF double value of the tf-idf
  */
  public void addDocMapStructTFIDF(String new_ID, double IDF ){
      
      if (this.docMapStructTFIDF == null) {
           this.docMapStructTFIDF = new HashMap<String, Double>(){{
           put(new_ID,IDF);
       }};
        }
      
      if (! this.docMapStructTFIDF.containsKey(new_ID)) {
           this.docMapStructTFIDF.putIfAbsent(new_ID,IDF);
           
      }
      
  }
   /**
    * Function that returns the DocMapStructTFIDF of the current docQueryStruct 
    * object
    * @return - HashMap DocMapStructTFIDF
    */
    public HashMap<String, Double> getDocMapStructTFIDF() {
        return docMapStructTFIDF;
    }
    /**
     * Function that sets the DocMapStructTFIDF of the current docQueryStruct
     * object
     * @param docMapStructTFIDF - HashMap value to be added to DocMapStructTFIDF
     */
    public void setDocMapStructTFIDF(HashMap<String, Double> docMapStructTFIDF) {
        this.docMapStructTFIDF = docMapStructTFIDF;
    }
    /**
     * Function that returns the integer value of the term count
     * @return - Integer value of the number of terms
     */
    public int getWordCount() {
        return wordCount;
    }
    /**
     * Function that sets the word count value of the term count
     * @param wordCount - Integer value of the number of terms
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    
    /**
     * Function that returns the document id value as integer 
     * @return - integer value of the document id
     */
    public int getDocIDInt() {
        return docIDInt;
    }
    /**
     * Function that sets the document id value as an integer
     * @param docIDInt - integer value of the document id
     */
    public void setDocIDInt(int docIDInt) {
        this.docIDInt = docIDInt;
    }

    /**
     * Function that returns the document id value as a string
     * @return - String value of the document id
     */
    public String getDocID() {
        return docID;
    }
    /**
     * Function that sets the document id value as a string
     * @param docID - String value of the document id
     */
    public void setDocID(String docID) {
        this.docID = docID;
    }
    /**
     * Function that returns the total number of terms in the docQueryStruct 
     * object
     * @return - integer value of the number of terms 
     */
    public int getTermCount() {
        return termCount;
    }
    /**
     * Function that sets the total number of terms in the docQueryStruct 
     * object
     * @param termCount - integer value of the number of terms 
     */
    public void setTermCount(int termCount) {
        this.termCount = termCount;
    }
    /**
     * Function that returns the dot product that includes the TF-IDF value
     * @return - double DotProduct value of docQueryStruct object
     */
    public double getDotProduct() {
        return dotProduct;
    }
    /**
     * Function that sets the dot product that includes the TF-IDF value
     * @param dotProduct - double DotProduct value of docQueryStruct object
     */
    public void setDotProduct(double dotProduct) {
        this.dotProduct = dotProduct;
    }
    /**
     * Function that returns the document length for a docQueryStruct object 
     * that includes the TF-IDF value
     * @return - double document length value
     */
    public double getDocLength() {
        return docLength;
    }
    /**
     * Function that sets the document length for a docQueryStruct object 
     * that includes the TF-IDF value
     * @param docLength - double document length value
     */
    public void setDocLength(double docLength) {
        this.docLength = docLength;
    }
   /**
    * Function that sets the sum of squares for a docQueryStruct object, which
    * is based on TF-IDF
    * @return - double value of sum of squares for the docQueryStruct object
    */
    public double getSumSquares() {
        return sumSquares;
    }
    /**
     * Function that sets the sum of squares for a docQueryStruct object, which 
     * is based on the TF-IDF
     * @param sumSquares 
     */
    public void setSumSquares(double sumSquares) {
        this.sumSquares = sumSquares;
    }
    /**
     * Function that returns the docMapStruct. This structure will contain a 
     * HashMap, where the key is the term name and the value is the terms 
     * frequency
     * @return - docMapStruct HashMap structure
     */
    public HashMap<String, Integer> getDocMapStruct() {
        return docMapStruct;
    }
    /**
     * Function that sets the docMapStruct. This structure will contain a 
     * HashMap, where the key is the term name and the value is the terms 
     * frequency
     * @param docMapStruct - docMapStruct HashMap value
     */
    public void setDocMapStruct(HashMap<String, Integer> docMapStruct) {
        this.docMapStruct = docMapStruct;
    }
    /**
     * Function that returns the Cosine value for the docQueryStruct
     * @return - double value of the cosine value 
     */
    public double getSimValue() {
        return simValue;
    }
    /**
     * Function that sets the Cosine value for the docQueryStruct
     * @param simValue - double value of the cosine value 
     */
    public void setSimValue(double simValue) {
        this.simValue = simValue;
    }
    

    /**
     * Comparator constructor for docQueryStruc. This compares the string value 
     * of the document id
     */
    public static Comparator<docQueryStruct> docIDComparator = 
            new Comparator<docQueryStruct>() {
        /**
         * Function that compares to docQueryStruct using the docID string 
         * values
         * @param s1 - docQueryStruct object
         * @param s2 - docQueryStruct object
         * @return - integer value of the comparison
         */
	public int compare(docQueryStruct s1, docQueryStruct s2) {
	   String word1 = s1.getDocID();
	   String word2 = s2.getDocID();

	   //ascending order
	   return word1.compareTo(word2);

    }};
   /**
    * Comparator constructor for docQueryStruc. This compares the double value 
     * of the cosine values
    */ 
   public static Comparator<docQueryStruct> simComparator = 
            new Comparator<docQueryStruct>() {
        /**
         * Function that compares to docQueryStruct using the cosine double  
         * values and returns an integer result
         * @param s1 - docQueryStruct object 
         * @param s2 - docQueryStruct object
         * @return - integer value of the comparison
         */
	public int compare(docQueryStruct s1, docQueryStruct s2) {
	   double b1 = s1.getSimValue() * 1000000;
           double b2 = s2.getSimValue() * 1000000;
	 
	   //ascending order
	   return (int)(b2 - b1);

    }};
    /**
     * Comparator constructor for docQueryStruc. This compares the double value 
     * of the dot product
     */
    public static Comparator<docQueryStruct> dotComparator = 
            new Comparator<docQueryStruct>() {
        /**
         * Function that compares to docQueryStruct using the dot product double  
         * values and returns an integer result
         * @param s1 - docQueryStruct object
         * @param s2 - docQueryStruct object
         * @return - integer value of the comparison
         */
	public int compare(docQueryStruct s1, docQueryStruct s2) {
            double b1 = s1.getDotProduct() * 1000000;
            double b2 = s2.getDotProduct() * 1000000;
	  return (int)(b2 - b1);

    }};

    /**
     * Function that returns a string representation of all the fields in the 
     * docQueryStruct object
     * @return - String value of all the fields in the docQueryStruct object
     */
    @Override
    public String toString() {
        return "docQueryStruct{" + "docID=" + docID + ", termCount=" + 
                termCount + ", dotProduct=" + dotProduct + ", docLength=" + 
                docLength + ", sumSquares=" + sumSquares + ", docMapStruct=" + 
                docMapStruct + '}';
    }
    /**
     * Function that compares to docQueryStruct using the document id integer  
     * values and returns an integer result
     * @param o1 - docQueryStruct object
     * @param o2 - docQueryStruct object
     * @return - integer value of the comparison
     */
    @Override
    public int compare(docQueryStruct o1, docQueryStruct o2) {
       return o1.docIDInt - o2.docIDInt; 
    //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
