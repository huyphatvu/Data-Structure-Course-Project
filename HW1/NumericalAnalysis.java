
/**
 * A class to calculate the zero given a range,
 * has only one method solveForZero
 *
 * @author 	Huy Vu
 * @version 21 January 2017
 */

public class NumericalAnalysis {
    /**
     *A method that solve for the zero of a function given that the input lines in a properly range
     *
     * @param function the function given
     * @param xlow the input of the low level such that f(xlow) always less than or equal to 0
     * @param xhigh the input of the low level such that f(xhigh) always greater than or equal to 0
     * @return the zero of the function in double with the precision is about 0.0000000001
     * @throws IllegalArgumentException if f(xlow) is greater than 0 or f(xhigh) is less than 0 or it couldn't find zero
    */

    public double solveForZero(Function function, double xlow, double xhigh){
        double ylow = function.f(xlow);
        double yhigh = function.f(xhigh);

        if(ylow > 0 || yhigh <0){
           throw new IllegalArgumentException("f(xlow) or f(xhigh) should be in range");
        }
        double ymid;
        double xmid = (xlow +xhigh)/2;

        while(yhigh >=0 && ylow <=0 ){
            xmid = (xhigh+xlow)/2;
            ymid = function.f(xmid);
            if(Math.abs(0.0-ymid)< 0.0000000001){
                break;
            } else if(ymid > 0){
                xhigh = xmid;
            } else if(ymid < 0) {
                xlow = xmid;
            } else{
                throw new IllegalArgumentException("Couldn't find zero");
            }


        }
       return xmid;
    }

}
