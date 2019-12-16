package largestFourNumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class largestFNumbers {
    /**
     * Assignment
       Write a function that takes a list of integers and returns the 4 highest in O(n) time. 
       We'd like to see how you think about algorithms and data structures. 
       So please use your own logic instead of calling out to libraries.
     * @param arrayofintegers
     * @return
     */
    public static int[] highIntegers(int[] arrayofintegers ) {
    	int[] temparray = new int[4]; 								// Array to contain the return list of the 4 largest 
    															    // integers in the list
    	int arrayofintegerslength = arrayofintegers.length;			// The value that contains the length of the array
    	if ( arrayofintegerslength <= 4 ) return arrayofintegers;   // If the array is empty or size of array is less than 5
    	                                                            // then there are no values to provided or the array already 
    	                                                            // has 4 largest integers. An empty is returned if the size is 0.
    	temparray[0] = arrayofintegers[0];                          // The following is assigned, since by default the 
    	                                                            // array is set to all zeros. This will cause an 
    	                                                            // issue when negative integers. Assigning the first 
    	                                                            // value from the array will ensure that the 
    	                                                            // comparison is completed with the remaining elements
    		                                                        // in the array. 
    	
    	for(int x = 1; x < arrayofintegerslength; x++ ) {
    		if ( arrayofintegers[x] > temparray[0] ) {
    			temparray[3] = temparray[2];
    			temparray[2] = temparray[1];
    			temparray[1] = temparray[0];
    			temparray[0] = arrayofintegers[x];			
    		}
    		if ( arrayofintegers[x] < temparray[0] && arrayofintegers[x] > temparray[1] ) {
    			temparray[3] = temparray[2];
    			temparray[2] = temparray[1];
    			temparray[1] = arrayofintegers[x];
       		}
    		if ( arrayofintegers[x] < temparray[1] && arrayofintegers[x] > temparray[2] ) {
    			temparray[3] = temparray[2];
    			temparray[2] = arrayofintegers[x];
    		}
    		if ( arrayofintegers[x] < temparray[2] && arrayofintegers[x] > temparray[3] ) {
    			temparray[3] = arrayofintegers[x];
    		}
    	}
    	return temparray;
    }
	/**
	 * The following is the main program that executes different test cases for the defined requested function
	 * The main program will call the function and execute against multiple array lists.
	 * The program has also been designed to capture a list of integers from the console and execute the 
	 * same function. A list of comma separated integers must be enter at the console for the program to work as 
	 * required. To implement the test portion of the code, please pass debug as a parameter to the main program 
	 * via command line to execute debugging. 
	 * @param args
	 */
	public static void main(String[] args) {
		// The originalset contains the integer set of integers that will be tested to ensure the function 
		// executes the task as requested 
	   boolean debug = false;
	   if ( args.length > 0) debug = (args[0].contains("debug")) ? true : false;
	   if ( debug ) {
			   int[][] originalset = {{-17,1,2,3,4,5,-6},{-17,1,2,3,-4,5,-6},{-1,1,2,3,4,5,-6},
					                   {1,1,2,3,4,5},{6,8,0,10},{0,0,0,0,5},{},{0},
				                       {11,14,23,33,-10,24,54,66}, {100,124,4302,232,324,2442,-1,224434,4353,234}, 
				                       {-17,-23,-45,-9,-8,-11}, 
				                       {-1,-3,5,78,20,300,40,-50,1000,21000,100,124,4302,232,324,2442,-1,224434,4353,234}};
			   for (int[] testcase : originalset ) {
				   System.out.println("The following is the largest 4 numbers");
				   for(int valueinarray : highIntegers(testcase))  System.out.print(valueinarray + " ");
				   System.out.println();
			   }
	  }else {
		  System.out.println("Please enter a list of integers separated by commas to determine the 4 largest values?");
		  System.out.println(" ");
		  
		  try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ) {
				  String[] myIntegers = br.readLine().replace(" ","").split(",");
				  int myIntegersLength = myIntegers.length;
				  int[] myIntegersForFunction  = new int[myIntegersLength]; 		  
				  for(int x = 0; x < myIntegersLength; x++ ){
				     myIntegersForFunction[x] = Integer.parseInt(myIntegers[x]);
		          }
	  		     for(int valueinarray : highIntegers(myIntegersForFunction))  System.out.print(valueinarray + " ");
		  } catch (IOException e) {
			   System.out.println("Something is wrong with the values entered. Please check the integer(s) and try again.");
		  } catch (NumberFormatException e) { 
			   System.out.println("The values entered are not all integers separated by commas. Please try again.");
		  }
	  }
	}
}
