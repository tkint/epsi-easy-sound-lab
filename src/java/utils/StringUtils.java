/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author tkint
 */
public class StringUtils {
    
    public static String toUpperFirst(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    
}
