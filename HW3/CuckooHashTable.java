import java.math.BigInteger;
import java.util.Random;

/**
 * A Class to model Cuckoo Hash Table.
 * It will provide methods insert, contains, and remove
 *
 *
 * @param <T> the generic type
 * @author Huy Vu
 * @version October 30 2017
 */
public class CuckooHashTable<T>{
    private static final double MAX_LOAD = 0.4;
    private static final int ALLOWED_REHASHES = 1;
    private static final int DEFAULT_TABLE_SIZE = 101;
    private final static int numHashFunctions =2; //only 2 hash function
    private static int [ ] MULTIPLIERSARR = new int[numHashFunctions];//2 hash function
    private Random r = new Random( );
    private static Random r_static = new Random( );
    private static final int FIRSTPOS = 0;//define first position
    private static final int SECPOS = 1;//define second position
    private T [ ] array;
    private int currentSize;
    private int rehashes = 0;

    /**
     * Construct the hash table based on the default size
     */
    public CuckooHashTable( )
    {
        this(DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table specified by size
     * The table size is the nearest bigger prime number
     * @param size the approximate initial size.
     */
    public CuckooHashTable(int size ) {
        allocateArray( nextPrime( size ) );
        doClear( );
        generateNewHashFunctions( );//generate new hash function
    }

    /*
     *method to clear the whole array
     */
    private void doClear( ) {
        currentSize = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }
    /*
     *Find the nearest next prime number using num as a start point
     */
    private int nextPrime(int num){
        String s = ""+num;//translate num to string

        BigInteger numB = new BigInteger(s);
        numB = numB.nextProbablePrime();
        long numL = numB.longValue();
        return (int) numL;//type cast
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if item is found.
     */
    public boolean contains( T x )
    {
        return findPos( x ) != -1;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item was found and removed
     */
    public boolean remove( T x ) {
        int pos = findPos( x );

        if( pos != -1 ) {
            array[ pos ] = null;
            currentSize--;
        }

        return pos != -1;
    }

    /**
     * Insert into the hash table.
     *
     * @param x the item to insert.
     * @return true if insert successful, If the item is already present, return false.
     */
    public boolean insert( T x ) {
        if( contains( x ) )
            return false;

        if( currentSize >= array.length * MAX_LOAD )
            expand( );

        return insertHelper1( x );
    }

    /*
     * Helper for insert
     */
    private boolean insertHelper1( T x ) {
        final int COUNT_LIMIT = 100;//limit of eviction for 100 times
        while( true ) {
            int lastPos = -1;
            int pos;

            for( int count = 0; count < COUNT_LIMIT; count++ ) {
                pos = myhash( x, FIRSTPOS);                             //get the first position
                if( array[ pos ] == null ) {
                    array[pos] = x;
                    currentSize++;
                    return true;
                }
                pos = myhash(x, SECPOS);                                //find second position if the first pos is not empty
                if( array[ pos ] == null ) {
                    array[pos] = x;
                    currentSize++;                                      //increment the size
                    return true;
                }


                int i = 0;                                              // none of the spots are available. Evict out a random one
                do {
                    pos = myhash(x, r.nextInt( numHashFunctions ) ) ;   //evict a random position (0 or 1 position)
                } while( pos == lastPos && i++ < 5 );                   //compare with lasPos to avoid cycle, evict the same position

                T tmp = array[ lastPos = pos ];                         //assign lastPos to pos and swap the evicted item with x and reinsert the evicted item
                array[ pos ] = x;
                x = tmp;
            }

            if( ++rehashes > ALLOWED_REHASHES ) {
                expand( );                                              // Make the table bigger
                rehashes = 0;                                           // Reset the # of rehashes
            }
            else
                rehash( );                                              // Same table size, new hash functions
                                                                        //after expand or rehash, we reinsert
        }
    }

    /*
     * Expand the array and rehash it
     */
    private void expand( )
    {
        rehash( (int) ( array.length / MAX_LOAD ) );
    }

    /*
     * create new hash functions and copy everything over
     */
    private void rehash( ) {
        generateNewHashFunctions();
        rehash( array.length);
    }

    /*
     * helper method for rehash
     */
    private void rehash( int newLength )
    {
        T [ ] oldArray = array;
        allocateArray( nextPrime( newLength ) );
        currentSize = 0;

        // Copy table over
        for( T str : oldArray )
            if( str != null )
                insert( str );
    }

    /*
     * Reserve some memory for the array
     */
    private void allocateArray( int arraySize ) {
            array = (T[]) new Object[arraySize];
    }

    /**
     * Method to print all items in the hash table array
     */
    public void printHashTable() {
        for(int i=0; i< array.length;i++){
            System.out.print(array[i]+"\t");
        }
        System.out.println();
    }

    /*
     * generate new hashfunction
     */
    private static void generateNewHashFunctions( ){
            int firstFunc = r_static.nextInt( );
            MULTIPLIERSARR[ 0] = firstFunc;
            int secFunc = r_static.nextInt( );
            while(secFunc == firstFunc){
                secFunc = r_static.nextInt( );//ensure that secFunc is diferrent than the first
            }
            MULTIPLIERSARR[ 1] = secFunc;
    }


    /*
     * Compute the hash code for x using specified hash function
     * @param x the item
     * @param which the hash function
     * @return the hash code
     */
    private int myhash( T x, int which ) {
        int hashVal = hash( x, which );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }

    /*
     * get hash function for x
     */
    private static <E>  int hash(E x, int which ){
        final int multiplier = MULTIPLIERSARR[ which ];
        return multiplier * x.hashCode();
    }

    /*
     * Method that searches all hash function places.
     * @param x the item to search for.
     * @return the position where the search terminates, or -1 if not found.
     */
    private int findPos( T x ) {
        int pos = myhash(x, FIRSTPOS);//get the first position
        if(array[pos] !=null && array[pos].equals(x))
            return pos;//found it

        pos = myhash(x, SECPOS);//get the second position
        if(array[pos] !=null && array[pos].equals(x))
            return pos;//found it

        return -1;//return -1 if not found
    }
}