package bracketsMissing;
import java.util.*;
import java.net.*;
import java.sql.*;
import java.util.Stack;

public class bracketSolution {
    public static char returnOpposite(char s1 ) {
    	switch(s1) {
    	case '>' : return '<';
    	case '<' : return '>';	
    	}
      return ' ';
    }
    
    public static String solution( String arrange ) {
    	int s1len = arrange.length();
    	long rightBraket = arrange.codePoints().filter(ch -> ch == '<').count();
    	long leftBraket = arrange.codePoints().filter(ch -> ch == '>').count();
    	
    	if( s1len == 0 ) return arrange;
    	if( rightBraket == 1  && s1len == 1 ) return "<>";
    	if(leftBraket == 1 && s1len == 1) return "<>";
    	Stack <Integer> s1 = new Stack<>();
    	Stack <Integer> s2 = new Stack<>();
    	
    	String newString = "";
    	for(int x = 0; x < s1len; x++ ) {
    		if ( arrange.charAt(x) == '<' ) {
    			s1.push((int) arrange.charAt(x));
    		    if(s2.empty() == true) {
    				newString = newString + returnOpposite(arrange.charAt(x));
    			}else {
        			char newValue = Character.forDigit(s2.pop(),10);
        			System.out.println(newValue);    				
    			}
    		}else if( arrange.charAt(x) == '>' ) {
    			s2.push((int) arrange.charAt(x));
    			if(s1.empty() == true) {
    				newString = returnOpposite(arrange.charAt(x)) + newString;
    			}else {
     				char newValue = Character.forDigit(s1.pop(),10);
     				System.out.println(newValue);
        			
    			}
    		}
    	}
   
    	return newString;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(solution("<") + " should be <>");
		System.out.println(solution(">") + " should be <>");
		System.out.println(solution("><<><") + " should be <><<><>>");
		System.out.println(solution("") + " should be <>");
		System.out.println(solution("<><") + " should be <><>");
		

	}

}
