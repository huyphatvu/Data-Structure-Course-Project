/*
 * JUnit test for the exercise 2.18 from
 * the textbook.
*/

import static org.junit.Assert.*;
// Import the annotations that JUnit tests can use
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;

// @Test flags a method as a test method.
// @Before indicates that a method will be run before every
//  test method is run.
// @BeforeClass indicates that a method will be run once before
//  any of the other methods in the test suite are run.
// @After indicates that a method will be run after every
//  test method is run.
// @AfterClass indicates that a method will be run once after
//  all the other methods in the test suite finish..

public class TestNumericalAnalysis{
    private NumericalAnalysis myAnalyzer;

    // Methods flagged with the @Before annotation will be
    @Before // before every test method is run.
    public void setUp(){
        myAnalyzer = new NumericalAnalysis();
    }

    // Methods flagged with the @Test annotation are the
    @Test // test methods and will run when the JUnit test is run
    public void testSolveForZero(){
        double ans = myAnalyzer.solveForZero(new MyFunction(), -9 ,10);
        assertEquals(1.155, ans, 0.001); // check that the answer returned
        // is == (1 +- 0.01)
    }

}
