package largestFourNumbers;

public class largestFNumbers {
	/**
	 * Assignment
       Write a function that takes a list of integers and returns the 4 highest in O(n) time. 
       We'd like to see how you think about algorithms and data structures. 
       So please use your own logic instead of calling out to libraries.
	 * @param args
	 */
    public static int[] highIntegers(int[] arrayofintegers ) {
    	int[] temparray = new int[4]; 								// Array to contain the return list of the 4 largest integers in the list
    	int arrayofintegerslength = arrayofintegers.length;			// The value that contains the length of the array
    	if ( arrayofintegerslength == 0 ) return arrayofintegers;   // If the array is empty then there is no values to provided. An empty is returned
    	if ( arrayofintegerslength == 1 ) return arrayofintegers;   // If the array has only one element, then that array is returned. 
    	temparray[0] = arrayofintegers[0];                          // The following is assigned, since by default the array is set to all zeros. This 
    	                                                            // will cause the issue when negative integers. Assigning the first value from the 
    																// array will ensure that the comparison is completed with the remaining elements
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	}

}
