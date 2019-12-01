package testingCode;

import java.util.*;
import java.sql.*;
import java.net.*;


public class myWork {

	public static int lengthOfLongestSubstring(String s) {
	     int scount = s.length(); 
	     int Arry[] = new int[255];
	     String st = "";
	     int largest = 0; 
	       
	        for(int x = 0, y = 0; x < scount; x++){ 
	            if(st.contains(s.charAt(x)+"")){
	             if( Arry[s.charAt(x)] + 1 > y ) {
	            	 y = Arry[s.charAt(x)] + 1;
	             }
	            }
	             if( largest < x - y + 1 ) {
	            	 largest = x - y + 1;
	             } 	 
	             st = st + s.charAt(x);
	             Arry[s.charAt(x)] = x;
	             
	         }
	       return largest;  
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(lengthOfLongestSubstring("pwwkew"));
		
	}

}
