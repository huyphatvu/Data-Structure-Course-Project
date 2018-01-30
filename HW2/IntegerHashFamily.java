public class IntegerHashFamily implements HashFamily<Integer> {
    private final int [ ] MULTIPLIERS;
    private final java.util.Random r = new java.util.Random( );

    public IntegerHashFamily( int d ) {
        MULTIPLIERS = new int[ d ];
        generateNewFunctions( );
    }

    public int getNumberOfFunctions( )
    {
        return MULTIPLIERS.length;
    }

    public void generateNewFunctions( )
    {
        for( int i = 0; i < MULTIPLIERS.length; i++ )
            MULTIPLIERS[ i ] = r.nextInt( );
    }

    public int hash(Integer x, int which )
    {
        final int multiplier = MULTIPLIERS[ which ];
        return multiplier * x.hashCode();

    }

}