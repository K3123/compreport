/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class javaFinalParser implements Comparator<javaFinalParser>, 
        Serializable{
    
    /**
     * The following class will be used as the lexicon for this project the
     * terms listed below are variables that will store information to create 
     * the dictionary file. The word object is a instance of the javaFinalParser
     */
    private int wordId;
    private String wordName;
    final List<String> documentID = new ArrayList<>(); // document id details
    final List<String> actualWord = new ArrayList<>(); // extracted word details
    final List<documentID1> docListStruct = new ArrayList<>(); 
                   
    HashMap<Integer, Integer> docMapStruct;
    private int collectionFrequency;
    private int documentFrequency;
    private double invertDocFrequency;
    private int pointerFile;
    
    /**
     * Constructor used for the class when no parameters are available
     */
    javaFinalParser()
    {}
    /**
     * Constructor used for the class when all the parameters are available
     * @param id
     * @param wordnam
     * @param newValue
     * @param actWord
     * @param instance
     */
    javaFinalParser( int id, String wordnam, String newValue, String actWord,
            int instance )
    {
        this.wordId = id;
        this.wordName = wordnam;
        this.collectionFrequency = instance;
        if (this.documentID.isEmpty()) {
            this.documentID.add(newValue);
        }
        if (this.actualWord.isEmpty()) {
            this.actualWord.add(newValue);
        }
        if (this.docListStruct.isEmpty()) {
            docListStruct.add(new documentID1(newValue,1));
        }
       if (this.docMapStruct.isEmpty()) {
           this.docMapStruct = new HashMap<Integer, Integer>(){{
           put(Integer.parseInt(newValue),1);
       }};
       }
    }
    
   /**
    * Function that sets the word object word ID
    * @param iD : Integer id of the current word object
    */ 
  public void setwordId( int iD )
    {
        this.wordId = iD;
    }
  /**
   * Function that gets the word object word ID
   * @return : Integer id of the current word object
   */
  public int getwordId()
   {
       return this.wordId;
   }
  /**
   * Function that sets the current pointer to the inverted index file
   * @param value1 
   */  
  public void setPointerFile(int value1)
  {
      this.pointerFile = value1;
  }
 /**
  * Function that returns the inverted document frequency value for the current
  * word object
  * @return 
  */
  public double getInvertDocFrequency() {
        return invertDocFrequency;
  }
  /**
   * Function that sets the inverted document frequency value for the current
   * word object
   * @param invertDocFrequency 
   */
  public void setInvertDocFrequency(double invertDocFrequency) {
        this.invertDocFrequency = invertDocFrequency;
  }
  /**
   * Function that returns the current pointer to the inverted index file
   * @return - integer value inverted file pointer
   */
  public int getPointerFile(){
      return this.pointerFile;
  }
  /**
   * Function that returns the document frequency for the current word object
   * @return - integer value of the document frequency for the term
   */
  public int getDocumentFrequencey()
  {
     return this.documentFrequency;
  }
  /**
   * Function that will copy document id list to this word objects document 
   * id list
   * @param nw - List of all documents id that contain this word object
   */
  public void copyDocumentID(List<String> nw)
  {
      this.documentID.addAll(nw);
  }
  /**
   * Function that will copy actual word list to this word objects actual 
   * word list
   * @param nw - List of all terms as extracted from corpus 
   */
  public void copyActualList(List<String> nw)
  {
     this.actualWord.addAll(nw);
  }
  /**
   * Function that sets the Document Frequency 
   * @param value - integer value of the document frequency for the word object
   */
  public void setDocumentFrequencey(int value)
  {
      this.documentFrequency = value;
  }
   /**
    * Function that sets the word object word name
    * @param newName : Integer id of the current word object
    */ 
  public void setwordName( String newName )
    {
        this.wordName = newName;
    }
  /**
   * Function that gets the word object word name
   * @return : String word name of the current word object
   */
  public String getWordName()
   {
       return this.wordName;
   }
  /**
   * Function to add the document id of this word object as it appears in 
   * the corpus text
   * @param new_ID 
   */ 
  public void addDocumentID(String new_ID ){
      
      if (this.documentID.isEmpty()) {
            this.documentID.add(new_ID);
        }
      
      if (! this.documentID.contains(new_ID)) {
          this.documentID.add(new_ID);
      }
      
  }
  
  /**
   * Function that adds a new document id and frequency and sorts the 
   * collection
   * @param new_ID 
   */
   public void addDocListStruct(String new_ID ){
      
      if (this.docListStruct.isEmpty()) {
            this.docListStruct.add(new documentID1(new_ID,1));
        }
      Collections.sort(this.docListStruct, documentID1.DocIDComparator2);
  }
   /**
    * Function that adds a new document id and frequency pair to current
    * HashMap
    * @param new_ID 
    */
  public void addDocMapStruct(String new_ID ){
      
      if (this.docMapStruct == null) {
           this.docMapStruct = new HashMap<Integer, Integer>(){{
           put(Integer.parseInt(new_ID),1);
       }};
        }
      
      if (! this.docMapStruct.containsKey(Integer.parseInt(new_ID))) {
           this.docMapStruct.putIfAbsent(Integer.parseInt(new_ID),1);
           
      }
      
  }
  /**
   * Function that updates the document frequency for a word by increasing 
   * termCount by 1
   * @param new_ID 
   */
  public void updateDocListStruct(String new_ID){
      int count = 0; 
      if (! this.docListStruct.isEmpty()) {
          for(documentID1 m : this.docListStruct)
          {  
              if(m.getDocID().equalsIgnoreCase(new_ID)){
               m.setTermCount(m.getTermCount() + 1);
               count++;
            }
          } 
          if(count == 0){
              this.docListStruct.add(new documentID1(new_ID,1)); 
          }
      } 
      Collections.sort(this.docListStruct, documentID1.DocIDComparator2);

  }
  
  /**
   * Function that updates the document frequency increasing by 1
   * @param new_ID - the new term that will be added to the Map Structure
   */
   public void updateDocMapStruct(String new_ID ){
      if (! this.docMapStruct.isEmpty()) {
          this.docMapStruct.forEach((key, value) -> 
          {  if(key == Integer.parseInt(new_ID)){
             this.docMapStruct.replace(Integer.parseInt(new_ID), 
                     value, value + 1);
          }
          }); 
          
        if (! this.docMapStruct.containsKey(Integer.parseInt(new_ID))) {
             this.docMapStruct.putIfAbsent(Integer.parseInt(new_ID),1);
         }
        }
      
  }
 /**
  * Function that returns the word object List of documentID1
  * @return - List of all documentID1 attached to the word object
  */  
 public List<documentID1>  getDocListStruct()
 {
     return this.docListStruct;
 }
   /**
    * Function that returns the current HashMap value
    * @return 
    */
   public HashMap<Integer, Integer> getdocMapStruct()
   {
       return this.docMapStruct;
   }
  /**
   * Function to add the actual spelling of the word as it appears in the text
   * @param new_Word 
   */
    public void addactualWord(String new_Word ){
      
      if (this.actualWord.isEmpty()) {
            this.actualWord.add(new_Word);
        }
      
      if (! this.actualWord.contains(new_Word)) {
          this.actualWord.add(new_Word);
      }
      
   }
   /**
    * Function that sets the collection Frequency for the word object
    * @param newValue : new count of word object object
    */  
   public void setCollectionFrequency( int newValue )
    {
        this.collectionFrequency = newValue;
     }
    
   /**
    * Function that gets the current collection frequency value for the  
    * word object
    * @return : collection frequency for the word object
    */
   public int getCollectionFrequency()
   {
       return this.collectionFrequency;
   }
    
   /**
    * Overriding the compare method to sort the word by collection frequency
    * @return : comparison of 2 collection frequencies 
    */
   public int compare(javaFinalParser d, javaFinalParser d1) {
      return d.collectionFrequency - d1.collectionFrequency;
   }
   
   /**
    * The following function compares to javaFinalPaser objects by name to 
    * correct sorting size
    */  
   public static Comparator<javaFinalParser> DocIDComparator = 
            new Comparator<javaFinalParser>() {

	public int compare(javaFinalParser s1, javaFinalParser s2) {
	   String word1 = s1.getWordName().toLowerCase();
	   String word2 = s2.getWordName().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};
    
   /**
    * Function returns the document frequency for the current class object
    * @return 
    */
   public int getDocumentFrequence()
   {
       return this.documentID.size();
   }
  /**
   * Function returns the string values for the current class parameters
   * @return : String value of the current class parameters 
   */
    @Override
    public String toString()
    {
        return " Word ID                : " + this.wordId + "\n" +
               " Word Name              : " + this.wordName+ "\n" +
               " Collection Frequency   : " + this.collectionFrequency + "\n" +
               " Number of Documents    : " + this.documentID.size() + "\n" ;
    }   

}
