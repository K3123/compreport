/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import com.aspose.ocr.ImageStream;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javaapplication9.JavaApplication6.*;
import javax.imageio.ImageIO;
import com.aspose.ocr.OcrEngine; 
import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreNLPProtos.CorefChain;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;

import org.ejml.simple.SimpleBase;


/**
 *
 * @author k3123
 */
public class JavaApplication9 {

 static int paragCTL1 = 0; 
   static int paragCTL2 = 0;
   static StringBuilder taskE = new StringBuilder();
   static StringBuilder taskG = new StringBuilder();
   
   static List<Integer> positionMain = new ArrayList<Integer>();
    
     /**
      * The following procedure will load the content of a file into a List of 
      * JavaParser objects
      * @param args List of string parameters to access the file
      * @param stemF - Boolean status that indicates if Stemming has been 
      * flagged
      * @return - The following function returns the a multi-dimensional array
      */  
  public static List<JavaParser>  mainFileLoader( String[] args, boolean stemF ) 
     {
        String[] docData = new String[2];
        String readdata1;
        String dataValue;
        int j = 0;
        List<JavaParser> javaWordList = new ArrayList<JavaParser>();
      
        try
        {   
          
            Scanner scan = new Scanner(new FileInputStream(args[0]));
            while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();  
                               
              if ( readdata1.contains("<P ID=")){
                    docData = readdata1.split("=");
                    docData[1] = docData[1].replace(">","");
                    paragCT++;
                    System.out.println("Current Document : " + docData[1]);
              }
                for(String data : readdata1.split(" "))
                {
                    dataValue = cleanWord(data,stemF);
                    if (validateWord(data)){
                              
                       if (! wordExists(javaWordList,dataValue) ){
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j++);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(data);
                             newWord.addDocMapStruct(docData[1]);
                             newWord.addDocListStruct(docData[1]);
                             newWord.addDocumentID(docData[1]);
                             javaWordList.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(javaWordList,
                                   dataValue);
                     
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(docData[1]); 
                           existingWord.updateDocMapStruct(docData[1]);
                           existingWord.updateDocListStruct(docData[1]);
                           existingWord.addActualWord(data);
                         
                        }                  
                    }
                }
            }
            scan.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot Open File" + e.getMessage());
        }
        
         return javaWordList;
  }
  
    /**
   * The following function will search the dictionary based on the provided 
   * word name and return the corresponding JavaParser object
   * @param word - List of JavaParser objects
   * @param selection - word that is used to search the list of JavaParser 
   * objects
   * @return - JavaParser objected identified during the search
   */
  public static JavaParser1 wordSearch1( List<JavaParser1>  word, 
            String selection )
  {
        JavaParser1 validateCheck = new JavaParser1();
        
        for(JavaParser1 data : word)
          {
            if( data.getwordName().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }
  
    /**
    * Function will determine the number of objects in a file that is larger 
    * than 80000. 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */
   public static void serializedNCount(int dataS){
        
        String type;
        int count = 0;
        
        switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;               
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    

            default : type = dictFileList[1];
                      break;
        }
 
     try {
                
                FileInputStream file = new FileInputStream(type);
                ObjectInputStream in = new ObjectInputStream(file);
                List<nameList> tempObj;
                while((tempObj = (List<nameList>) in.readObject()) != null){
                count++;
                }
                in.close();
           } catch (FileNotFoundException e) {
              System.out.printf("System can not find file : %s %n", 
                          type);
 
           } catch (EOFException e) {
             System.out.printf("System has found %d objects in file : %s %n", 
                          count,type);
                  
           } catch (IOException e) {
             System.out.printf("System had issue with file : %s %n", 
                          type);
           } catch (ClassNotFoundException ex) {
             System.out.printf("System had Class issue with file : %s %n", 
                          type);
         }
     
     
         serilizationData.add(new documentID1(type,count));
    
   }
     /**
    * Function will determine the number of objects in a file that is larger 
    * than 80000. 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */
   public static void serializedWCount(int dataS){
        
        String type;
        int count = 0;
        
        switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;               
             case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    

            default : type = dictFileList[1];
                      break;
        }
 
     try {
                
                FileInputStream file = new FileInputStream(type);
                ObjectInputStream in = new ObjectInputStream(file);
                List<Web_Query_Data> tempObj;
                while((tempObj = (List<Web_Query_Data>) in.readObject()) != null){
                count++;
                }
                in.close();
           } catch (FileNotFoundException e) {
              System.out.printf("System can not find file : %s %n", 
                          type);
 
           } catch (EOFException e) {
             System.out.printf("System has found %d objects in file : %s %n", 
                          count,type);
                  
           } catch (IOException e) {
             System.out.printf("System had issue with file : %s %n", 
                          type);
           } catch (ClassNotFoundException ex) {
             System.out.printf("System had Class issue with file : %s %n", 
                          type);
         }
     
     
         serilizationData.add(new documentID1(type,count));
    
   }
  public static void serializedQCount(int dataS){
        
        String type;
        int count = 0;
        
        switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;               
             case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    
            default : type = dictFileList[1];
                      break;
        }
 
     try {
                
                FileInputStream file = new FileInputStream(type);
                ObjectInputStream in = new ObjectInputStream(file);
                List<queryData2> tempObj;
                while((tempObj = (List<queryData2>) in.readObject()) != null){
                count++;
                }
                in.close();
           } catch (FileNotFoundException e) {
              System.out.printf("System can not find file : %s %n", 
                          type);
 
           } catch (EOFException e) {
             System.out.printf("System has found %d objects in file : %s %n", 
                          count,type);
                  
           } catch (IOException e) {
             System.out.printf("System had issue with file : %s %n", 
                          type);
           } catch (ClassNotFoundException ex) {
             System.out.printf("System had Class issue with file : %s %n", 
                          type);
         }
     
     
         serilizationData.add(new documentID1(type,count));
    
   }
   /**
    * Function sorts the dictionary and writes the results to a file specified 
    * by the user
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */   
  
  public static nameList nameSearch1( List<nameList>  word, 
            String selection )
  {
        nameList validateCheck = new nameList();
        
        for(nameList data : word)
          {
            if( data.getName().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }
  
  public static Web_Query_Data webSearch1( List<Web_Query_Data>  word, 
            String selection )
  {
        Web_Query_Data validateCheck = new Web_Query_Data();
        
        for(Web_Query_Data data : word)
          {
            if( data.getUserID().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }  
  
  public static nlpClassData nlpSearch1( List<nlpClassData>  word, 
            String selection )
  {
        nlpClassData validateCheck = new nlpClassData();
        
        for(nlpClassData data : word)
          {
            if( data.getWordName().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  } 
  
  public static queryData2 querySearch1( List<queryData2>  word, 
            String selection )
  {
        queryData2 validateCheck = new queryData2();
        
        for(queryData2 data : word)
          {
            if( data.getQuery1().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  } 
 /**
  * The following function will determine if a word exists in the dictionary 
  * and returns a boolean result
  * @param word -  List of JavaParser objects
  * @param selection - String word that will be used to search the JavaParser 
  * objects
  * @return - boolean result of searching the list of JavaParser objects 
  */
  public static boolean wordExists1(  List<JavaParser1>  word, 
            String selection) 
    {
        
        for(JavaParser1 data : word)
          {
            if( data.getwordName().toLowerCase().
                    equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    }
  
    public static boolean webExists1(  List<Web_Query_Data>  word, 
            String selection) 
    {
        
        for(Web_Query_Data data : word)
          {
            if( data.getUserID().toLowerCase().
                    equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    }
    
   public static boolean nlpExists1(  List<nlpClassData>  word, 
            String selection) 
    {
        
        for(nlpClassData data : word)
          {
            if( data.getWordName().toLowerCase().
                    equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    }
    
    public static boolean queryExists1(  List<queryData2>  word, 
            String selection) 
    {
        
        for(queryData2 data : word)
          {
            if( data.getQuery1().toLowerCase().
                    equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    } 
    
    
   public static boolean nameExists1(  List<nameList>  word, 
            String selection) 
    {
        
        for(nameList data : word)
          {
            if( data.getName().toLowerCase().
                    equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    }
   /**
      * The following procedure will load the content of a file into a List of 
      * JavaParser objects
      * @param args List of string parameters to access the file
      * @param stemF - Boolean status that indicates if Stemming has been 
      * flagged
      * @return - The following function returns the a multi-dimensional array
      */  
  public static List<Web_Query_Data>  mainFileLoaderW( String[] args) 
     {
        
        String readdata1;
        int countE = 0, countG = 0;
        
        int j = 0;
        List<Web_Query_Data> javaWordList = new ArrayList<Web_Query_Data>();
      
        try
        {   
          
            Scanner scan = new Scanner(new FileInputStream(args[0] + 
                    args[1]));
            while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();  
              paragCT++;                 
            
            int count = readdata1.split("\\t").length;
            
            String[] docData = new String[count];  
            docData = readdata1.split("\\t");
             System.out.printf("%d Current Document : %s %n",paragCT,readdata1);
                 
                   if ( count > 1 ){
                       if (docData[1].toLowerCase().
                               matches(taskG.toString()) == false ){
                            if ( taskG.length() <=0 ){
                              taskG.append(docData[1].toLowerCase());
                            }else{
                              taskG.append("|").append(docData[1].toLowerCase()); 
                             }
                             Web_Query_Data newWord = new Web_Query_Data();
                             newWord.setWebDocID(j++);
                             newWord.setTimeStamp(docData[0]);
                             newWord.setUserID(docData[1].toLowerCase());
                             newWord.setRankNum(Integer.parseInt(docData[2]));
            
                             if ( count > 3 ){
                               newWord.setQueryData(docData[3]);
                               newWord.addQueryDetails(docData[3]);
                               newWord.addQueryInfo(docData[3], docData[0], 
                                     docData[2]);
                             }
                             newWord.setQueryCT(1);
                             javaWordList.add(newWord);
                            
                        }else{
 
                          Web_Query_Data existingWord = webSearch1(javaWordList,
                                   docData[1].toLowerCase());
                           existingWord.setQueryCT(existingWord.
                                   getQueryCT() + 1);
                           if ( count > 3 ){
                           if ( existingWord.getQueryDetails().
                                   indexOf(docData[3]) < 0 ){
                               existingWord.addQueryDetails(docData[3]);
                               existingWord.addQueryInfo(docData[3], docData[0], 
                                     docData[2]);
                           }
                           }
                   
                        }                  
 
                }
            }
            scan.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot Open File" + e.getMessage());
        }

         return javaWordList;
  }   
  
  
   public static List<queryData2> update1(List<queryData2> wordW){
      String http = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}"
              + "\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";

       List<queryData2> newW = new ArrayList<queryData2>();
       
       newW.addAll(wordW);
       
         for ( queryData2 data1 : newW ){
           if(data1.getQuery1().toLowerCase().startsWith("what")){
              data1.setQuestion(Boolean.TRUE);
           }
           if (data1.getQuery1().toLowerCase().matches(http) == true){
              data1.setWebSite(Boolean.TRUE);
           } 
           if(data1.getQuery1().toLowerCase().startsWith("who")){
              data1.setQuestion(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().startsWith("where")){
              data1.setQuestion(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().startsWith("when")){
              data1.setQuestion(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().startsWith("why")){
              data1.setQuestion(Boolean.TRUE);
           } 
           if(data1.getQuery1().toLowerCase().startsWith("could")){
              data1.setQuestion(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().endsWith("?")){
              data1.setQuestion(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().contains("al gore")){
               data1.setNameAlGore(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().contains("johns hopkins")){
               data1.setNameJohnHs(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().contains("john hopkins")){
               data1.setNameJohnH(Boolean.TRUE);
           }
           if(data1.getQuery1().toLowerCase().contains("www.") &&
                 data1.getQuery1().toLowerCase().contains(".com")  ){
               data1.setWebSite(Boolean.TRUE);
           }
          if(data1.getQuery1().toLowerCase().contains("http:\\/\\/.") &&
                 data1.getQuery1().toLowerCase().contains(".html")  ){
               data1.setWebSite(Boolean.TRUE);
           } 
          if(data1.getQuery1().toLowerCase().contains("http:\\/\\/.")) {
               data1.setWebSite(Boolean.TRUE);
           }
         if( data1.getQuery1().matches("[A-Z]&&[^a-z]") == true  ){
               data1.setAllUpper(Boolean.TRUE);
           } 
         if( data1.getQuery1().matches("[a-z]&&[^A-Z]") == true  ){
               data1.setAllLower(Boolean.TRUE);
           } 
         if( data1.getQuery1().matches("[a-z]&&[A-Z]") == true  ){
               data1.setMixedW(Boolean.TRUE);
           } 
         }
       return newW;
       
   } 
   
   public static List<queryData2> mainQueryLoader(List<Web_Query_Data> wordW){
        processTimeData("mainQueryLoader","started");
       List<queryData2> dataQ = new ArrayList<queryData2>();
       int j = 0;
       for(Web_Query_Data dataW : wordW){
           for(String dataB : dataW.getQueryDetails().toString().
                   split("\\|")){
               if ( queryExists1(dataQ,dataB) == false){
                 queryData2 newQ = new queryData2();
                 newQ.setQueryID(j++);
                 newQ.setQueryCT(1);
                 newQ.setQuery1(dataB);
                 newQ.addUserID(dataW.getUserID());
                 dataQ.add(newQ);
               }else{
                  queryData2 newO = querySearch1(dataQ,dataB);
                  newO.setQueryCT(newO.getQueryCT() + 1);
                  newO.addUserID(dataW.getUserID());
               }
           }   
           
       }
        processTimeData("mainQueryLoader","ended");
       return dataQ;
       
   }

   public static List<queryData2> mainQueryLoader2(List<Web_Query_Data> wordW){
        processTimeData("mainQueryLoader2","started");
       List<queryData2> dataQ = new ArrayList<queryData2>();
       int j = 0;
       for(Web_Query_Data dataW : wordW){
           for(String dataB : dataW.getQueryDetails().toString().
                   split("\\|")){
               if ( queryExists1(dataQ,dataB) == false){
                 queryData2 newQ = new queryData2();
                 newQ.setQueryID(j++);
                 newQ.setQueryCT(1);
                 newQ.setQuery1(dataB);
                 for(documentID3 dataWQ :dataW.getQueryInfo() ){
                     for(String dataQ1 : dataWQ.getTimeStamp()){
                       newQ.addTimeS(dataQ1);
                     }
                     for(int dataQ2 : dataWQ.getPosition()){
                         newQ.addRankS(Integer.toString(dataQ2));
                     }
                     
                 }
                 newQ.addUserID(dataW.getUserID());
                 dataQ.add(newQ);
               }else{
                  queryData2 newO = querySearch1(dataQ,dataB);
                  newO.setQueryCT(newO.getQueryCT() + 1);
                  newO.addUserID(dataW.getUserID());
                  for(documentID3 dataWQ :dataW.getQueryInfo() ){
                     for(String dataQ1 : dataWQ.getTimeStamp()){
                       newO.addTimeS(dataQ1);
                     }
                     for(int dataQ2 : dataWQ.getPosition()){
                         newO.addRankS(Integer.toString(dataQ2));
                     }
                     
                 }
               }
           }   
           
       }
       processTimeData("mainQueryLoader2","ended");
       return dataQ;
       
   }
   
   public static List<JavaParser> mainWordLoader1(List<queryData2> query,
           boolean stemF){
       processTimeData("mainWordLoader1","started");
       List<JavaParser> wordD = new ArrayList<JavaParser>();
       int j = 0;
       for(queryData2 queryD : query){
          String qID =  Integer.toString(queryD.getQueryID());
           for(String dataQ : queryD.getQuery1().split(" ")){
                String dataValue = cleanWord(dataQ,stemF);
                    if (validateWord(dataValue)){      
                       if (! wordExists(wordD,dataValue) ){
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(dataQ);
                             newWord.addDocMapStruct(qID);
                             newWord.addDocListStruct(qID);
                             newWord.addDocumentID(qID);
                             newWord.setWordType("non-stopword");
                             wordD.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(wordD,
                                   dataValue);
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(qID); 
                           existingWord.updateDocMapStruct(qID);
                           existingWord.updateDocListStruct(qID);
                           existingWord.addActualWord(dataQ);
                         
                        }
              }else if (dataValue.toLowerCase().matches(stopWords.
                      toLowerCase()) == true){
                       if (! wordExists(wordD,dataValue) ){
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(dataQ);
                             newWord.addDocMapStruct(qID);
                             newWord.addDocListStruct(qID);
                             newWord.addDocumentID(qID);
                             newWord.setWordType("stopword");
                             wordD.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(wordD,
                                   dataValue);
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(qID); 
                           existingWord.updateDocMapStruct(qID);
                           existingWord.updateDocListStruct(qID);
                           existingWord.addActualWord(dataQ);
                         
                        }
          }
       }
      }
        processTimeData("mainWordLoader1","ended");
       return wordD;
   }
   
    public static void mainPDF2(String[] args) {
        File folder = new File("C:\\Users\\k3123\\Documents\\jfk20171109");
        File[] listOfFiles = folder.listFiles();
        String[] docData = new String[2];
        StringBuilder dataF = new StringBuilder();
        StringBuilder dataFX = new StringBuilder();
        
        String readdata1;
        String dataValue;
        String oldFileName;
      
      for (File file : listOfFiles) {
      if (file.isFile()&&(file.getName().contains(".pdf"))) {
       oldFileName = file.getName();
       File fileCheck = new File(folder.getAbsolutePath() + 
                      "\\" + oldFileName.replaceAll(".pdf", ".txt")); 
       if(fileCheck.exists() == false ) {
       
       System.out.printf("Yes we were working on %s %n", oldFileName);   
        
       try {
         // load PDF document
         PDFDocument document = new PDFDocument();
         document.load(new File(folder.getAbsolutePath() + "\\" + oldFileName));
         // create renderer
         org.apache.log4j.BasicConfigurator.configure();
         
         SimpleRenderer renderer = new SimpleRenderer();
         // set resolution (in DPI)
         renderer.setResolution(300);
         // render
         List<Image> images = renderer.render(document);
         // write images to files to disk as PNG
         int count = images.size();
         String[] pngfiles = new String[count];
         
            try {
                for (int i = 0; i < count; i++) {
                    pngfiles[i] = folder.getAbsolutePath() + "\\" + oldFileName.
                                    replaceAll(".pdf", (i + 1) + ".png");
                    ImageIO.write((RenderedImage) images.get(i), "png", 
                            new File(pngfiles[i]));
                }
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
           
         for (int z = 0; z < count; z++ ){
                String imagePath = pngfiles[z];
                ITesseract instance = new Tesseract(); 
                String result;
                File imageFile = new File(imagePath);  
                result = instance.doOCR(imageFile);
                dataF.append(result).append(" ");
                System.out.println("Tesser : " + z + " " + result);
            try {    

             FileWriter fw = new FileWriter(new File(folder.getAbsolutePath() + 
                      "\\" + oldFileName.replaceAll(".pdf", ".txt")),true);
              fw.write(dataF.toString());
              dataF.delete(0, dataF.length());
              fw.close();

             } catch (IOException e) {
                 //do stuff with exception
                 e.printStackTrace();
             }  
            
        }
       org.apache.log4j.BasicConfigurator.resetConfiguration();   
       } catch (Exception e) {
         System.out.println("ERROR: " + e.getMessage());
       }
      
       }
      }
     }  
    }
   
 public static List<nameList> mainLoader(String[] args) {
        // TODO code application logic here
         processTimeData("mainLoader","started");
        File folder = new File("D:\\Downloads\\names\\");
        File[] listOfFiles = folder.listFiles();
        String[] docData = new String[2];
        StringBuilder dataF = new StringBuilder();
        StringBuilder dataFX = new StringBuilder();
        List<nameList> nameL2= new ArrayList<nameList>();
        String readdata1;
        String dataValue;
        String oldFileName;
        int j = 0;
 for (File file : listOfFiles) {
  if (file.isFile()&& file.getName().contains("yob") ) {
       oldFileName = file.getName();
       System.out.printf("%d Yes we were working on %s %n",j, oldFileName);
      try {
         
          Scanner scan = new Scanner(file);
           while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();
              String[] data = new String[4];
              data = readdata1.split(",");
              
               if ( data[0].toLowerCase().matches(taskE.
                       toString()) == false){
                   
                  nameList nameW = new nameList();
              
                  if ( taskE.length() <=0 ){
                     taskE.append(data[0].toLowerCase());
                  }else{
                     taskE.append("|").append(data[0].toLowerCase()); 
                  }
                  nameW.setName(data[0].toLowerCase());
                  nameW.setGender(data[1]);
                  nameW.setYear(Integer.parseInt(data[2]));
                  nameW.setNameCt(1);
                  nameL2.add(nameW);
               }else{
                  nameList nameS = nameSearch1(nameL2,data[0]);
                  nameS.setNameCt(nameS.getNameCt() + 1);
               }
               j++;
            }
            
           scan.close();
          // Scanner 
      } catch (FileNotFoundException ex) {
         ex.printStackTrace();
      }
      
      
      
  }
 }
  processTimeData("mainLoader","ended");     
  return  nameL2;
  
  }

    public static void mainNLP(String text, boolean stemF) { 
        // Create a document. No computation is done yet.
        Document doc = new Document(cleanWord2(text.replaceAll("[\\p{Punct}"
                               + "&&[^']&&[^-]]", " "),stemF));
        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
            // We're only asking for words -- no need to load any models yet
            System.out.println("The second word of the sentence '" + sent + 
                    "' is " + sent.word(1));
            // When we ask for the lemma, it will load and run the part of speech tagger
            System.out.println("The third lemma of the sentence '" + sent + 
                    "' is " + sent.lemma(2));
            // When we ask for the parse, it will load and run the parser
            System.out.println("The parse of the sentence '" + sent + 
                    "' is " + sent.parse());
            // ...
        }
    }

 public static void mainNLP2(String[] args, String text, boolean stemF, 
         List<nlpClassData> nlpList)
   {

    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
    int j = 0;
    Properties props = new Properties();

    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    Annotation document = new Annotation(cleanWord2(text,stemF));

    pipeline.annotate(document);

    List<CoreMap> sentences = document.get(SentencesAnnotation.class);

    for(CoreMap sentence: sentences) {

      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

        String word = token.get(TextAnnotation.class);
        String pos = token.get(PartOfSpeechAnnotation.class);
        String ne = token.get(NamedEntityTagAnnotation.class);

         if (! nlpExists1(nlpList,word) ){
                            
                             nlpClassData newWord = new nlpClassData();
                             newWord.setWordId(nlpList.size());
                             newWord.setWordCT(1);
                             newWord.setWordName(word);
                             newWord.addNamedEntityTag(ne);
                             newWord.addWordPartOfSpeech(pos);
                             nlpList.add(newWord);
                            
                        }else{
 
                           nlpClassData existingWord = nlpSearch1(nlpList,
                                   word);
                           existingWord.setWordCT
                          (existingWord.getWordCT()+1);
                           existingWord.addNamedEntityTag(ne); 
                           existingWord.addWordPartOfSpeech(pos);
                         
                        }
        
        
        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);

      }



      // this is the parse tree of the current sentence

      Tree tree = sentence.get(TreeAnnotation.class);

      System.out.println("parse tree:\n" + tree);


      // this is the Stanford dependency graph of the current sentence

      SemanticGraph dependencies;
      dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);

      System.out.println("dependency graph:\n" + dependencies);

    }



    // This is the coreference link graph

    // Each chain stores a set of mentions that link to each other,

    // along with a method for getting the most representative mention

    // Both sentence and token offsets start at 1!

  }
public static List<JavaParser> mainLoader2(String[] args, boolean stemF, 
        int fileD) {
        // TODO code application logic here
        processTimeData("mainLoader","started");
        File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();
        String[] docData = new String[2];
        StringBuilder dataF = new StringBuilder();
        StringBuilder dataFX = new StringBuilder();
        List<nlpClassData> nameL2 = new ArrayList<nlpClassData>();
        String readdata1;
        String dataValue;
        String oldFileName;
        List<JavaParser> wordD = new ArrayList<JavaParser>();
        int j = 0;
 for (File file : listOfFiles) {
  if (file.isFile()&& file.getName().contains(".txt") && 
         ! file.getName().contains("dictionary") && 
         ! file.getName().contains("data")  ) {
       oldFileName = file.getName();
       System.out.printf("%d Yes we were working on %s %n",j, oldFileName);
        try {
         
          Scanner scan = new Scanner(file);
          
           while(scan.hasNextLine()) { 
            readdata1 = scan.nextLine();

              int count = readdata1.replaceAll("[\\p{Punct}"
                               + "&&[^']&&[^-]]", " ").split("\\s+").length;
              String dataValue2 = readdata1.replaceAll("[\\p{Punct}"
                               + "&&[^']&&[^-]]", " ");    
              int countCheck = 0;
              for(String dataQ : dataValue2.split(" ")){
                    dataValue = cleanWord(dataQ,stemF);
                    if (validateWord(dataValue)){   
                        countCheck++;
                       if (! wordExists(wordD,dataValue) ){
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(dataQ);
                             newWord.addDocMapStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.addDocListStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.addDocumentID(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.setWordType("non-stopword");
                             wordD.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(wordD,
                                   dataValue);
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", "")); 
                           existingWord.updateDocMapStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                           existingWord.updateDocListStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                           existingWord.addActualWord(dataQ);
                         
                        }
              }else if (dataValue.toLowerCase().matches(stopWords.
                      toLowerCase()) == true){
                       if (! wordExists(wordD,dataValue) ){
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(dataQ);
                             newWord.addDocMapStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.addDocListStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.addDocumentID(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                             newWord.setWordType("stopword");
                             wordD.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(wordD,
                                   dataValue);
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", "")); 
                           existingWord.updateDocMapStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                           existingWord.updateDocListStruct(file.getName().
                                     replaceAll(".txt", "").
                                     replaceAll("-", ""));
                           existingWord.addActualWord(dataQ);
                         
                        }
                }
             }
              
               j++;
               if (( countCheck > 4)){
                              Properties props = new Properties();
               props.setProperty("annotators", "tokenize, ssplit, pos, lemma, "
                       + "parse, sentiment");
               StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

               Annotation annotation = pipeline.process(cleanWord2(dataValue2,stemF));
               List<CoreMap> sentences = annotation.get(CoreAnnotations.
                       SentencesAnnotation.class);
               for (CoreMap sentence : sentences) {
                 String sentiment = sentence.get(SentimentCoreAnnotations.
                         SentimentClass.class);
                 System.out.println(sentiment + "\t" + sentence);
                 if ( ! sentiment.equalsIgnoreCase("Negative") && 
                         ! sentiment.toLowerCase().contains("negative")){
                      mainNLP2(args,cleanWord2(dataValue2,stemF),stemF,nameL2); 
                 }
               }   

             }  
            }
            
           scan.close();
          // Scanner 
      } catch (FileNotFoundException ex) {
         ex.printStackTrace();
      }  
      
  }
 }
  serializeDictionaryNlp(nameL2,fileD);
  processTimeData("mainLoader","ended");     
  return  wordD;
  
  } 
   /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - JavaParser object
   */
  public static List<nameList> deserializeDictionaryName(int dataS){
      List<nameList>  word = new ArrayList<nameList>();
      processTimeData("deserializeDictionaryName","started");
      String outFile;
       switch(dataS){
            case 1 : outFile = dictFileList[2];
                           break;
            case 3 : outFile = dictFileList[1];
                           break;
            case 0 : outFile = dictFileList[6];
                           break;
            case 2 : outFile = dictFileList[5];
                         break;
            case 4 : outFile = dictFileList[14];
                         break;
            case 5 : outFile = dictFileList[15];
                         break;       
            case 6 : outFile = dictFileList[16];
                         break;       
            case 7 : outFile = dictFileList[17];
                         break;                                
            default :   outFile = dictFileList[1];
                      break;
        }
      
       try {
                
                ObjectInputStream in = new ObjectInputStream(new 
                FileInputStream(outFile));
                List<nameList> tempObj;
                int count = searchSeriData(serilizationData,outFile);
                if ( count > 1 ){
                    for(int i = 0; i < count; i++ ){
                        tempObj = (List<nameList>) in.readObject();
                        word.addAll(tempObj);
                    }
                }else{
                  word = (List<nameList>) in.readObject();
                }
                if (debugType.equalsIgnoreCase("Debug6")){
                System.out.println("Serialized data is read from " + 
                        outFile);
                }
                in.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          outFile);
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      processTimeData("deserializeDictionaryName","ended");
      return word;
  }

   /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - JavaParser object
   */
  public static List<Web_Query_Data> deserializeDictionaryWeb(int dataS){
      List<Web_Query_Data>  word = new ArrayList<Web_Query_Data>();
      processTimeData("deserializeDictionaryWeb","started");
      String outFile;
       switch(dataS){
            case 1 : outFile = dictFileList[2];
                           break;
            case 3 : outFile = dictFileList[1];
                           break;
            case 0 : outFile = dictFileList[6];
                           break;
            case 2 : outFile = dictFileList[5];
                         break;
            case 4 : outFile = dictFileList[14];
                         break;
            case 5 : outFile = dictFileList[15];
                         break;       
            case 6 : outFile = dictFileList[16];
                         break;       
            case 7 : outFile = dictFileList[17];
                         break;                                
            default :   outFile = dictFileList[1];
                      break;
        }
      
       try {
                
                ObjectInputStream in = new ObjectInputStream(new 
                FileInputStream(outFile));
                List<Web_Query_Data> tempObj;
                int count = searchSeriData(serilizationData,outFile);
                if ( count > 1 ){
                    for(int i = 0; i < count; i++ ){
                        tempObj = (List<Web_Query_Data>) in.readObject();
                        word.addAll(tempObj);
                    }
                }else{
                  word = (List<Web_Query_Data>) in.readObject();
                }
                if (debugType.equalsIgnoreCase("Debug6")){
                System.out.println("Serialized data is read from " + 
                        outFile);
                }
                in.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          outFile);
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      processTimeData("deserializeDictionaryWeb","ended");
      return word;
  }
 /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - JavaParser object
   */
  public static List<queryData2> deserializeDictionaryQuery(int dataS){
      List<queryData2>  word = new ArrayList<queryData2>();
      processTimeData("deserializeDictionaryQuery","started");
      String outFile;
       switch(dataS){
            case 1 : outFile = dictFileList[2];
                           break;
            case 3 : outFile = dictFileList[1];
                           break;
            case 0 : outFile = dictFileList[6];
                           break;
            case 2 : outFile = dictFileList[5];
                         break;
            case 4 : outFile = dictFileList[14];
                         break;
            case 5 : outFile = dictFileList[15];
                         break;       
            case 6 : outFile = dictFileList[16];
                         break;       
            case 7 : outFile = dictFileList[17];
                         break;                                
            default :   outFile = dictFileList[1];
                      break;
        }
      
       try {
                
                ObjectInputStream in = new ObjectInputStream(new 
                FileInputStream(outFile));
                List<queryData2> tempObj;
                int count = searchSeriData(serilizationData,outFile);
                if ( count > 1 ){
                    for(int i = 0; i < count; i++ ){
                        tempObj = (List<queryData2>) in.readObject();
                        word.addAll(tempObj);
                    }
                }else{
                  word = (List<queryData2>) in.readObject();
                }
                if (debugType.equalsIgnoreCase("Debug6")){
                System.out.println("Serialized data is read from " + 
                        outFile);
                }
                in.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          outFile);
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      processTimeData("deserializeDictionaryQuery","ended");
      return word;
  }
    /**
    * Function will determine the number of objects in a file that is larger 
    * than 80000. 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */
   public static void serializedFCount(int dataS){
        
        String type;
        int count = 0;
        
        switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;           
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                                               
            default : type = dictFileList[1];
                      break;
        }
 
     try {
                
                FileInputStream file = new FileInputStream(type);
                ObjectInputStream in = new ObjectInputStream(file);
                List<JavaParser> tempObj;
                while((tempObj = (List<JavaParser>) in.readObject()) != null){
                count++;
                }
                in.close();
           } catch (FileNotFoundException e) {
              System.out.printf("System can not find file : %s %n", 
                          type);
 
           } catch (EOFException e) {
             System.out.printf("System has found %d objects in file : %s %n", 
                          count,type);
                  
           } catch (IOException e) {
             System.out.printf("System had issue with file : %s %n", 
                          type);
           } catch (ClassNotFoundException ex) {
             System.out.printf("System had Class issue with file : %s %n", 
                          type);
         }
     
     
         serilizationData.add(new documentID1(type,count));
    
   }
   /**
    * Function sorts the dictionary and writes the results to a file specified 
    * by the user
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */   
 
   public static void serializeDictionaryName(List<nameList>  word, 
           int dataS){
          Collections.sort(word, nameList.NameComparator);
          FileOutputStream fileOut;
          String type;
         processTimeData("serializeDictionaryName","started");          
         switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;     
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    
            default : type = dictFileList[1];
                      break;
        }
           
          try {
             fileOut = new FileOutputStream(type);
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(word);
             out.close();
             fileOut.close();
           } catch (FileNotFoundException e) {

                  e.printStackTrace();
           } catch (IOException e) {

                  e.printStackTrace();
           }  
         processTimeData("serializeDictionaryName","ended");
     }
    
  /**
    * Function sorts the dictionary, then writes the results to a default file
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionaryWeb(List<Web_Query_Data>  word, 
           int dataS){
          Collections.sort(word, Web_Query_Data.WebComparator);
          FileOutputStream fileOut;
          String type;
         processTimeData("serializeDictionaryWeb","started");          
         switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;     
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    
            default : type = dictFileList[1];
                      break;
        }
           
          try {
               if (debugType.equalsIgnoreCase("Debug3")){
                System.out.printf("Actual size %d splitter num %d %s %n ",
                      word.size(),
                      word.size()/splitNumber[0], type);
               }
             if ( word.size()/splitNumber[0] >= 100 ){
               List<Web_Query_Data> wordSplit1 = new ArrayList<Web_Query_Data>();
              
              wordSplit1.addAll( word.subList(0, (word.size()/63)));
              fileOut = new FileOutputStream(type);
              ObjectOutputStream out1 = new ObjectOutputStream(fileOut);
              out1.writeObject(wordSplit1);
              out1.close();
              int countP = word.size()/splitNumber[0];
              int countP2 = countP * 2;
              int countP3 = word.size();
              int count = 0;
              for(int i = countP + 1 ; i <= word.size(); ){ 
              fileOut = new FileOutputStream(type,true);
              AppendOutputStream out2 = new AppendOutputStream(fileOut){
                                @Override
                protected void writeStreamHeader() throws IOException {
                  reset();
                }
              };
               wordSplit1 = new ArrayList<Web_Query_Data>(); 
               if (debugType.equalsIgnoreCase("Debug3")){
               System.out.printf("Exporting objects %d to %d to %s %n",
                       i,countP2,type);
               }
               wordSplit1.addAll(word.subList(i, countP2));
               out2.writeObject(wordSplit1);
               out2.close();
               i = countP2 + 1;
               countP2 = countP2 + countP;
               count++;
               if (countP2 > countP3){
                   countP2 = countP3;
               }
              }
              serilizationData.add(new documentID1(type,count));
             }else{
                fileOut = new FileOutputStream(type);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(word);
                out.close();
                fileOut.close();
            }

           } catch (IOException e) {
                  e.printStackTrace();
           }  
           
         processTimeData("serializeDictionaryWeb","ended");
     }
    
   /**
    * Function sorts the dictionary, then writes the results to a default file
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionaryNlp(List<nlpClassData>  word, 
           int dataS){
          Collections.sort(word, new nlpClassData());
          FileOutputStream fileOut;
          String type;
         processTimeData("serializeDictionaryWeb","started");          
         switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;     
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    
            default : type = dictFileList[1];
                      break;
        }
           
          try {
               if (debugType.equalsIgnoreCase("Debug3")){
                System.out.printf("Actual size %d splitter num %d %s %n ",
                      word.size(),
                      word.size()/splitNumber[0], type);
               }
             if ( word.size()/splitNumber[0] >= 100 ){
               List<nlpClassData> wordSplit1 = new ArrayList<nlpClassData>();
              
              wordSplit1.addAll( word.subList(0, (word.size()/63)));
              fileOut = new FileOutputStream(type);
              ObjectOutputStream out1 = new ObjectOutputStream(fileOut);
              out1.writeObject(wordSplit1);
              out1.close();
              int countP = word.size()/splitNumber[0];
              int countP2 = countP * 2;
              int countP3 = word.size();
              int count = 0;
              for(int i = countP + 1 ; i <= word.size(); ){ 
              fileOut = new FileOutputStream(type,true);
              AppendOutputStream out2 = new AppendOutputStream(fileOut){
                                @Override
                protected void writeStreamHeader() throws IOException {
                  reset();
                }
              };
               wordSplit1 = new ArrayList<nlpClassData>(); 
               if (debugType.equalsIgnoreCase("Debug3")){
               System.out.printf("Exporting objects %d to %d to %s %n",
                       i,countP2,type);
               }
               wordSplit1.addAll(word.subList(i, countP2));
               out2.writeObject(wordSplit1);
               out2.close();
               i = countP2 + 1;
               countP2 = countP2 + countP;
               count++;
               if (countP2 > countP3){
                   countP2 = countP3;
               }
              }
              serilizationData.add(new documentID1(type,count));
             }else{
                fileOut = new FileOutputStream(type);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(word);
                out.close();
                fileOut.close();
            }

           } catch (IOException e) {
                  e.printStackTrace();
           }  
           
         processTimeData("serializeDictionaryWeb","ended");
     }
    /**
    * Function sorts the dictionary, then writes the results to a default file
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionaryQuery(List<queryData2>  word, 
           int dataS){
          Collections.sort(word, queryData2.QueryComparator);
          FileOutputStream fileOut;
          String type;
         processTimeData("serializeDictionaryQuery","started");          
         switch(dataS){
            case 1 : type = dictFileList[2];
                           break;
            case 3 : type = dictFileList[1];
                           break;
            case 0 : type = dictFileList[6];
                           break;
            case 2 : type = dictFileList[5];
                           break;     
            case 4 : type = dictFileList[14];
                           break;                
            case 5 : type = dictFileList[15];
                           break;         
            case 6 : type = dictFileList[16];
                           break;         
            case 7 : type = dictFileList[17];
                           break;            
            case 8 : type = dictFileList[18];
                           break;                                    
            default : type = dictFileList[1];
                      break;
        }
           
          try {
               if (debugType.equalsIgnoreCase("Debug3")){
                System.out.printf("Actual size %d splitter num %d %s %n ",
                      word.size(),
                      word.size()/splitNumber[0], type);
               }
             if ( word.size()/splitNumber[0] >= 100 ){
               List<queryData2> wordSplit1 = new ArrayList<queryData2>();
              
              wordSplit1.addAll( word.subList(0, (word.size()/63)));
              fileOut = new FileOutputStream(type);
              ObjectOutputStream out1 = new ObjectOutputStream(fileOut);
              out1.writeObject(wordSplit1);
              out1.close();
              int countP = word.size()/splitNumber[0];
              int countP2 = countP * 2;
              int countP3 = word.size();
              int count = 0;
              for(int i = countP + 1 ; i <= word.size(); ){ 
              fileOut = new FileOutputStream(type,true);
              AppendOutputStream out2 = new AppendOutputStream(fileOut){
                                @Override
                protected void writeStreamHeader() throws IOException {
                  reset();
                }
              };
               wordSplit1 = new ArrayList<queryData2>(); 
               if (debugType.equalsIgnoreCase("Debug3")){
               System.out.printf("Exporting objects %d to %d to %s %n",
                       i,countP2,type);
               }
               wordSplit1.addAll(word.subList(i, countP2));
               out2.writeObject(wordSplit1);
               out2.close();
               i = countP2 + 1;
               countP2 = countP2 + countP;
               count++;
               if (countP2 > countP3){
                   countP2 = countP3;
               }
              }
              serilizationData.add(new documentID1(type,count));
             }else{
                fileOut = new FileOutputStream(type);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(word);
                out.close();
                fileOut.close();
            }

           } catch (IOException e) {
                  e.printStackTrace();
           }  
          
         processTimeData("serializeDictionaryQuery","ended");
     }
     
   
   /**
  * Function will display the complete list of javaFinalParser objects that contain
  * the word represented by the string w 
  * @param word - List of javaFinalParser objects
  * @param w - String word to use to search the list of JavaParser objects
  */
 public static void printWordDetailsNameS(List<nameList>  word, String w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new nameList());  
       float m;  
          
        for(nameList data : word)
          {
            if(data.getName().equalsIgnoreCase(w.toLowerCase())){
            System.out.printf("%-20s %-20s %-20s %n","Name","Gender",
                    "Name Frequence");
                System.out.printf("%-20s %-2s %-5d %n",data.getName(),
                  data.getGender(),
                  data.getNameCt());
                  count = count + data.getNameCt();
                  count2++;
            }
          }
         if (count2 == 0 ){
             System.out.println(w + " was not found in the dictionary ");
         }
  }
 
    /**
  * Function will display the complete list of javaFinalParser objects that contain
  * the word represented by the string w 
  * @param word - List of javaFinalParser objects
  * @param w - String word to use to search the list of JavaParser objects
  */
 public static void printWordDetailsName(List<nameList>  word)
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new nameList());  
       float m;  
       
       System.out.printf("%-15s %-2s %-5s %n","Name","G",
                    "Freq");       
        for(nameList data : word)
          {
            

                System.out.printf("%-15s %-2s %-5d %n",
                  data.getName(),
                  data.getGender(),
                  data.getNameCt());
                  count = count + data.getNameCt();
                  count2++;
            
          }
        
  }
 
 /**
  * Function will display the complete list of javaFinalParser objects that contain
  * the word represented by the string w 
  * @param word - List of javaFinalParser objects
  * @param w - String word to use to search the list of JavaParser objects
  */
 public static void printWordDetailWeb(List<Web_Query_Data>  word)
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new Web_Query_Data());  
       float m;  
       
       System.out.printf("%-15s %-2s %-5s %n","Name","G",
                    "Freq");       
        for(Web_Query_Data data : word)
          {
            

                System.out.printf("%-15s %-2s %-5d %n",
                  data.getUserID(),
                  data.getTimeStamp(),
                  data.getQueryCT());
                  count = count + data.getQueryCT();
                  count2++;
            
          }
        
  }
 
  public static void printWordDetailQeury(List<queryData2>  query)
  {
       int count = 0, count2 = 0;
       Collections.sort(query,queryData2.QueryCTComparator);  
       
       System.out.printf("%-300s %-2s %-20s %n","Query","Freq", "UserIDs");       
        for(queryData2 data : query)
          {
            

                  System.out.printf("%-300s %-5d ",
                  data.getQuery1(),
                  data.getQueryCT());
                  count = count + data.getQueryCT();
                  for(documentID1 userD : data.getUserID()){
                      System.out.printf(" (%s , %d) ", userD.getDocID(),
                              userD.getTermCount());
                  }
                  System.out.println();
                  count2++;
                  if ( count2 <= 21 ){
                      break;
                  }
          }
        
  }
  
    public static void mainInt(String[] args, Boolean stemF){
        
        List<nameList> nameM = new ArrayList<nameList>();
        List<Web_Query_Data> webM = new ArrayList<Web_Query_Data>();
        List<Web_Query_Data> webM2 = new ArrayList<Web_Query_Data>();
        List<queryData2> queryD2 = new ArrayList<queryData2>();
        List<queryData2> queryD3 = new ArrayList<queryData2>();
 
        List<JavaParser> wordD2 = new ArrayList<JavaParser>();
        List<JavaParser> wordD3 = new ArrayList<JavaParser>();
        
        int[] queryDataS = new int[20]; 
           if ( stemF == false){
               queryDataS[0] =  1;
               queryDataS[1] =  3;
               queryDataS[2] =  4;
               queryDataS[3] =  5;
               queryDataS[4] =  6;
               queryDataS[5] =  7;
               queryDataS[6] =  8;
           }else{
               queryDataS[0] =  0;
               queryDataS[1] =  2; 
               queryDataS[2] =  4;
               queryDataS[3] =  5;
               queryDataS[4] =  6;
               queryDataS[5] =  7;
               queryDataS[6] =  8;
               
           }
            
        mainPDF2(args);
        if ( checkFileExist(queryDataS[0],"I") == false){    
         nameM = mainLoader(args);
         serializeDictionaryName(nameM,queryDataS[0]);
        }else{
         serializedNCount(queryDataS[0]);   
         nameM = deserializeDictionaryName(queryDataS[0]);
         for(nameList data1 : nameM){
                  if ( taskE.length() <=0 ){
                     taskE.append(data1.getName().toLowerCase());
                  }else{
                     taskE.append("|").append(data1.getName().toLowerCase()); 
                  }
         }
        } 
        
        printWordDetailsName(nameM);
        
        if ( checkFileExist(queryDataS[1],"I") == false ){
            wordD3 = mainLoader2(args,stemF,queryDataS[2]);
            serializeFDictionary(wordD3,queryDataS[1]);
        }else{
            serializedQCount(queryDataS[1]);
            wordD3 = deserializeDictionary(queryDataS[1]);
        }
        
        printWordDetails(wordD3);
        
        
        nameM.clear();
     
        
    }
   
    /**
     * @param args the command line arguments
     */
  /**
     * This procedure is the main portion of the program that will be used to 
     * initialize the application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length >= 2 ){ 
           setFilesDetails(args[0]);
           splitNumber[0] = 64;
           splitNumber[1] = 2;
           processTimeData("main","started");
           mainInt(args,false);
           processTimeData("main","ended");
        }else{ 
           System.out.println("Please enter the location and filename of the "
                   + "input file that contains the corpus and "
                   + "input file that contains the queries. Also, please "
                   + "enter the location that will store the output files "
                   + "and enter a number from (1-50) for the "
                   + "number of concurrent processes to run. Note : "
                   + "if your memory is under 16 GB, it is recommended to "
                   + "select a small number between (1-5).");
           System.out.printf("Ex: %s %s %s %d %n", "C:\\Users\\k3123\\Documents"
                   + "\\fire10.en.utf81", "C:\\Users\\k3123\\Documents\\fire10."
                   +"topics.txt","C:\\Users\\K3123\\Documents\\", 63);
              
           System.out.printf("Ex: %s %s %s %d %n", "D:\\John Hopkins School\\"
                   + "fire10.en.utf81", "D:\\John Hopkins School\\fire10"
                   + "topics.txt","D:\\John Hopkins School\\" , 63);
           
        }
                
    }
    
}
    
 