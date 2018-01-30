/**
 * Created by Huy Vu on 10/17/2017.
 */
import java.util.Random;

/**
 * A class to provide the test for
 * the runtime of Cuckoo Hashing
 *
 * @author Huy Vu
 * @version October 30 2017
 */
public class CuckooHashTester{

    /**
     * A static method to perform runtime
     * analysis of Cuckoo Hashing
     *
     */
    public static void performCuckooTest(){
        long start;
        long totalTime;
        Random ran = new Random();//random number generator

        System.out.printf("%40s %60s %60s\n","Testing CuckooHash Insertion(Random Number):","Testing CuckooHash Contains (Random Number):","Testing CuckooHash Deletion (Random Number):");
        System.out.printf("%4s %20s  %20s%20s %20s  %20s%20s %20s  %20s\n","Case", "Test Size", "Total Time (ms)","Case", "Test Size", "Total Time (ms)","Case", "Test Size", "Total Time (ms)");

        int[] testSize = {500000,1000000,2000000, 4000000, 8000000, 16000000}; //Formula: ran.nextInt((max - min) +1)+ min
//        int[] testSize = {32000000}; //Formula: ran.nextInt((max - min) +1)+ min
        //500000,1000000,2000000, 4000000, 8000000, 16000000
        int indexTest = 0;
        while(indexTest < testSize.length) {
            CuckooHashTable<Integer> cuckoo = new CuckooHashTable<>(testSize[indexTest]);//we used a lot of space
            //AVLTree does not deal with duplicate items insertion

            long[] time = new long[3];
            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                cuckoo.insert(ranNum);
            }
            totalTime = System.currentTimeMillis() - start;
            time[0] =totalTime;


            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                cuckoo.contains(ranNum);
            }
            totalTime = System.currentTimeMillis() - start;
            time[1] = totalTime;


            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                cuckoo.remove(ranNum);
            }
            totalTime = System.currentTimeMillis() - start;
            time[2] = totalTime;

            System.out.printf("%4s %20s %20s%20s %20s %20s%20s %20s %20s\n",indexTest, testSize[indexTest], time[0],indexTest, testSize[indexTest], time[1],indexTest, testSize[indexTest], time[2]);
            indexTest++;
        }
    }
}


