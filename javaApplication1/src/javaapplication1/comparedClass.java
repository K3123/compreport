/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author k3123
 */
public class comparedClass implements Comparator<comparedClass>, Serializable{
 
 private int comID;   
 private String english,greek;
 private int englishID = 0, greekID = 0;
 private int countA = 0, countB = 0, countC = 0, countD = 0;
 private double  PMI = 0;
 private List<documentID1> wordList = new ArrayList<documentID1>();

    public comparedClass() {
    }

    public comparedClass(int comid, String english, String greek, int 
            countA, int countB, int countC, int countD, double PMI) {
        this.comID = comid;
        this.english = english;
        this.greek = greek;
        this.countA = countA;
        this.countB = countB;
        this.countC = countC;
        this.countD = countD;
        this.PMI = PMI;
    }

    public int getComID() {
        return comID;
    }

    public void setComID(int comID) {
        this.comID = comID;
    }

    
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getGreek() {
        return greek;
    }

    public void setGreek(String greek) {
        this.greek = greek;
    }

    public int getCountA() {
        return countA;
    }

    public void setCountA(int countA) {
        this.countA = countA;
    }

    public int getCountB() {
        return countB;
    }

    public void setCountB(int countB) {
        this.countB = countB;
    }

    public int getCountC() {
        return countC;
    }

    public void setCountC(int countC) {
        this.countC = countC;
    }

    public int getCountD() {
        return countD;
    }

    public void setCountD(int countD) {
        this.countD = countD;
    }

    public double getPMI() {
        return PMI;
    }

    public void setPMI(double PMI) {
        this.PMI = PMI;
    }

    public List<documentID1> getWordList() {
        return wordList;
    }

    public void setWordList(List<documentID1> wordList) {
        this.wordList = wordList;
    }
 
   public void addWordListL(List<documentID1> wordData){
       this.wordList.addAll(wordData);
   }
    
    public void addWordListS(documentID1 wordData){
       this.wordList.add(wordData);
   } 

    @Override
    public int compare(comparedClass t, comparedClass t1) {
        return t.getComID() - t1.getComID(); 
 //To change body of generated methods, choose Tools | Templates.
    }

   public static Comparator<comparedClass> DocIDComparator = 
            new Comparator<comparedClass>() {

	public int compare(comparedClass s1, comparedClass s2) {
	   String word1 = s1.getEnglish().toLowerCase();
	   String word2 = s2.getEnglish().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};


      public static Comparator<comparedClass> PMIDComparator = 
            new Comparator<comparedClass>() {

	public int compare(comparedClass s1, comparedClass s2) {
	  return (int) s1.getPMI() * 1000 - (int) s2.getPMI() * 1000 ; 

    }};

   
   

}
