/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import static javaapplication1.JavaApplication6.*;

/**
 *
 * @author k3123
 */
public class JavaApplication1 {

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
  public static List<JavaParser>  mainFileLoaderL( String[] args,
          int order, boolean stemF ) 
     {
        
        String readdata1;
        int countE = 0, countG = 0;
        
        int j = 0;
        List<JavaParser> javaWordList = new ArrayList<JavaParser>();
      
        try
        {   
          
            Scanner scan = new Scanner(new FileInputStream(args[0] + 
                    args[order]));
            while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();  
              paragCT++;                 
            
            int count = readdata1.replaceAll("[\\p{Punct}"
                               + "&&[^']&&[^-]]", " ").split("\\s+").length;
            if (readdata1.toLowerCase().
                    contains("washington") && countE < 4 && count <= 20 ){
                taskE.append(readdata1).append("*");
                positionMain.add(paragCT);
                countE++;
             }
            if ( order == 1 ){
                  paragCTL1++;
              }else{
                  paragCTL2++;
                  if(positionMain.contains(paragCTL2)){
                     taskG.append(readdata1).append("*"); 
                     countG++;
                  }
             } 
            
            String[] docData = new String[count];  
            docData = readdata1.replaceAll("[\\p{Punct}"
                                    + "&&[^']&&[^-]]", " ").split("\\s+");
                 int x = 0;
                 for(String data1 : docData){   
                     
                      String dataValue = data1.toLowerCase().
                              replaceAll("[0-9]","").
                              replaceAll("[\\p{Punct}]", "").
                              replaceAll("'", "").
                              replaceAll("\\s+","");
                      if ( order == 1){
                        dataValue = cleanWord(dataValue,stemF);
                      }
                      
                      if (validateWord(dataValue) == true && order == 1 )  {
                       if (! wordExists(javaWordList,dataValue) ){
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j++);
                             newWord.setSearch(dataValue);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(data1);
                             newWord.setDocSearch(Integer.toString(x));
                             newWord.addDocMapStruct(Integer.toString(x),x);
                             newWord.addDocListStruct(Integer.toString(x),x);
                             newWord.addDocumentID(Integer.toString(x));
                             javaWordList.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(javaWordList,
                                   dataValue);
                           existingWord.setCollectionFrequency
                                (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(docData[1]); 
                           existingWord.updateDocMapStruct(Integer.
                                   toString(x),x);
                           existingWord.updateDocListStruct(Integer.
                                   toString(x),x);
                           existingWord.setDocSearch(Integer.toString(x));
                           existingWord.addActualWord(data1);
                         
                        }                  
                    
                     }else{
                         if (order == 2 && 
                              dataValue.length() > 0){
                              if (! wordExists(javaWordList,dataValue) ){
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j++);
                             newWord.setSearch(dataValue);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(data1);
                             newWord.setDocSearch(Integer.toString(x));
                             newWord.addDocMapStruct(Integer.toString(x),x);
                             newWord.addDocListStruct(Integer.toString(x),x);
                             newWord.addDocumentID(Integer.toString(x));
                             javaWordList.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(javaWordList,
                                   dataValue);
                           existingWord.setCollectionFrequency
                                (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(docData[1]); 
                           existingWord.updateDocMapStruct(Integer.
                                   toString(x),x);
                           existingWord.updateDocListStruct(Integer.
                                   toString(x),x);
                           existingWord.setDocSearch(Integer.toString(x));
                           existingWord.addActualWord(data1);
                         
                        }            
                         }
                      }
                   x++;   
                }
            }
            scan.close();
        }
        catch (IOException e)
        {
            System.out.println("Cannot Open File" + e.getMessage());
        }

