
/*
   Project 2
   Name: Minhngan Do netid: mvd170130
   Class: CS3345.007
   Professor: Greg Ozbirn
*/


/******************************
*             Node            *
*******************************/
class Node<T> {
   
   //instance variables
   private T data; 
   public Node<T> left;
   public Node<T> right;
   
   //constructors
   public Node(T theData) {
      this(theData, null,null);
   }
   
   public Node(T theData, Node<T> theLeft, Node<T> theRight) {
      data = theData;
      left = theLeft;
      right = theRight;
   }
   
   public T getData() {
      return data;
   }
   
}

/******************************
*             Tree            *
*******************************/
class BinarySearchTree<T extends Comparable<? super T>> {
   
   //instance variables
   private Node<T> root;
   private String allAncestors;
   
   //constructor
   public BinarySearchTree() {
      root = null;
   }
   
   public Node<T> getRoot() {
      return root;
   }

   /*
    * adds a node to the tree containing the passed value
    */
   public void add(T x) {
      root = insert(x, root);
   }
   
   /*
    * x is the item to insert
    * t is the node that roots the subtree
    * returns the new root of the subtree
    */
   private Node<T> insert(T x, Node<T> t) {
   
      //if tree empty just make a new node 
      if (t == null) {
         return new Node<T>(x, null, null);
      }
      
      int compareResult = x.compareTo(t.getData());
      
      //if less than t, go left
      if (compareResult < 0) {
         t.left = insert(x, t.left);
      }
      
      //if more than t, go right
      else if (compareResult > 0) {
         t.right = insert(x, t.right);
      }
      else {
         ;
      }
      
      return t;
   }
   
   /*
    * returns true if the value passed is in the tree
    */
   public boolean find(T x) {
      return contains(x, root);
   }
   
   /*
    * x is the data of the Node to search for
    * t is the node that roots the subtree
    * returns node containing the matched item
    */
   private boolean contains(T x, Node<T> t) {
      
      //if empty return false
      if (t == null) {
         return false;
      }   
      
      int compareResult = x.compareTo(t.getData());
      
      //if less than t, go left
      if (compareResult < 0) {
         return contains(x, t.left);
      }
      //if more than t, go right
      else if (compareResult > 0) {
         return contains(x, t.right);
      }
      else {
         return true;
      }
   }
   
   /*
    * t is the node that roots the subtree
    * returns an int (height of the tree)
    */
   private int height(Node<T> t) {
      //if empty 
      if (t == null) {
         return -1;
      }
      //recursion adds 1 to the height for every node in the subtrees, tree height is the bigger height of either the left/right, plus the root
      else {
         return 1 + Math.max(height(t.left), height(t.right));
      }
   }
   
   /*
    * leaf count
    * returns the count of all of the leaves in the tree
    */
    public int leafCount() {
      return getLeafCount(root);
    }
    
    /*
     * t is the node of the root of the subtree
     */
    private int getLeafCount(Node<T> t) {
      //if empty
      if (t == null) {
         return 0;
      }
      //if the root of the subtree t, has no chilren then it is a leaf
      if (t.left == null && t.right == null) {
         return 1;
      }
      //recursion all the way left to count all left leaves, plus recursion all the way right to count all right leaves is total leaves
      else {
         return getLeafCount(t.left) + getLeafCount(t.right);
      }
    }
    
   /*
    * parent count
    * returns the count of all of the parents in the tree
    */
    public int parentCount() {
      return getParentCount(root);
    }
    
    private int getParentCount(Node<T> t) {
      
      //if empty
      if (t == null) {
         return 0;
      }
      //if no children then its a leaf => not a parent
      if (t.left == null && t.right == null) {
         return 0;
      }
      //recursively call the method for the left subtrees and the right subtrees, adds call if it makes it all the way through the call(it is a parent)
      else {
         return getParentCount(t.left) + getParentCount(t.right) + 1;
      }
    }
    
   /*
    * ancestors
    * returns the ancestor values of the passed value
    */
    public String ancestors(T node) {
            
      getAncestors(root, node);
      return allAncestors;
      
    }
    private boolean getAncestors(Node<T> t, T node) {
      //if empty
      if (t == null) {
         return false;
      }
      
      if(t.getData() == node) {
         return true;
      }
      
      //if the passed value (that we're trying to find the ancestors of) exists in either the left/right subtree of t, then add t's data to ancestor list
      if (getAncestors(t.left, node) || getAncestors(t.right, node)) {
         if(t.getData() != null) {
            allAncestors += t.getData() + " ";
         }
         return true;
      }
      
      return false;
    }    
    
    
    private int findDepth(Node<T> t) {
      int d = 0;
      while(t != null) {
         d++;
         t = t.left;
      }
      return d;
    }

    
    private boolean isPerfectP(Node<T> t,int d, int level) {
      //if empty
      if(t == null) {
         return true;
      }
      
      //if no children at t, then their heights are the same right there
      if (t.left == null && t.right == null) {
         return (d == level+1);
      }
      
      //if only one child, not perfect
      if(t.left == null || t.right == null) {
         return false;
      }
      
      //return true if both left subtree and right subtree return true at t (meaning none have only one child), recursion does this for the whole tree (at every t)
      return isPerfectP(t.left, d, level+1) && isPerfectP(t.right, d, level+1);
    }
    
