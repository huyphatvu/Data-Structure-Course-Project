import java.util.Random;

/**
 * A class to provide the test for
 * the runtime of AVL Tree
 *
 * @author Huy Vu
 * @version October 30 2017
 */
public class TreeTester {

    /**
     * A static method to perform runtime
     * analysis of AVL Tree
     *
     */
    public static void performAVLTreeTest(){
        long start;
        long totalTime=0;
        Random ran = new Random();//random number generator
        System.out.printf("%30s %60s %60s\n","Testing Tree Insertion(Random Number):","Testing Tree Contains (Random Number):","Testing Tree Deletion (Random Number):");
        System.out.printf("%4s %20s  %20s%20s %20s  %20s%20s  %20s %20s\n","Case", "Test Size", "Total Time (ms)","Case", "Test Size", "Total Time (ms)","Case", "Test Size", "Total Time (ms)");
//        long fixedTestSize = 500000-1;
        int[] testSize = {500000,1000000,2000000, 4000000, 8000000, 16000000}; //Formula: ran.nextInt((max - min) +1)+ min

        //500000,1000000,2000000, 4000000, 8000000, 16000000
        int indexTest = 0;

        while(indexTest < testSize.length) {
            AVLTree<Integer> tree = new AVLTree<>(); //initialize tree
            //AVLTree does not deal with duplicate items insertion

            long[] time = new long[3];
            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                tree.insert(ranNum);
//                if(i == fixedTestSize)
//                    totalTime = System.currentTimeMillis() - start;
            }

            time[0] =totalTime;


            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                tree.contains(ranNum);
//                if(i == fixedTestSize)
//                    totalTime = System.currentTimeMillis() - start;
            }
//            totalTime = System.currentTimeMillis() - start;
            time[1] = totalTime;


            start = System.currentTimeMillis(); //start timer
            for (int i = 0; i < testSize[indexTest]; i++) {
                int ranNum = ran.nextInt(testSize[indexTest] + 1);//get the random number from [0 to testSize[indexTest]]
                tree.remove(ranNum);
//                if(i == fixedTestSize)
//                    totalTime = System.currentTimeMillis() - start;
            }
//            totalTime = System.currentTimeMillis() - start;
            time[2] = totalTime;
            System.out.printf("%4s %20s %20s%20s %20s %20s%20s %20s %20s\n",indexTest, testSize[indexTest], time[0],indexTest, testSize[indexTest], time[1],indexTest, testSize[indexTest], time[2]);
            indexTest++;
        }
    }
}
