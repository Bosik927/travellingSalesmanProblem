package com.github.bosik927.sequential;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stanisław Fiuta
 * Date: 9/18/2018
 * Time: 9:21 PM
 */
public class StringFindAllPermutation {
    public Set<String> permutationFinder(String str) {
        Set<String> perm = new HashSet<String>();

        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            perm.add("");
            return perm;
        }
        char initial = str.charAt(0);
        String rem = str.substring(1);
        Set<String> words = permutationFinder(rem);
        for (String strNew : words) {
            for (int i = 0; i <= strNew.length(); i++) {
                perm.add(charInsert(strNew, initial, i));
            }
        }
        return perm;
    }

    public static String charInsert(String str, char c, int j) {
        String begin = str.substring(0, j);
        String end = str.substring(j);
        return begin + c + end;
    }
}