    /*
    * isPerfect
    * returns true if the tree is a perfect tree 
    */
    public boolean isPerfect(Node<T> t) {
      int d = findDepth(t);
      return isPerfectP(t, d, 0);
    }

   /*
    * in order print
    */
   public void printTreeInOrder() {
      if (root == null) {
         System.out.println("Empty tree");
      }
      else {
         inOrderPrint(root);
      }
   }
   
   /*
    * pre order print
    */
   public void printTreePreOrder() {
      if(root == null) {
         System.out.println("Empty tree");
      }
      else {
         preOrderPrint(root);
      }
   }
   
   private void inOrderPrint(Node<T> t) {
      if (t != null) {
         inOrderPrint(t.left);
         System.out.println(t.getData());
         inOrderPrint(t.right);
      }
   }
   
   private void preOrderPrint(Node<T> t) {
      if (t != null) {
         System.out.println(t.getData());
         preOrderPrint(t.left);
         preOrderPrint(t.right);
      }
   }
   
/******************************
*             Main            *
*******************************/
   public static void main(String[] args) {
   
     BinarySearchTree<Integer> myTree = new BinarySearchTree<>();
     
     System.out.println("Testing add...");
     /*          50
      *         /   \
      *      33      68
      *     /  \    /   \
      *   16   35  60   76    
      */
     myTree.add(50);
     myTree.add(33);
     myTree.add(68);
     myTree.add(16);
     myTree.add(35);
     myTree.add(60);
     myTree.add(76);

     System.out.println("\nTesting printTreeInOrder...");
     myTree.printTreeInOrder();
     
     System.out.println("\nTesting printTreePreOrder...");
     myTree.printTreePreOrder();
     
     System.out.println("\nTesting isPerfect...");
     if(myTree.isPerfect(myTree.getRoot())) {
         System.out.println("The tree is perfect");
     }
     else {
         System.out.println("\nThe tree is NO perfect");
     }
     
     System.out.println("\nTesting find...");
     if(myTree.find(12)) {
         System.out.println("The number 12 was found in the tree");
     }
     else {
         System.out.println("The number 12 was NOT found in the tree");
     }
     
     System.out.println("\nTesting find...");
     if(myTree.find(35)) {
         System.out.println("The number 35 was found in the tree");
     }
     else {
         System.out.println("The number 35 was NOT found in the tree");
     }
     
     System.out.println("\nTesting height...");
     System.out.println("The height of the tree is " + myTree.height(myTree.getRoot()));
     
     System.out.println("\nTesting leafCount...");
     System.out.println("The tree has " + myTree.leafCount() + " leaf node(s)");
     
     System.out.println("\nTesting parentCount...");
     System.out.println("The tree has " + myTree.parentCount() + " parent node(s)");
     
     System.out.println("\nTesting ancestors...");   
     String ancestorList = myTree.ancestors(76);
     System.out.println("76 has ancestors: " + ancestorList.substring(4));
      
      
     BinarySearchTree<Integer> myTree2 = new BinarySearchTree<>();
     System.out.println("Testing add...");
     
     /*          73
      *         /  \       
      *       24    89
      *      /
      *     14
      *    /
      *   5
      *  
      */
     myTree2.add(73);
     myTree2.add(24);
     myTree2.add(14);
     myTree2.add(89);
     myTree2.add(5);


     System.out.println("\nTesting printTreeInOrder...");
     myTree2.printTreeInOrder();
     
     System.out.println("\nTesting printTreePreOrder...");
     myTree2.printTreePreOrder();
     
     System.out.println("\nTesting isPerfect...");
     if(myTree2.isPerfect(myTree2.getRoot())) {
         System.out.println("The tree is perfect");
     }
     else {
         System.out.println("The tree is NOT perfect");
     }
     
     System.out.println("\nTesting find...");
     if(myTree2.find(12)) {
         System.out.println("The number 12 was found in the tree");
     }
     else {
         System.out.println("The number 12 was NOT found in the tree");
     }
     
     System.out.println("\nTesting find...");
     if(myTree2.find(5)) {
         System.out.println("The number 36 was found in the tree");
     }
     else {
         System.out.println("The number 36 was NOT found in the tree");
     }
     
     System.out.println("\nTesting height...");
     System.out.println("The height of the tree is " + myTree2.height(myTree2.getRoot()));
     
     System.out.println("\nTesting leafCount...");
     System.out.println("The tree has " + myTree2.leafCount() + " leaf node(s)");
     
     System.out.println("\nTesting parentCount...");
     System.out.println("The tree has " + myTree2.parentCount() + " parent node(s)");
     
     System.out.println("\nTesting ancestors...");
     String ancestorList2 = myTree2.ancestors(24);
     
     System.out.println("24 has ancestors: " + ancestorList2.substring(4));

   
   
   
   }
}

