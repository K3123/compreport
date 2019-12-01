/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author K3123
 */
public class queryData2 implements Comparator<queryData2>, 
        Serializable{
    private int queryID;
    private String query1;
    List<documentID1> userID = new ArrayList<documentID1>();
    List<documentID1> rankS = new ArrayList<documentID1>();
    List<documentID1> timeS = new ArrayList<documentID1>();
    private String timeStamp;
    private String timeStamp2;
    private int queryCT = 0;
    private Boolean question = false;
    private Boolean stopW = false;
    private Boolean mixedW = false;
    private Boolean allUpper = false;
    private Boolean allLower = false;
    private Boolean nameAlGore = false;
    private Boolean nameJohnH = false;
    private Boolean nameJohnHs = false;
    private Boolean webSite = false;
            
    private int coutWords = 0;
    private int countChar = 0;

    
    public queryData2() {
    }

    public queryData2(int queryID, String query1, List<documentID1> userID, 
            String timeStamp, String timeStamp2, int queryCT) {
        this.queryID = queryID;
        this.query1 = query1;
        this.userID = userID;
        this.timeStamp = timeStamp;
        this.timeStamp2 = timeStamp2;
        this.queryCT = queryCT;
    }

    public List<documentID1> getRankS() {
        return rankS;
    }

    public void setRankS(List<documentID1> rankS) {
        this.rankS = rankS;
    }

    public List<documentID1> getTimeS() {
        return timeS;
    }

    public void setTimeS(List<documentID1> timeS) {
        this.timeS = timeS;
    }

    public Boolean getQuestion() {
        return question;
    }

    public void setQuestion(Boolean question) {
        this.question = question;
    }

    public Boolean getStopW() {
        return stopW;
    }

    public void setStopW(Boolean stopW) {
        this.stopW = stopW;
    }

    public Boolean getMixedW() {
        return mixedW;
    }

    public void setMixedW(Boolean mixedW) {
        this.mixedW = mixedW;
    }

    public Boolean getAllUpper() {
        return allUpper;
    }

    public void setAllUpper(Boolean allUpper) {
        this.allUpper = allUpper;
    }

    public Boolean getAllLower() {
        return allLower;
    }

    public void setAllLower(Boolean allLower) {
        this.allLower = allLower;
    }

    public Boolean getNameAlGore() {
        return nameAlGore;
    }

    public void setNameAlGore(Boolean nameAlGore) {
        this.nameAlGore = nameAlGore;
    }

    public Boolean getNameJohnH() {
        return nameJohnH;
    }

    public void setNameJohnH(Boolean nameJohnH) {
        this.nameJohnH = nameJohnH;
    }

    public Boolean getNameJohnHs() {
        return nameJohnHs;
    }

    public void setNameJohnHs(Boolean nameJohnHs) {
        this.nameJohnHs = nameJohnHs;
    }

    public Boolean getWebSite() {
        return webSite;
    }

    public void setWebSite(Boolean webSite) {
        this.webSite = webSite;
    }

    public int getCoutWords() {
        return coutWords;
    }

    public void setCoutWords(int coutWords) {
        this.coutWords = coutWords;
    }

    public int getCountChar() {
        return countChar;
    }

    public void setCountChar(int countChar) {
        this.countChar = countChar;
    }

    
    public int getQueryID() {
        return queryID;
    }

    public void setQueryID(int queryID) {
        this.queryID = queryID;
    }

    public String getQuery1() {
        return query1;
    }

    public void setQuery1(String query1) {
        this.query1 = query1;
    }

    public List<documentID1> getUserID() {
        return userID;
    }

    public void setUserID(List<documentID1> userID) {
        this.userID = userID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp2() {
        return timeStamp2;
    }

    public void setTimeStamp2(String timeStamp2) {
        this.timeStamp2 = timeStamp2;
    }

    public int getQueryCT() {
        return queryCT;
    }

    public void setQueryCT(int queryCT) {
        this.queryCT = queryCT;
    }
    
    public void addUserID(String usern){
        if (this.userID.isEmpty() == true){
            this.userID.add(new documentID1(usern,1));
        }else{
            boolean checkUserID = false;
            for(documentID1 data1 : this.getUserID()){
                if(data1.getDocID().equalsIgnoreCase(usern)){
                    data1.setTermCount(data1.getTermCount() + 1);
                    checkUserID = true;
                }
            }
            if(checkUserID == false){
               this.userID.add(new documentID1(usern,1)); 
            }
        }
    }
    
       public void addTimeS(String timeM){
        if (this.timeS.isEmpty() == true){
            this.timeS.add(new documentID1(timeM,1));
        }else{
            boolean checkUserID = false;
            for(documentID1 data1 : this.getUserID()){
                if(data1.getDocID().equalsIgnoreCase(timeM)){
                    data1.setTermCount(data1.getTermCount() + 1);
                    checkUserID = true;
                }
            }
            if(checkUserID == false){
               this.timeS.add(new documentID1(timeM,1)); 
            }
        }
    }
  public void addRankS(String rankN){
        if (this.rankS.isEmpty() == true){
            this.rankS.add(new documentID1(rankN,1));
        }else{
            boolean checkUserID = false;
            for(documentID1 data1 : this.getUserID()){
                if(data1.getDocID().equalsIgnoreCase(rankN)){
                    data1.setTermCount(data1.getTermCount() + 1);
                    checkUserID = true;
                }
            }
            if(checkUserID == false){
               this.rankS.add(new documentID1(rankN,1)); 
            }
        }
    }    
    
  public static Comparator<queryData2>  QueryComparator = 
            new Comparator<queryData2>() {

	public int compare(queryData2 s1, queryData2 s2) {
	   String word1 = s1.getTimeStamp().toLowerCase();
	   String word2 = s2.getTimeStamp().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};
  
    public static Comparator<queryData2>  QueryCTComparator = 
            new Comparator<queryData2>() {

	public int compare(queryData2 s1, queryData2 s2) {
	   
	   //ascending order
	   return s2.getQueryCT() - s1.getQueryCT();

    }};

    @Override
    public int compare(queryData2 o1, queryData2 o2) {
        return o1.getQuery1().compareToIgnoreCase(o2.getQuery1());
//To change body of generated methods, choose Tools | Templates.
    }
    
    
}
