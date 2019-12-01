/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.List;
import static javaapplication2.JavaApplication2.*;

/**
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
public class parserRunnable implements Runnable {
 
    private int start;
    private int end;
    private String[] argsm;
    private String outFileName;
    private int dataS;
    private boolean stemF;

   /**
    * Constructor for the parserRunnable object
    * @param argsm - array arguments from the command line. This parameter 
    * generally contains the corpus and the query file name 
    * @param start - integer value for the process to start reading from the 
    * corpus or the query file
    * @param end - integer value for the process to stop reading from the corpus
    * or query file
    * @param outFileName - outfile name that will contain the serialized data 
    * from the javaParser objet
    * @param dataS - integer indicated that determines if 
    * the file being process is a corpus or query file and if stemming will be 
    * applied
    * @param stemF - boolean value that indicates if stemming should be applied 
    */ 
    public parserRunnable(String[] argsm, int start, int end, 
            String outFileName, int dataS, boolean stemF) {
        this.start = start;
        this.end = end;
        this.argsm = argsm;
        this.outFileName = outFileName;
        this.dataS = dataS;
        this.stemF = stemF;
    }
   
    /**
     * Function that runs executes the function called by concurrent workers. 
     * This function will parse the corpus in the javaParser object and then
     * serialize the javaParser object
     */
    @Override
    public void run() {
     try {     
        List<javaParser> currentList = mainFileLoader2(argsm,start,end, 
                dataS, stemF);
        serializeDictionaryRun(currentList,outFileName);
        System.out.println(outFileName + " has been generated ");
     }catch (Exception e) {
         System.out.println("Could not generate dictionary : " + 
                 outFileName);
     }
    }
    
}
