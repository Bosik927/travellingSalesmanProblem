package com.github.bosik927.random;

import java.util.Random;

/**
 * @author Stanisław Fiuta
 * Date: 9/18/2018
 * Time: 8:41 PM
 */
public class RandomGraphGenerator {

    private int size;
    private int graph[][];
    private static int LOW = 10;
    private static int HIGH = 100;

    public RandomGraphGenerator(int size) {
        this.size = size;
    }

    public int [][] getGraph(){
        return graph;
    }

    public void displayGraph(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                System.out.print(graph[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public void generateGraph() {
        if(size <=0) throw new IllegalArgumentException("Size cannot be less than or equal to 0!");

        int[][] graph = new int[size][size];
        createFirstHalfGraph(graph);
        fillSecondHalfGraph(graph);
        this.graph= graph;
    }

    private void createFirstHalfGraph(int[][] graph) {
        Random r = new Random();
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < i; j++) {
                graph[i][j] = r.nextInt(HIGH - LOW) + LOW;
            }
        }
    }

    private void fillSecondHalfGraph(int[][] graph) {
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > i; j--) {
                graph[i][j] = graph[j][i];
            }
        }
    }
}