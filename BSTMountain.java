package project4;

/**
 * The BSTMountain class represents the mountain itself.
 * This class inherits from the RestStop class. 
 * The class contains add methods and traverse methods along with a private Node class.
 * 
 * @author Nicole Issagholian 
 *
 */

public class BSTMountain {
	
	//data fields
	private BSTNode root; //root of tree
	private boolean added; //set to true if new node is added to tree, else it is false
	public int depth; //stores the depth of the tree
	private int newDepth; //stores the temporary depth of the tree at a given node; updated if new node added

    /**
     * Default constructor. Creates an empty tree.
     */
    public BSTMountain() {
    	root = null;
	}

    /**
     * Wrapper method for the recursive add method, recurAdd
     *
     * @param newNode is the RestStop object to be added to the tree
     * @return true if data is successfully added, otherwise false
     */
    public boolean add(RestStop newNode) { 
    	if (newNode == null) {
    		return false;
	    }
	    //replace root with reference to new tree after data is added
	    root = recurAdd(newNode, root);
	    newDepth = 0;
	    return added;
	}

    /**
	 * An actual recursive implementation of the add method
	 *
	 * @param newNode is the RestStop object to be added
	 * @param n is the node at which the recursive call is made
	 * @return the root of the tree containing the new value
	 */
	private BSTNode recurAdd(RestStop newNode, BSTNode n) {
	//if the current node is null, set the current node to be the new node
		if (n == null) {
			added = true;
			//if the depth after adding the new node is greater than the original depth, set depth to the new depth 
			if (newDepth > depth) {
				depth = newDepth;
	        }
	        return new BSTNode(newNode);
	    }

		//compare values of current node and new node
        int diff = n.compareTo(newNode);
        
      //traverses to the right if the current node's value is less than that of the new node
        if (diff > 0) {
        	newDepth++;
            n.left = recurAdd(newNode, n.left);
        }
        
        //traverses to the left if the current node's value is greater than that of the new node
        else if (diff < 0) {
        	newDepth++;
            n.right = recurAdd(newNode, n.right);
        }
        //if they are equal, the recursive call ends
        else {
            added = false;
            return n;
        }
        return n;
    }

    /**
     * Wrapper method for the recursive traversal method, recurTraverse
     */
    public void traverse() {
        //hiker object
    	Hiker hiker = new Hiker();
        
    	//create current node and set it to root of tree
    	BSTNode curr = root;
        
    	//depth of hiker
    	int hikeDepth = 0;
    	
    	//call to recursive traversal method
        recurTraverse(hiker, curr, hikeDepth, "");
    }

    /**
	 * An actual recursive implementation of the traverse method
	 *
	 * This method prints all of the possible paths the hiker can take down the mountain. 
	 * This is determined by the Hiker object's processRestStop method in the RestStop class 
	 * which determines whether or not the hiker is able to continue through a rest stop.
	 *
	 * @param hiker is the hiker to traverse the mountain
	 * @param current is the node at which the recursive call is made
	 * @param hikerDepth keeps track of the depth of the current node; the hiker cannot leave the mountain unless they are at the max depth of the tree
	 * @param paths is a string containing possible paths down the mountain
	 */
	public void recurTraverse(Hiker hiker, BSTNode curr, int hikeDepth, String paths) {
		if (curr == null) {
			return; //nothing to traverse
	    }
	    
		//if the current node has no children and the hiker is at the max depth, print the path to the current node
	    if (curr.left == null && curr.right == null) {
	    	if (hikeDepth == depth){
	    		paths = paths + curr.data.getLabel();
	            System.out.println(paths);
	        }
	    }
	    
	    //recursively calls the two child nodes if the hiker has enough supplies to continue
	    else if (hiker.processRestStop(curr.data)) {
	    	paths += curr.data.getLabel() + " ";
	    	recurTraverse(new Hiker(hiker), curr.left, hikeDepth+1, paths);
	    	recurTraverse(new Hiker(hiker), curr.right, hikeDepth+1, paths);
	    }
	}

	/**
	 * The BSTNode class represents a single node of the tree
	 * It implements the comparable interface
	 */
	private class BSTNode implements Comparable<RestStop> {
		
		private RestStop data;
		BSTNode left;
	    BSTNode right;

        /**
         * Creates a new node using the given rest stop
         *
	     * @param data must be a RestStop object
	     */
	    public BSTNode(RestStop data) {
	    	this.data = data;
	        this.left = null;
	        this.right = null;
	    }
	    
	    /** Compares the RestStop data object with the specified object
	     * 
         * @param o is the RestStop object to be compared to
         * @return a positive integer if this is greater than o, a negative integer if this is less than o, and 0 if they are equal
		 */
        @Override
        public int compareTo(RestStop o) {
            return this.data.compareTo(o);
        }
    }
}
