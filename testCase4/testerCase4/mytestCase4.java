package testerCase4;

import java.util.*;
import java.net.*;
import java.sql.*;

public class mytestCase4 {
	
    public static void quickSort(Integer[] arr, int low, int high) {
    	if((arr == null) || (arr.length == 0)) return;
    	if(low >= high ) return;
    	int middle = low + (high - low)/2;
    	int pivot = arr[middle];
    	
    	int i = low, j = high;
    	
    	while(i <= j) {
    		
    		while(arr[i] < pivot) {
    			i++;
    		}
    		
    		while(arr[j] > pivot) {
    			j--;
    		}
    		
    		if ( i <= j) {
    			swap(arr,i,j);
    			i++;
    			j--;
    		}
    		if( low < j) {
    			quickSort(arr, low, j);
    		}
    		if( high > i) {
    			quickSort(arr, i, high);
    		}
    		
    	}
    	
    	
    }
    
    public static int charLent(int[] A) {       // write your code in Java SE 8
    if (A.length == 1) return A[0];
    if (A.length == 0) return 0;
     Arrays.sort(A);
     for (int i = 0; i < A.length - 1; i+=2) {
        if (A[i] != A[i + 1]){
         return A[i];
       }
     }

       return A[A.length - 1];
    }   
    
    
    public static void swap(Integer[] array, int x, int y) {
    	int temp = array[x];
    	array[x] = array[y];
    	array[y] = temp;
    	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
     Integer[][] array = {{12,13,23,1,90,70},{12,13,23,10,3,6,90,70},{0},{},{11,123}, {2,-1,34,224,23,2313}};
     
     for(Integer[] ar : array) {
    	Integer[] ar1 = new Integer[ar.length];
    	Integer[] ar2 = new Integer[ar.length];
    	
    	ar1 = ar.clone();
    	ar2 = ar.clone();
    	Arrays.parallelSort(ar2);
        quickSort(ar, 0, ar.length - 1);
        if (Arrays.toString(ar2).contains(Arrays.toString(ar)) == true ) {
            System.out.println("Test : " + Arrays.toString(ar1) + " " + Arrays.toString(ar) + " Sorted");
        }else {
        	System.out.println("Test : " + Arrays.toString(ar1) + " " + Arrays.toString(ar) + " Not Sorted");
            	
        }
     }
     
	}

}
