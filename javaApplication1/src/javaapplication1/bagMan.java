/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.List;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static javaapplication1.JavaApplication6.*;
/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class bagMan implements Runnable {
    private final List<javaFinalParser> word;
    private final List<docQueryStruct> query;
    private final int qNum;
    private final int start;
    private final int end;
    private final String outFileName;
    private final int[] queryDataS;
    /**
     * Constructor for the bagMag class object. Object used to create document 
     * bags, based on a query and set number of terms
     * @param word - current list of documents that are javaFinalParser 
     * objects
     * @param query - current list of queries that are docQueryStruct objects
     * @param qNum - query id integer number 
     * @param start - starting position for the current list of documents
     * @param end - end position for the current list of documents
     * @param outFileName - string name of results file to contain the bag of 
     * documents
     * @param queryDataS - Integer array that specifies the type of process to 
     * occur query or document 
     */
    public bagMan(List<javaFinalParser> word, List<docQueryStruct> query, 
            int qNum, int start, int end, String outFileName, 
            int[] queryDataS) {
        this.word = word;
        this.query = query;
        this.qNum = qNum;
        this.start = start;
        this.end = end;
        this.outFileName = outFileName;
        this.queryDataS = queryDataS;
    }
    
    
    @Override
    public void run() {

        try {     
          
          File f;
          f = new File(outFileName);
          if ( ! f.exists() ){
            List<docQueryStruct> newDocData = buildBagOfDocsI(word,query
                    ,qNum, queryDataS[1], start, end);
       
           serializeQueryRun(newDocData,outFileName);
           newDocData.clear();
          }
      }catch (Exception e) {
         System.out.println("Could not generate dictionary : " + 
                 outFileName);
     }

    }
}
