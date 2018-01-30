/**
 * A Node that keeps track of the element it contains, its children, and its height. The type
 * of the element that the node contains must be Comparable.
 * <p>
 * Default access has been provided to the instance variables for each of coding when performing
 * rotations.
 * @param <E> must be comparable
 */
public class AVLNode<E extends Comparable<? super E>>{
    E element;
    AVLNode<E> left;  // Left Child
    AVLNode<E> right; // Right Child
    int height;

    /**
     * Construct a Node holding element with no children
     * @param element the element this node will contain
     */
    AVLNode( E element ) {
        this.element = element;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
}
