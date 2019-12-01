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
public class JavaParser implements Comparator<JavaParser>, Serializable{
    
    private static final long serialVersionUID = 471919991438093043L;
    private int wordId;
    private String wordName;
    private String wordType;
    final List<String> documentID = new ArrayList<>();
    final List<String> actualWord = new ArrayList<>();
    final List<documentID1> docListStruct = new ArrayList<>();
    HashMap<Long, Integer> docMapStruct;
    private int collectionFrequency;
    
    /**
     * Constructor used for the class when no parameters are available
     */
    JavaParser()
    {}
    /**
     * Constructor used for the class when all the parameters are available
     * @param id
     * @param wordnam
     * @param newValue
     * @param actWord
     * @param instance
     */
    JavaParser( int id, String wordnam, String newValue, String actWord,
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
           this.docMapStruct = new HashMap<Long, Integer>(){{
           put(Long.parseLong(newValue),1);
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

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
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
   * @param new_ID - new word to be added to documentID structure
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
   * Function that returns the array list of terms as it appears in corpus
   * @return - List of strings of words
   */ 
  public List<String>  getActualList()
  {
    return this.actualWord;  
  }
  /**
   * Function that returns the array list of document id that contain the term
   * @return - List of strings of document id
   */
  public List<String> getDocumentList()
  {
      return this.documentID;
  }
  /**
   * Function that adds a new term to the docListStruct that contains the 
   * document ids and the term frequency
   * @param new_ID - new term to be added to the docListStruct object
   */
   public void addDocListStruct(String new_ID ){
      
      if (this.docListStruct.isEmpty()) {
            this.docListStruct.add(new documentID1(new_ID,1));
        }
      Collections.sort(this.docListStruct, documentID1.DocIDComparator2);
  }
   /**
    * Function that adds a new term to the docMapStruct that contains the 
   * document ids and the term frequency
    * @param new_ID 
    */
  public void addDocMapStruct(String new_ID ){
      
      if (this.docMapStruct == null) {
           this.docMapStruct = new HashMap<Long, Integer>(){{
           put(Long.parseLong(new_ID),1);
       }};
        }
      
      if (! this.docMapStruct.containsKey(Long.parseLong(new_ID))) {
           this.docMapStruct.putIfAbsent(Long.parseLong(new_ID),1);
           
      }
      
  }
    /**
     * Function that returns the current term id
     * @return - Integer value of the term id
     */
    public int getWordId() {
        return wordId;
    }
    /**
     * Function that sets the current term id
     * @param wordId - Integer value of the term id
     */
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
    /**
     * Function that returns the string name of the current term
     * @return - String value of the term
     */
    public String getWordName() {
        return wordName;
    }
    /**
     * Function that sets the string name of the current term
     * @param wordName - String value of the term
     */
    public void setWordName(String wordName) {
        this.wordName = wordName;
    }
    /**
     * Function that returns the current docMapStruct
     * @return - HashMap Structure of the docMapStruct object
     */
    public HashMap<Long, Integer> getDocMapStruct() {
        return docMapStruct;
    }
    /**
     * Function that sets the current docMapStruct
     * @param docMapStruct - HashMap Structure for the docMapStruct object
     */
    public void setDocMapStruct(HashMap<Long, Integer> docMapStruct) {
        this.docMapStruct = docMapStruct;
    }
    /**
     * Function that returns the docListStruct object for a current term
     * @return - List of documentID1 for current term
     */
    public List<documentID1> getDocListStruct() {
        return docListStruct;
    }
 /**
  * Function that sets the current docListStruct
  * @param docListStruct - List of docimentID1 for current term
  */
 public void setDocListStruct(List<documentID1> docListStruct) {
       this.docListStruct.addAll(docListStruct); 
 }
 /**
  * Function that returns the DocIDComparator function object
  * @return - DocIDComparator
  */   
 public static Comparator<JavaParser> getDocIDComparator() {
        return DocIDComparator;
 }
 /**
  * Function that sets the docIDcomparator object
  * @param DocIDComparator - comparator<javaParser> object
  */
  public static void setDocIDComparator(Comparator<JavaParser> 
            DocIDComparator) {
        JavaParser.DocIDComparator = DocIDComparator;
  }
  
  /**
   * Function that is used to update the docListStruct with a new term
   * @param new_ID - String value of term
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
   * Function to udpate the docMapStruct object with a new term
   * @param new_ID - String value of new term
   */
   public void updateDocMapStruct(String new_ID ){
      if (! this.docMapStruct.isEmpty()) {
          this.docMapStruct.forEach((key, value) -> 
          {  if(key == Long.parseLong(new_ID)){
             this.docMapStruct.replace(Long.parseLong(new_ID), 
                     value, value + 1);
          }
          }); 
          
        if (! this.docMapStruct.containsKey(Long.parseLong(new_ID))) {
             this.docMapStruct.putIfAbsent(Long.parseLong(new_ID),1);
         }
        }
      
  }
  /**
   * Function to add the actual spell of the word as it appears in the text
   * @param new_Word - String value of the term from corpus
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
   public int compare(JavaParser d, JavaParser d1) {
      return d.collectionFrequency - d1.collectionFrequency;
   }

     public static Comparator<JavaParser> DocIDComparator = 
            new Comparator<JavaParser>() {

	public int compare(JavaParser s1, JavaParser s2) {
	   String word1 = s1.getwordName().toLowerCase();
	   String word2 = s2.getwordName().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};
    
   /**
    * Function that returns the size of the posting
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

