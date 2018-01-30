/**
 * A Class that model AVL Tree. This class will provide the
 * implementation of insert, remove, and contains methods. The type
 * of the element that the node contains must be Comparable.
 * <p>
 *
 * @param <T> must be comparable
 * @author Huy Vu
 * @version October 30 2017
 */
 public class AVLTree<T extends Comparable<? super T>>{
    private static final int ALLOWED_IMBALANCE = 1;
    private AVLNode<T> myRoot;

    /**
     * AVL Tree constructor that will
     * set the root to null
     */
    public AVLTree(){
        myRoot = null;
     }

    /**
     *
     * @return the root of the tree.
     */
    public AVLNode<T> getRoot(){
        return myRoot;
    }

    /**
     * public method to insert a root into
     * a tree
     * @param x the item to insert.
     * @return the new root of the subtree.
     */
    public AVLNode<T>  insert(T x){
        myRoot = insert(x, myRoot);
        return myRoot;
    }

    /*
     *internal insert method
     */
    private AVLNode<T> insert( T x, AVLNode<T> t ){
             if( t == null ) {
                 return new AVLNode<>(x);
             }
             int compareResult = x.compareTo( t.element );

             if( compareResult < 0 )
             t.left = insert( x, t.left );
             else if( compareResult > 0 )
             t.right = insert( x, t.right );
             else
             ; // Duplicate; do nothing
             return balance( t );
    }


     /*
      *method to balance the tree
      */
     private AVLNode<T> balance( AVLNode<T> t ) {
         if( t == null )
         return t;

         if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
         if( height( t.left.left ) >= height( t.left.right ) )
            t = rotateWithLeftChild( t );
         else
            t = doubleWithLeftChild( t );
         else
         if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
         if( height( t.right.right ) >= height( t.right.left ) )
            t = rotateWithRightChild( t );
         else
            t = doubleWithRightChild( t );

         t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
//         System.out.println("T is: "+ t.element+"   "+t.height);
         return t;
     }

     /*
      * Rotate binary tree node with left child.
      * For AVL trees, this is a single rotation for case 1.
      * Update heights, then return new root.
      */
    private AVLNode<T> rotateWithLeftChild( AVLNode<T> k2 ){
          AVLNode<T> k1 = k2.left;
          k2.left = k1.right;
          k1.right = k2;
          k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
          k1.height = Math.max( height( k1.left ), k2.height ) + 1;
          return k1;
          }

     /*
      * Rotate binary tree node with right child.
      * For AVL trees, this is a single rotation for case 4.
      * Update heights, then return new root.
      */
     private AVLNode<T> rotateWithRightChild( AVLNode<T> k1 ){

         AVLNode<T> k2 = k1.right;
         k1.right= k2.left ;
         k2.left = k1;
         k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
         k2.height = Math.max( height( k2.right ), k1.height ) + 1;
         return k2;
     }

     /*
       * Double rotate binary tree node: first left child
       * with its right child; then node k3 with new left child.
       * For AVL trees, this is a double rotation for case 2.
       * Update heights, then return new root.
       */
     private AVLNode<T> doubleWithRightChild( AVLNode<T> k3 ) {
          k3.right = rotateWithLeftChild( k3.right );
          return rotateWithRightChild( k3 );
          }

     /*
      * Double rotate binary tree node: first right child
      * with its left child; then node k3 with new right child.
      * For AVL trees, this is a double rotation for case 3.
      * Update heights, then return new root.
      */
     private AVLNode<T> doubleWithLeftChild( AVLNode<T> k3 ) {
         k3.left = rotateWithRightChild( k3.left );
         return rotateWithLeftChild( k3 );
     }

    /**
     * A method to remove an item from a tree.
     * @param x the item to remove.
     * @return the new root of the subtree.
     */
     public AVLNode<T> remove(T x){
         myRoot = remove(x,myRoot);//reset my Root
         return myRoot;
     }

     /*
      *helper method to remove an element x from a subtree t
      */
    private AVLNode<T> remove( T x, AVLNode<T> t ) {
         if( t == null )
              return t; // Item not found; do nothing

          int compareResult = x.compareTo( t.element );

          if( compareResult < 0 )
              t.left = remove( x, t.left );
          else if( compareResult > 0 )
              t.right = remove( x, t.right );
          else if( t.left != null && t.right != null ) // Two children
              {
              t.element = findMin( t.right ).element;
              t.right = remove( t.element, t.right );
              }
          else {

              t = (t.left != null) ? t.left : t.right;
          }
          t = balance( t );
          return t;
          }

      /*
      * Return the height of node t, or -1, if null.
      */
     private AVLNode<T> findMin(AVLNode<T> t){
         if(t==null){
             return null;
         }
         if(t.left == null){
             return t;
         }
         return findMin(t.left);
     }

     /*
      * A method to get the height of AVL Tree
      */
    private int height( AVLNode<T> t ) {
         return t == null ? -1 : t.height;
         }

    /**
     * A method to check if an item is in a tree.
     * @param x the item to be checked
     *
     * @return true if the item is in the tree, false if otherwise
     */
     public boolean contains(T x){
         return containsHelper(x, myRoot);
     }

    /*
     * A method to check whether an item is in a subtree
     */
     private boolean containsHelper( T x, AVLNode<T> t ) {
           if( t == null )
               return false;

         int compareResult = x.compareTo( t.element );
          
           if( compareResult < 0 )
               return containsHelper( x, t.left );
           else if( compareResult > 0 )
               return containsHelper( x, t.right );
           else
           return true; // Match
     }
 }