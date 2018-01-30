

import java.math.BigInteger;
        import java.util.Random;

public class CuckooHashTableVer2<T>{
    private static final double MAX_LOAD = 0.4;
    private static final int ALLOWED_REHASHES = 1;
    private static final int DEFAULT_TABLE_SIZE = 101;
    private Random r = new Random( );
    private final HashFamily<? super T> hashFunctions;
    private   int numHashFunctions;
    private T [ ] array;
    private int currentSize;
    private int rehashes = 0;

    /**
     * Construct the hash table.
     * @param hf the hash family
     */
    public CuckooHashTableVer2( HashFamily<? super T> hf )
    {
        this( hf, DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param hf the hash family
     * @param size the approximate initial size.
     */
    public CuckooHashTableVer2( HashFamily<? super T> hf, int size ) {
        allocateArray( nextPrime( size ) ); //COULDN'T FIND NEXT PRIME FUNCTION
//        array = new T [size];
        doClear( );
        hashFunctions = hf;
        numHashFunctions = hf.getNumberOfFunctions( );
    }
    private void doClear( ) {
        currentSize = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }

    private int nextPrime(int num){
        String s = ""+num;//translate num to string

        BigInteger numB = new BigInteger(s);
//        System.out.println(s+"       "+numB);
        numB = numB.nextProbablePrime();
        long numL = numB.longValue();
//        System.out.println((int) numL);
        return (int) numL;//type cast
    }
    /**
     * Compute the hash code for x using specified hash function
     * @param x the item
     * @param which the hash function
     * @return the hash code
     */
    private int myhash( T x, int which )
    {
        int hashVal = hashFunctions.hash( x, which );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }

    /**
     * Method that searches all hash function places.
     * @param x the item to search for.
     * @return the position where the search terminates, or -1 if not found.
     */
    private int findPos( T x )
    {
        for( int i = 0; i < numHashFunctions; i++ )
        {
            int pos = myhash( x, i );
            if( array[ pos ] != null && array[ pos ].equals( x ) )
                return pos;
        }

        return -1;
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
    public boolean remove( T x )
    {
        int pos = findPos( x );

        if( pos != -1 )
        {
            array[ pos ] = null;
            currentSize--;
        }

        return pos != -1;
    }
    /**
     * Insert into the hash table. If the item is
     * already present, return false.
     * @param x the item to insert.
     */
    public boolean insert( T x )
    {
        if( contains( x ) )
            return false;

        if( currentSize >= array.length * MAX_LOAD )
            expand( );

        return insertHelper1( x );
    }
    //Helper for insert
    private boolean insertHelper1( T x )
    {
        final int COUNT_LIMIT = 100;
        while( true ) {
            int lastPos = -1;
            int pos;

            for( int count = 0; count < COUNT_LIMIT; count++ ) {
                for( int i = 0; i < numHashFunctions; i++ ) {
                    pos = myhash( x, i );
                    if( array[ pos ] == null ) {
                        array[ pos ] = x;
                        currentSize++;
                        return true;
                    }
                }

                // none of the spots are available. Evict out a random one
                int i = 0;
                do {
//                    lastPos = pos;
                    pos = myhash( x, r.nextInt( numHashFunctions ) ) ;
                } while( pos == lastPos && i++ < 5 );

                T tmp = array[ lastPos = pos ];
                array[ pos ] = x;
                x = tmp;
            }

            if( ++rehashes > ALLOWED_REHASHES ) {
                expand( ); // Make the table bigger
                rehashes = 0; // Reset the # of rehashes
            }
            else
                rehash( ); // Same table size, new hash functions
        }
    }
    private void expand( )
    {
        rehash( (int) ( array.length / MAX_LOAD ) );
    }

    private void rehash( )
    {
        hashFunctions.generateNewFunctions( );
        rehash( array.length );
    }

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


    private void allocateArray( int arraySize )
    { array = (T[]) new Object[ arraySize ]; }

    public void printHashTable() {
        for(int i=0; i< array.length;i++){
            System.out.print(array[i]+"\t");
        }
        System.out.println();
    }
}