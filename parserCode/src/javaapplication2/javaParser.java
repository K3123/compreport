/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;
/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class javaParser implements Comparator<javaParser>, Serializable{
    
    private static final long serialVersionUID = 471919991438093042L;
    private int wordId;
    private String wordName;
    final List<String> documentID = new ArrayList<>();
    final List<String> actualWord = new ArrayList<>();
    final List<documentID1> docListStruct = new ArrayList<>();
    HashMap<Integer, Integer> docMapStruct;
    private int collectionFrequency;
    
    /**
     * Constructor used for the class when no parameters are available
     */
    javaParser()
    {}
    /**
     * Constructor used for the class when all the parameters are available
     * @param id
     * @param wordnam
     * @param newValue
     * @param actWord
     * @param instance
     */
    javaParser( int id, String wordnam, String newValue, String actWord,
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
  public String getwordName()
   {
       return this.wordName;
   }
  /**
   * Function to add the document id of the word as it appears in the text
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
   * 
   * @return 
   */ 
  public List<String>  getActualList()
  {
    return this.actualWord;  
  }
  /**
   * 
   * @return 
   */
  public List<String> getDocumentList()
  {
      return this.documentID;
  }
  /**
   * 
   * @param new_ID 
   */
   public void addDocListStruct(String new_ID ){
      
      if (this.docListStruct.isEmpty()) {
            this.docListStruct.add(new documentID1(new_ID,1));
        }
      Collections.sort(this.docListStruct, documentID1.DocIDComparator2);
  }
   /**
    * 
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

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public HashMap<Integer, Integer> getDocMapStruct() {
        return docMapStruct;
    }

    public void setDocMapStruct(HashMap<Integer, Integer> docMapStruct) {
        this.docMapStruct = docMapStruct;
    }

    public List<documentID1> getDocListStruct() {
        return docListStruct;
    }

 public void setDocListStruct(List<documentID1> docListStruct) {
       this.docListStruct.addAll(docListStruct); 
 }
    
 public static Comparator<javaParser> getDocIDComparator() {
        return DocIDComparator;
 }

  public static void setDocIDComparator(Comparator<javaParser> 
            DocIDComparator) {
        javaParser.DocIDComparator = DocIDComparator;
  }
  
  /**
   * 
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
   * 
   * @param new_ID 
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
   * Function to add the actual spell of the word as it appears in the text
   * @param new_Word 
   */
    public void addActualWord(String new_Word ){
      
      if (this.actualWord.isEmpty()) {
            this.actualWord.add(new_Word);
        }
      
      if (! this.actualWord.contains(new_Word)) {
          this.actualWord.add(new_Word);
      }  
   }
   /**
    * Function that sets the collectionFrequency for the word object
    * @param newValue : new count of word object object
    */  
   public void setCollectionFrequency( int newValue )
    {
        this.collectionFrequency = newValue;
     }
    
   /**
    * Function that gets the current collection frequency value for the current 
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
   public int compare(javaParser d, javaParser d1) {
      return d.collectionFrequency - d1.collectionFrequency;
   }

     public static Comparator<javaParser> DocIDComparator = 
            new Comparator<javaParser>() {

	public int compare(javaParser s1, javaParser s2) {
	   String word1 = s1.getwordName().toLowerCase();
	   String word2 = s2.getwordName().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};
    
   /**
    * 
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
