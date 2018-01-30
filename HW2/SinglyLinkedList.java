import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class to model an efficient
 * SinglyLinkedList without knowing
 * its size some of their main operations.
 * Methods include add, remove, clear
 * getNthToLast, and iterator
 * The class also contains its iterator class
 * and node class
 *
 * @author 	Huy Vu
 * @version 23 September 2017
 */
public class SinglyLinkedList<T> implements Iterable<T>{
    private Node<T> head;
    private Node<T> tail;


    /**
     * Initializes head and tail
     * and setup the empty list
     *
     */
    SinglyLinkedList(){
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
    }

    /**
     * Add the element to the end
     * of the list
     *
     * @param e the element to be added
     */
    public void add(T e){//e is element
    //add new element to the end
    //if list is null
    tail.setData(e);
    tail.setNext(new Node<>());
    tail = tail.next;
    }

    /**
     * Remove the specified element
     * starting from the beginning
     * of the list
     *
     * @param e the element to be removed
     */
    public void remove(T e){//e is element
        Node<T> pres = head;//previous node
        Node curr = head.next;//current node
        while(curr != tail){
            if(curr.data.equals(e)){//removing e require equals b/c it is object
                 pres.setNext(curr.next);
                 curr.next = null;
                 return;
            }
            pres = pres.next;
            curr = curr.next;
        }
    }

    /**
     * Clear the whole list
     *
     *@throws NoSuchElementException if attempting to remove empty list
     */
    public void clear(){
    //clear everything
        if(head.next == tail)
            throw new NoSuchElementException("Empty List");
        head.setNext(tail);
    }

    /**
     * Returns nth item from last by the
     * specified n distance
     *
     * @param n the distance from last
     * @return the nth item from last
     */
    public T getNthToLast(int n){
        Node<T> p = head.next;//prev
        Node<T> c = head.next;//curr
        for(int count = 0; count < n; count++){
            c = c.next;
        }
        while(c.next != null){
           p = p.next;//increase p
           c = c.next;//increase c
        }
        return p.data;

    }

    /**
     * Returns the iterator represent
     * the list
     *
     * @return the iterator of the list
     */
    public SinglyLinkedListIterator iterator(){
        return new SinglyLinkedListIterator( );
    }

    /**
     * A inner class that implement iterator
     * and model it by providing essential
     * methods: hasNext, next, remove, and add
     */
    class SinglyLinkedListIterator implements Iterator<T>{
        private Node<T> currNode= head;//will be tail if the list is empty

        /**
         * Return the boolean value indicating whether iterator
         * has any next node
         * @return true if the iterator has next node, false if otherwise
         */
        @Override
        public boolean hasNext(){
            return currNode.next != tail;
        }

        /**
         * Move the iterator to the next node
         *
         * @return next node's data that the current node is pointing to
         * @throws NoSuchElementException if the iterator reach the tail
         */
        @Override
        public T next(){
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            currNode = currNode.next;//move the iterator to nextNode
            return currNode.data;
        }

        /**
         * removes from the list the node currently pointed to by the Iterator
         *
         * @throws IllegalStateException if attempting to remove the tail
         */
        @Override
        public void remove(){
            if(!hasNext()){
                throw new IllegalStateException();
            }
            Node<T> preNode = head;//previous node
             while(preNode.next !=currNode) {
                 preNode = preNode.next;
             }
             preNode.next = currNode.next;
             currNode.next = null;
             currNode = preNode;
        }

        /**
         * adds the new element to the list after the node
         * the iterator currently points to.
         *
         * @param e the element to be added
         */
        public void add(T e){
            Node<T> newNode = new Node<>(e);//create the node
                newNode.setNext(currNode.next);
                currNode.setNext(newNode);
        }

    }
    //Private inner class Node
    private class Node<T>{
        private T data;
        private Node<T> next;
        private Node(T e){
            this.data = e;
        }
        private Node(){
            this(null);
        }
        private void setData(T data) {
            this.data = data;
        }
        private void setNext(Node<T> n){
            this.next = n;
        }
    }
}
