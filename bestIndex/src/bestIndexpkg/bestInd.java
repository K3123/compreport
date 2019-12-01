package bestIndexpkg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
 
public class bestInd {
 
    public static int counter(int[] arry, int st, int ed, int limit){
        int sum = 0;
        if ( st + ed < limit ){
            for(int x = st; x < (st + ed); x ++ ){
                sum+=arry[x];
                System.out.println(x + " " + st + " " + ed + " " + sum);
            }
        }
        return sum;
    }
    
        
    public static int counter2(int[] arry, int st, int ed, int limit){
        int sum = 0;
        System.out.print(" st " + st + " ed " + ed + " ");
        if ( st + ed <= limit - 1 ){
            for(int x = st; x < (st + ed); x ++ ){
                System.out.print( x + " " );
            }
        }
        return sum;
    }
    
       
        public static void main(String args[] ) throws Exception {
               //BufferedReader
               
               BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
               int T = Integer.parseInt(br.readLine());                // Reading input from STDIN
               String[] myword1 = br.readLine().split(" ");
               int[] myword  = new int[T]; 
              
               for(int x = 0; x < T; x++ ){
                   myword[x] = Integer.parseInt(myword1[x]);
               }
               
               int largest = 0;
               for(int x = 0; x < T; x++ ){
                   int sum1 = myword[x];
                   int array_values_sum = 2;
                   int position_array = x + 1;
                   while( position_array < T ){
                        sum1+=counter2(myword,position_array,array_values_sum, T);
                        System.out.println();
                        position_array = position_array + array_values_sum;
                        array_values_sum+=1;
                   }
                   if( largest < sum1){
                           largest = sum1;
                   } 
               }
               System.out.println(largest);
           }
 
}