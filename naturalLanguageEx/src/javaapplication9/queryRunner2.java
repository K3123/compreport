/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static javaapplication9.JavaApplication6.*;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */

public class queryRunner2 implements Runnable {
    
    private final int qNum;
    private final List<javaFinalParser>  currentList ; 
    private final List<docQueryStruct> queryR ;
    private final String outFileName;
    private int dataS;
    private final boolean stemF;
    private final int[] queryDataS;    
    
    /**
     * Constructor for the queryRunnable object
     * @param qNum - query id integer number 
     * @param currentList - current list of documents that are javaFinalParser 
     * objects
     * @param queryR - current list of queries that are docQueryStruct objects
     * @param outFileName - string name of results file to contain the bag of 
     * documents
     * @param queryDataS - Integer array that specifies the type of process to 
     * occur query or document 
     * @param stemF - boolean flag to specify if stemming was implemented
     */
    public queryRunner2(int qNum, List<javaFinalParser> currentList, 
            List<docQueryStruct> queryR, String outFileName, int[] queryDataS , 
            boolean stemF) {
      this.qNum = qNum;
      this.currentList = currentList;
      this.queryR = queryR;
      this.outFileName = outFileName;
      this.stemF = stemF;
      this.queryDataS = queryDataS;
  }  

     /**
     * Function that runs executes the function called by concurrent workers. 
     * This function will build bags of documents based on the specified
     * query number. The data will be contain in docQueryStruct object and then
     * serialize the docQueryStruct object
     */
    @Override
    public void run() {
     try {     
          
          File f;
          f = new File(outFileName);
          if ( ! f.exists() ){
            List<docQueryStruct> newDocData = buildBagOfDocs(currentList,queryR
                    ,qNum, queryDataS[1]);
            dotProduct(queryR,newDocData,qNum,
                   queryDataS[0]);
            dotProduct(queryR,queryR,qNum,
                   queryDataS[0]);
           simProduct(newDocData,queryR,qNum);
           serializeQueryRun(newDocData,outFileName);
           newDocData.clear();
          }
      }catch (Exception e) {
         System.out.println("Could not generate dictionary : " + 
                 outFileName);
     }
    }
      
    
    
}
