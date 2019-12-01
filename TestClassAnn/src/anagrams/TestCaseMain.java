package anagrams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
 
public class TestCaseMain {

      
       public static int check(String a1, String b1){

               int fct = a1.length();

               int sct = b1.length();

               int largest = Math.max(fct, sct);

               int count = 0;

               String found = "";

 
               for( int y = 0; y < largest; y++ ){

                    if ( y < fct ) {

                         final char a2 = a1.charAt(y);

                         if ( b1.chars().filter(ch -> ch == a2).count() == 0 ){

                             count+=1;

                         }

                    }

                    if ( y < sct ) {

                         final char a3 = b1.charAt(y);

                         if ( a1.chars().filter(ch -> ch == a3).count() == 0 ){

                             count+=1;

                         }

                      long count1 = b1.chars().filter(ch -> ch == a3).count();

                     long count2 = a1.chars().filter(ch -> ch == a3).count();

                     long count3 = found.chars().filter(ch -> ch == a3).count();

                     if ( count1 > 0 && count2 > 0 && count3 == 0 ){

                           count+=Math.abs(count1 - count2);

                           found = found + a3;

                     }

                    }

            

               }

           return count;

          

          }

 

       public static int check2(String a1, String b1){

               int fct = a1.length();

               int sct = b1.length();

               int largest = Math.max(fct, sct);

               int count = 0;

               String found = "";

 

               for( int y = 0; y < largest; y++ ){

                    if ( y < fct ) {

                         final char a2 = a1.charAt(y);

                         if ( b1.contains(a2+"") == false ){

                             count+=1;

                         }

                    }

                    if ( y < sct ) {

                         final char a3 = b1.charAt(y);

                         if ( a1.contains(a3+"") == false ){

                             count+=1;

                         }

                      long count1 = b1.chars().filter(ch -> ch == a3).count();

                     long count2 = a1.chars().filter(ch -> ch == a3).count();

                     long count3 = found.chars().filter(ch -> ch == a3).count();

                     if ( count1 > 0 && count2 > 0 && count3 == 0 ){

                           count+=Math.abs(count1 - count2);

                           found = found + a3;

                     }

                    }

            

               }

           return count;

          

          }

 

      

        public static int check3(String a1, String b1){

            int count = 0;

            int[] cha = new int[26];

            int[] chb = new int[26];

             for( char c : a1.toCharArray()) {

                    cha[((int) c) - 97 ]++;

             }

 

             for( char c : b1.toCharArray()) {

                    chb[((int) c) -97]++;

             }

 

               for( int y = 0; y < 26; y++ ){

             count = count + Math.abs(cha[y] - chb[y]);

               }

           return count;

          

          }

      

       public static void main(String[] args) throws Exception {

             // TODO Auto-generated method stub

            

           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int count = Integer.parseInt(br.readLine());  

        for(int x = 0; x < count; x++ ){

            System.out.println(check3(br.readLine(), br.readLine()));

        }

 

           br.close();

            

            

 

       }

 

}

 
