package com.github.bosik927.concurrent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Stanisław Fiuta
 * Date: 9/18/2018
 * Time: 10:33 PM
 */
public class ConcurentChecker implements Runnable{
    public static int theBestWay;

    private int myNumber;
    private int size;
    private int [][] graph;

    public ConcurentChecker(int size, int myNumber, int [][] graph){
        this.myNumber = myNumber;
        this.size = size;
        this.graph = graph;
    }

    synchronized void setTheBestWay(int theBestWay){
        ConcurentChecker.theBestWay = theBestWay;
    }

    @Override
    public void run() {
        String myFirstPermutation = createFirstPermutation();
        Set<String> allPermutation = generateAllPermutation(myFirstPermutation);

        int theBestWay = findTheBestWay(allPermutation,graph);
        if(theBestWay<ConcurentChecker.theBestWay || ConcurentChecker.theBestWay==0){
            setTheBestWay(theBestWay);
        }

        System.out.println("Thread " + myNumber + " finished");
    }

    int findTheBestWay( Set<String> permutation,int graph[][]) {
        int theBestWay=0;

        Iterator iter = permutation.iterator();
        while (iter.hasNext()) {
            int actualWay = 0;
            String actualPermutation =  myNumber + iter.next().toString();

            for (int i = 0; i < actualPermutation.length()-1; i++){
                actualWay+=graph[i][i+1];
            }
            if(actualWay> theBestWay ){
                theBestWay=actualWay;
            }
        }
        return theBestWay;
    }

    private Set<String> generateAllPermutation(String firstPermutation){
        return permutationFinder(firstPermutation);
    }

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

    private String createFirstPermutation(){
        StringBuilder myFirstPermutation = new StringBuilder();
        for(int i=0; i<size; i++){
            if(i==myNumber) continue;

            myFirstPermutation.append(i);
        }

        return myFirstPermutation.toString();
    }
}
