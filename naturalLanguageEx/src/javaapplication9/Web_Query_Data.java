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
public class Web_Query_Data implements Comparator<Web_Query_Data>, 
        Serializable{
    private int webDocID;
    private String timeStamp;
    private String userID;
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
    private int rankNum;
    private StringBuilder queryDetails = new StringBuilder();
    private List<documentID3> queryInfo = new ArrayList<documentID3>();
    private String queryData;
    private int queryCT = 0;

    public Web_Query_Data() {
    }

    public Web_Query_Data(int webDocID, String timeStamp, 
            String userID, int rankNum, String queryData) {
        this.webDocID = webDocID;
        this.timeStamp = timeStamp;
        this.userID = userID;
        this.rankNum = rankNum;
        this.queryData = queryData;
    }

    public int getQueryCT() {
        return queryCT;
    }

    public void setQueryCT(int queryCT) {
        this.queryCT = queryCT;
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

    
    public void addQueryDetails(String data){
        if(this.queryDetails.length() == 0){
          this.queryDetails.append(data);
        }else{
          this.queryDetails.append("|").append(data);   
        }
    }
    
    public int getWebDocID() {
        return webDocID;
    }

    public void setWebDocID(int webDocID) {
        this.webDocID = webDocID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public StringBuilder getQueryDetails() {
        return queryDetails;
    }

    public void setQueryDetails(StringBuilder queryDetails) {
        this.queryDetails = queryDetails;
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }

  public static Comparator<Web_Query_Data>  WebComparator = 
            new Comparator<Web_Query_Data>() {

	public int compare(Web_Query_Data s1, Web_Query_Data s2) {
	   String word1 = s1.getUserID().toLowerCase();
	   String word2 = s2.getUserID().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};

   public List<documentID3> getQueryInfo() {
        return queryInfo;
    }

   public void setQueryInfo(List<documentID3> queryInfo) {
        this.queryInfo = queryInfo;
    }
    
   public void addQueryInfo(String query, String timeS, String rankS){
    if ( this.queryInfo.isEmpty() == true){
       this.queryInfo.add(new documentID3(query,1,timeS,
               Integer.parseInt(rankS)));
    }else{
        boolean checkQuery = false;
        for(documentID3 dataQ : this.getQueryInfo()){
            if(dataQ.getDocID().equalsIgnoreCase(query) == true){
                dataQ.addPosition( Integer.parseInt(rankS));
                dataQ.addTimeStamp(timeS);
                dataQ.setTermCount(dataQ.getTermCount() + 1);
                checkQuery = true;
            }
        }
        if ( checkQuery == false ){
         this.queryInfo.add(new documentID3(query,1,timeS,
               Integer.parseInt(rankS)));  
        } 
    }
       
    }
    
    @Override
    public int compare(Web_Query_Data  o1, Web_Query_Data  o2) {
        return o1.getRankNum() - o2.getRankNum(); 
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Web_Query_Data{" + "webDocID=" + webDocID + ", timeStamp=" + 
                timeStamp + ", userID=" + userID + ", question=" + question + 
                ", stopW=" + stopW + ", mixedW=" + mixedW + ", allUpper=" + 
                allUpper + ", allLower=" + allLower + ", nameAlGore=" + 
                nameAlGore + ", nameJohnH=" + nameJohnH + ", nameJohnHs=" + 
                nameJohnHs + ", webSite=" + webSite + ", coutWords=" + 
                coutWords + ", countChar=" + countChar + ", rankNum=" + 
                rankNum + ", queryDetails=" + queryDetails + ", queryData=" + 
                queryData + ", queryCT=" + queryCT + '}';
    }
    
    
            
    
}
