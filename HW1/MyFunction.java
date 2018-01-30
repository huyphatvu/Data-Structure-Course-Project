
/**
 * A class to calculate f(x) given a specific x
 *
 * @author 	Huy Vu
 * @version 21 January 2017
 */
public class MyFunction  implements Function {

    /**
     * f(x) = 3x^3-4x
     * @inheritdoc
     */
    public double f(double input){


       return  3 * input * input * input - 4 * input;
    }

}