         for ( JavaParser data1 : javaWordList ){
           if ( data1.getDocSearch().length() > 1 ){
            String[] c = new String[data1.getDocSearch().toString().
                    split("|").length];   
            c = data1.getDocSearch().toString().split("\\|");
            int countI = 0;
            int[] cNum = new int[c.length];
            for(String dataC : c){
             cNum[countI] = Integer.parseInt(dataC);
             countI++;
            }
            data1.delDocSearch();
            Arrays.sort(c);
            Arrays.sort(cNum);
            for(int dataC : cNum){
             if ( data1.getDocSearch().toString().
                     contains(Integer.toString(dataC)) == false  ) {  
             data1.setDocSearch(Integer.toString(dataC));
             }
            }
           }
         }
         return javaWordList;
  }    
  public static comparedClass searchTable1(List<comparedClass> tableW, 
         String term, String type) {
         comparedClass validateCheck = new comparedClass();
        Collections.sort(tableW, comparedClass.PMIDComparator);
        for(comparedClass data : tableW)
          {
            if ( type.contains("G")){  
            if( data.getGreek().equals(term) == true )
            {
               validateCheck = data; 
               break;
             }            
            }else{
             term = cleanWord(term,false);   
             if( data.getEnglish().
                     equalsIgnoreCase(term) == true )
            {
               validateCheck = data; 
               break;
             }  
            }
          }     
        return validateCheck;
  }
   public static void printWashington(List<comparedClass> tableW){
       
       Collections.sort(tableW, comparedClass.PMIDComparator);
       String[] dataG = new String[taskE.toString().split("\\*").length];
       StringBuilder translation = new StringBuilder();
       dataG = taskE.toString().split("\\*");
       for(int x = 0; x < dataG.length; x++ ){
           for(String dataW : dataG[x].split("\\s+")){
              comparedClass dataM = searchTable1(tableW,
                      cleanWord(dataW,false),"E");
              translation.append(dataM.getGreek()).append(" ");
           }
           System.out.printf("Sentences : %s %n",dataG[x]);
           System.out.printf("Translate : %s %n",translation.toString());
           
       }
   }
   /**
     * This procedure will initiate the postProcessData function and 
     * de-serialize the document and query objects to print the complete list
     * of objects
     * @param args - command line arguments
     * @param stemF - - boolean value that indicates if stemming should be 
     * applied  
     */
    public static void mainInt(String[] args, boolean stemF)
    {
        int[] queryDataS = new int[20]; 
        List<JavaParser> updateList1 = new ArrayList<JavaParser>();
        List<JavaParser> updateList2 = new ArrayList<JavaParser>();
        processTimeData("mainInt","started");
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

        if ( checkFileExist(queryDataS[0],"I") == false){   
             updateList1 = mainFileLoaderL(args,1,stemF); 
            serializeFDictionary(updateList1,queryDataS[0]);
        }else{
             paragCTL1 = 15000;
             serializedFCount(queryDataS[0]);
             updateList1 = deserializeDictionary(queryDataS[0]); 
        }
        
        printWordDetails(updateList1);
        List<javaFinalParser> updateListF1 = conversionDictionary(
                             updateList1,queryDataS[0]);
        updateList1.clear();
        if ( checkFileExist(queryDataS[1],"I") == false){ 
            updateList2 = mainFileLoaderL(args,2,stemF);
            serializeFDictionary(updateList2,queryDataS[1]);
        }else{
            paragCTL2 = 15000;
            serializedFCount(queryDataS[1]);
            updateList2 = deserializeDictionary(queryDataS[1]);  
        }
        printWordDetails(updateList2);    
        List<javaFinalParser> updateListF2 = conversionDictionary(
                             updateList2,queryDataS[1]);      
        updateList2.clear();
        
        printWordDetailsF(updateListF1);
        printWordDetailsF(updateListF2);
        
        
        List<comparedClass> tableWL =  comparisonW1(updateListF1,updateListF2,
                                       queryDataS);
        serializeDictionaryCom(tableWL,queryDataS[4]);
        printDocID2FWordDetails5(tableWL);
        
        List<comparedClass> tableWL2 =  comparisonW2(updateListF1,updateListF2,
                                       queryDataS);
        serializeDictionaryCom(tableWL2,queryDataS[5]);
        printDocID2FWordDetails5(tableWL2);
        
        
        List<comparedClass> tableWS =  comparisonW(updateListF1,updateListF2,
                                       queryDataS);
        serializeDictionaryCom(tableWS,queryDataS[3]);
        printWashington(tableWS);
        tableWS.clear();
             
        List<comparedClass> tableD =  comparisonL(updateListF1,updateListF2,
                                       queryDataS);
        
        printDocID2FWordDetails5Words(tableD);
        serializeDictionaryCom(tableD,queryDataS[2]);
        tableD.clear();
        
       processTimeData("mainInt","started"); 
    }
