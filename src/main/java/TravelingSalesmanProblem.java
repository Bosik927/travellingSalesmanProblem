import com.github.bosik927.concurrent.ConcurentChecker;
import com.github.bosik927.sequential.StringFindAllPermutation;
import com.github.bosik927.random.RandomGraphGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Stanisław Fiuta
 * Date: 9/18/2018
 * Time: 8:21 PM
 */
public class TravelingSalesmanProblem {

    static int size = 10;
    static int [][] generatedGraph;

    static public void main(String[] a) throws InterruptedException {

        /*Graph creator*/
        RandomGraphGenerator randomGraphGenerator = new RandomGraphGenerator(size);
        randomGraphGenerator.generateGraph();
        randomGraphGenerator.displayGraph();

        /*Generated graph*/
        generatedGraph = randomGraphGenerator.getGraph();

        long senquentialTime = sequentialCountering();
        long concurrentTime =concurrentCountering();

        System.out.println("\n Sequential time :"+senquentialTime);
        System.out.println("\n Concurrent time :"+concurrentTime);
    }

    static long concurrentCountering() throws InterruptedException {
        long startTime = System.nanoTime();

        String firstPermutation = createFirstPermutation(size);

        List<Thread> threads = new ArrayList<>(size);

        ConcurentChecker.theBestWay = 0;

        for(int i=0; i <size; i++){
            Thread thread = new Thread(new ConcurentChecker(size,i,generatedGraph));
            thread.start();
            threads.add(thread);
        }


        for (Thread thre : threads) {
            thre.join();
        }

        long endTime = System.nanoTime()-startTime;

        System.out.println("All threads finished in time :" + endTime + " with the best way = " + ConcurentChecker.theBestWay);
        return endTime;
    }

    static long sequentialCountering(){
        long startTime = System.nanoTime();

        String firstPermutation = createFirstPermutation(size);
        Set<String> permutation = generateAllPermutation(size, firstPermutation);

        System.out.println(findTheBestWay(permutation,generatedGraph));

        long endTime = System.nanoTime() - startTime;

        System.out.println("Sequential total time: " + endTime + " nanoseconds");

        return endTime;
    }

    static int findTheBestWay( Set<String> permutation,int graph[][]) {
        int theBestWay=0;
        String theBestPermutation = "";

        Iterator iter = permutation.iterator();
        while (iter.hasNext()) {
            int actualWay = 0;
            String actualPermutation = iter.next().toString();

            for (int i = 0; i < actualPermutation.length()-1; i++){
                actualWay+=graph[i][i+1];
            }
            if(actualWay> theBestWay){
                theBestWay=actualWay;
            theBestPermutation = actualPermutation;
            }
        }
        System.out.println("The best permutation:");
        System.out.println(theBestPermutation);
        return theBestWay;
    }

    static String createFirstPermutation(int size){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<size;i++){
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    static Set<String> generateAllPermutation(int size, String firstPermutation){
        StringFindAllPermutation stringFindAllPermutation = new StringFindAllPermutation();

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<size;i++){
            stringBuilder.append(i);
        }

        return stringFindAllPermutation.permutationFinder(stringBuilder.toString());
    }
}
