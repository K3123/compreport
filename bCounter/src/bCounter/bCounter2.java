package bCounter;

import java.util.*;

class Node {
	int number;
	 
	public Node() {}

	public Node(int i) {
		// TODO Auto-generated constructor stub
		this.number = i;
	};
   
}

class Solution {
    public int solution(int N) {
        // write your code in Java SE 8
    String nBin = Integer.toString(N, 2);
    Integer lBin = nBin.length();
    Integer nBMax = 0;
    Integer nBcounter = 0;
    Boolean gBin = false; 
    for(int x = 0; x < lBin; x++){
        if ( nBin.charAt(x) == '1' ){
            if(nBcounter > nBMax ){
                nBMax = nBcounter;
                nBcounter = 0;
                gBin = true;
            }else if( gBin){
                nBcounter+=1;
            }
        }
           
    }
    if (nBMax == 0){
        return -1;
    } 
      
    return nBMax; 
    
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
    System.out.println("Yes we were called");
    Node node40 = new Node(40);
    Node node10 = new Node(10);
    }
}