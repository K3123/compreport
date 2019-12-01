/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author K3123
 */
public class nameList implements Comparator<nameList>, 
        Serializable{
    
    private int nameID;
    private String name;
    private String gender;
    private int year;
    private int nameCt = 0;

    public nameList() {
    }

    public nameList(int nameID, String name, String gender, int year) {
        this.nameID = nameID;
        this.name = name;
        this.gender = gender;
        this.year = year;
    }

    public int getNameCt() {
        return nameCt;
    }

    public void setNameCt(int nameCt) {
        this.nameCt = nameCt;
    }

    
    
    public int getNameID() {
        return nameID;
    }

    public void setNameID(int nameID) {
        this.nameID = nameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static Comparator<nameList> NameComparator = 
            new Comparator<nameList>() {

	public int compare(nameList s1, nameList s2) {
	   String word1 = s1.getName().toLowerCase();
	   String word2 = s2.getName().toLowerCase();

	   //ascending order
	   return word1.compareTo(word2);

    }};
    @Override
    public int compare(nameList o1, nameList o2) {
        return o1.nameID - o2.nameID; 
//To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
