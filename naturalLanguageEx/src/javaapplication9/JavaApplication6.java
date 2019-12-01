/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class JavaApplication6 {
/**
 * The following is the main class of the JavaApplication2 program. This class
 * will contain the functions and procedures to parse the corpus and query file
 * enter at the command line. This program requires 4 parameters, to ensure 
 * execution, please provide the location and file name of the corpus file and 
 * the file containing the queries. Please also ensure to add the directory 
 * that will contain the output files. The last parameter is a split number. 
 * This program utilizes concurrent processing, so the split number will be use
 * to determine the number or processes to execute to parse the corpus file. 
 * The split number is also used to assist in the export of the dictionary 
 * content. If a dictionary is greater than 63 * 800 or 5040 in total size or in 
 * number of objects, then split number will be used to break up the large file 
 * or object into smaller portions to be processed. The validation function is 
 * used to parse the corpus and determine a valid word term. If the word is not 
 * valid it will not be included in the dictionary. Stemming has also be 
 * introduce by setting a boolean value for the cleaner function. If stemming is 
 * required, then setting stemF to true will enable cleaner program to return 
 * the first 5 characters in a string
 * @author Clarence L. Leslie
 * Programming Assignment #1
 * 605.744 Information Retrieval 
 */
    
  static int paragCT = 0;
  static int[] splitNumber = new int[2];
  static String debugType = "TEST";

  
  static final String[] dictFileList = new String[25];
  
  private static final DateTimeFormatter timeStampF = 
          DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
  static int[][] queryNumData; 
  
  static int[][] docNumData;
  
  static List<documentID1> serilizationData = new ArrayList<documentID1>();
  
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
  
  
   static String stopWords = "a|about|above|above|across|after|afterwards|" +
                            "again|against|all|almost|alone|along|already|" + 
                            "also|although|always|am|among|amongst|amoungst|" + 
                            "amount|an|and|another|any|anyhow|anyone|anything|" +
                            "anyway|anywhere|are|around|as|at|back|be|became|" + 
                            "because|become|becomes|becoming|been|before|" + 
                            "beforehand|behind|being|below|beside|besides|" + 
                            "between|beyond|bill|both|bottom|but|by|call|" + 
                            "can|cannot|cant|co|con|could|couldnt|cry|de|" + 
                            "describe|detail|do|done|down|due|during|each|" + 
                            "eg|eight|either|eleven|else|elsewhere|empty|" + 
                            "enough|etc|even|ever|every|everyone|everything|" + 
                            "everywhere|except|few|fifteen|fify|fill|find|" + 
                            "fire|first|five|for|former|formerly|forty|found|" + 
                            "four|from|front|full|further|get|give|go|had|" + 
                            "has|hasnt|have|he|hence|her|here|hereafter|" + 
                            "hereby|herein|hereupon|hers|herself|him|" + 
                            "himself|his|how|however|hundred|ie|if|in|" + 
                            "inc|indeed|interest|into|is|it|its|itself|" + 
                            "keep|last|latter|latterly|least|less|ltd|made|" + 
                            "many|may|me|meanwhile|might|mill|mine|more|" + 
                            "moreover|most|mostly|move|much|must|my|myself|" + 
                            "name|namely|neither|never|nevertheless|next|" + 
                            "nine|no|nobody|none|noone|nor|not|nothing|now|" + 
                            "nowhere|of|off|often|on|once|one|only|onto|or|" + 
                            "other|others|otherwise|our|ours|ourselves|out|" + 
                            "over|own|part|per|perhaps|please|put|rather|" + 
                            "re|same|see|seem|seemed|seeming|seems|serious|" + 
                            "several|she|should|show|side|since|sincere|six|" + 
                            "sixty|so|some|somehow|someone|something|" + 
                            "sometime|sometimes|somewhere|still|such|system|" + 
                            "take|ten|than|that|the|their|them|themselves|" + 
                            "then|thence|there|thereafter|thereby|therefore|" + 
                            "therein|thereupon|these|they|thickv|thin|third|" + 
                            "this|those|though|three|through|throughout|thru|" + 
                            "thus|to|together|too|top|toward|towards|twelve|" + 
                            "twenty|two|un|under|until|up|upon|us|very|via|" + 
                            "was|we|well|were|what|whatever|when|whence|" + 
                            "whenever|where|whereafter|whereas|whereby|" + 
                            "wherein|whereupon|wherever|whether|which|while|" + 
                            "whither|who|whoever|whole|whom|whose|why|will|" + 
                            "with|within|without|would|yet|you|your|yours|" + 
                            "yourself|yourselves|the";
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
      
      if (wordTest.toLowerCase().matches("a|i|o") == false && 
             (wordTest.length() == 1) ){
              return false;
      }
      
      if (wordTest.toLowerCase().matches(twoLetters.toLowerCase()) == false && 
             (wordTest.length() == 2) ){
              return false;
      }
      if (wordTest.toLowerCase().matches(threeLetters.toLowerCase()) == false && 
             (wordTest.length() == 3) ){
              return false;
      }
      
      if (wordTest.toLowerCase().matches(stopWords.toLowerCase()) == true){
          return false;
      }
      
      
      if (checkWord(wordTest.toLowerCase(),"a|i|o") == false && 
              (wordTest.length() == 1)){
          return false;
      }
      
      if (checkWord(wordTest.toLowerCase(),
              twoLetters.toLowerCase()) == false && 
              (wordTest.length() == 2)){
          return false;
      }
  
     if (checkWord(wordTest.toLowerCase(),
             threeLetters.toLowerCase()) == false && 
              (wordTest.length() == 3)){
          return false;
      }
         
      
      return true;
  }
  /**
   * Function initialized the FileList array that will be used to contain 
   * results queries and inverted file creation
   * @param mainD - String value directory that will all output files
   */
  public static void setFilesDetails(String mainD){
      
      dictFileList[0] = mainD;
      dictFileList[1] = dictFileList[0]+ "dictionary.txt"; //dictFileName+ 
      dictFileList[2] = dictFileList[0]+ "dictionaryQ0.txt"; //dictFileNameQ0+ 
      dictFileList[3] = dictFileList[0]+ "dictionary2.txt"; //dictFileName2+ 
      dictFileList[4] = dictFileList[0]+ "dictionaryQ1.txt"; //dictFileNameQ1+ 
      dictFileList[5] = dictFileList[0]+ "dictionaryS.txt"; //dictFileNameS+ 
      dictFileList[6] = dictFileList[0]+ "dictionarySQ0.txt"; //dictFileNameSQ0+ 
      dictFileList[7] = dictFileList[0]+ "dictionaryS2.txt"; //dictFileNameS2+ 
      dictFileList[8] = dictFileList[0]+ "dictionarySQ1.txt"; //dictFileNameSQ1+ 
      dictFileList[9] = dictFileList[0]+ "invert.bin"; //inverFileName+
      dictFileList[10] = dictFileList[0]+ "invertQ.bin"; //inverQFileName+ 
      dictFileList[11] = dictFileList[0]+ "invertS.bin"; //inverFileNameS+ 
      dictFileList[12] = dictFileList[0]+ "invertSQ.bin"; //inverQFileNameS+
      dictFileList[13] = dictFileList[0]+ "queryData.txt"; //queryData 
      dictFileList[14] = dictFileList[0]+ "queryData1.txt"; //queryData 
      dictFileList[15] = dictFileList[0]+ "queryData2.txt"; //queryData 
      dictFileList[16] = dictFileList[0]+ "queryData3.txt"; //queryData 
      dictFileList[17] = dictFileList[0]+ "queryData4.txt"; //queryData 
      dictFileList[18] = dictFileList[0]+ "queryData5.txt"; //queryData 
 
      dictFileList[1] = dictFileList[0]+ "dictionary.txt"; //dictFileName 
       
  }
  
  /**
   * The following function will remove punctuation, and lower-case words. This
   * function is used to help normalize the words after splitting the text line 
   * using space as the delimiter
   * @param data - String value that requires removal of unwanted characters
   * @param stemF - Boolean status that indicates if Stemming has been 
   * flagged
   * @return - String - final results from removing unwanted characters from a 
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
      
      cleaner = cleaner.replaceAll("\\s+", "");
      cleaner = cleaner.toLowerCase();
      
      if (stemF == true && cleaner.length() > 4){
        return cleaner.substring(0, 4);
        
      }
       
      return cleaner; 
  }
   /**
   * The following function will remove punctuation, and lower-case words. This
   * function is used to help normalize the words after splitting the text line 
   * using space as the delimiter
   * @param data - String value that requires removal of unwanted characters
   * @param stemF - Boolean status that indicates if Stemming has been 
   * flagged
   * @return - String - final results from removing unwanted characters from a 
   * term
   */
  public static String cleanWord2(String data, boolean stemF )
  {
      String cleaner = data;
      cleaner = cleaner.replaceAll("[\\[\\]\\{\\}\\/,_\"--.!?:;)(`]||[---]", "");
      cleaner = cleaner.replaceAll("[\\p{Punct}&&[^']&&[^-]]", " ");
      cleaner = cleaner.replaceAll("�", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("'", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("", " ");
      cleaner = cleaner.replaceAll("", " ");
      
      cleaner = cleaner.replaceAll("[0-9]", " ");
      cleaner = cleaner.replaceAll("  ", " ");
      if (cleaner.length() > 2 ){
      if (cleaner.charAt(cleaner.length()-1) == '-'){
        cleaner = cleaner.replace("-", " ");  
      }
      if ((cleaner.charAt(cleaner.length()-1) == '\'') && 
              ((cleaner.toLowerCase().charAt(cleaner.length()-2) != 's'))){
        cleaner = cleaner.replace("\'", " ");  
      }
      
      if (cleaner.charAt(0) == '-'){
        cleaner = cleaner.replace("-", " ");  
      }
      }
      
      cleaner = cleaner.replaceAll("\\s+", " ");
      cleaner = cleaner.toLowerCase();
      
      if (stemF == true && cleaner.length() > 4){
        return cleaner.substring(0, 4);
        
      }
       
      return cleaner; 
  }
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
     * The following procedure will load the content of a file into a List of 
     * JavaParser objects
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
  public static List<JavaParser>  mainFileLoader2( String[] args, int start, 
          int end, int dataS, boolean stemF) 
     {
         
        String[] docData = new String[2];
        String readdata1 =  null;
        String[] dataValue = new String[2];
        Integer runCreate = 0;
        int j = 0, type;
        List<JavaParser> javaWordList = new ArrayList<JavaParser>();
        
        
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
                            
                             JavaParser newWord = new JavaParser();
                             newWord.setwordId(j);
                             newWord.setCollectionFrequency(1);
                             newWord.setwordName(dataValue[1]);
                             newWord.addActualWord(data);
                             newWord.addDocMapStruct(docData[1]);
                             newWord.addDocListStruct(docData[1]);
                             newWord.addDocumentID(docData[1]);
                             javaWordList.add(newWord);
                            
                        }else{
 
                           JavaParser existingWord = wordSearch(javaWordList,
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
            System.out.println("Cannot Open File" + args[type]);
        }
        
         return javaWordList;
        }
   /**
      * The following procedure will load the content of a file into a List of 
      * JavaParser objects
      * @param args List of string parameters to access the file
      * @param dataS - Integer value that indicates if a import file contains 
      * queries or documents and also if Stem was flagged
      * @return - The following function returns the a multi-dimensional array
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
                                System.out.printf("Parser found %d" + 
                                  " documents to process.",--j);;       
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
                               System.out.printf("Parser found %d" + 
                                  " queries to process.",--j);
                               break;  
            }

        }
        catch (IOException e)
        {
            System.out.println("Cannot Open File" + args[type]);
        }
        System.out.println();
         return j;
        }
 
  /**
   * Function will display the complete dictionary using the javaFinalParser
   * structure. The collection frequency and document frequency are also 
   * displayed.
   * @param word - List of JavaParser objects 
   */
  public static void printWordDetails(List<JavaParser>  word )
  {
      int count = 0;
      System.out.println("The program should calculate both the total number "
              + " times each word is seen (collection frequency of the word) "
              + "and the number of documents which the word occurs in "
              + "(document frequency of the word).  \n");
      System.out.println("Report the number of ‘paragraphs’ processed; we'll"
              + " consider each paragraph to be a 'document', even though all "
              + "paragraphs are contained in a single file1.");
           
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(JavaParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
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
   * Function will display the complete dictionary and the total number 
   * of documents
   * @param word - List of JavaParser objects 
   */
   public static void printWordDetailsF(List<javaFinalParser>  word )
  {
        int count = 0;
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(javaFinalParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d %n",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
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
   * Function will display the complete dictionary and the total number 
   * of documents
   * @param word - List of JavaParser objects 
   */
   public static void printDocIDWordDetails(List<JavaParser>  word )
  {
       int count = 0;
       System.out.printf("%-20s %-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence","DocID Details");
         for(JavaParser data : word)
          {
           System.out.printf("%-20s %-20d %-20d ",data.getWordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence());
                HashMap<Long,Integer> hm = data.getDocMapStruct();
                for (Map.Entry m:hm.entrySet()){
                   System.out.print("("+m.getKey()+","+m.getValue() + "),");
                }
            count++;    
           System.out.println(data.docMapStruct.size());
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
   public static void printDocID2FWordDetails(List<javaFinalParser>  word, 
           int dataS)
  {
        int count = 0;
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
     public static void printDocID2WordDetails(List<JavaParser>  word )
  {    
        Collections.sort(word, JavaParser.DocIDComparator);
        System.out.printf("%-20s %-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence","DocID Details");
         for(JavaParser data : word)
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
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged 
    */  
   public static void serializeDictionary(List<JavaParser>  word, 
           int dataS){
          Collections.sort(word, JavaParser.DocIDComparator);
          FileOutputStream fileOut;
          String type;
                   
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
         
     }
   /**
    * Function sorts the dictionary and writes the results to a file specified 
    * by the user
    * @param word - List of JavaParser objects 
    * @param outFile - File that will contain the serialized data from the 
    * JavaParser object
    */  
   public static void serializeDictionaryRun(List<JavaParser>  word, 
           String outFile){
          Collections.sort(word, JavaParser.DocIDComparator);
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
    * @param word - List of JavaParser objects 
    * @param outFile - File that will contain the serialized data from the 
    * JavaParser object
    */  
   public static void serializeQueryRun(List<docQueryStruct>  word, 
           String outFile){
          Collections.sort(word, docQueryStruct.simComparator);
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
   public static void serializeFDictionary(List<JavaParser>  word, 
           int dataS){
          Collections.sort(word, JavaParser.DocIDComparator);
          FileOutputStream fileOut;
          String type;
          
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
               List<JavaParser> wordSplit1 = new ArrayList<JavaParser>();
              
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
               wordSplit1 = new ArrayList<JavaParser>(); 
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
         
     }
   
  /**
    * Function sorts the dictionary object javaFinalParser and writes the 
    * results to the default file
    * @param word - List of JavaParser objects 
    * @param dataS - Integer value that indicates if a import file contains 
    * queries or documents and also if Stem was flagged
    */
     public static void serializeFinalDictionary(List<javaFinalParser>  word, 
             int dataS){
          Collections.sort(word, javaFinalParser.DocIDComparator);
          String outFile;
          
         switch(dataS){
            case 1 : outFile = dictFileList[4];
                           break;
            case 3 : outFile = dictFileList[3];
                           break;
            case 0 : outFile = dictFileList[8];
                           break;
            case 2 : outFile = dictFileList[7];
                           break;  
                           
            default :   outFile = dictFileList[3];
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
                  System.out.printf("System can not find file : %s %n", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          outFile);
           }  
         
     }
   /**
   * Function converts the JavaParser object to javaFinalParser object and 
   * reduces the content of the dictionary to contain less datasets 
   * @param word - List of JavaParser objects 
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - returns a javaFinalParser object
   */      
  public static List<javaFinalParser> conversionDictionary(List<JavaParser>  
          word, int dataS) 
  {
      String outFile;
      Integer countDocID = 0, counter2 = 0;
      double idf = 0.0;
        
           
            switch(dataS){
            case 1 : outFile = dictFileList[10];
                           break;
            case 3 : outFile = dictFileList[9];
                           break;
            case 0 : outFile = dictFileList[12];
                           break;
            case 2 : outFile = dictFileList[11];
                           break;               
            default :   outFile = dictFileList[9];
                      break;
        }
      
      Collections.sort(word, JavaParser.DocIDComparator);
      
      List<javaFinalParser>  word2 = new ArrayList<javaFinalParser>();
      try {
          
           DataOutputStream fileData = new DataOutputStream
        (new FileOutputStream(outFile));
           
       for(JavaParser data : word)
          {   idf = 0.0;
              javaFinalParser newWord = new javaFinalParser();
                  newWord.setwordId(data.getwordId());
                  newWord.setwordName(data.getwordName());  
                  newWord.setCollectionFrequency
                        (data.getCollectionFrequency());  
                  newWord.copyActualList(data.getActualList());
                  newWord.copyDocumentID(data.getDocumentList());
                  newWord.docListStruct.addAll(data.getDocListStruct());
                  newWord.setDocumentFrequencey(data.docListStruct.size());
                  double idf0 = paragCT /  data.
                          docListStruct.size(); 
                  idf = (double) Math.log(idf0)/ Math.log(2.0);
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
         
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s", 
                          outFile);
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
                  ( new FileInputStream(dictFileList[9]));
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
                  ( new FileInputStream(dictFileList[9]));
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
   * @param position - pointer in the inverted file
   * @param length - length of posting in the inverted file
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - Integer array of the document id and the term frequency 
   */
    public static int[][] importInvertData(int position, int length, 
            int dataS)
  {
      String outFile;
      int intCounter = 0;
      int value = 0;
      
             
            switch(dataS){
            case 1 : outFile = dictFileList[10];
                           break;
            case 3 : outFile = dictFileList[9];
                           break;
            case 0 : outFile = dictFileList[12];
                           break;
            case 2 : outFile = dictFileList[11];
                           break;               
            default : outFile = dictFileList[9];
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
                  
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s", 
                          outFile);
           }   
     
      return dictionCode;  
      
  }
    /**
     * Function that provides the count of splits required for a specific file 
     * to be process. The term split is referenced in this description to 
     * identify the breakup of a large file or object into smaller portion to 
     * be process
     * @param seData - List of all documentID1 objects that contain the filename 
     * and the count of splits
     * @param fileName - String name of the file that was processed
     * @return - Integer value of the total number of splits required
     */
  public static int searchSeriData(List<documentID1> seData, String fileName ){
      for(documentID1 data1 : seData){
          if(data1.getDocID().toLowerCase().
                  compareTo(fileName.toLowerCase()) == 0){
              return data1.getTermCount();
          }
      }
      return 0;
  }  
  /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - JavaParser object
   */
  public static List<JavaParser> deserializeDictionary(int dataS){
      List<JavaParser>  word = new ArrayList<JavaParser>();
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
            default :   outFile = dictFileList[1];
                      break;
        }
      
       try {
                
                ObjectInputStream in = new ObjectInputStream(new 
                FileInputStream(outFile));
                List<JavaParser> tempObj;
                int count = searchSeriData(serilizationData,outFile);
                if ( count > 1 ){
                    for(int i = 0; i < count; i++ ){
                        tempObj = (List<JavaParser>) in.readObject();
                        word.addAll(tempObj);
                    }
                }else{
                  word = (List<JavaParser>) in.readObject();
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
     
      
      return word;
  }
  /**
   * Function that imports data from the lexicon used for keyword search
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged
   * @return - javaFinalParser object
   */
    public static List<javaFinalParser> deserialize2Dictionary(int dataS){
      List<javaFinalParser>  word = new ArrayList<javaFinalParser>();
      String outFile;
         switch(dataS){
            case 1 : outFile = dictFileList[4];
                           break;
            case 3 : outFile = dictFileList[3];
                           break;
            case 0 : outFile = dictFileList[8];
                           break;
            case 2 : outFile = dictFileList[7];
                           break;                           
            default :   outFile = dictFileList[3];
                      break;
        }
      
      
       try {
                FileInputStream fileIn = new FileInputStream(outFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                word = (List<javaFinalParser>) in.readObject();
                if (debugType.equalsIgnoreCase("Debug6")){
                  System.out.println("Serialized data is read from " + 
                        outFile);
                   }
                fileIn.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          outFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          outFile);
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      return word;
  }
   /**
   * Function that imports data from the lexicon used for keyword search
   * @param inFile - String file name that will contain the JavaParser 
   * serialized object data 
   * @return - List of JavaParser objects
   */
    public static List<JavaParser> deserializeFDictionary(String inFile){
      List<JavaParser>  word = new ArrayList<JavaParser>();
      
       try {
                FileInputStream fileIn = new FileInputStream(inFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                word = (List<JavaParser>) in.readObject();
                if (debugType.equalsIgnoreCase("Debug6")){
                 System.out.println("Serialized data was read from " + 
                        inFile + " Status : Completed ");
                }
                fileIn.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          inFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          inFile);
           }   catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      return word;
  }
   /**
   * Function that imports data from the lexicon used for keyword search
   * @param inFile - String file name that will contain the JavaParser 
   * serialized object data 
   * @return - List of JavaParser objects
   */
    public static List<docQueryStruct> deserializeQDictionary(String inFile){
      List<docQueryStruct>  word = new ArrayList<docQueryStruct>();
      
       try {
                FileInputStream fileIn = new FileInputStream(inFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                word = (List<docQueryStruct>) in.readObject();
                if (debugType.equalsIgnoreCase("Debug6")){
                 System.out.println("Serialized data was read from " + 
                        inFile + " Status : Completed ");
                }
                fileIn.close();
           } catch (FileNotFoundException e) {
                  System.out.printf("System can not find file : %s %n", 
                          inFile);
           } catch (IOException e) {
                  System.out.printf("System had issue with file : %s %n", 
                          inFile);
           }   catch (ClassNotFoundException e) {
               e.printStackTrace();
        }
     
      return word;
  }
  
  /**
   * Function will display the full list of all JavaParser objects including the
   * name and collection term frequency 
   * @param word - List of JavaParser objects
   */
  public static void printWordDetailsTagclouds(List<JavaParser>  word )
  {
        System.out.printf("%-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
         for(JavaParser data : word)
          {
           System.out.printf("%s:%d %n",data.getwordName(),
                  data.getCollectionFrequency());
          }
         
         System.out.println("\n");
  
  }
  /**
   * The following function will display the complete details of the Top 30 
   * words from the text being analyzed 
   * @param word - List of JavaParser objects 
   */
  public static void printWordDetailsTop30(List<JavaParser>  word )
  {
       
        Collections.sort(word,new JavaParser());  
        
        List<JavaParser> top30 = word.subList(word.size()-30, word.size());
        System.out.println("Identify the 30 most frequent words (by total "
                + "count, also known as collection frequency) and report "
                + "both the collection frequency and the document frequency "
                + "for each.  \n");
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(JavaParser data : top30)
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
   * @param word - List of JavaParser objects
   */
  public static void printWordDetailsPlace(List<JavaParser>  word )
  {
        Collections.sort(word,new JavaParser());  
        
        List<JavaParser> top100 = word.subList(word.size()-100, word.size());
        List<JavaParser> top500 = word.subList(word.size()-500, word.size());
        List<JavaParser> top1000 = word.subList(word.size()-1000, word.size());
        
        System.out.println("Also print the 100th, 500th, and 1000th "
                + "most-frequent words and their frequencies of occurrence. "
                + "(But please do not just printout the top 1000.)  \n");
        
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(JavaParser data : top100)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence()); 
             break;
          }
         for(JavaParser data : top500)
          {
            System.out.printf("%-20s %-20d %-20d %n",data.getwordName(),
                  data.getCollectionFrequency(),
                  data.getDocumentFrequence()); 
             break;
          }
         for(JavaParser data : top1000)
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
   * @param word -  List of JavaParser objects 
   */
  public static void printWordDetailsUnique(List<JavaParser>  word )
  {
        int count = 0, count2 = 0;
        Collections.sort(word,new JavaParser());  
        
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(JavaParser data : word)
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
   * @param word -  List of JavaParser objects 
   */
  public static void printWordDetailsUnique1(List<JavaParser>  word )
  {
        int count = 0, count2 = 0;
        Collections.sort(word,new JavaParser());  
        
        for(JavaParser data : word)
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
  * @param word -  List of JavaParser objects
  */ 
 public static void printWordDetailsSingle(List<JavaParser>  word )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new JavaParser());  
       float m;  
       
       System.out.println(" Calculate and print the number of words that occur "
               + "in exactly one document. (For Les Mis, I believe reptile, "
               + "silkworm, and signatures are such words.) What percentage "
               + "of the dictionary terms occur in just one document? \n");
       
        System.out.printf("%-20s %-20s %-20s %n","Word Name","Collection "
                  + "Frequence","Document Frequence");
        for(JavaParser data : word)
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
  * Function will display the complete list of JavaParser objects that contain
  * the word represented by the string w
  * @param word - List of JavaParser objects
  * @param w - String word to use to search the list of JavaParser objects
  */
 public static void printWordSingle(List<JavaParser>  word, String w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new JavaParser());  
       float m;  
       
        for(JavaParser data : word)
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
  * Function will display the complete list of javaFinalParser objects that contain
  * the word represented by the string w 
  * @param word - List of javaFinalParser objects
  * @param w - String word to use to search the list of JavaParser objects
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
  * Function will display the complete list of javaFinalParser objects that 
  * contain the word represented by the string w. This function will also 
  * display the collection frequency and the document frequency and the aligned 
  * documents post listing
  * @param word - List of javaFinalParser objects
  * @param w - String word to use to search the list of JavaParser objects
  */
  public static void printWordDetailsWordPL(List<javaFinalParser>  word, String
          w )
  {
       int count = 0, count2 = 0;
       Collections.sort(word,new javaFinalParser());  
       
          for(javaFinalParser data : word)
          {
            if(data.getWordName().toLowerCase().
                    equalsIgnoreCase(w.toLowerCase())){
             System.out.printf("%-20s %-20s %-20s %n","Word Name",
                     "Collection Frequence","Document Frequence");
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
  * @param word -  List of JavaParser objects
  * @param selection - String word that will be used to search the JavaParser 
  * objects
  * @return - boolean result of searching the list of JavaParser objects 
  */
  public static boolean wordExists(  List<JavaParser>  word, 
            String selection) 
    {
        
        for(JavaParser data : word)
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
 /**
  * The following function will determine if a word exists in the dictionary 
  * and returns a boolean result
  * @param word -  List of docQueryStruct objects
  * @param selection - integer document number used to search the list of 
  * docQueryStruct objects
  * @return - boolean result of the search for selection 
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
   * word name and return the corresponding JavaParser object
   * @param word - List of JavaParser objects
   * @param selection - word that is used to search the list of JavaParser 
   * objects
   * @return - JavaParser objected identified during the search
   */
  public static JavaParser wordSearch( List<JavaParser>  word, 
            String selection )
  {
        JavaParser validateCheck = new JavaParser();
        
        for(JavaParser data : word)
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
   * word name and return the corresponding JavaParser object
   * @param word - List of JavaParser objects
   * @param selection - integer value that is used to search the list of 
   * docQueryStruct objects
   * @return - docQueryStruct identified during the search
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
   * Function that mergers multiple JavaParser objects into one JavaParser list
   * @param word1 - Collection of JavaParser list objects
   * @param word2 - new JavaParser list object
   * @param x - flag to determine if initial stage. If x = 1, then that word2 is
   * the first to be added to the collection and no preprocessing is required. 
   * However, additional word2 will have to be process prior to be added to the
   * collection
   */
  public static void merger( List<JavaParser>  word1, 
          List<JavaParser>  word2, int x)
  {
          for(JavaParser data1 : word2 ){
             if( wordExists(word1,data1.getwordName())){
                  JavaParser existingWord = wordSearch(word1,
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
    /**
   * Function that mergers multiple JavaParser objects into one JavaParser list
   * @param word1 - Collection of JavaParser list objects
   * @param word2 - new JavaParser list object
   * @param x - flag to determine if initial stage. If x = 1, then that word2 is
   * the first to be added to the collection and no preprocessing is required. 
   * However, additional word2 will have to be process prior to be added to the
   * collection
   */
  public static void mergerQ( List<docQueryStruct>  word1, 
          List<docQueryStruct>  word2, int x, int[] queryDataS )
  {
      
          for(docQueryStruct data1 : word2 ){
             if( wordExistsDocID(word1,data1.getDocIDInt(),queryDataS[1])){
                  docQueryStruct existingWord = wordIDSearchQ(word1,
                                   data1.getDocIDInt(),queryDataS[1]);
                           existingWord.setTermCount(existingWord.getTermCount()
                           + data1.getTermCount());
                           existingWord.setWordCount(existingWord.getWordCount()
                           + data1.getWordCount());
                           existingWord.docMapStructTFIDF.
                                   putAll(data1.getDocMapStructTFIDF()); 
                           existingWord.docMapStruct.
                                   putAll(data1.getDocMapStruct());
                           existingWord.setSumSquares(existingWord.
                                   getSumSquares() + data1.getSumSquares());
                           existingWord.setSumSquaresNoTFIDF(existingWord.
                                   getSumSquaresNoTFIDF() + data1.
                                           getSumSquaresNoTFIDF());
             }else{
                 word1.add(data1);
             };
          }
              
      
      
  }
  /**
   * Function that calculates the cosine value based on the length and dot
   * product. The length and dot product are based on the TF/IDF weights, which 
   * are identified in the javaFinalParser objects. 
   * @param word - List of docQueryStruct objects that are documents
   * @param query - List of docQueryStruct objects that are queries
   * @param qNum - Query document id reference number
   */
  public static void simProduct(List<docQueryStruct> word, 
          List<docQueryStruct> query,
          int qNum ){
      
      docQueryStruct qu = docSearch(query,qNum,1);
      for( docQueryStruct wData : word ){
          wData.setSimValue(0);
          wData.setSimValueNoTFIDF(0);
      }

      for( docQueryStruct wData : word ){
          if( (wData.getDocLength() * qu.getDocLength()) > 0){
          wData.setSimValue(wData.getDotProduct()
                  /(wData.getDocLength() * qu.getDocLength()));
          } 
          if ( (wData.getDocLengthNoTFIDF() *
                          qu.getDocLengthNoTFIDF()) > 0){

          wData.setSimValue(wData.getDotProductNoTFIDF()
                  /(wData.getDocLengthNoTFIDF() *
                          qu.getDocLengthNoTFIDF()));
          }
      }
      
  }
  /**
   * Function used to display all the terms contain for all the queries found
   * @param query - List of all query based docQueryStruct objects
   */
  public static void displayALLBags(List<docQueryStruct> query){
      
      for(int i = 1; i <= query.size(); i++ ){
          displayBag(query,i,"Query");
      }
      
  }
  /**
   * Function that outputs to a file the document dot products that were 
   * determined based on the query id
   * @param qNum - query number used in the printf formatting
   * @param word - List of all docQueryStruct objects that match the query
   * terms
   * @param outFile - Output file that will contains the results of the 
   * documents found. This file will only contain the highest 50 documents
   */
  public static void writeFDocIDDOTPRODUT(int qNum, List<docQueryStruct> word, 
           String outFile, boolean appendS){
           Collections.sort(word, docQueryStruct.dotComparator);
           String outData;
           int j = 1;
      try {    
            
         FileWriter fw = new FileWriter(new File(outFile),appendS);
         for(docQueryStruct wData : word ){
           outData = String.format("%d %2s %d %d %f %s %n",
                   queryNumData[qNum][0]
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
   * Function that outputs to a file the document cosine values that were 
   * determined based on the query id
   * @param qNum - query number used in the printf formatting
   * @param word - List of all docQueryStruct objects that match the query
   * terms
   * @param outFile - Output file that will contains the results of the 
   * documents found. This file will only contain the highest 50 documents
   */
    public static void writeFDocIDSimPRODUT(int qNum, 
            List<docQueryStruct> word, String outFile, boolean appendS){
           Collections.sort(word, docQueryStruct.dotComparator);
           String outData;
           int j = 1;
      try {    
            
         FileWriter fw = new FileWriter(new File(outFile),appendS);   
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
   */  
  public static void docLengthDa(List<docQueryStruct> query){
      
       for(docQueryStruct wData : query ){
        wData.setDocLength(0);
         wData.setDocLengthNoTFIDF(0);
       }
      
      for(docQueryStruct wData : query ){
        wData.setDocLength(Math.sqrt(wData.getSumSquares()));
         wData.setDocLengthNoTFIDF(Math.sqrt(wData.getSumSquaresNoTFIDF()));
       }
       
  }
  /**
   * Function that determines the dot product for each document that matches 
   * query specified by the qNumber.  
   * qNumber value. 
   * @param query - List of all query docQueryStruct objects identified
   * @param word - List of all document docQueryStruct objects that matched 
   * the query
   * @param qNumber - query identification number 
   */  
  public static void dotProduct(List<docQueryStruct> query, 
          List<docQueryStruct> word, int qNumber, int dataS){

      docQueryStruct dData = docSearch(query,qNumber,dataS);
      
       for(docQueryStruct wData : word ){
        wData.setDotProduct(0);
         wData.setDotProductNoTFIDF(0);
       }
      
      if ( dData.getDocMapStruct() != null ){
        HashMap<String,Double> hm = dData.getDocMapStructTFIDF();
        for (Map.Entry m:hm.entrySet()){
           for(docQueryStruct wData : word ){
              if ( wData.getDocMapStructTFIDF() != null ){
                HashMap<String,Double> hmW = wData.
                        getDocMapStructTFIDF();
                 for (Map.Entry mW:hmW.entrySet()){
                     if (m.getKey().toString().
                          equalsIgnoreCase(mW.getKey().toString())){
                         wData.setDotProduct(wData.getDotProduct() + 
                          (Double.parseDouble(m.
                                  getValue().toString()) *
                           Double.parseDouble(mW.getValue().
                                   toString())));
                 }
           }
         }
        }
     }
       HashMap<String,Integer> hm1 = dData.getDocMapStruct();
        for (Map.Entry m1:hm1.entrySet()){
           for(docQueryStruct wData : word ){
              if ( wData.getDocMapStruct() != null ){
                HashMap<String,Integer> hmW1 = wData.
                        getDocMapStruct();
                 for (Map.Entry mW1:hmW1.entrySet()){
                     if (m1.getKey().toString().
                          equalsIgnoreCase(mW1.getKey().toString())){
                         wData.setDotProductNoTFIDF(wData.getDotProductNoTFIDF() + 
                          (Integer.parseInt(m1.
                                  getValue().toString()) *
                           Integer.parseInt(mW1.getValue().
                                   toString())));
                 }
           }
         }
        }
     }     
        
  }
      
  }
 /**
  * Function to display all the identified docQueryStruct objects identified. 
  * This function will display the sum of squares, dot product, length and 
  * term count based on non TF-IDF weights
  * @param queryDoc - List of docQueryStruct objects that are either queries or 
  * documents
  * @param x - Integer specific number for the document or query
  * @param type - String docQueryStruct type indicator, specifies if a query or 
  * document is being process
  */
  public static void displayBag(List<docQueryStruct> queryDoc , int x,
          String type){
      int convertedN;
      switch(cleanWord(type,false)){
          case "querytfidf"  :                 
          case  "query" :   convertedN = queryNumData[x][0];
                             break;
          default  :     convertedN = x;              
      }
      
      for(docQueryStruct qdata : queryDoc){
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
  * Function to display all the identified docQueryStruct objects identified. 
  * This function will display the sum of squares, dot product, length and 
  * term count based on TF-IDF weights
  * @param query - List of docQueryStruct objects that are either queries or 
  * documents
  * @param x - Integer specific number for the document or query
  * @param type - String docQueryStruct type indicator, specifies if a query or 
  * document is being process
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
                      printf("Q%d (%s,%f) %f %f %f %d %n", k, v,
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
  * Function to display all the identified docQueryStruct objects identified. 
  * This function will display the sum of squares, dot product, length and 
  * term count based on TF-IDF weights
  * @param query - List of docQueryStruct objects that are either queries or 
  * documents
  * @param x - Integer specific number for the document or query
  * @param type - String docQueryStruct type indicator, specifies if a query or 
  * document is being process
  */
  public static void displayBagTFIDF2(List<docQueryStruct> query , int x,
          String type){
      int convertedN;
      switch(cleanWord(type,false).toLowerCase()){
          case "querytfidf"  : convertedN = queryNumData[x][0];
                             break;                
          case  "query" :   convertedN = queryNumData[x][0];
                             break;
          default  :     convertedN = x;              
      }
      
      for(docQueryStruct qdata : query){
          if( qdata.getDocIDInt() == convertedN ){
          System.out.printf(" %s %s ", qdata.getDocIDInt(), type);
          if ( qdata.getDocMapStructTFIDF() != null ){
               qdata.getDocMapStructTFIDF().
                  forEach((k,v)->System.out.
                      printf("(%s,%f) ", k, v));
          }
            System.out.println();
          }
         }           
  }
  /**
   * Function to display a docID sort ordered docQueryStruct object
   * @param word - List of docQueryStruct objects
   */
  public static void displayDocID(List<docQueryStruct> word){
      
     Collections.sort(word, new docQueryStruct());  
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getDotProduct());
     }    
  }
  /**
   * Function to display all dot product sort ordered docQueryStruct object
   * @param word  - List of docQueryStruct objects
   */
  public static void displayDocIDDOTPRODUT2(List<docQueryStruct> word){
      
     Collections.sort(word, docQueryStruct.dotComparator); 
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getDotProduct());
     } 
  }
  /**
   * Function to display highest 50 dot product sort ordered docQueryStruct 
   * object
   * @param word - List of docQueryStruct objects
   * @param qNum - query document id number
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
   * Function to display all cosine value sort ordered docQueryStruct object
   * @param word - List of docQueryStruct objects
   */   
  public static void displayDocIDSIMPRODUT2(List<docQueryStruct> word){
      
     Collections.sort(word, docQueryStruct.simComparator); 
    
     for(docQueryStruct wData : word ){
         System.out.printf("%10s %6f %n", wData.getDocID(),
                 wData.getSimValue());
     } 
      
  } 
  /**
   * Function to display highest 50 cosine value sort ordered docQueryStruct 
   * object
   * @param word - List of docQueryStruct objects
   * @param qNum - query document id number
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
  
  public static boolean checkWord(String word1, String word2){
      for(String data : word2.split("|")){
          if (data.equalsIgnoreCase(word1)){
              return true;
          }
      }
      return false;
  }
  
  /**
   * Function to build a list of docQueryStruct documents based on the query 
   * selected. This function will identify all the documents that match a 
   * specific query's terms and create a list of docQueryStruct objects. Each 
   * docQueryStruct object will contain a list of terms and frequency data
   * @param word - List of javaFinalParser objects
   * @param query - List of docQueryStruct objects
   * @param qQuery - query document id number
   * @param dataS  - Integer value that indicates if a import file contains 
   * @return - List of docQueryStruct object
   */ 
  public static List<docQueryStruct> buildBagOfDocs(List<javaFinalParser> word,
      List<docQueryStruct> query, int qQuery, int dataS ){
      List<docQueryStruct> docData = new ArrayList();
      List<javaFinalParser> wordX = new ArrayList();
      List<javaFinalParser> wordZ = new ArrayList();

      int[] sizeWord = new int[2];
      sizeWord[0] = 1000000000;
      sizeWord[1] = 0;
      StringBuilder qW = new StringBuilder();  
      StringBuilder qW2 = new StringBuilder();  
        
      docQueryStruct  wordQ = docSearch(query,qQuery,0);
      
    
       
        if ( wordQ.getDocMapStructTFIDF() != null ){
               wordQ.getDocMapStructTFIDF().
                  forEach((k,v)->qW2.append(k).
                          append("|"));
          }
        
        qW2.deleteCharAt(qW2.length()-1);
      
      
        for(String dW :  qW2.toString().split("\\|")){
          if( sizeWord[0] > dW.length() ){
              sizeWord[0] = dW.length();
          }
          if( sizeWord[1] < dW.length() ){
              sizeWord[1] = dW.length();
          }
      }
        
        if ( wordQ.getDocMapStructTFIDF() != null ){
               HashMap<String,Double> hmW = wordQ.getDocMapStructTFIDF();
               for (Map.Entry mW : hmW.entrySet()) {
                  qW.append(mW.getKey().toString().substring(0,sizeWord[0])).
                          append("|");
               }
          }  
        
        qW.deleteCharAt(qW.length()-1);
       System.out.println(qW2);
       for(javaFinalParser wordD1 : word){ 
          if (wordD1.getWordName().replaceAll("\\s+", "").
                  length() >= sizeWord[0] && wordD1.getWordName().
                          replaceAll("\\s+", "").length() <= sizeWord[1]){ 
          if(wordD1.getWordName().substring(0, sizeWord[0]).
                  matches(qW.toString())){
        
              wordX.add(wordD1);
          }
          }
      }

        for(javaFinalParser wordD1 : wordX){ 
          if(wordD1.getWordName().substring(0, sizeWord[0]).
                  matches(qW2.toString())){
        
              wordZ.add(wordD1);
          }  
      }
       
        wordX.clear();
       
      for(javaFinalParser wordD : wordZ){
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
              
      
     System.out.printf("The query %d had results %d using %s %n ",
             qQuery,  docData.size(),qW2); 
     wordZ.clear();
     
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
      
       for(docQueryStruct wordF : docData){
          sumSQRS = 0;
             if ( wordF.getDocMapStruct() != null ){
               HashMap<String,Integer> hm = wordF.getDocMapStruct();
                for (Map.Entry m:hm.entrySet()){
                   prodW = Integer.parseInt(m.getValue().toString());
                   sumSQRS = sumSQRS  +  (prodW * prodW);
                }
            }
             wordF.setSumSquaresNoTFIDF(sumSQRS);
             wordF.setDocLengthNoTFIDF(Math.sqrt(sumSQRS)); 
      }
      
      
      Collections.sort(docData, docQueryStruct.docIDComparator);
      
      return docData;
  }
  
  
  /**
   * Function to build a list of docQueryStruct documents based on the query 
   * selected. This function will identify all the documents that match a 
   * specific query's terms and create a list of docQueryStruct objects. Each 
   * docQueryStruct object will contain a list of terms and frequency data
   * @param word - List of javaFinalParser objects
   * @param query - List of docQueryStruct objects
   * @param qQuery - query document id number
   * @param dataS  - Integer value that indicates if a import file contains 
   * @param start - starting position for the current list of terms in 
   * javaFinalParser object
   * @param end - end position for the current list of terms in 
   * javaFinalParser object
   * @return - List of docQueryStruct object
   */ 
  public static List<docQueryStruct> buildBagOfDocsI(List<javaFinalParser> word,
      List<docQueryStruct> query, int qQuery, int dataS, int start, int end ){
      List<docQueryStruct> docData = new ArrayList();
      List<javaFinalParser> wordX = new ArrayList();
      List<javaFinalParser> wordZ = new ArrayList();
 
      int[] sizeWord = new int[2];
      sizeWord[0] = 1000000000;
      sizeWord[1] = 0;      
      StringBuilder qW = new StringBuilder();  
      StringBuilder qW2 = new StringBuilder(); 
      
      docQueryStruct  wordQ = docSearch(query,qQuery,0);
      

       
        if ( wordQ.getDocMapStructTFIDF() != null ){
               wordQ.getDocMapStructTFIDF().
                  forEach((k,v)->qW2.append(k).
                          append("|"));
          }
        
        qW2.deleteCharAt(qW2.length()-1);
      
      
        for(String dW :  qW2.toString().split("\\|")){
          if( sizeWord[0] > dW.length() ){
              sizeWord[0] = dW.length();
          }
          if( sizeWord[1] < dW.length() ){
              sizeWord[1] = dW.length();
          }
      }
        
        if ( wordQ.getDocMapStructTFIDF() != null ){
               HashMap<String,Double> hmW = wordQ.getDocMapStructTFIDF();
               for (Map.Entry mW : hmW.entrySet()) {
                  qW.append(mW.getKey().toString().substring(0,sizeWord[0])).
                          append("|");
               }
          }  
        
        qW.deleteCharAt(qW.length()-1);
        
       for(javaFinalParser wordD1 : word){ 
          if (wordD1.getWordName().replaceAll("\\s+", "").
                  length() >= sizeWord[0] && wordD1.getWordName().
                          replaceAll("\\s+", "").length() <= sizeWord[1]){ 
          if(wordD1.getWordName().substring(0, sizeWord[0]).matches(qW.toString())){
        
              wordX.add(wordD1);
          }
          }
      }
         for(javaFinalParser wordD1 : wordX){ 
          if(wordD1.getWordName().substring(0, sizeWord[0]).
                  matches(qW2.toString())){
              wordZ.add(wordD1);
          }
      }
      
      
      for(javaFinalParser wordD : wordZ ){
        if ( wordQ.getDocMapStruct() != null &&
                wordQ.getDocIDInt() == queryNumData[qQuery][0]){
            if (debugType.equalsIgnoreCase("Debug5")){
              System.out.printf("Yes we found the query %d %s %n",
                    wordQ.getDocIDInt(),wordD.getWordName());
            }
         if (wordD.getWordName().matches(qW2.toString()) == true){
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
      
      System.out.printf("The query %d had results %d using %s start %d "
              + " end %d %n ", qQuery,  docData.size(),qW2, start, end); 
     wordX.clear();
      double sumSQRS, prodW;
      if ( ! docData.isEmpty() ){
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
      
       for(docQueryStruct wordF : docData){
          sumSQRS = 0;
             if ( wordF.getDocMapStruct() != null ){
               HashMap<String,Integer> hm = wordF.getDocMapStruct();
                for (Map.Entry m:hm.entrySet()){
                   prodW = Integer.parseInt(m.getValue().toString());
                   sumSQRS = sumSQRS  +  (prodW * prodW);
                }
          }
             wordF.setSumSquaresNoTFIDF(sumSQRS);
             wordF.setDocLengthNoTFIDF(Math.sqrt(sumSQRS)); 
      }

      Collections.sort(docData, docQueryStruct.docIDComparator);
      }
      return docData;
  }
  
  /**
   * Function that products a list of queries along with a bag of terms for each
   * query
   * @param query - List of javaFinalParser objects
   * @param size - Total number of queries to include in the bag
   * @return - List of docQueryStruct objects
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
   * Function that products a query along with a bag of terms the
   * query
   * @param query - List of javaFinalParser objects
   * @param qNum - query id number
   * @return - List of docQueryStruct objects
   */
  public static List<docQueryStruct> buildBag( List<javaFinalParser>  query, 
          int qNum)
  {
      List<docQueryStruct> queryData = new ArrayList();
      double sumSqu = 0.0, sumSquNoTFIDF = 0;
               
              docQueryStruct newQuery = new docQueryStruct();
                newQuery.setDocIDInt(queryNumData[qNum][0]);
                newQuery.setDocID(Integer.
                        toString(queryNumData[qNum][0]));
                for(javaFinalParser wordD : query){
                    sumSqu = 0.0;
                     sumSquNoTFIDF = 0; 
                    for(documentID1 docCT : wordD.
                            getDocListStruct()){
                       if(docCT.getDocID().compareTo(newQuery.
                               getDocID()) == 0 ){
                           newQuery.addDocMapStruct
                                  (wordD.getWordName(), 
                                     docCT.getTermCount());
                                  sumSquNoTFIDF = sumSquNoTFIDF + 
                                          (docCT.getTermCount() *
                                          docCT.getTermCount() );
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
                newQuery.setSumSquaresNoTFIDF(sumSquNoTFIDF);
                newQuery.setDocLength(Math.sqrt(sumSqu));
                newQuery.setDocLengthNoTFIDF(Math.sqrt(sumSquNoTFIDF));
                queryData.add(newQuery);


      return queryData;
  }
  /**
   * The following function will search the dictionary based on the provided 
   * word id and return the corresponding JavaParser object
   * @param word - List of JavaParser objects
   * @param selection - integer value of word id
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged  
   * @return - JavaParser object
   */
  public static JavaParser wordIDSearch( List<JavaParser>  word, 
            int selection, int dataS )
  {

        int convertedN;
          
          switch(dataS){
          case 2 :    
          case 3 :  convertedN = selection;
                             break;               
          case 0 :                  
          case 1 :   convertedN = queryNumData[selection][0];
                             break;
          default  :     convertedN = selection;              
      }
      
        JavaParser validateCheck = new JavaParser();
        
        for(JavaParser data : word)
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
   * The following function will search the dictionary based on the provided 
   * word id and return the corresponding JavaParser object
   * @param word - List of JavaParser objects
   * @param selection - integer value of word id
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged  
   * @return - JavaParser object
   */
  public static docQueryStruct wordIDSearchQ( List<docQueryStruct>  word, 
            int selection, int dataS )
  {

        int convertedN;
          
          switch(dataS){
          case 2 :    
          case 3 :  convertedN = selection;
                             break;               
          case 0 :                  
          case 1 :   convertedN = queryNumData[selection][0];
                             break;
          default  :     convertedN = selection;              
      }
      
        docQueryStruct validateCheck = new docQueryStruct();
        
        for(docQueryStruct data : word)
          {
            if( data.getDocIDInt() == (convertedN))
            {
               validateCheck = data; 
               break;
             }            
          }     
        return validateCheck;
    }
  /**
   * Function that executes the concurrent processing of the corpus and query
   * files. This function will also serialize the data once extracted. The 
   * function determines the number of workers to generate based on the split
   * number assigned on command line as argument 3. If the totalCT / splitNumber[0]
   * is greater than 1000, then the total number of workers is assigned as 
   * the splitNumber[0]. Note : If your system does not have 16GB of memory, it is 
   * highly suggested to ensure a low number is entered. 
   * @param args - command line arguments
   * @param dataS - Integer value that indicates if a import file contains 
   * queries or documents and also if Stem was flagged 
   * @param fileDetails - file name details to hold serialize content
   * @param stemF - boolean value that indicates if stemming should be applied 
   * @return - List of JavaParser objects
   */
    public static List<JavaParser>  preProcessing(String[] args, int dataS, 
            String fileDetails, boolean stemF){
      
      List<JavaParser>  currentList = new ArrayList<JavaParser>(); 
      List<JavaParser>  newList; 
          
      int totalCT = mainFileLoaderCT(args,dataS); 
      
      int currentCT;
      if (totalCT / splitNumber[0] >= 1000){
            currentCT = totalCT / splitNumber[0];
        }else{
            currentCT =  totalCT; 
      }
      int start = 1,  end = currentCT;
      String[] fileName = new String[totalCT/currentCT];
      processTimeData("preProcessing","started");     
      
      boolean fileStatus = true; 
      File f;
      for(int i = 0; i < totalCT/currentCT; i++){
           fileName[i] = dictFileList[0] + fileDetails + i + ".txt";
           f = new File(fileName[i]);
           if ( ! f.exists() ) { 
             fileStatus = false;
             if (debugType.equalsIgnoreCase("Debug1")){
             System.out.println("The following file was not found "
                     + fileName[i] + " " + fileStatus);
             }
          }
      }
      
          
      if ( fileStatus == false ){
            ExecutorService executor = Executors.
                      newFixedThreadPool((totalCT/currentCT) + 1);

            for(int i = 0; i < totalCT/currentCT; i++){
                  fileName[i] = dictFileList[0] + fileDetails + i + ".txt";
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
            newList.clear();
        }
        
        if (debugType.equalsIgnoreCase("Debug1")){
           printWordDetails(currentList);
        }
        processTimeData("preProcessing","ended");     
      
        return currentList;
        
    }
    
     /**
   * Function that executes the concurrent processing of the corpus and query
   * files. This function will also serialize the data once extracted. The 
   * function determines the number of workers to generate based on the split
   * number assigned on command line as argument 3. If the totalCT is total 
   * number of workers is assigned based. However, the splitNumber[0] will be the 
   * max per session of workers. The number of sessions is determine by the 
   * total number of queries / splitNumber[0] 
   * Note : If your system does not have 16GB of memory, it is 
   * highly suggested to ensure a low number is entered. 
   * @param queryList - List of docQueryStruct objects
   * @param currentList - List of javaFinalParser objects
   * @param fileDetails - file name details to hold serialize content
   * @param stemF - boolean value that indicates if stemming should be applied
   * @param queryDataS
   */
    public static void  postProcessing(List<docQueryStruct> queryList, 
            List<javaFinalParser> currentList, boolean stemF, 
            String fileDetails, int[] queryDataS ){ 
     
      List<docQueryStruct>  newList; 
      processTimeData("postProcessing","started");     
      int totalCT = queryList.size(); 
      String[] outPutFile = new String[4];
      int start = 1,  end;
      String[] fileName = new String[totalCT+1];
      boolean fileStatus = true; 
      File f;
      if (totalCT > splitNumber[1]){
          end = splitNumber[1];
      }else{
          end = totalCT;
      }
        for(int i = 1; i <= totalCT; i++){
             fileName[i] = dictFileList[0] + fileDetails + i + ".txt";
             f = new File(fileName[i]);
             if ( ! f.exists() ) { 
               fileStatus = false;
               if (debugType.equalsIgnoreCase("Debug1")){
               System.out.println("The following file was not found "
                       + fileName[i] + " " + fileStatus);
               }
            }
        }
          if ( fileStatus == false || debugType.equalsIgnoreCase("RUN2")){
            ExecutorService executorQ;             
            for (int z = 0; z < (totalCT/splitNumber[1]) + 1; z++){
                executorQ = Executors.
                      newFixedThreadPool((splitNumber[1]) * 2);
             for(int i = start; i <= end; i++){
                  fileName[i] = dictFileList[0] + fileDetails + i + ".txt";
                  Runnable workerQ = new bagBuilderRunner(currentList,
                          queryList,i,fileName[i],queryDataS);
                  executorQ.execute(workerQ);   
              }
              executorQ.shutdown();
             
              while (!executorQ.isTerminated()) {

              }
              
              start = end + 1;
              end = end + splitNumber[1];
              
              if(end > totalCT){
                 end = totalCT; 
              }
              
            }
        }
          
         if (stemF == false ){
             outPutFile[0] = "Leslie-aDotS.txt";
             outPutFile[1] = "Leslie-aSimS.txt";
             
         }else{
             outPutFile[0] = "Leslie-aDotSS.txt";
             outPutFile[1] = "Leslie-aSimSS.txt";
             
         }  
        
        for(int i = 1; i <= totalCT; i++){
            newList = deserializeQDictionary(fileName[i]);
            writeFDocIDDOTPRODUT(i,newList, dictFileList[0]  + 
              outPutFile[0],true);
            writeFDocIDSimPRODUT(i,newList, dictFileList[0]  + 
             outPutFile[1],true);
            newList.clear();
        }
        processTimeData("postProcessing","ended"); 
    }
    /**
     * Function that prints start & stop messages
     * @param processN - String name of process running
     * @param state - String state (Start or End)
     */
    public static void processTimeData(String processN, String state){
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%s %s %s %n",processN,state,
                timeStampF.format(now));
    }
    /**
     * Function that check for the existing of specified app files
     * @param queryFile - file id reference
     * @param typeC - process type
     * @return - boolean value of existing of file
     */
    public static boolean checkFileExist(int queryFile, String typeC){
      String type = "";
      File f;
      switch(typeC){
         case "I"    :   switch(queryFile){
                            case 1 : type = dictFileList[2];
                                           break;
                            case 3 : type = dictFileList[1];
                                           break;
                            case 0 : type = dictFileList[6];
                                           break;
                            case 2 : type = dictFileList[5];
                                           break;               
                            default : type = dictFileList[1];
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
                                         }
                       break;
       case "C" :  switch(queryFile){
                        case 1 : type = dictFileList[4];
                                       break;
                        case 3 : type = dictFileList[3];
                                       break;
                        case 0 : type = dictFileList[8];
                                       break;
                        case 2 : type = dictFileList[7];
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
   
                        default :  type = dictFileList[3];
                                  break;
                    }
                   break;
       case "Q" :  type = dictFileList[13];
                   break;
      }   
      
        f = new File(type);
        return f.exists();
    }
          
            
    /**
     * Procedure that execute preProcessing to read from the corpus or query 
     * files and serialize the data
     * @param args - command line arguments 
     * @param stemF - boolean value that indicates if stemming should be applied 
     */
    public static void processData(String[] args, boolean stemF, int[] 
            queryDataS){
        List<JavaParser>  currentListM = new ArrayList<JavaParser>();
        List<JavaParser>  currentQueryM = new ArrayList<JavaParser>();
        String[] nameFType = new String[2];
        processTimeData("processData","started");
        
          if(stemF == false){
              nameFType[0] = "dictionaryQ";
              nameFType[1] = "dictionaryF";
          }else{
              nameFType[0] = "dictionaryQS";
              nameFType[1] = "dictionaryFS";
          }
          
 
          if ( ! checkFileExist(queryDataS[0],"I")){
            currentQueryM = preProcessing(args,queryDataS[0],nameFType[0],stemF);
            serializeFDictionary(currentQueryM,queryDataS[0]);
            currentQueryM.clear();
          }else{
              serializedFCount(queryDataS[0]);
          }
           if ( ! checkFileExist(queryDataS[1],"I")){
            currentListM = preProcessing(args,queryDataS[1], nameFType[1],stemF);
            serializeFDictionary(currentListM,queryDataS[1]);
            currentListM.clear();
           }else{
               serializedFCount(queryDataS[1]);
           }
          processTimeData("processData","ended"); 
    }
    /**
     * Function that completes processing after serialization of the corpus and
     * query files. This function will execute the main requirements for the 
     * assignment. 
     * @param args - command line arguments
     * @param stemF - boolean value that indicates if stemming should be applied 
     * @return  
     */
    public static int[] postProcessData(String[] args, boolean stemF){
     
        List<docQueryStruct> newQueryData = new ArrayList<docQueryStruct>();
        List<docQueryStruct> newDocData = new ArrayList<docQueryStruct>();
        List<javaFinalParser> updateQuery1, updateList;
        
        int[] queryDataS = new int[2];
        int qQuery = 1; 
        String[] outPutFile = new String[2];
        processTimeData("postProcessData","started");
        if ( stemF == false){
            queryDataS[0] =  1;
            queryDataS[1] =  3;
        }else{
            queryDataS[0] =  0;
            queryDataS[1] =  2; 
        }
        
        if (debugType.equalsIgnoreCase("Run") ||
                debugType.equalsIgnoreCase("TEST")){
            processData(args,stemF,queryDataS);
        }
        
        int totalQY = mainFileLoaderCT(args,queryDataS[0]);      
        int totalCT = mainFileLoaderCT(args,queryDataS[1]);
        
        
         if ( checkFileExist(queryDataS[1],"C") == false ) {
                List<JavaParser> testcurrentList = 
                deserializeDictionary(queryDataS[1]); 
                if (debugType.equalsIgnoreCase("Debug1")){
                printDocIDWordDetails(testcurrentList);
                }

                updateList = conversionDictionary(testcurrentList,queryDataS[1]);
                testcurrentList.clear();
                serializeFinalDictionary(updateList,queryDataS[1]);
           }else{
               updateList = deserialize2Dictionary(queryDataS[1]);
           }

            if ( checkFileExist(queryDataS[0],"C") == false ) {
               List<JavaParser> testcurrentQuery = 
                deserializeDictionary(queryDataS[0]);
               if (debugType.equalsIgnoreCase("Debug1")){
               printDocIDWordDetails(testcurrentQuery);
               } 
             updateQuery1 =  conversionDictionary(testcurrentQuery,queryDataS[0]);
             testcurrentQuery.clear();
             serializeFinalDictionary(updateQuery1,queryDataS[0]); 
            }else{
                updateQuery1 = deserialize2Dictionary(queryDataS[0]);
           }

         if (debugType.equalsIgnoreCase("Debug2")){
            printDocID2FWordDetails(updateQuery1,queryDataS[0]);
            printDocID2FWordDetails(updateList,queryDataS[1]);
         }
         
         if ( checkFileExist(queryDataS[0],"Q") == false ) {
            newQueryData = buildBags(updateQuery1,totalQY);
            serializeQueryRun(newQueryData,dictFileList[13]); 
         }else{
           newQueryData = deserializeQDictionary(dictFileList[13]);  
         }
         
         newDocData = buildBagOfDocs(updateList,newQueryData,qQuery,
                 queryDataS[1]);
          if (debugType.equalsIgnoreCase("Debug2A")){
                for(int z = 1; z <= newQueryData.size(); z++){
                   displayBag(newQueryData,z,"Query");
                }

                for(int z = 1; z <= newDocData.size(); z++){
                   displayBag(newDocData,z,"Documents");
                }

                for(int z = 1; z <= newQueryData.size(); z++){
                   displayBagTFIDF(newQueryData,z,"Query TFIDF");
                }
          }
          
         displayBagTFIDF2(newQueryData,qQuery,"Query TFIDF");
         
          if (debugType.equalsIgnoreCase("Debug2C")){
                for(int z = 1; z <= newDocData.size(); z++){
                   displayBagTFIDF(newDocData,z,"Documents TFIDF");
                }
          }
          
         dotProduct(newQueryData,newDocData,qQuery,
                 queryDataS[0]);
         dotProduct(newQueryData,newQueryData,totalQY,
                 queryDataS[0]);
         
         simProduct(newDocData,newQueryData,qQuery);
         
         System.out.println();
         
         if (stemF == false ){
             outPutFile[0] = "Leslie-aDot.txt";
             outPutFile[1] = "Leslie-aSim.txt";
             
         }else{
             outPutFile[0] = "Leslie-aDotS.txt";
             outPutFile[1] = "Leslie-aSimS.txt";
             
         }
         displayDocIDDOTPRODUT(newDocData,qQuery);
         writeFDocIDDOTPRODUT(qQuery,newDocData,dictFileList[0]  + 
                 outPutFile[0],false);
         
         System.out.println();
         
         displayDocIDSIMPRODUT(newDocData,qQuery);
         writeFDocIDSimPRODUT(qQuery,newDocData,dictFileList[0]  + 
                 outPutFile[1],false);
         
         System.out.println();
      if (debugType.equalsIgnoreCase("Debug2C")){  
       boolean fileStatus; 
       for(int z = 1; z <= newQueryData.size(); z++){
         fileStatus = z != 1;
         newDocData = buildBagOfDocs(updateList,newQueryData,z,
                 queryDataS[1]);
          dotProduct(newQueryData,newDocData,z,
                 queryDataS[0]);
          dotProduct(newQueryData,newQueryData,z,
                 queryDataS[0]);
          
           simProduct(newDocData,newQueryData,z);
           
           if (stemF == false ){
             outPutFile[0] = "Leslie-aDotA.txt";
             outPutFile[1] = "Leslie-aSimA.txt";
             
         }else{
             outPutFile[0] = "Leslie-aDotSA.txt";
             outPutFile[1] = "Leslie-aSimSA.txt";
             
           }
         
         displayDocIDDOTPRODUT(newDocData,z);
         writeFDocIDDOTPRODUT(z,newDocData, dictFileList[0]  + 
                 outPutFile[0],fileStatus );
         
         System.out.println();
         
         displayDocIDSIMPRODUT(newDocData,z);
         writeFDocIDSimPRODUT(z,newDocData, dictFileList[0]  + 
                 outPutFile[1],fileStatus );
           
         }
      }
         postProcessing(newQueryData,updateList,stemF,"ResultsQ",
                 queryDataS);

         
         updateList.clear();
         updateQuery1.clear();
         processTimeData("postProcessData","ended"); 
         return queryDataS;
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
           int[] queryDataS; 
        
        queryDataS = postProcessData(args,stemF);
        
         List<javaFinalParser> updateList2 = 
                 deserialize2Dictionary(queryDataS[1]);
         List<javaFinalParser> updateQuery2 = 
                 deserialize2Dictionary(queryDataS[0]);
         printWordDetailsF(updateList2);
         printWordDetailsF(updateQuery2);
    }
  
}

