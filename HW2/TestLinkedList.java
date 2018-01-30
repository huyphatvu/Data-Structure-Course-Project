/**
 * JUnit test for HW2
 * Nth to last item in a linked list
 * Aaron Maus
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class TestLinkedList{
    private SinglyLinkedList<Integer> list;

    @Rule // this rule allows one of the tests to verify that an exception is thrown
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        list = new SinglyLinkedList<>();
        SinglyLinkedList<Integer>.SinglyLinkedListIterator it = list.iterator();
        for(int i = 9; i > 0; i--){
            it.add(new Integer(i));
            it.next();
        }
        // Question to all: The process of adding numbers to the list could
        // have been simplified by dispensing with the iterator and simply
        // calling SinglyLinkedList's add() method. Why use the iterator?
        // What benefit does it provide over SinglyLinkedList's add?
    }

    /**
     * Ensure that the linked list holds the expected values after the
     * initialization in the setup() method. This initialization used the
     * list iterator to perform the adds.
     */
    @Test
    public void testIteratorAdd(){
        Iterator<Integer> it = list.iterator();
        Integer listElement;
        for(int i = 9; i > 0; i--){
            listElement = it.next();
            assertEquals(i, listElement.intValue());
        }
    }

    /**
     * Ensure that the list is built correctly if the list's add method is
     * used instead of the iterator's add method.
     */
    @Test
    public void testListAdd(){
        list.clear();
        for(int i = 9; i > 0; i--){
            list.add(new Integer(i));
        }
        Iterator<Integer> it = list.iterator();
        Integer listElement;
        for(int i = 9; i > 0; i--){
            listElement = it.next();
            assertEquals(i, listElement.intValue());
        }
    }

    /**
     * Remove all the odd numbers using the list's remove method and ensure
     * that the remaining elements are as expected (the even numbers 8, 6,
     * 4, and 2).
     */
    @Test
    public void testListRemove(){
        list.remove(9);
        list.remove(7);
        list.remove(5);
        list.remove(3);
        list.remove(1);

        Iterator<Integer> it = list.iterator();
        int evenNum = 8;
        while(it.hasNext()){
            assertEquals(evenNum,it.next().intValue());
            evenNum = evenNum - 2;
        }
    }

    /**
     * Remove all the even numbers using the iterators's remove method and ensure
     * that the remaining elements are as expected (the odd numbers 9, 7,
     * 5, 3, and 1).
     */
    @Test
    public void testIteratorRemove(){
        // first, remove all the even numbers
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            Integer theVal = it.next().intValue();
            if( theVal.intValue() % 2 == 0){
                it.remove();
            }
        }
        // Next, check that the list contains the correct
        // remaining numbers
        it = list.iterator();
        int oddNum = 9;
        while(it.hasNext()){
            assertEquals(oddNum,it.next().intValue());
            oddNum = oddNum - 2;
        }
    }

    /**
     * Attempt to remove from an empty list and ensure that the
     * IllegalStateException is thrown
     */
    @Test
    public void testEmptyRemove(){
        list.clear();
        thrown.expect(IllegalStateException.class);
        Iterator<Integer> it = list.iterator();
        it.remove();
    }

    /**
     * Attempt to call next() when already at the end of the list and
     * ensure that the NoSuchElementException is thrown.
     */
    @Test
    public void testInvalidNext(){
        list.clear();
        thrown.expect(NoSuchElementException.class);
        Iterator<Integer> it = list.iterator();
        it.next();
    }

    /**
     * Test the getNthToLast method.
     *
     * As an example, given the numbers that is list holds:
     * 9 8 7 6 5 4 3 2 1
     * the 2nd to last element is 2, the 3rd to last
     * is 3, and so on.
     *
     * Ensure that the getNthToLast returns 4 when requested the 4th to
     * last element.
     */
    @Test
    public void testGetNthToLast(){
        Integer ans = list.getNthToLast(4);
        assertEquals(new Integer(4),ans);
    }
}
