/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static javaapplication9.JavaApplication6.*;
/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class bagBuilderRunner implements Runnable {
    
    private final List<javaFinalParser> word;
    private final List<docQueryStruct> query;
    private final int qQuery;
    private final String outFileName;
    private int[] queryDataS;
    /**
     * Constructor for bagBuilderRunner class object. This object will be 
     * execute concurrent workers to build document bags based on a specific 
     * query
     * @param word - current list of documents that are javaFinalParser 
     * objects
     * @param query - current list of queries that are docQueryStruct objects
     * @param qQuery - query id integer number 
     * @param outFileName - string name of results file to contain the bag of 
     * documents
     * @param queryDataS - Integer array that specifies the type of process to 
     * occur query or document 
     */
    public bagBuilderRunner(List<javaFinalParser> word, 
            List<docQueryStruct> query, int qQuery, String outFileName,
            int[] queryDataS ) {
        this.word = word;
        this.query = query;
        this.qQuery = qQuery;
        this.outFileName = outFileName;
        this.queryDataS = queryDataS;
    }
    
    @Override
    public void run() {
        
      List<docQueryStruct>  newList, currentList = 
              new ArrayList<docQueryStruct>(); 
      String fileDetails = "bagManFile";
      processTimeData("bagBuilderRunner","started");     
      int totalCT = word.size(); 
      int start = 1,  end;
      String[] fileName = new String[totalCT+1];
      boolean fileStatus = true; 
      File f;
      f = new File(outFileName);
      if ( ! f.exists() ){
        if (totalCT > splitNumber[1]){
            end = totalCT/splitNumber[1];
        }else{
            end = totalCT;
        }
        int current = end;
       for(int i = 0; i < (splitNumber[1]); i++){
             fileName[i] = dictFileList[0] + fileDetails + start + qQuery +  
                     i + ".txt";
             f = new File(fileName[i]);
             if ( ! f.exists() ) { 
               fileStatus = false;
               if (debugType.equalsIgnoreCase("Debug1")){
               System.out.println("The following file was not found "
                       + fileName[i] + " " + fileStatus);
               }
             }
                start = end + 1;
                  end = end + current;
                 if(end > totalCT){
                    end = totalCT;  
            }
        }
          start = 1;   
          end = current;
          if ( fileStatus == false || debugType.equalsIgnoreCase("RUN2")){
            ExecutorService executorB;             
            for (int z = 0; z < splitNumber[1]; z++){
                executorB = Executors.newFixedThreadPool((splitNumber[1]) * 2);
                  fileName[z] = dictFileList[0] + fileDetails + start + qQuery + 
                          z + ".txt";
                 Runnable workerQ = new bagMan(word,query,qQuery,
                   start,end,fileName[z],queryDataS);
                  executorB.execute(workerQ);   
                  start = end + 1;
                  end = end + current;
                 if(end > totalCT){
                    end = totalCT; 
                 }

               executorB.shutdown();
             
              while (!executorB.isTerminated()) {

              }
              
            }
            
        }
          
        for(int a = 0; a <  (splitNumber[1]); a++){
            newList = deserializeQDictionary(fileName[a]);
            mergerQ(currentList,newList,a,queryDataS);
            newList.clear();
        }
        
             docLengthDa(query);
             docLengthDa(currentList);
             dotProduct(query,currentList,qQuery,
                   queryDataS[0]);
             
           simProduct(currentList,query,qQuery); 
           displayBagTFIDF2(currentList,qQuery,"Query TFIDF");
        serializeQueryRun(currentList,outFileName); 
        currentList.clear();
       }
        processTimeData("bagBuilderRunner","ended"); 
        
        
    }
    
}
