/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaApplication2 {
/**
 *
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
    
  static int paragCT = 0;
  static String debugType = "Debug1";
  //static final String directoryD = "C:\\Users\\k3123\\Documents\\";
  static final String directoryD = "D:\\John Hopkins School\\";
  
  static final String dictFileName = directoryD + "dictionary.txt";
  static final String dictFileNameQ0 = directoryD + "dictionaryQ0.txt";
  
  static final String dictFileName2 = directoryD + "dictionary2.txt";
  static final String dictFileNameQ1 = directoryD + "dictionaryQ1.txt";
  
  static final String dictFileNameS = directoryD + "dictionaryS.txt";
  static final String dictFileNameSQ0 = directoryD + "dictionarySQ0.txt";
  
  static final String dictFileNameS2 = directoryD + "dictionaryS2.txt";
  static final String dictFileNameSQ1 = directoryD + "dictionarySQ1.txt";
  
  static final String inverFileName = directoryD + "invert.bin"; 
  static final String inverQFileName = directoryD + "invertQ.bin"; 
  
  static final String inverFileNameS = directoryD + "invertS.bin"; 
  static final String inverQFileNameS = directoryD + "invertSQ.bin"; 
  
  static int[][] queryNumData; 
  
  static int[][] docNumData;
  
  static String twoLetters =  "AA|AB|AD|AE|AG|AH|AI|AL|AM|AN|AR|AS|AT|AW|AX" +
                               "|AY|BA|BE|BI|BO|BY|DA|DE|DO|ED|EF|EH|EL|EM" +
                               "|EN|ER|ES|ET|EX|FA|FE|GI|GO|HA|HE|HI|HM|IF|IN" +
                               "|IS|IT|JO|KA|KI|LA|LI|LO|MA|ME|MI|MM|MO|MU|MY" +
                               "|NA|NE|NO|NU|OD|OE|OF|OH|OI|OM|ON|OP|OR|OS|OW" +
                               "|OX|OY|PA|PE|PI|PO|QI|RE|SH|SI|SO|TA|TE|TI|TO" +
                               "|UH|UM|UN|UP|US|UT|WE|WO|XI|XU|YA|YE|YO|ZA";
  
  static String threeLetters =  "aah|aal|aas|aba|abo|abs|aby|ace|act|add|ado" +
                                "|ads|adz|aff|aft|aga|age|ago|ags|aha|ahi|ahs" +
                                "|aid|ail|aim|ain|air|ais|ait|ala|alb|ale|all" +
                                "|alp|als|alt|ama|ami|amp|amu|ana|and|ane|ani" +
                                "|ant|any|ape|apo|app|apt|arb|arc|are|arf|ark" +
                                "|arm|ars|art|ash|ask|asp|ass|ate|att|auk|ava" +
                                "|ave|avo|awa|awe|awl|awn|axe|aye|ays|azo|baa" +
                                "|bad|bag|bah|bal|bam|ban|bap|bar|bas|bat|bay" + 
                                "|bed|bee|beg|bel|ben|bes|bet|bey|bib|bid|big" +
                                "|bin|bio|bis|bit|biz|boa|bob|bod|bog|boo|bop" +
                                "|bos|bot|bow|box|boy|bra|bro|brr|bub|bud|bug" +
                                "|bum|bun|bur|bus|but|buy|bye|bys|cab|cad|cam" +
                                "|can|cap|car|cat|caw|cay|cee|cel|cep|chi|cig" +
                                "|cis|cob|cod|cog|col|con|coo|cop|cor|cos|cot" +
                                "|cow|cox|coy|coz|cru|cry|cub|cud|cue|cum|cup" +
                                "|cur|cut|cwm|dab|dad|dag|dah|dak|dal|dam|dan" +
                                "|dap|daw|day|deb|dee|def|dei|del|den|des|dev" +
                                "|dew|dex|dey|dib|did|die|dif|dig|dim|din|dip" +
                                "|dis|dit|dna|doc|doe|dog|dol|dom|don|dor|dos" +
                                "|dot|dow|dry|dub|dud|due|dug|duh|dui|dun|duo" +
                                "|dup|dux|dye|ear|eat|eau|ebb|ecu|edh|eds|eek" +
                                "|eel|eff|efs|eft|egg|ego|eke|eld|elf|elk|ell" + 
                                "|elm|els|eme|emf|ems|emu|end|eng|ens|eon|era" +
                                "|ere|erg|ern|err|ers|ess|eta|eth|eve|ewe|eye" +
                                "|fab|fad|fag|fan|far|fas|fat|fax|fay|fed|fee" + 
                                "|feh|fem|fen|fer|fes|fet|feu|few|fey|fez|fib" + 
                                "|fid|fie|fig|fil|fin|fir|fit|fix|fiz|flu|fly" + 
                                "|fob|foe|fog|foh|fon|fop|for|fou|fox|foy|fro" + 
                                "|fry|fub|fud|fug|fun|fur|gab|gad|gae|gag|gal" + 
                                "|gam|gan|gap|gar|gas|gat|gay|ged|gee|gel|gem" +
                                "|gen|get|gey|ghi|gib|gid|gie|gig|gin|gip|git" + 
                                "|gnu|goa|gob|god|goo|gor|gos|got|gox|goy|gul" + 
                                "|gum|gun|gut|guv|guy|gym|gyp|had|hae|hag|hah" + 
                                "|haj|ham|hao|hap|has|hat|haw|hay|heh|hem|hen" +
                                "|hep|her|hes|het|hew|hex|hey|hic|hid|hie|him" +
                                "|hin|hip|his|hit|hmm|hob|hod|hoe|hog|hon|hop" +
                                "|hos|hot|how|hoy|hub|hue|hug|huh|hum|hun|hup" +
                                "|hut|hyp|ice|ich|ick|icy|ids|iff|ifs|igg|ilk" +
                                "|ill|imp|ink|inn|ins|ion|ire|irk|ism|its|ivy" +
                                "|jab|jag|jam|jar|jaw|jay|jee|jet|jeu|jew|jib" +
                                "|jig|jin|job|joe|jog|jot|jow|joy|jug|jun|jus" + 
                                "|jut|kab|kae|kaf|kas|kat|kay|kea|ked|kef|keg" +
                                "|ken|kep|kev|kex|key|khi|kid|kif|kin|kip|kir" +
                                "|kis|kit|koa|kob|koi|kop|kor|kos|kue|kye|lab" +
                                "|lac|lad|lag|lam|lap|lar|las|lat|lav|law|lax" +
                                "|lay|lea|led|lee|leg|lei|lek|les|let|leu|lev" +
                                "|lex|ley|lez|lib|lid|lie|lin|lip|lis|lit|lob" +    
                                "|log|loo|lop|lot|low|lox|lug|lum|luv|lux|lye" +
                                "|mac|mad|mae|mag|man|map|mar|mas|mat|maw|max" +
                                "|may|med|meg|mel|mem|men|met|mew|mho|mib|mic" + 
                                "|mid|mig|mil|mim|mir|mis|mix|moa|mob|moc|mod" + 
                                "|mog|mol|mom|mon|moo|mop|mor|mos|mot|mow|mud" +
                                "|mug|mum|mun|mus|mut|myc|nab|nae|nag|nah|nam" +
                                "|nan|nap|naw|nay|neb|nee|neg|net|new|nib|nil" +
                                "|nim|nip|nit|nix|nob|nod|nog|noh|nom|noo|nor" + 
                                "|nos|not|now|nth|nub|nun|nus|nut|oaf|oak|oar" + 
                                "|oat|oba|obe|obi|oca|oda|odd|ode|ods|oes|off" + 
                                "|oft|ohm|oho|ohs|oil|oka|oke|old|ole|oms|one" + 
                                "|ono|ons|ooh|oot|ope|ops|opt|ora|orb|orc|ore" + 
                                "|ors|ort|ose|oud|our|out|ova|owe|owl|own|oxo" + 
                                "|oxy|pac|pad|pah|pal|pam|pan|pap|par|pas|pat" + 
                                "|paw|pax|pay|pea|pec|ped|pee|peg|peh|pen|pep" + 
                                "|per|pes|pet|pew|phi|pht|pia|pic|pie|pig|pin" + 
                                "|pip|pis|pit|piu|pix|ply|pod|poh|poi|pol|pom" + 
                                "|poo|pop|pot|pow|pox|pro|pry|psi|pst|pub|pud" + 
                                "|pug|pul|pun|pup|pur|pus|put|pya|pye|pyx|qat" + 
                                "|qis|qua|rad|rag|rah|rai|raj|ram|ran|rap|ras" + 
                                "|rat|raw|rax|ray|reb|rec|red|ree|ref|reg|rei" + 
                                "|rem|rep|res|ret|rev|rex|rho|ria|rib|rid|rif" + 
                                "|rig|rim|rin|rip|rob|roc|rod|roe|rom|rot|row" + 
                                "|rub|rue|rug|rum|run|rut|rya|rye|sab|sac|sad" + 
                                "|sae|sag|sal|sap|sat|sau|saw|sax|say|sea|sec" + 
                                "|see|seg|sei|sel|sen|ser|set|sew|sex|sha|she" + 
                                "|shh|shy|sib|sic|sim|sin|sip|sir|sis|sit|six" + 
                                "|ska|ski|sky|sly|sob|sod|sol|som|son|sop|sos" + 
                                "|sot|sou|sow|sox|soy|spa|spy|sri|sty|sub|sue" + 
                                "|suk|sum|sun|sup|suq|syn|tab|tad|tae|tag|taj" + 
                                "|tam|tan|tao|tap|tar|tas|tat|tau|tav|taw|tax" + 
                                "|tea|ted|tee|teg|tel|ten|tet|tew|the|tho|thy" + 
                                "|tic|tie|til|tin|tip|tis|tit|tod|toe|tog|tom" + 
                                "|ton|too|top|tor|tot|tow|toy|try|tsk|tub|tug" + 
                                "|tui|tun|tup|tut|tux|twa|two|tye|udo|ugh|uke" + 
                                "|ulu|umm|ump|uns|upo|ups|urb|urd|urn|urp|use" + 
                                "|uta|ute|uts|vac|van|var|vas|vat|vau|vav|vaw" + 
                                "|vee|veg|vet|vex|via|vid|vie|vig|vim|vin|vis" + 
                                "|voe|von|vow|vox|vug|vum|wab|wad|wae|wag|wan" + 
                                "|wap|war|was|wat|waw|wax|way|web|wed|wee|wen" + 
                                "|wet|wha|who|why|wig|win|wis|wit|wiz|woe|wog" + 
                                "|wok|won|woo|wop|wos|wot|wow|wry|wud|wye|wyn" + 
                                "|xis|yag|yah|yak|yam|yap|yar|yaw|yay|yea|yeh" + 
                                "|yen|yep|yes|yet|yew|yid|yin|yip|yob|yod|yok" + 
                                "|yom|yon|you|yow|yuk|yum|yup|zag|zap|zas|zax" + 
                                "|zed|zee|zek|zen|zep|zig|zin|zip|zit|zoa|zoo" + 
                                "|zuz|zzz";
  
  /**
   * The following function will determine if a word is valid. 
   * @param data - String value used to determine if valid word
   * @return - Boolean results that indicate if term is valid word
   */  
  public static boolean validateWord(String data) 
  {
      String numberTest = "", wordTest = "";
      numberTest = data;
      int numberSize = numberTest.length();
      wordTest = data;
      
     
      numberTest = numberTest.replaceAll("[0-9]", "");
     
      if (numberTest.length() < numberSize){
          return false;
      }
      if ( numberTest.isEmpty() ){    
         return false;
      }
      numberTest = numberTest.replace(",", "");
      if ( numberTest.isEmpty() ){    
          return false;
      }
      numberTest = numberTest.replace(".", "");
      if ( numberTest.isEmpty() ){    
          return false;
      }
      
      if ( wordTest.matches("M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})"
              + "(IX|IV|V?I{0,3})") ){
         return false;
      }
      
      if ( wordTest.replace(" ","").toUpperCase().
              matches("<P*|</P>|ID=*| *ID=*") ){
         return false;
      }
      if ( wordTest.replace(" ","").toUpperCase().
              matches("<Q*|</Q>|ID=*| *ID=*") ){
         return false;
      }
      if ( wordTest.toLowerCase().contains("id=")) {
          return false;
      }
      if (!wordTest.toLowerCase().matches("a|i|o") && 
              (wordTest.length() == 1)){
          return false;
      }
      
      if (!wordTest.toLowerCase().matches(twoLetters) && 
              (wordTest.length() == 2)){
          return false;
      }
  
     if (!wordTest.toLowerCase().matches(threeLetters) && 
              (wordTest.length() == 3)){
          return false;
      }
      
      
      return true;
  }
  /**
   * The following function will remove punctuation, and lower-case words. This
   * function is used to help normalize the words after splitting the text line 
   * using space as the delimiter
   * @param data - String value that requires removal of unwanted characters
   * @param stemF - Boolean status that indicates if Stemming has been 
   * flagged
   * @return String - final results from removing unwanted characters from a 
   * term
   */
  public static String cleanWord(String data, boolean stemF )
  {
      String cleaner = data;
      cleaner = cleaner.replaceAll("[\\[\\]\\{\\}\\/,_\"--.!?:;)(`]||[---]", "");
      cleaner = cleaner.replaceAll("[\\p{Punct}&&[^']&&[^-]]", "");
      if (cleaner.length() > 2 ){
      if (cleaner.charAt(cleaner.length()-1) == '-'){
        cleaner = cleaner.replace("-", "");  
      }
      if ((cleaner.charAt(cleaner.length()-1) == '\'') && 
              ((cleaner.toLowerCase().charAt(cleaner.length()-2) != 's'))){
        cleaner = cleaner.replace("\'", "");  
      }
      
      if (cleaner.charAt(0) == '-'){
        cleaner = cleaner.replace("-", "");  
      }
      }
      
      cleaner = cleaner.toLowerCase();
      
      if (stemF == true && cleaner.length() > 4){
        return cleaner.substring(0, 4);
        
      }
       
      return cleaner; 
  }
   /**
      * The following procedure will load the content of a file into a List of 
      * javaParser objects
      * @param args List of string parameters to access the file
      * @param stemF - Boolean status that indicates if Stemming has been 
      * flagged
      * @return The following function returns the a multi-dimensional array
      */  
  public static List<javaParser>  mainFileLoader( String[] args, boolean stemF ) 
     {
        String[] docData = new String[2];
        String readdata1;
        String dataValue;
        int j = 0;
        List<javaParser> javaWordList = new ArrayList<javaParser>();
      
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
                            
                             javaParser newWord = new javaParser();
                             newWord.setwordId(j++);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue);
                             newWord.addActualWord(data);
                             newWord.addDocMapStruct(docData[1]);
                             newWord.addDocListStruct(docData[1]);
                             newWord.addDocumentID(docData[1]);
                             javaWordList.add(newWord);
                            
                        }else{
 
                           javaParser existingWord = wordSearch(javaWordList,
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
     * The following procedure will load the content of a file into a List of 
     * javaParser objects
     * @param args - List of string parameters to access the file
     * @param start - Integer starting number that a worker from 
     * parserRunnable will engage 
     * @param end - Integer end point number that will cause the work to stop
     * processing
     * @param dataS - Integer type that indicates type of structure query or 
     * document and if Stemming has been flagged
     * @param stemF - Boolean type indicator that specifies stemming
     * @return The following function returns the a multi-dimensional array
     */  
  public static List<javaParser>  mainFileLoader2( String[] args, int start, 
          int end, int dataS, boolean stemF) 
     {
         
        String[] docData = new String[2];
        String readdata1;
        String[] dataValue = new String[2];
        Integer runCreate = 0;
        int j = 0, type;
        List<javaParser> javaWordList = new ArrayList<javaParser>();
        
        
        switch(dataS){
            case 0 : type = 1;
                             dataValue[0] = "<Q ID=";
                            break;
            case 1 : type = 1;
                            dataValue[0] = "<Q ID=";
                           break;
            case 2 : type = 0;
                            dataValue[0] = "<P ID=";
                           break;              
            case 3 : type = 0;
                            dataValue[0] = "<P ID=";
                           break;
            default : type = 0;
                      dataValue[0] = "<P ID=";
                      break;
        }
      
        try
        {   
            
            Scanner scan = new Scanner(new FileInputStream(args[type]));
            
            while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();  
              
              if ( readdata1.contains(dataValue[0])){
                    docData = readdata1.split("=");
                    docData[1] = docData[1].replace(">","");
                    j = Integer.parseInt(docData[1]);
                 
              }

            switch(dataValue[0]){
            case "<Q ID=" :     
                           if (j >= queryNumData[start][0] && 
                                    j <= queryNumData[end][0] ) { 
                              runCreate = 1;
                            }else if (j > queryNumData[end][0] ) {
                              runCreate = -1;  
                            }else if (j < queryNumData[start][0] ) {
                              runCreate = 0; 
                            }  
                           break;
             case "<P ID=" : 
                            if (j >= start && j <= end ) { 
                              runCreate = 1;
                            }else if (j > end ){
                              runCreate = -1;  
                            }else if (j < start ){
                               runCreate = 0;  
                            }
                            break;
             default :  runCreate = 0; 
                        break;
          }     
            
           if(runCreate == 1){
               for(String data : readdata1.split(" "))
                {   
                    
                    dataValue[1] = cleanWord(data,stemF);
                    if (validateWord(data)){      
                       if (! wordExists(javaWordList,dataValue[1]) ){
                            
                             javaParser newWord = new javaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue[1]);
                             newWord.addActualWord(data);
                             newWord.addDocMapStruct(docData[1]);
                             newWord.addDocListStruct(docData[1]);
                             newWord.addDocumentID(docData[1]);
                             javaWordList.add(newWord);
                            
                        }else{
 
                           javaParser existingWord = wordSearch(javaWordList,
                                   dataValue[1]);
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+1);
                           existingWord.addDocumentID(docData[1]); 
                           existingWord.updateDocMapStruct(docData[1]);
                           existingWord.updateDocListStruct(docData[1]);
                           existingWord.addActualWord(data);
                         
                        }
                        
                    }
                  }
                }else if (runCreate == -1){
                    break;
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
      * The following procedure will load the content of a file into a List of 
      * javaParser objects
      * @param args List of string parameters to access the file
      * @param dataS - Integer value that indicates if a import file contains 
      * queries or documents and also if Stem was flagged
      * @return The following function returns the a multi-dimensional array
      */  
  public static int mainFileLoaderCT( String[] args, int dataS) 
     {
        String readdata1;
        String[] docData, valueData = new String[2];
        int type = 0, j = 0;
        
        switch(dataS){
            case 0 : type = 1;
                             valueData[0] = "<Q ID=";
                             break;
            case 1 :  type = 1;
                             valueData[0] = "<Q ID=";
                             break;
            case 2 : type = 0;
                             valueData[0] = "<P ID="; 
                             break;              
            case 3 :  type = 0;
                             valueData[0] = "<P ID=";
                             break;
            default : type = 0;
                      valueData[0] = "<P ID="; 
                      break;
        }
      
        try
        {   
            Scanner scan = new Scanner(new FileInputStream(args[type]));
            while(scan.hasNextLine())
            { 
              readdata1 = scan.nextLine();  
               
              if ( readdata1.contains(valueData[0])){
                    j++;
              }                 
            }
            scan.close();
            switch(valueData[0]){
                case  "<P ID=" : paragCT = j;
                                 docNumData = new int[j+1][2];
                                 scan = new Scanner(new 
                                 FileInputStream(args[type]));
                                j=1;
                                while(scan.hasNextLine())
                                { 
                                  readdata1 = scan.nextLine();  

                                  if ( readdata1.contains(valueData[0])){
                                    docData = readdata1.split("=");
                                    docData[1] = docData[1].replace(">","");
                                    docNumData[j][0] = Integer.
                                            parseInt(docData[1]);
                                    docNumData[j][1] = j;
                                    j++;
                                  }                 
                                }
                                scan.close();
                                System.out.println("Parser found " 
                                        + j + " documents to "
                                 + "process.");       
                                 break;
                case  "<Q ID=" :
                                queryNumData = new int[j+1][2];
                                scan = new Scanner(new 
                                FileInputStream(args[type]));
                                j=1;
                                while(scan.hasNextLine())
                                { 
                                  readdata1 = scan.nextLine();  

                                  if ( readdata1.contains(valueData[0])){
                                    docData = readdata1.split("=");
                                    docData[1] = docData[1].replace(">","");
                                    queryNumData[j][0] = Integer.
                                            parseInt(docData[1]);
                                    queryNumData[j][1] = j;
                                    j++;
                                  }                 
                                }
                               scan.close();
                               System.out.println("Parser found " + --j + 
                                  " documents to process.");
                               break;  
            }

        }
        catch (IOException e)
        {
            System.out.println("Cannot Open File" + e.getMessage());
        }
        System.out.println();
         return j;
        }
 
  /**
   * The following function will display the complete details of the words
   * @param word - List of javaParser objects 
   */
  public static void printWordDetails(List<javaParser>  word )
  {
      
      System.out.println("The program should calculate both the total number "
              + " times each word is seen (collection frequency of the word) "
              + "and the number of documents which the word occurs in "
              + "(document frequency of the word).  \n");
      System.out.println("Report the number of ‘paragraphs’ processed; we'll"
              + " consider each paragraph to be a 'document', even though all "
              + "paragraphs are contained in a single file1.");
           
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(javaParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.println("\n");
  
  }
  /**
   * Function will display the complete dictionary using the javaFinalParser
   * structure. The collection frequency and document frequency are also 
   * displayed.
   * @param word - List of javaParser objects 
   */
   public static void printWordDetailsF(List<javaFinalParser>  word )
  {
      
           
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(javaFinalParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d %n",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.println("\n");
  
  }
  /**
   * Function will display the complete dictionary and the total number 
   * of documents
   * @param word - List of javaParser objects 
   */
   public static void printDocIDWordDetails(List<javaParser>  word )
  {
      
       System.out.printf("%-20s %-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence","DocID Details");
         for(javaParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d ",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                HashMap<Integer,Integer> hm = data.getDocMapStruct();
                for (Map.Entry m:hm.entrySet()){
                   System.out.print("("+m.getKey()+","+m.getValue() + "),");
                }
           System.out.println(data.docMapStruct.size());
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.println("\n");
         
  
  }
 
    /**
   * Function will display the complete dictionary and the total number 
   * of documents
   * @param word - List of javaParser objects 
   */
   public static void printDocID2FWordDetails(List<javaFinalParser>  word, 
           int dataS)
  {
      
        System.out.printf("%-20s %-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence","DocID Details");
         for(javaFinalParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d ",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  int[][] value = importInvertData(data.getPointerFile(),
                           data.getDocumentFrequence(),dataS);
                   for(int i = 0; i < data.getDocumentFrequence(); i++) {
                    System.out.print(value[i][0] + " " + value[i][1] + " ");
                  } 
                System.out.println("\n");
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.println("\n");
  }
 
   /**
   * Function will display the complete dictionary with the sorted document ids
   * @param word - List of javaParser objects 
   */
     public static void printDocID2WordDetails(List<javaParser>  word )
  {    
        Collections.sort(word, javaParser.DocIDComparator);
        System.out.printf("%-20s %-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence","DocID Details");
         for(javaParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d ",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
           Collections.sort(data.docListStruct,documentID1.DocIDComparator2);
           System.out.print(data.docListStruct + " ");
           System.out.println(data.docListStruct.size());
          }
         System.out.println("\n");
         System.out.printf("%-100s %-20d %n"," Total number of documents",
                 paragCT );
         System.out.println("\n");
         
  }
   /**
    * Function sorts the dictionary, then writes the results to a default file
    * @param word - List of javaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionary(List<javaParser>  word, 
           int dataS){
          Collections.sort(word, javaParser.DocIDComparator);
          FileOutputStream fileOut;
          String type;
                   
         switch(dataS){
            case 1 : type = dictFileNameQ0;
                           break;
            case 3 : type = dictFileName;
                           break;
            case 0 : type = dictFileNameSQ0;
                           break;
            case 2 : type = dictFileNameS;
                           break;               
            default : type = dictFileName;
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
         
     }
    /**
    * Function sorts the dictionary and writes the results to a file specified 
    * by the user
    * @param word - List of javaParser objects 
    * @param outFile 
    */  
   public static void serializeDictionaryRun(List<javaParser>  word, 
           String outFile){
          Collections.sort(word, javaParser.DocIDComparator);
          FileOutputStream fileOut;
       
          
          try {
             fileOut = new FileOutputStream(outFile);
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(word);
             out.close();
             fileOut.close();
           } catch (FileNotFoundException e) {

                  e.printStackTrace();
           } catch (IOException e) {

                  e.printStackTrace();
           }  
         
     }

    /**
    * Function sorts the dictionary and writes the results to a file specified 
    * by the user
    * @param word - List of javaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */  
   public static void serializeFDictionary(List<javaParser>  word, 
           int dataS){
          Collections.sort(word, javaParser.DocIDComparator);
          FileOutputStream fileOut;
          String type;
          
         switch(dataS){
            case 1 : type = dictFileNameQ0;
                           break;
            case 3 : type = dictFileName;
                           break;
            case 0 : type = dictFileNameSQ0;
                           break;
            case 2 : type = dictFileNameS;
                           break;               
            default : type = dictFileName;
                      break;
        }
      
          
          try {
             fileOut = new FileOutputStream(type);
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(word);
             out.close();
             fileOut.flush();
             fileOut.close();
           } catch (IOException e) {
                  e.printStackTrace();
           }  
         
     }
   
   
   /**
    * Function sorts the dictionary object javaFinalParser and writes the 
    * results to the default file
    * @param word - List of javaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */
     public static void serializeFinalDictionary(List<javaFinalParser>  word, 
             int dataS){
          Collections.sort(word, javaFinalParser.DocIDComparator);
          String outFile;
          
         switch(dataS){
            case 1 : outFile = dictFileNameQ1;
                           break;
            case 3 : outFile = dictFileName2;
                           break;
            case 0 : outFile = dictFileNameSQ1;
                           break;
            case 2 : outFile = dictFileNameS2;
                           break;                           
            default :   outFile = dictFileName2;
                      break;
        }
          
          try {
             FileOutputStream fileOut = new FileOutputStream(outFile); 
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             out.writeObject(word);

             System.out.println("Serialized data is saved in " + outFile);
             System.out.println("The final size of this lexicon " 
                     + outFile + " is : " + fileOut.getChannel().size());
             out.close();
             fileOut.close(); 
           } catch (FileNotFoundException e) {

                  e.printStackTrace();
           } catch (IOException e) {

                  e.printStackTrace();
           }  
         
     }
  /**
   * Function converts the javaParser object to javaFinalParser object and 
   * reduces the content of the dictionary to contain less datasets 
   * @param word - List of javaParser objects 
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - returns a javaFinalParser object
   */   
  public static List<javaFinalParser> conversionDictionary(List<javaParser>  
          word, int dataS) 
  {
      String outFile;
      Integer countDocID = 0, counter2 = 0;
      double idf = 0.0;
        
           
            switch(dataS){
            case 1 : outFile = inverQFileName;
                           break;
            case 3 : outFile = inverFileName;
                           break;
            case 0 : outFile = inverQFileNameS;
                           break;
            case 2 : outFile = inverFileNameS;
                           break;               
            default :   outFile = inverFileName;
                      break;
        }
      
      Collections.sort(word, javaParser.DocIDComparator);
      
      List<javaFinalParser>  word2 = new ArrayList<javaFinalParser>();
      try {
          
           DataOutputStream fileData = new DataOutputStream
        (new FileOutputStream(outFile));
           
       for(javaParser data : word)
          {
              javaFinalParser newWord = new javaFinalParser();
                  newWord.setwordId(data.getwordId());
                  newWord.setwordName(data.getwordName());  
                  newWord.setCollectionFrequency
                        (data.getCollectionFrequency());  
                  newWord.copyActualList(data.getActualList());
                  newWord.copyDocumentID(data.getDocumentList());
                  newWord.docListStruct.addAll(data.getDocListStruct());
                  newWord.setDocumentFrequencey(data.docListStruct.size());
                  idf = (double) Math.log(paragCT /  data.
                          docListStruct.size())/ Math.log(2);
                  newWord.setInvertDocFrequency(idf);
                  Collections.sort(data.docListStruct,
                      documentID1.DocIDComparator2);
                  counter2 = 0;
              for(documentID1 docData : data.docListStruct )
              {
                  fileData.writeInt(Integer.parseInt(docData.getDocID()));
                  fileData.writeInt(docData.getTermCount());
                  counter2 = counter2 + 2;
                  
              }
              fileData.flush();
              newWord.setPointerFile(countDocID);
              countDocID = countDocID + counter2;
              word2.add(newWord);
          }
          System.out.println("The Inverted file " + outFile + 
                  " size is : " + fileData.size());
          fileData.close();
         
      } catch (IOException e) {
          e.printStackTrace();
          
      }
       
       return word2;
  }
  /**
   * Function used to import data from the inverted index file
   * @return - Integer array of document id and term frequency in document. 
   */
  public static int[][] importFileData()
  {
      int intCounter = 0;
      
      try {
          
             DataInputStream is = new DataInputStream
                  ( new FileInputStream(inverFileName));
                   while ( is.available() > 0  ){
                      int docIDR = is.readInt();
                      int termIDR = is.readInt();
                      System.out.println(docIDR + " " + termIDR);
                      intCounter++;
                  } 
                  is.close();
                  
            } catch ( FileNotFoundException e ){
                    e.printStackTrace();
            } catch (IOException e){
                    e.printStackTrace();
            }      
      
            int[][] dictionCode = new int[intCounter][2];
            intCounter = 0;
            try {
          
             DataInputStream is = new DataInputStream
                  ( new FileInputStream(inverFileName));
                   while ( is.available() > 0  ){
                      dictionCode[intCounter][0] = is.readInt();
                      dictionCode[intCounter][1] = is.readInt();
                      System.out.println(dictionCode[intCounter][0] + " " +
                              dictionCode[intCounter][1]);
                      intCounter++;
                  } 
                  is.close();
                  
            } catch ( FileNotFoundException e ){
                    e.printStackTrace();
            } catch (IOException e){
                    e.printStackTrace();
            }   
     
      return dictionCode;
      
      
  }
  /**
   * Function used to import inverted data from inverted index file
   * @param position
   * @param length
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return 
   */
    public static int[][] importInvertData(int position, int length, 
            int dataS)
  {
      String outFile;
      int intCounter = 0;
      int value = 0;
      
             
            switch(dataS){
            case 1 : outFile = inverQFileName;
                           break;
            case 3 : outFile = inverFileName;
                           break;
            case 0 : outFile = inverQFileNameS;
                           break;
            case 2 : outFile = inverFileNameS;
                           break;               
            default :   outFile = inverFileName;
                      break;
        }
      
      int[][] dictionCode = new int[length][2];
      int currentP = 0;
            intCounter = 0;
            try {
               DataInputStream is = new DataInputStream
                  ( new FileInputStream(outFile));
                   while ( is.available() > 0  ){
                       if(intCounter == position){
                        dictionCode[currentP][0] = is.readInt();
                        dictionCode[currentP][1] = is.readInt();
                        currentP++;
                       }else{
                        int docIDR = is.readInt();
                        intCounter++;
                      }
                      if (currentP == length){
                          break;
                      }      
                  }  
                  is.close();
                  
            } catch ( FileNotFoundException e ){
                    e.printStackTrace();
            } catch (IOException e){
                    e.printStackTrace();
            }   
     
      return dictionCode;
      
      
  }
  /**
   * 
   * @return 
   */
  public static List<javaParser> deserializeDictionary(int dataS){
      List<javaParser>  word = new ArrayList<javaParser>();
      String outFile;
       switch(dataS){
            case 1 : outFile = dictFileNameQ0;
                           break;
            case 3 : outFile = dictFileName;
                           break;
            case 0 : outFile = dictFileNameSQ0;
                           break;
            case 2 : outFile = dictFileNameS;
                           break;                           
            default :   outFile = dictFileName;
                      break;
        }
      
       try {
                
                ObjectInputStream in = new ObjectInputStream(new 
                 FileInputStream(outFile));
                word = (List<javaParser>) in.readObject();
                System.out.println("Serialized data is read from " + 
                        outFile);
                in.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      
      return word;
  }
  /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return 
   */
    public static List<javaFinalParser> deserialize2Dictionary(int dataS){
      List<javaFinalParser>  word = new ArrayList<javaFinalParser>();
      String outFile;
         switch(dataS){
            case 1 : outFile = dictFileNameQ1;
                           break;
            case 3 : outFile = dictFileName2;
                           break;
            case 0 : outFile = dictFileNameSQ1;
                           break;
            case 2 : outFile = dictFileNameS2;
                           break;                           
            default :   outFile = dictFileName2;
                      break;
        }
      
      
       try {
                FileInputStream fileIn = new FileInputStream(outFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                word = (List<javaFinalParser>) in.readObject();
                System.out.println("Serialized data is read from " + 
                        outFile);
                fileIn.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      return word;
  }
 
    public static List<javaParser> deserializeFDictionary(String inFile){
      List<javaParser>  word = new ArrayList<javaParser>();
      
       try {
                FileInputStream fileIn = new FileInputStream(inFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                word = (List<javaParser>) in.readObject();
                System.out.println("Serialized data was read from " + 
                        inFile + " Status : Completed ");
                fileIn.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
               e.printStackTrace();
        } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      return word;
  }
  
  /**
   * 
   * @param word 
   */
  public static void printWordDetailsTagclouds(List<javaParser>  word )
  {
        System.out.printf("%-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(javaParser data : word)
          {
           System.out.printf("%s:%d %n",data.getwordName(),
                  data.getCollectionFrequency());
          }
         
         System.out.println("\n");
  
  }
  /**
   * The following function will display the complete details of the Top 30 
   * words from the text being analyzed 
   * @param word - List of javaParser objects 
   */
  public static void printWordDetailsTop30(List<javaParser>  word )
  {
       
        Collections.sort(word,new javaParser());  
        
        List<javaParser> top30 = word.subList(word.size()-30, word.size());
        System.out.println("Identify the 30 most frequent words (by total "
                + "count, also known as collection frequency) and report "
                + "both the collection frequency and the document frequency "
                + "for each.  \n");
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(javaParser data : top30)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());       
          }
         System.out.println("\n");
  }
  /**
   * The following function will print the 100th, 500th, and 1000th 
   * most-frequent words and their frequencies of occurrence. 
   * @param word - List of javaParser objects
   */
  public static void printWordDetailsPlace(List<javaParser>  word )
  {
        Collections.sort(word,new javaParser());  
        
        List<javaParser> top100 = word.subList(word.size()-100, word.size());
        List<javaParser> top500 = word.subList(word.size()-500, word.size());
        List<javaParser> top1000 = word.subList(word.size()-1000, word.size());
        
        System.out.println("Also print the 100th, 500th, and 1000th "
                + "most-frequent words and their frequencies of occurrence. "
                + "(But please do not just printout the top 1000.)  \n");
        
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(javaParser data : top100)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence()); 
             break;
          }
         for(javaParser data : top500)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence()); 
             break;
          }
         for(javaParser data : top1000)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence()); 
             break;
          }
          System.out.println("\n");
  }
  /**
   * The following function reports the number of unique words observed 
   * (vocabulary size), and the total number of words encountered (collection
   * size, in words)
   * @param word -  List of javaParser objects 
   */
  public static void printWordDetailsUnique(List<javaParser>  word )
  {
        int count = 0, count2 = 0;
        Collections.sort(word,new javaParser());  
        
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(javaParser data : word)
          {
            if(data.getCollectionFrequency() == 1){
                System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  count++;
            }
          }
        System.out.println("\n");
        System.out.printf("%-100s %-20d %n","Number of words that appear in"
                + " exactly once", count );
        System.out.println("\n");
        System.out.printf("%-100s %-20d %n","Number of words that in the "
                + " collection ", word.size() );
        System.out.println("\n");
  }
 
  /**
   * The following function reports the number of unique words observed 
   * (vocabulary size), and the total number of words encountered (collection
   * size, in words)
   * @param word -  List of javaParser objects 
   */
  public static void printWordDetailsUnique1(List<javaParser>  word )
  {
        int count = 0, count2 = 0;
        Collections.sort(word,new javaParser());  
        
        for(javaParser data : word)
          {
            if(data.getCollectionFrequency() == 1){
                  count++;
            }
          }
        System.out.println("\n");
        System.out.printf("%-100s %-20d %n","Number of words that appear in"
                + " exactly once", count );
        System.out.println("\n");
        System.out.printf("%-100s %-20d %n","Number of words that in the "
                + " collection ", word.size() );
        System.out.printf("%-100s %-20d %n","Number of documents that in the "
                + " collection ", paragCT );
        
        System.out.println("\n");
  }
  
  
 /**
  * Function that calculates and prints the number of words that occur in 
  * exactly one document and the percentage of the dictionary terms occur in 
  * just one document  
  * @param word -  List of javaParser objects
  */ 
 public static void printWordDetailsSingle(List<javaParser>  word )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new javaParser());  
       float m;  
       
       System.out.println(" Calculate and print the number of words that occur "
               + "in exactly one document. (For Les Mis, I believe reptile, "
               + "silkworm, and signatures are such words.) What percentage "
               + "of the dictionary terms occur in just one document? \n");
       
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(javaParser data : word)
          {
            if(data.getDocumentFrequence() == 1){
                System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  count = count + data.getCollectionFrequency();
                  count2++;
            }
          }
        System.out.println("\n");
        System.out.printf("%-100s %-20d %n","Number of words that appear in"
                + " exactly one document", count2 );
        System.out.println("\n");
        m = (float) count2/word.size() * 100;
        System.out.printf("%-100s %-20f%% %n","Percentage of the dictionary "
                + "terms occur in just one document",m  );
         System.out.println("\n");
  }
 /**
  * 
  * @param word
  * @param w 
  */
 public static void printWordSingle(List<javaParser>  word, String w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new javaParser());  
       float m;  
       
        for(javaParser data : word)
          {
            if(data.getwordName().toLowerCase().
                    equalsIgnoreCase(w.toLowerCase())){
           System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");

                System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  count = count + data.getCollectionFrequency();
                  count2++;
                  System.out.println("\n");
            }
          }
         if (count2 == 0 ){
             System.out.println(w + " was not found in the dictionary ");
         }
  }
 /**
  * 
  * @param word
  * @param w 
  */
 public static void printWordDetailsWord(List<javaFinalParser>  word, String w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new javaFinalParser());  
       float m;  
       
       
        for(javaFinalParser data : word)
          {
            if(data.getWordName().equalsIgnoreCase(w.toLowerCase())){
            System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
                System.out.printf("%-20s %-20d %-20d %n",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  count = count + data.getCollectionFrequency();
                  count2++;
            }
          }
         if (count2 == 0 ){
             System.out.println(w + " was not found in the dictionary ");
         }
  }
 /**
  * 
  * @param word
  * @param w 
  */
  public static void printWordDetailsWordPL(List<javaFinalParser>  word, String w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new javaFinalParser());  
       
       
          for(javaFinalParser data : word)
          {
            if(data.getWordName().toLowerCase().
                    equalsIgnoreCase(w.toLowerCase())){
               System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
               System.out.printf("%-20s %-20d %-20d ",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                  count = count + data.getCollectionFrequency();
                  count2++;
                   int[][] value = importInvertData(data.getPointerFile(),
                           data.getDocumentFrequence(),3);
                   for(int i = 0; i < data.getDocumentFrequence(); i++) {
                    System.out.print(value[i][0] + " " + value[i][1] + " ");
                  } 
               System.out.println("\n");    
            } 
                            
          }
      
         if (count2 == 0 ){
             System.out.println(w + " was not found in the dictionary ");
         }
  }
      
        
 /**
  * The following function will determine if a word exists in the dictionary 
  * and returns a boolean result
  * @param word -  List of javaParser objects
  * @param selection
  * @return boolean
  */
  public static boolean wordExists(  List<javaParser>  word, 
            String selection) 
    {
        
        for(javaParser data : word)
          {
            if( data.getwordName().toLowerCase().equalsIgnoreCase(selection.
                    toLowerCase()))
            {
               return true; 
         
            }
          }
        return false;
    }
 /**
  * The following function will determine if a word exists in the dictionary 
  * and returns a boolean result
  * @param word -  List of javaParser objects
  * @param selection
  * @param dataS - Integer value that indicates if a import file contains 
  * queries or documents and also if Stem was flagged
  * @return boolean
  */
  public static boolean wordExistsDocID(  List<docQueryStruct>  word, 
            int selection, int dataS) 
    {
         int convertedN;
          
          switch(dataS){
          case 3  :  convertedN = selection;
                             break;               
          case 1 :   convertedN = queryNumData[selection][0];
                             break;
          case 2  :  convertedN = selection;
                             break;               
          case 0 :   convertedN = queryNumData[selection][0];
                             break;                             
          default  :     convertedN = selection;              
      }
      
        for(docQueryStruct data : word)
          {
            if( data.getDocIDInt() == convertedN)
            {
               return true; 
         
            }
          }
        return false;
    }  
  
  /**
   * The following function will search the dictionary based on the provided 
   * word name and return the corresponding javaParser object
   * @param word - List of javaParser objects
   * @param selection - word that to search the list
   * @return javaParser
   */
  public static javaParser wordSearch( List<javaParser>  word, 
            String selection )
  {
        javaParser validateCheck = new javaParser();
        
        for(javaParser data : word)
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
   * The following function will search the dictionary based on the provided 
   * word name and return the corresponding javaParser object
   * @param word - List of javaParser objects
   * @param selection - word that to search the list
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return javaParser
   */
  public static docQueryStruct docSearch( List<docQueryStruct>  word, 
            int selection, int dataS )
  {       
          int convertedN;
          
          switch(dataS){
          case 3  :  convertedN = selection;
                             break;               
          case 1 :   convertedN = queryNumData[selection][0];
                             break;
          case 2  :  convertedN = selection;
                             break;               
          case 0 :   convertedN = queryNumData[selection][0];
                             break;                             
          default  :     convertedN = selection;              
      }
      
        docQueryStruct validateCheck = new docQueryStruct();
        
        for(docQueryStruct data : word)
          {
            if( data.getDocIDInt() == convertedN)
            {
               validateCheck = data; 
               break;
             }          
          }   
        return validateCheck;
  }
  
  
  /**
   * 
   * @param word1
   * @param word2
   * @param x 
   */
  public static void merger( List<javaParser>  word1, 
          List<javaParser>  word2, int x)
  {
      if( x == 1 ){
          word1.addAll(word2);
      }else{
          for(javaParser data1 : word2 ){
             if( wordExists(word1,data1.getwordName())){
                  javaParser existingWord = wordSearch(word1,
                                   data1.getwordName());
                           existingWord.setCollectionFrequency
                          (existingWord.getCollectionFrequency()+
                              data1.getCollectionFrequency());
                           existingWord.documentID.
                                   addAll(data1.getDocumentList()); 
                           existingWord.docMapStruct.
                                   putAll(data1.getDocMapStruct());
                           existingWord.docListStruct.
                                   addAll(data1.getDocListStruct());
                           existingWord.actualWord.
                                   addAll(data1.getActualList());
             }else{
                 word1.add(data1);
             };
          }
              
      }
  }
  /**
   * 
   * @param word
   * @param query
   * @param qlength 
   */
  public static void simProduct(List<docQueryStruct> word, 
          List<docQueryStruct> query,
          int qlength ){
      
      docQueryStruct qu = docSearch(query,qlength,1);
      for( docQueryStruct wData : word ){
          wData.setSimValue(wData.getDotProduct()
                  /(wData.getDocLength() * qu.getDocLength())); 
      }
      
  }
  /**
   * 
   * @param query 
   */
  public static void displayALLBags(List<docQueryStruct> query){
      
      for(int i = 1; i <= query.size(); i++ ){
          displayBag(query,i,"Query");
      }
      
  }
  /**
   * 
   * @param qNum
   * @param word
   * @param outFile 
   */
  public static void writeFDocIDDOTPRODUT(int qNum, List<docQueryStruct> word, 
           String outFile){
           Collections.sort(word, docQueryStruct.dotComparator);
           String outData;
           int j = 1;
      try {    
            
         FileWriter fw = new FileWriter(new File(outFile));   
         for(docQueryStruct wData : word ){
           outData = String.format("%d %2s %d %d %f %s %n",queryNumData[qNum][0]
                   ,"Q0",wData.getDocIDInt(),j,wData.getDotProduct(),
                   "Clarence Leslie");
            fw.write(outData);
            j++;
            if (j > 50 ){
                break;
            }
         }
            fw.close();

        } catch (IOException e) {
            //do stuff with exception
            e.printStackTrace();
        }
       
   } 
   /**
    * 
    * @param qNum
    * @param word
    * @param outFile 
    */
    public static void writeFDocIDSimPRODUT(int qNum, List<docQueryStruct> word, 
           String outFile){
           Collections.sort(word, docQueryStruct.dotComparator);
           String outData;
           int j = 1;
      try {    
            
         FileWriter fw = new FileWriter(new File(outFile));   
         for(docQueryStruct wData : word ){
           outData = String.format("%d %2s %d %d %6f %s %n",
                   queryNumData[qNum][0],"Q0",wData.getDocIDInt(),j,
                   wData.getSimValue(),"Clarence Leslie");
            fw.write(outData);
            j++;
            if (j > 50 ){
                break;
            }
         }
            fw.close();

        } catch (IOException e) {
            //do stuff with exception
            e.printStackTrace();
        }
       
  } 
  /**
   * 
   * @param query
   * @param word
   * @param qNumber 
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged 
   */  
  public static void dotProduct(List<docQueryStruct> query, 
          List<docQueryStruct> word, int qNumber, int dataS){

      docQueryStruct dData = docSearch(query,qNumber,dataS);
      
      if ( dData.getDocMapStruct() != null ){
                HashMap<String,Double> hm = dData.getDocMapStructTFIDF();
                for (Map.Entry m:hm.entrySet()){
                   for(docQueryStruct wData : word ){
                       if ( wData.getDocMapStructTFIDF() != null ){
                        HashMap<String,Double> hmW = wData.getDocMapStructTFIDF();
                         for (Map.Entry mW:hmW.entrySet()){
                             if (m.getKey().toString().
                                  equalsIgnoreCase(mW.getKey().toString())){
                                 wData.setDotProduct(wData.getDotProduct() + 
                                  (Double.parseDouble(m.getValue().toString()) *
                                   Double.parseDouble(mW.getValue().toString())));
                         }
                   }
                 }
                }
             }
          }
      
  }
 /**
  * 
  * @param query
  * @param x
  * @param type 
  */
  public static void displayBag(List<docQueryStruct> query , int x,
          String type){
      int convertedN;
      switch(cleanWord(type,false)){
          case "querytfidf"  :                 
          case  "query" :   convertedN = queryNumData[x][0];
                             break;
          default  :     convertedN = x;              
      }
      
      for(docQueryStruct qdata : query){
          if( qdata.getDocIDInt() == convertedN ){
          System.out.printf(" %8s %s %n", qdata.getDocIDInt(), type);
          if ( qdata.getDocMapStruct() != null ){
               qdata.getDocMapStruct().
                  forEach((k,v)->System.out.
                      printf("%-20s %d %f %f %f %d %n", k, v,
                              qdata.getSumSquares(),
                              qdata.getDotProduct(),
                              qdata.getDocLength(),
                              qdata.getTermCount()));
          }
            System.out.println();
          }
         }
          System.out.println();
                  
      
  }
  /**
   * 
   * @param query
   * @param x
   * @param type 
   */
  public static void displayBagTFIDF(List<docQueryStruct> query , int x,
          String type){
      int convertedN;
      switch(cleanWord(type,false)){
          case "querytfidf"  :                 
          case  "query" :   convertedN = queryNumData[x][0];
                             break;
          default  :     convertedN = x;              
      }
      
      for(docQueryStruct qdata : query){
          if( qdata.getDocIDInt() == convertedN ){
          System.out.printf(" %8s %s %n", qdata.getDocIDInt(), type);
          if ( qdata.getDocMapStructTFIDF() != null ){
               qdata.getDocMapStructTFIDF().
                  forEach((k,v)->System.out.
                      printf("%-20s %f %f %f %f %d %n", k, v,
                              qdata.getSumSquares(),
                              qdata.getDotProduct(),
                              qdata.getDocLength(),
                              qdata.getTermCount()));
          }
            System.out.println();
          }
         }
          System.out.println();
                  
      
  }
  
  /**
   * 
   * @param word 
   */
  public static void displayDocID(List<docQueryStruct> word){
      
     Collections.sort(word, new docQueryStruct());  
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getDotProduct());
     } 
      
  }
  /**
   * 
   * @param word 
   */
  public static void displayDocIDDOTPRODUT2(List<docQueryStruct> word){
      
     Collections.sort(word, docQueryStruct.dotComparator); 
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getDotProduct());
     } 
      
  }
  /**
   * 
   * @param word
   * @param qNum 
   */
  public static void displayDocIDDOTPRODUT(List<docQueryStruct> word,
           int qNum){
      
     Collections.sort(word, docQueryStruct.dotComparator); 
     int j=1;
     for(docQueryStruct wData : word ){
         System.out.printf("%d %2s %d %d %f %s %n",queryNumData[qNum][0],"Q0",
                   wData.getDocIDInt(),j,
               wData.getDotProduct(),"Clarence Leslie");
                     j++;
            if (j > 50 ){
                break;
            }
     } 
      
  }
  /**
   * 
   * @param word 
   */   
  public static void displayDocIDSIMPRODUT2(List<docQueryStruct> word){
      
     Collections.sort(word, docQueryStruct.simComparator); 
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getSimValue());
     } 
      
  } 
  /**
   * 
   * @param word
   * @param qNum 
   */
  public static void displayDocIDSIMPRODUT(List<docQueryStruct> word, 
          int qNum){
      
     Collections.sort(word, docQueryStruct.simComparator); 
     int j=1;
     for(docQueryStruct wData : word ){
         
         System.out.printf("%d %2s %d %d %6f %s %n",queryNumData[qNum][0],"Q0",
                   wData.getDocIDInt(),j,
               wData.getSimValue(),"Clarence Leslie");
                     j++;
            if (j > 50 ){
                break;
            }
     } 
      
  } 
  /**
   * 
   * @param word
   * @param query
   * @param qQuery
   * @param dataS
   * @return 
   */ 
  public static List<docQueryStruct> buildBagOfDocs(List<javaFinalParser> word,
      List<docQueryStruct> query, int qQuery, int dataS ){
      List<docQueryStruct> docData = new ArrayList();
      
      docQueryStruct  wordQ = docSearch(query,qQuery,0);
      
      for(javaFinalParser wordD : word){
              if ( wordQ.getDocMapStruct() != null &&
                      wordQ.getDocIDInt() == queryNumData[qQuery][0]){
                  if (debugType.equalsIgnoreCase("Debug5")){
                    System.out.printf("Yes we found the query %d %s %n",
                          wordQ.getDocIDInt(),wordD.getWordName());
                  }
               if (wordQ.getDocMapStruct().containsKey(wordD.getWordName())){
                   if (debugType.equalsIgnoreCase("Debug5A")){
                   System.out.printf("Yes word are being found %s for query %d "
                           + " in %d documents %n",wordD.getWordName(),
                           wordQ.getDocIDInt(),wordD.getDocumentFrequence());
                   }
                   int[][] value = importInvertData(wordD.getPointerFile(),
                           wordD.getDocumentFrequence(),dataS );
                   for(int i = 0; i < wordD.getDocumentFrequence(); i++) {
                          if ( ! wordExistsDocID(docData,value[i][0],dataS)){
                    if (debugType.equalsIgnoreCase("Debug5B")){          
                    System.out.printf("Yes word are being found %s for "
                            + "query %d "
                           + " in documents %s %n ",wordD.getWordName(),
                           wordQ.getDocIDInt(),value[i][0]); 
                    }
                             docQueryStruct newWordD = new docQueryStruct();
                             newWordD.setDocIDInt(value[i][0]);
                             newWordD.setDocID(Integer.
                                     toString(value[i][0]));
                             newWordD.addDocMapStruct(wordD.getWordName(),
                                     value[i][1]);
                             newWordD.addDocMapStructTFIDF(wordD.getWordName(),
                                 (double) value[i][1] * 
                                  wordD.getInvertDocFrequency());
                             newWordD.setTermCount(1);
                             docData.add(newWordD);
                            
                          } else {
                             docQueryStruct newWordUD = 
                                     docSearch(docData,value[i][0],dataS);
                             newWordUD.addDocMapStruct(wordD.getWordName(),
                                     value[i][1]);
                             newWordUD.addDocMapStructTFIDF(wordD.getWordName(),
                                 (double) value[i][1] * 
                                   wordD.getInvertDocFrequency());
                             newWordUD.setTermCount(newWordUD.getTermCount()
                                     + 1);
                            
                          }
                          
                  }                    
                 
               }                 
          }
              
          }
      
      
     double sumSQRS, prodW;
      
      for(docQueryStruct wordF : docData){
          sumSQRS = 0;
             if ( wordF.getDocMapStructTFIDF() != null ){
               HashMap<String,Double> hm = wordF.getDocMapStructTFIDF();
                for (Map.Entry m:hm.entrySet()){
                   prodW = Double.parseDouble(m.getValue().toString());
                   sumSQRS = sumSQRS  +  (prodW * prodW);
                }
          }
             wordF.setWordCount(wordF.getDocMapStructTFIDF().size());
             wordF.setSumSquares(sumSQRS);
             wordF.setDocLength(Math.sqrt(sumSQRS)); 
      }
      
      Collections.sort(docData, docQueryStruct.docIDComparator);
      
      return docData;
  }
  /**
   * 
   * @param query
   * @param size
   * @return 
   */
  public static List<docQueryStruct> buildBags( List<javaFinalParser>  query, 
          int size){
  
      List<docQueryStruct> queryData = new ArrayList();
      double sumSqu;
            for(int i = 1; i <= size; i++ ){
              docQueryStruct newQuery = new docQueryStruct();
                              newQuery.setDocIDInt(queryNumData[i][0]);
                              newQuery.setDocID(Integer.
                                      toString(queryNumData[i][0]));
                              sumSqu = 0.0;
                              for(javaFinalParser wordD : query){
                                  for(documentID1 docCT : wordD.
                                          getDocListStruct()){
                                     if(docCT.getDocID().equals(newQuery.
                                             getDocID())){
                                         newQuery.addDocMapStruct
                                                (wordD.getWordName(), 
                                                  docCT.getTermCount());
                                                  sumSqu = sumSqu + 
                                                       ((docCT.getTermCount() *
                                                       wordD.getInvertDocFrequency()) *
                                                       ( docCT.getTermCount() * 
                                                       wordD.getInvertDocFrequency()));
                                                  newQuery.addDocMapStructTFIDF
                                                       ( wordD.getWordName(), 
                                                     (double) docCT.getTermCount()*
                                               wordD.getInvertDocFrequency());
                                                   
                                     } 
                                  }
                              }
                              if(newQuery.docMapStruct != null){
                               newQuery.setTermCount
                                (newQuery.getDocMapStruct().size());
                              }else{
                                 newQuery.setTermCount(0); 
                              }
                              newQuery.setSumSquares(sumSqu);
                              newQuery.setDocLength(Math.sqrt(sumSqu));
                              queryData.add(newQuery);
                }
      
      return queryData;
  
  }
  /**
   * 
   * @param query
   * @param size
   * @return 
   */
  public static List<docQueryStruct> buildBag( List<javaFinalParser>  query, 
          int size)
  {
      List<docQueryStruct> queryData = new ArrayList();
      double sumSqu = 0.0;
               
              docQueryStruct newQuery = new docQueryStruct();
                              newQuery.setDocIDInt(queryNumData[size][0]);
                              newQuery.setDocID(Integer.
                                      toString(queryNumData[size][0]));
                              for(javaFinalParser wordD : query){
                                  
                                  for(documentID1 docCT : wordD.
                                          getDocListStruct()){
                                     if(docCT.getDocID().compareTo(newQuery.
                                             getDocID()) == 0 ){
                                         newQuery.addDocMapStruct
                                                (wordD.getWordName(), 
                                                   docCT.getTermCount());
                                               sumSqu = sumSqu + 
                                            (docCT.getTermCount() * 
                                                wordD.getInvertDocFrequency() * 
                                                       docCT.getTermCount() * 
                                                 wordD.getInvertDocFrequency());
                                          newQuery.addDocMapStructTFIDF
                                                        (wordD.getWordName(), 
                                                         docCT.getTermCount()*
                                               wordD.getInvertDocFrequency());
                                                   
                                     } 
                                  }
                              }
                              if(newQuery.docMapStruct != null){
                               newQuery.setTermCount
                                (newQuery.getDocMapStruct().size());
                              }else{
                                 newQuery.setTermCount(0); 
                              }
                              newQuery.setSumSquares(sumSqu);
                              newQuery.setDocLength(Math.sqrt(sumSqu));
                              queryData.add(newQuery);
  
      
      return queryData;
  }
  /**
   * The following function will search the dictionary based on the provided 
   * word id and return the corresponding javaParser object
   * @param word - List of javaParser objects
   * @param selection
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return 
   */
  public static javaParser wordIDSearch( List<javaParser>  word, 
            int selection, int dataS )
  {

        int convertedN;
          
          switch(dataS){
          case 2 :    
          case 3  :  convertedN = selection;
                             break;               
          case 0 :                  
          case 1  :   convertedN = queryNumData[selection][0];
                             break;
          default  :     convertedN = selection;              
      }
      
        javaParser validateCheck = new javaParser();
        
        for(javaParser data : word)
          {
            if( data.getwordId() == (convertedN))
            {
               validateCheck = data; 
               break;
             }
             
          }
        
        return validateCheck;
    }
    /**
   * 
   * @param args
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @param type
   * @param stemF
   * @return 
   */
    public static List<javaParser>  preProcessing(String[] args, int dataS, 
            String type, boolean stemF){
      
      List<javaParser>  currentList = new ArrayList<javaParser>(); 
      List<javaParser>  newList; 
          
      int totalCT = mainFileLoaderCT(args,dataS); 
      
      int currentCT;
      if (totalCT / 63 >= 1000){
            currentCT = totalCT / 63;
        }else{
            currentCT =  totalCT; 
      }
      int start = 1,  end = currentCT;
      String[] fileName = new String[totalCT/currentCT];
      boolean fileStatus = true; 
      File f;
      for(int i = 0; i < totalCT/currentCT; i++){
           fileName[i] = directoryD + type + i + ".txt";
           f = new File(fileName[i]);
           if ( ! f.exists() ) { 
             fileStatus = false;
             System.out.println("The following file was not found "
                     + fileName[i] + " " + fileStatus);
          }
      }
      
          
      if ( fileStatus == false ){
            ExecutorService executor = Executors.
                      newFixedThreadPool((totalCT/currentCT) + 1);

            for(int i = 0; i < totalCT/currentCT; i++){
                  fileName[i] = directoryD + type + i + ".txt";
                  Runnable worker = new parserRunnable(args,start,end,
                  fileName[i],dataS,stemF);
                  start = end + 1;
                  end = end + currentCT;
                  executor.execute(worker);   
              }
              executor.shutdown();

              while (!executor.isTerminated()) {

              }
        }
        for(int i = 0; i < totalCT/currentCT; i++){
            newList = deserializeFDictionary(fileName[i]);
            merger(currentList,newList,i);
        }
        
        if (debugType.equalsIgnoreCase("Debug1")){
           printWordDetails(currentList);
        }
        return currentList;
        
    }
    /**
     * 
     * @param args
     * @param stemF 
     * @param queryDataS 
     */
    public static void processData(String[] args, boolean stemF, int[] 
            queryDataS){
        List<javaParser>  currentListM = new ArrayList<javaParser>();
        List<javaParser>  currentQueryM = new ArrayList<javaParser>();
        String[] nameFType = new String[2];
          
          if(stemF == false){
              nameFType[0] = "dictionaryQ";
              nameFType[1] = "dictionaryF";
          }else{
              nameFType[0] = "dictionaryQS";
              nameFType[1] = "dictionaryFS";
          }
          
          currentQueryM = preProcessing(args,queryDataS[0],nameFType[0],stemF);
          serializeFDictionary(currentQueryM,queryDataS[0]);
          currentQueryM.clear();
          
          currentListM = preProcessing(args,queryDataS[1], nameFType[1],stemF);
          serializeFDictionary(currentListM,queryDataS[1]);
          currentListM.clear();
          
          
    }
    /**
     * 
     * @param args
     * @param stemF 
     * @return  
     */
    public static int[] postProcessData(String[] args, boolean stemF){
     
        List<docQueryStruct> newQueryData = new ArrayList<docQueryStruct>();
        List<docQueryStruct> newDocData = new ArrayList<docQueryStruct>();
        int[] queryDataS = new int[2];
        
        
        if ( stemF == false){
            queryDataS[0] =  1;
            queryDataS[1] =  3;
        }else{
            queryDataS[0] =  0;
            queryDataS[1] =  2; 
        }
        
        processData(args,stemF,queryDataS);

        int totalCT = mainFileLoaderCT(args,queryDataS[0]);      

         List<javaParser> testcurrentList = 
                 deserializeDictionary(queryDataS[1]);
         List<javaParser> testcurrentQuery = 
                 deserializeDictionary(queryDataS[0]);
         
         if (debugType.equalsIgnoreCase("Debug1")){
             printDocIDWordDetails(testcurrentList);
            printDocIDWordDetails(testcurrentQuery);
         }
         List<javaFinalParser> updateList = 
                 conversionDictionary(testcurrentList,queryDataS[1]);
         
         List<javaFinalParser> updateQuery1 = 
                 conversionDictionary(testcurrentQuery,queryDataS[0]);
         
         if (debugType.equalsIgnoreCase("Debug2")){
            printDocID2FWordDetails(updateQuery1,queryDataS[0]);
            printDocID2FWordDetails(updateList,queryDataS[1]);
         }
         
         newQueryData = buildBags(updateQuery1,totalCT);
         newDocData = buildBagOfDocs(updateList,newQueryData,2,
                 queryDataS[1]);
         
         for(int z = 1; z <= newQueryData.size(); z++){
            displayBag(newQueryData,z,"Query");
         }
         
         for(int z = 1; z <= newDocData.size(); z++){
            displayBag(newDocData,z,"Documents");
         }
         
         for(int z = 1; z <= newQueryData.size(); z++){
            displayBagTFIDF(newQueryData,z,"Query TFIDF");
         }
         
         for(int z = 1; z <= newDocData.size(); z++){
            displayBagTFIDF(newDocData,z,"Documents TFIDF");
         }
        
         dotProduct(newQueryData,newDocData,2,
                 queryDataS[0]);
         dotProduct(newQueryData,newQueryData,totalCT,
                 queryDataS[0]);
         
         simProduct(newDocData,newQueryData,2);
         
         System.out.println();
         
         displayDocIDDOTPRODUT(newDocData,2);
         writeFDocIDDOTPRODUT(2,newDocData,directoryD + "Leslie-aDot.txt");
         
         System.out.println();
         
         displayDocIDSIMPRODUT(newDocData,2);
         writeFDocIDSimPRODUT(2,newDocData,directoryD + "Leslie-aSim.txt");
         
         System.out.println();
         
         serializeFinalDictionary(updateList,queryDataS[1]);
         serializeFinalDictionary(updateQuery1,queryDataS[0]);
         
         return queryDataS;
    }
    /**
     * 
     * @param args
     * @param type 
     */
    public static void mainInt(String[] args, boolean type)
    {
           int[] queryDataS; 
        
        queryDataS = postProcessData(args,type);
        
         List<javaFinalParser> updateList2 = 
                 deserialize2Dictionary(queryDataS[1]);
         List<javaFinalParser> updateQuery2 = 
                 deserialize2Dictionary(queryDataS[0]);
         printWordDetailsF(updateList2);
         printWordDetailsF(updateQuery2);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
     
        if (args.length == 2 ){ 
           mainInt(args,true);
        }else{ 
           System.out.println("Please enter a input file that contains "
                   + " the corpus and input file that contains the queries");
        }
                
    }
    
}