/**
   * Function will display the complete dictionary with the sorted document ids
   * @param word - List of JavaParser objects 
   */
   public static void printDocID2FWordDetails(List<comparedClass>  word )
  {
        int count = 0;
        Collections.sort(word, comparedClass.PMIDComparator);
        System.out.printf("%-20s %-20s %-2s %-2s %-2s %-3s %n","English Word ",
                "Greek Word ", "A","B","C","D","PMI");
         for(comparedClass data : word)
          {
           System.out.printf("%-20s %-20s %d %d %d %d  %-6f  ",
                  data.getEnglish(),
                  data.getGreek(),
                  data.getCountA(),
                  data.getCountB(),
                  data.getCountC(),
                  data.getCountD(),
                  data.getPMI());
                  for(documentID1 dataW : data.getWordList()){
                    System.out.printf("(%s,%d,%d) ",
                            dataW.getDocID(),
                            dataW.getTermCount(),
                            dataW.getPosition().size()
                    );                   
                  }
                 System.out.println("\n");
            count++;    
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.printf("%-100s %-20d %n"," Total number of terms",
                 count );
         System.out.println("\n");
  }

/**
   * Function will display the complete dictionary with the sorted document ids
   * @param word - List of JavaParser objects 
   */
   public static void printDocID2FWordDetails5(List<comparedClass>  word )
  {
        Collections.sort(word, comparedClass.PMIDComparator);
        
        int count = 0;
        System.out.printf("%-20s %-20s %-2s %-2s %-2s %-2s %-3s %n","English Word ",
                "Greek Word ", "A","B","C","D","PMI");
         for(comparedClass data : word)   
          {
             System.out.printf("%-20s %-20s %d %d %d %d %6f  ",
                  data.getEnglish(),
                  data.getGreek(),
                  data.getCountA(),
                  data.getCountB(),
                  data.getCountC(),
                  data.getCountD(),
                  data.getPMI());
                  int count2 = 0; 
                  Collections.sort(data.getWordList(),
                          documentID1.DocIDComparator);
                  for(documentID1 dataW : data.getWordList()){
                    System.out.printf("(%s,%d,%d)",
                            dataW.getDocID(),
                            dataW.getTermCount(),
                            dataW.getPosition().size()
                    );
                   if ( count2 == 5 ){
                       break;
                   } 
                   count2++;                    
                  }
                 System.out.println("\n");
            count++;    
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.printf("%-100s %-20d %n"," Total number of terms",
                 count );
         System.out.println("\n");
  }   

   /**
   * Function will display the complete dictionary with the sorted document ids
   * @param word - List of JavaParser objects 
   */
   public static void printDocID2FWordDetails5Words(List<comparedClass>  word )
  {
        Collections.sort(word, comparedClass.PMIDComparator);
        List<comparedClass>  word2 = new ArrayList<comparedClass>(); 
        int count = 0, count3 = 0;
        System.out.printf("%-20s %-20s %-2s %-2s %-2s %-2s %-3s %n","English Word ",
                "Greek Word ", "A","B","C","D","PMI");
        String currentW = null;
        for (comparedClass data : word){
            if (data.getEnglish().equalsIgnoreCase(currentW)){
                count3++;
            }else{
                count3 = 0;
                currentW = data.getEnglish();
            }
            
            if (count3 <= 5 ){
              word2.add(data);
            }
        }
        
         for(comparedClass data : word2)   
          {
             System.out.printf("%-20s %-20s %d %d %d %d %6f  ",
                  data.getEnglish(),
                  data.getGreek(),
                  data.getCountA(),
                  data.getCountB(),
                  data.getCountC(),
                  data.getCountD(),
                  data.getPMI());
                  int count2 = 0; 
                  Collections.sort(data.getWordList(),
                          documentID1.DocIDComparator);
                  for(documentID1 dataW : data.getWordList()){
                    System.out.printf("(%s,%d,%d)",
                            dataW.getDocID(),
                            dataW.getTermCount(),
                            dataW.getPosition().size()
                    );
                   if ( count2 == 5 ){
                       break;
                   } 
                   count2++;                    
                  }
                 System.out.println("\n");
            count++;    
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.printf("%-100s %-20d %n"," Total number of terms",
                 count );
         System.out.println("\n");
  }   
   
   
public static List<comparedClass> comparisonW1(List<javaFinalParser> eng, 
        List<javaFinalParser> greek, int[] queryDataS ){
    List<comparedClass> lanTable = new ArrayList<comparedClass>();
    processTimeData("comparisonW1","started");
    List<javaFinalParser> eng1 = new ArrayList<javaFinalParser>();
    List<javaFinalParser> gre1 = new ArrayList<javaFinalParser>();
    StringBuilder greData1 = new StringBuilder();
    StringBuilder wBag = new StringBuilder(); 
    StringBuilder engData1 = new StringBuilder();
    
        wBag.append("telephone").append("|");
        wBag.append("swimming").append("|");
        wBag.append("helicopter").append("|");
        wBag.append("washington");
    
       
        for(javaFinalParser dataE : eng ){
          if( dataE.getWordName().toLowerCase().
                  matches(wBag.toString())){
              if ( ! eng1.contains(dataE) ){
                 if ( engData1.length() <= 0 ){
                  engData1.append(dataE.getDocSearch());
                  }else{
                  engData1.append("|").append(dataE.getDocSearch());    
                 }
                eng1.add(dataE);
              }
          }
        }  
          
       for(javaFinalParser dataE : eng1 ){
          for( javaFinalParser dataG : greek){
                 int count = 0;
                 if ( dataE.getDocumentFrequencey() <= dataG.getDocumentFrequencey()){
                 for(String dataG1 : dataG.getDocSearch().toString().split("\\|")){
                    if ((dataE.getDocSearch().toString().contains("|"+dataG1+"|") == true) ||
                       (dataE.getDocSearch().toString().startsWith(dataG1+"|") == true) ||
                            (dataE.getDocSearch().toString().endsWith("|"+dataG1) == true))
                            
                    {
                      count++;
                    }
                 }
                 }
                 
                 if ( count >= 5){
                     comparedClass dataC = new comparedClass();
                     dataC.setComID(Integer.parseInt(Integer.
                     toString(dataE.getwordId()) + 
                     Integer.toString(dataG.getwordId())));
                     dataC.addWordListL(dataG.getDocListStruct());
                     dataC.setGreek(dataG.getWordName());
                     dataC.setEnglish(dataE.getWordName());
                     dataC.setCountA(count);
                     dataC.setCountB(dataG.getDocumentFrequence() - count);
                     dataC.setCountC(dataE.getDocumentFrequence() - count);
                     dataC.setCountD(paragCTL1 - dataC.getCountA());
                     
                 double dataPMI1T = paragCTL1 * dataC.getCountA();
                 double dataPMI1B = (dataC.getCountA() + dataC.getCountB())*
                                    (dataC.getCountA() + dataC.getCountC());
                 dataC.setPMI(Math.log(dataPMI1T/dataPMI1B)/Math.log(2));
                  lanTable.add(dataC);
          }
      }
    }
       
    processTimeData("comparisonW1","ended");
    return lanTable;
}    

public static List<comparedClass> comparisonW2(List<javaFinalParser> eng, 
        List<javaFinalParser> greek, int[] queryDataS ){
    List<comparedClass> lanTable = new ArrayList<comparedClass>();
    processTimeData("comparisonW2","started");
    List<javaFinalParser> eng1 = new ArrayList<javaFinalParser>();
    List<javaFinalParser> gre1 = new ArrayList<javaFinalParser>();
    StringBuilder greData1 = new StringBuilder();
    StringBuilder wBag = new StringBuilder(); 
    StringBuilder engData1 = new StringBuilder();
    
       
       
        for(javaFinalParser dataE : eng ){
          if( dataE.getWordName().toLowerCase().
                  matches("motorcycle")){
             System.out.printf("Yes we were found %d %n", 
                      dataE.getCollectionFrequency());
              if ( ! eng1.contains(dataE) ){
                eng1.add(dataE);
              }
          }
        }  
          
        
               
        for(javaFinalParser dataG : greek ){
          if( dataG.getWordName().toLowerCase().
                  matches("μοτοσικλέτα")){
              System.out.printf("Yes we were found %d %n", 
                      dataG.getCollectionFrequency());
              if ( ! gre1.contains(dataG) ){
                gre1.add(dataG);
              }
          }
        }  
          
      
        
        
       for(javaFinalParser dataE : eng1 ){
          for( javaFinalParser dataG : gre1){
                 int count = 0;
                  for(String dataG1 : dataG.getDocSearch().toString().split("\\|")){
                    if ((dataE.getDocSearch().toString().contains("|"+dataG1+"|") == true) ||
                       (dataE.getDocSearch().toString().startsWith(dataG1+"|") == true) ||
                            (dataE.getDocSearch().toString().endsWith("|"+dataG1) == true))
                            
                    {
                      count++;
                    }
                 }
             System.out.printf("comparisonW2 : Yes count is : %d %n",count);
             if ( count >= 5){
                     comparedClass dataC = new comparedClass();
                     dataC.setComID(Integer.parseInt(Integer.
                     toString(dataE.getwordId()) + 
                     Integer.toString(dataG.getwordId())));
                     dataC.addWordListL(dataG.getDocListStruct());
                     dataC.setGreek(dataG.getWordName());
                     dataC.setEnglish(dataE.getWordName());
                     dataC.setCountA(count);
                     dataC.setCountB(dataG.getDocumentFrequence() - count);
                     dataC.setCountC(dataE.getDocumentFrequence() - count);
                     dataC.setCountD(paragCTL1 - dataC.getCountA());
                     
                 double dataPMI1T = paragCTL1 * dataC.getCountA();
                 double dataPMI1B = (dataC.getCountA() + dataC.getCountB())*
                                    (dataC.getCountA() + dataC.getCountC());
                 dataC.setPMI(Math.log(dataPMI1T/dataPMI1B)/Math.log(2));
             
                  lanTable.add(dataC);
          }
      }
    }
       
    processTimeData("comparisonW2","ended");
    return lanTable;
}    
public static List<comparedClass> comparisonW(List<javaFinalParser> eng, 
        List<javaFinalParser> greek, int[] queryDataS ){
    List<comparedClass> lanTable = new ArrayList<comparedClass>();
    processTimeData("comparisonW","started");
    List<javaFinalParser> eng1 = new ArrayList<javaFinalParser>();
    List<javaFinalParser> greek1 = new ArrayList<javaFinalParser>();
    String[] wBag = new String[taskE.toString().split("\\*").length]; 
    wBag = taskE.toString().split("\\*");
    StringBuilder engData1 = new StringBuilder();
    StringBuilder greData1 = new StringBuilder();
    for(int z = 0; z < wBag.length; z++){
      System.out.printf("Yes : %s %d %d %n", wBag[z],z,wBag[z].length());
    }
    for(int i = 0; i < wBag.length; i++ ){
        String dataW1 = wBag[i].replaceAll(" ", "|").toLowerCase();
        for(javaFinalParser dataE : eng ){
          if( dataE.getWordName().toLowerCase().matches(dataW1)){
              if ( ! eng1.contains(dataE)  ){
                  if ( engData1.length() <= 0 ){
                  engData1.append(dataE.getDocSearch());
                  }else{
                  engData1.append("|").append(dataE.getDocSearch());    
                  }
                  eng1.add(dataE);
              }
          }
      }
    }
    
      for(javaFinalParser dataE : eng1 ){
        for( javaFinalParser dataG : greek){
                 int count = 0;
                 if ( dataE.getDocumentFrequencey() <= dataG.getDocumentFrequencey()){
                 for(String dataG1 : dataG.getDocSearch().toString().split("\\|")){
               if ((dataE.getDocSearch().toString().contains("|"+dataG1+"|") == true) ||
                       (dataE.getDocSearch().toString().startsWith(dataG1+"|") == true) ||
                            (dataE.getDocSearch().toString().endsWith("|"+dataG1) == true))
                 {
                            count++;
                    }
                 }
                 }
                  if ( count >= 5){
                    comparedClass dataC = new comparedClass();
                     dataC.setComID(Integer.parseInt(Integer.
                    toString(dataE.getwordId()) + 
                     Integer.toString(dataG.getwordId())));
                     dataC.addWordListL(dataG.getDocListStruct());
                     dataC.setGreek(dataG.getWordName());
                     dataC.setEnglish(dataE.getWordName());
                     dataC.setCountA(count);
                     dataC.setCountB(dataG.getDocumentFrequence() - count);
                     dataC.setCountC(dataE.getDocumentFrequence() - count);
                     dataC.setCountD(paragCTL1 - dataC.getCountA());
                     
                 double dataPMI1T = paragCTL1 * dataC.getCountA();
                 double dataPMI1B = (dataC.getCountA() + dataC.getCountB())*
                                    (dataC.getCountA() + dataC.getCountC());
                 dataC.setPMI(Math.log(dataPMI1T/dataPMI1B)/Math.log(2));
                  lanTable.add(dataC);                
          }
      }
    }
    processTimeData("comparisonW","ended");
    return lanTable;
}       
   
    public static List<comparedClass> comparisonL(List<javaFinalParser> eng, 
    List<javaFinalParser> greek, int[] queryDataS ){
    List<comparedClass> lanTable = new ArrayList<comparedClass>();
    processTimeData("comparisonL","started");
    List<javaFinalParser> eng1 = new ArrayList<javaFinalParser>();
    List<javaFinalParser> gre1 = new ArrayList<javaFinalParser>();
    StringBuilder greData1 = new StringBuilder();
    StringBuilder engData1 = new StringBuilder();
    
    for(javaFinalParser dataE : eng ){
    
      if( dataE.getDocumentFrequence() >= 70 
              && dataE.getDocumentFrequence() <= 80){
           if ( engData1.length() <= 0 ){
                  engData1.append(dataE.getDocSearch());
                  }else{
                  engData1.append("|").append(dataE.getDocSearch());    
            }
          eng1.add(dataE);
      }
    }  
    
       for(javaFinalParser dataE : eng1 ){
        for( javaFinalParser dataG : greek){

             int count = 0;
              if ( dataE.getDocumentFrequencey() <= dataG.getDocumentFrequencey()){
               for(String dataG1 : dataG.getDocSearch().toString().split("\\|")){
                if ((dataE.getDocSearch().toString().contains("|"+dataG1+"|") == true) ||
                       (dataE.getDocSearch().toString().startsWith(dataG1+"|") == true) ||
                            (dataE.getDocSearch().toString().endsWith("|"+dataG1) == true))
                     {
                       count++;
                    }
                 }
              }
                 if ( count >= 5){
                      comparedClass dataC = new comparedClass();
                     dataC.setComID(Integer.parseInt(Integer.
                             toString(dataE.getwordId()) + 
                             Integer.toString(dataG.getwordId())));
                     dataC.addWordListL(dataG.getDocListStruct());
                     dataC.setGreek(dataG.getWordName());
                     dataC.setEnglish(dataE.getWordName());
                     dataC.setCountA(count);
                     dataC.setCountB(dataG.getDocumentFrequence() - count);
                     dataC.setCountC(dataE.getDocumentFrequence() - count);
                     dataC.setCountD(paragCTL1 - dataC.getCountA());
                     
                 double dataPMI1T = paragCTL1 * dataC.getCountA();
                 double dataPMI1B = (dataC.getCountA() + dataC.getCountB())*
                                    (dataC.getCountA() + dataC.getCountC());
                 dataC.setPMI(Math.log(dataPMI1T/dataPMI1B)/Math.log(2));
                  lanTable.add(dataC);
          }
      }
    }
    processTimeData("comparisonL","ended");
    return lanTable;
}    

 /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - JavaParser object
   */
  public static List<comparedClass> deserializeDictionaryCom(int dataS){
      List<comparedClass>  word = new ArrayList<comparedClass>();
      processTimeData("deserializeDictionaryCom","started");
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
                List<comparedClass> tempObj;
                int count = searchSeriData(serilizationData,outFile);
                if ( count > 1 ){
                    for(int i = 0; i < count; i++ ){
                        tempObj = (List<comparedClass>) in.readObject();
                        word.addAll(tempObj);
                    }
                }else{
                  word = (List<comparedClass>) in.readObject();
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
     
      processTimeData("deserializeDictionaryCom","ended");
      return word;
  }

  /**
    * Function sorts the dictionary, then writes the results to a default file
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionaryCom(List<comparedClass>  word, 
           int dataS){
          Collections.sort(word, comparedClass.PMIDComparator);
          FileOutputStream fileOut;
          String type;
         processTimeData("serializeDictionaryCom","started");          
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
         processTimeData("serializeDictionaryCom","ended");
     }
    
/**
 * This procedure is the main portion of the program that will be used to 
 * initialize the application
 * @param args the command line arguments
 */
 public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 4 ){ 
           setFilesDetails(args[0]);
           splitNumber[0] = Integer.parseInt(args[3]);
           splitNumber[1] = 4;
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
