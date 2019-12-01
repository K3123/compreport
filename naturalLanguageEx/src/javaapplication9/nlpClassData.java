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
 *
 * @author k3123
 */
public class nlpClassData implements Comparator<nlpClassData>, Serializable{
    
    private static final long serialVersionUID = 471919991438093043L;
    private int wordId;
    private String wordName;
    private List<documentID1> wordPartOfSpeech = new ArrayList<documentID1>();
    private List<documentID1> namedEntityTag = new ArrayList<documentID1>();
    private int wordCT = 0;
    
    public nlpClassData() {
    }

    public nlpClassData(int wordId, String wordName, List<documentID1> 
            wordPartOfSpeech, List<documentID1> NamedEntityTag) {
        this.wordId = wordId;
        this.wordName = wordName;
        this.wordPartOfSpeech = wordPartOfSpeech;
        this.namedEntityTag = NamedEntityTag;
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

    public List<documentID1> getWordPartOfSpeech() {
        return wordPartOfSpeech;
    }

    public void setWordPartOfSpeech(List<documentID1> wordPartOfSpeech) {
        this.wordPartOfSpeech = wordPartOfSpeech;
    }

    public List<documentID1> getNamedEntityTag() {
        return namedEntityTag;
    }

    public void setNamedEntityTag(List<documentID1> NamedEntityTag) {
        this.namedEntityTag = NamedEntityTag;
    }
    
    public void addNamedEntityTag(String nameentity){
          int count = 0; 
  
        if (! this.namedEntityTag.isEmpty()) {
          for(documentID1 m : this.namedEntityTag)
          {  
              if(m.getDocID().equalsIgnoreCase(nameentity)){
               m.setTermCount(m.getTermCount() + 1);
               count++;
            }
          } 
          if(count == 0){
             this.namedEntityTag.add(new documentID1(nameentity,1)); 
          }
     }
      Collections.sort(this.namedEntityTag, documentID1.DocIDComparator2);
          
    }
    
     public void addWordPartOfSpeech(String nameentity){
          int count = 0;  
      if (! this.wordPartOfSpeech.isEmpty()) {
          for(documentID1 m : this.wordPartOfSpeech)
          {  
              if(m.getDocID().equalsIgnoreCase(nameentity)){
               m.setTermCount(m.getTermCount() + 1);
               count++;
            }
          } 
          if(count == 0){
             this.wordPartOfSpeech.add(new documentID1(nameentity,1)); 
          }
      }
      Collections.sort(this.wordPartOfSpeech, documentID1.DocIDComparator2);
          
    }
    
    public int getWordCT() {
        return wordCT;
    }

    public void setWordCT(int wordCT) {
        this.wordCT = wordCT;
    }

    
    
    
    @Override
    public int compare(nlpClassData o1, nlpClassData o2) {
        return o1.getWordId() - o2.getWordId();
        //To change body of generated methods, choose Tools | Templates.
    }
    
}
