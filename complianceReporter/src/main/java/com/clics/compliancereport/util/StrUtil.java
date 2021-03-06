package com.clics.compliancereport.util;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class StrUtil {
    private StrUtil(){}

    public static Object[] tokenize(final String str, final String delimiter) {
        List<String> list = new ArrayList<String>();

        //check if params are valid
        if(StringUtils.isEmpty(str) ||
           StringUtils.isEmpty(delimiter)) {
           return null;
        }
        //tokenize based on delimiter
        String[] tokens = str.split("["+delimiter+"]");

        if(tokens != null && tokens.length != 0){
            for (String s : tokens) {
                list.add(s);
            }
        }
        return (!list.isEmpty())? list.toArray(): null;
    }


    public static String removeSpaces(final String str) {
        return str.replaceAll("\\s","");
    }
}
