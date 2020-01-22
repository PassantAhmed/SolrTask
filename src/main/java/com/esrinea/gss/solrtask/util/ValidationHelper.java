/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esrinea.gss.solrtask.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author passant.swelum
 */
public class ValidationHelper {

    private final static String QUERY_REGEX = "[\\-\\_\\d\\*A-Za-z]+";
    
    private ValidationHelper(){}
    
    public static boolean checkString(String string){
        return ( !(string==null && string.trim().equals("")) );
    }
    
    private static boolean isValidQueryInput(String queryTxt) {
        Pattern pattern = Pattern.compile(QUERY_REGEX);
        Matcher matcher = pattern.matcher(queryTxt);
        return matcher.matches();
    }
    
     public static boolean isValidQuery(String queryTxt){
        boolean result = false;
        if(queryTxt!=null && isValidQueryInput(queryTxt)){
            result = true;
        }
        return result;
    }
    
}
