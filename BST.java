package project4;

import java.util.*;

/**
 * The BST<E> is class is an implementation of a binary search tree of guaranteed O(H) time cost for the basic operations. 
 * The elements are ordered using their natural ordering.
 * The class implements many of the many provided by the Java framework's TreeSet class.
 * 
 * @author Nicole Issagholian 
 *
 */

public class BST<E extends Comparable<E>> implements Iterable<E> {
	
	private Node root; //reference to the root node of the tree
	private Comparator<E> comparator; //comparator object to overwrite the natural ordering of the elements
	private boolean found; //helper variable used by the remove methods
	private boolean added; //helped variable used by the add methods
	private int size; //stores number of elements in tree
	private Node left; //reference to the left node of a specific node
	private Node right; //reference to the right node of a specific node
	private int height; //keep track of height of tree

	//constructor that constructs a new, empty tree, sorted according to the natural ordering of its elements
	public BST() {
		this.root = null;
		comparator = null;
	}
	
	//constructor that constructs a new tree containing the elements in the specified collection, sorted according 
	//to the natural ordering of its elements
	public BST(E[] collection) throws NullPointerException {
		if (collection == null) {
			throw new NullPointerException("Specified collection is null.");
		}
		
		size = 0;
		
		for(E element: collection) {
			add(element);
		}
	}
	
	/**
	 * Adds the specified element to this set if it is not already present
	 * More formally, adds the specified element e to this tree if the set contains no element e2 such that Objects.equals(e, e2)
	 * If this set already contains the element, the call leaves the set unchanged and returns false
	 * 
	 * @param e is the element to be added to this set
	 * @return true if this set did not already contain the specified element
	 * @throws NullPointerException if the specified element is null and this set uses natural ordering, 
	 * or its comparator does not permit null elements 
	 * 
	 * From BST class in Ed workspace
	 */
	public boolean add(E e) throws NullPointerException {
		added = false; 
        int newHeight = 0;
		
		if (e == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		//replace root with the reference to the tree after the new value is added
		root = add(e, root, newHeight);
		if (added) {
			size++;
		}
		return added;
	}
	
	/**
	 * Actual recursive implementation of add
     * 
     * This function returns a reference to the subtree in which the new value was added
     * 
	 * @param e element to be added to this tree
	 * @param current is the node at which the recursive call is made
	 * @param newHeight is the updated height once a node is added
	 * 
	 * From BST class in Ed workspace
	 */
	private Node add(E e, Node current, int newHeight){
		if (current == null) {
            added = true; 
            current = new Node(e); 
            
            if (newHeight > height) {
            	height = newHeight;
            }
            
            return current;
        }
		
		//decide how comparisons should be done 
        int comp = 0;
        if (comparator == null) { //use natural ordering of the elements 
            comp = e.compareTo(current.data); 
        }
        else {                    //use the comparator 
            comp = comparator.compare(e, current.data);
        }

        //find the location to add the new value  
        if (comp < 0) { //add to the left subtree 
        	current.left = add(e, current.left, ++newHeight); 
        }
        else if (comp > 0) { //add to the right subtree
        	current.right = add(e, current.right, ++newHeight); 
        }
        else { //duplicate found, do not add 
            added = false; 
            //return node; 
        }

        return current; 
	}
	
	/**
	 * Removes the specified element from this tree if it is present
	 * More formally, removes an element e such that Objects.equals(o, e), if this tree contains such an element 
	 * Returns true if this tree contained the element
	 * 
	 * @param o is the object to be removed from this set, if present
	 * @return true if this set contained the specified element
	 * @throws NullPointerException if the specified element is null
	 * 
	 * From BST class in Ed workspace
	 */
	public boolean remove(Object o) throws NullPointerException {
		if (o == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		int newHeight = 1;
		
		//replace root with a reference to the tree after o was removed
		root = recRemove(o, root, newHeight);
        if (found) {
        	size--; 
        }
		return found;
	}
	
	/**
	 * Actual recursive implementation of remove method: find the node to remove
     *
	 * This function recursively finds and eventually removes the node with the target element 
     * and returns the reference to the modified tree to the caller.
	 * 
	 * @param o object to be removed from this tree, if present
	 * @param current is the node at which the recursive call is made 
	 * @param newHeight is the updated height once a node is added
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in this tree
	 * 
	 * From BST class in Ed workspace
	 */
	private Node recRemove(Object o, Node current, int newHeight) throws ClassCastException {
		if (current == null) { //value not found 
			found = false;
			
			if (newHeight > height) {
				height = newHeight;
	        }
			
			return current; 
        }
		
		try {
			@SuppressWarnings("unchecked") 
			E obj = (E) o; //cast o from type Object to type E
	    	
			//decide how comparisons should be done 
	        int comp = 0 ;
	        if (comparator == null) { //use natural ordering of the elements 
	            comp = obj.compareTo(current.data);
	        }
	        else {                    //use the comparator 
	            comp = comparator.compare(obj, current.data);
	        }

	        //find the location to remove the value 
	        if (comp < 0) {      //o might be in a left subtree 
				current.left = recRemove(obj, current.left, --newHeight);
	        }
			else if (comp > 0) {  //o might be in a right subtree 
				current.right = recRemove(obj, current.right, --newHeight);
			}
			else {          //o found, now remove it 
				current = removeNode(current);
				found = true;
			}
	    }
		catch(ClassCastException ex) {
			System.out.println("ClassCastException thrown");
	    }

		return current;
	}
	
	/**
	 * Actual recursive implementation of remove method: perform the removal
     *
	 * @param current is the node at which the recursive call is made 
	 * @return a reference to the node itself, or to the modified subtree currently in this tree
	 * 
	 * From BST class in Ed workspace
	 */
	private Node removeNode(Node current) {
		E data;
		
		if (current.left == null) {  //handle the leaf and one child node with right subtree 
			return current.right;
		}
		else if (current.right == null) { //handle one child node with left subtree 
			return current.left;
		}
		else {                   //handle nodes with two children 
			data = getPredecessor(current.left);
			current.data = data;
			current.left = recRemove(data, current.left, height);
			return current;
		}
	}
	
	/**
	 * Returns the information held in the rightmost node of subtree
	 * 
	 * @param subtree root of the subtree within which to search for the rightmost node
	 * @return returns data stored in the rightmost node of subtree
	 * @throws NullPointerException if subtree is null 
	 * 
	 * From BST class in Ed workspace
	 */
	private E getPredecessor(Node subtree) throws NullPointerException {
		if (subtree == null) {
            throw new NullPointerException("getPredecessor called with an empty subtree");
		}
		Node temp = subtree;
		while (temp.right  != null) {
			temp = temp.right;
		}
		return temp.data;
	}
	
	/**
	 * Removes all of the elements from this set
	 * The set will be empty after this call returns
	 */
	public void clear() {
		root = null; //set reference to root node as null
		height = size = 0; //height and size of tree are 0
	}
	
	/**
	 * Returns true if this set contains the specified element
	 * More formally, returns true if and only if this set contains an element e such that Objects.equals(o, e)
	 * 
	 * @param o is the object to be checked for containment in this set
	 * @return true if this set contained the specified element
	 * @throws NullPointerException if the specified element is null and this set uses natural ordering, or its
	 * comparator does not permit null elements
	 */
	public boolean contains(Object o) throws NullPointerException {
		if (o == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		return contains(o, root);
	}
	
	/**
	 * Actual recursive implementation of contains method
	 * 
	 * @param o is the object to be checked for containment in this set
	 * @param current is the node at which the recursive call is made 
	 * @return true if this set contained the specified element
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in this set
	 */
	private boolean contains(Object o, Node current) throws ClassCastException {
		if (o == null) {
			return false;
		}
		
		try {
			@SuppressWarnings("unchecked") 
			E obj = (E) o; //cast o from type Object to type E
	    	
			//compare value of o to that of current
			int comp = obj.compareTo(current.data);
		    
			if (comp < 0) { //o might be in a left subtree
		    	return contains(o, current.left);
		    }
		    if (comp > 0) { //o might be in a right subtree
		    	return contains(o, current.right);
		    }
		    else { //o found, return true
		    	return true;
		    }
	    }
		catch(ClassCastException ex) {
			System.out.println("ClassCastException thrown");
	    }
	
	    // no matching node was found
	    return false;
	
	}
	
	/**
	 * Returns the number of elements in this tree
	 * 
	 * @return the number of elements in this tree
	 */
    public int size() {
        return size(root);
    }

    /**
	 * Actual recursive implementation of the size method
	 * 
	 * @param current is the node at which the recursive call is made 
	 * @return the number of elements in this tree
	 */
    private int size(Node current) {
        if (current == null) {
        	return 0;
        }
        else {
        	return size;
        }
    }
	
	/**
	 * Returns true if this set contains no elements
	 * 
	 * @return true if this set contains no elements
	 */
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns the height of this tree
	 * The height of a leaf is 1 
	 * The height of the tree is the height of its root node
	 * 
	 * @return the height of this tree or zero if the tree is empty
	 */
	public int height() {
		return height;
	}
	
	/**
	 * Iterator class to iterate over the elements in this tree in ascending order
	 * contains hasNext() and next() methods along with a private moveLeft method
	 */
	private class ForwardItr implements Iterator<E> {
		private Stack<Node> traverse;
		
		//TreeIterator constructor
		public ForwardItr(Node root) {
			traverse = new Stack<Node>();
			moveLeft(root);
		}
		
		/**
		 * Returns true if the iteration has more elements
		 * 
		 * @return true if iteration has more elements
		 */
		@Override
		public boolean hasNext() {		
			return (!traverse.isEmpty());
		}
		
		/**
		 * Returns the next element in the iteration
		 * 
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("A next element does not exist.");			
			}
			
			Node current = traverse.pop();
			
			if (current.right != null) {
				moveLeft(current.right);
			}
			
			return current.data;	
		}
		
		/**
		 * Pushes all the left nodes starting with the root onward
		 * 
		 * @param root of the tree 
		 */
		private void moveLeft(Node current) {
			while (current != null) {
				traverse.push(current);
				current = current.left;
			}
		}
		
	}
	
	/**
	 * Iterator class to iterate through the tree using preorder traversal
	 * contains hasNext() and next() methods
	 */
	private class PreOrderItr implements Iterator<E> {
		private ArrayDeque<Node> traverse = new ArrayDeque<Node>();
		
		//TreeIterator constructor
		public PreOrderItr(Node root) {
			if (root != null) {
				traverse.push(root);
			}
		}
		
		/**
		 * Returns true if the iteration has more elements 
		 * 
		 * @return true if iteration has more elements
		 */
		@Override
		public boolean hasNext() {		
			return (!(traverse.isEmpty()));
		}
		
		/**
		 * Returns the next element in the iteration 
		 * 
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements 
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("A next element does not exist.");			
			}
			
			Node current = traverse.pop();
			
			if (current.right != null) {
				traverse.push(current.right);
			}
			if (current.left != null) {
				traverse.push(current.left);
			}
			
			return current.data;
		}
	    
	}
	
	/**
	 * Iterator class to iterate through the tree using postorder traversal
	 * contains hasNext() and next() methods along with a traverseTree method
	 */
	private class PostOrderItr implements Iterator<E> {	 
		private ArrayDeque<Node> traverse;
		
		//TreeIterator constructor
		public PostOrderItr(Node root) {
			traverse = new ArrayDeque<Node>();
			traverseTree(root);
		}
		
		/**
		 * Returns true if the iteration has more elements 
		 * 
		 * @return true if iteration has more elements
		 */
		@Override
		public boolean hasNext() {		
			return (!(traverse.isEmpty()));
		}
		
		/**
		 * Returns the next element in the iteration 
		 * 
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("A next element does not exist.");			
			}
			
			Node current = traverse.pop();
	        if (!traverse.isEmpty()) {
	        	Node top = traverse.peek();
	            
	        	if (current == top.left) {
	                // find next leaf in right sub-tree
	                traverseTree(top.right);
	            }
	        }
	        return current.data;
		}
		
		/**
		 * Pushes all the nodes in the tree starting with the root 
		 * 
		 * @param root of the tree
		 */
		private void traverseTree(Node root) {
	        while (root != null) {
	        	traverse.push(root);
	            if (root.left != null) {
	                root = root.left;
	            }
	            else {
	                root = root.right;
	            }
	        }
	    }
		
	}	
	
	/**
	 * Returns an iterator over the elements in this tree in ascending order
	 * 
	 * @return an iterator over the elements in this tree in ascending order 
	 */
	public Iterator<E> iterator() {
		return new ForwardItr(root);
	}
	
	/**
	 * Returns an iterator over the elements in this tree in order of the preorder traversal
	 * 
	 * @return an iterator over the elements in this tree in order of the preorder traversal
	 */
	public Iterator<E> preorderIterator() {
		return new PreOrderItr(root);
	}
	
	/**
	 * Returns an iterator over the elements in this tree in order of the postorder traversal
	 * 
	 * @return an iterator over the elements in this tree in order of the postorder traversal
	 */
	public Iterator<E> postorderIterator() {
		return new PostOrderItr(root);
	}
	
	/**
	 * Returns the element at the specified position in this tree 
	 * The order of the indexed elements is the same as provided by this tree's iterator
	 * The indexing is zero based
	 * 
	 * @param index is the index of the element to return
	 * @return the element at the specified position in this tree
	 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("The index is out of range");
		}
		
		return get(root, index);
	}
	
	/**
	 * Actual recursive implementation of get method: perform the search
	 * 
	 * @param current is the node at which the recursive call is made
	 * @param index is the index of the element to return
	 * @return the element at the specified position in this tree
	 */
	private E get(Node current, int index) {	
		if (current == null) {
			return null;
		}

		if (current.key == index) { //if current value is equal to index then you found the element
			return current.data;
		}
		else if (current.key > index) { //if current value is larger than index, then call on left of current
			return get(current.left, index);
		}
		
		//else, recurse on right of current
		return get(current.right, index);
	}
	
	/**
	 * Returns the least element in this tree greater than or equal to the given element, or null if there is no such element
	 * 
	 * @param e is the value to match
	 * @return the least element greater than or equal to e, or null if there is no such element
	 * @throws NullPointerException if the specified element is null
	 */
	public E ceiling(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		return ceiling(root, e);
		
	}
	
	/**
	 * Actual recursive implementation of ceiling method
	 * 
	 * @param current is the node at which the recursive call is made 
	 * @param e is the value to match
	 * @return the least element greater than or equal to e, or null if there is no such element
	 * @throws NullPointerException if the specified node is null
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in the set
	 */
	private E ceiling(Node current, E e) throws NullPointerException, ClassCastException {
//		Node larger = null;
		
		if (current == null) {
			try {
//				@SuppressWarnings("unchecked") 
//				E bigger = (E)larger; //cast larger from node to E
//				return bigger;
				throw new NullPointerException("Element is null");
			}
			catch(ClassCastException ex){
				System.out.println("ClassCastException thrown");
			}
		}
		
		//compare value of e to that of current
		int comp = e.compareTo(current.data);
	    
		
		if (comp == 0) { //if e is equal to current, value is found
	    	return current.data;
	    }
	    if (comp < 0) { //if e is less than current, go to left subtree
	    	Node n = new Node(ceiling(current.left, e)); 
	        if (n != null) {
	        	return n.data;
	        }
	    } 
	    
	    //else, go to right subtree
	    return ceiling(current.right, e);
	    
	}
	
	/**
	 * Returns the greatest element in this set less than or equal to the given element, or null if there is no such element
	 *
	 * @param e is the value to match
	 * @return the greatest element less than or equal to e, or null if there is no such element
	 * @throws NullPointerException if the specified element is null
	 */
	public E floor(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		return floor(root, e);
	}
	
	/**
	 * Actual recursive implementation of floor method
	 * 
	 * @param current is the node at which the recursive call is made 
	 * @param e is the value to match
	 * @return the greatest element less than or equal to e, or null if there is no such element
	 * @throws NullPointerException if the specified element is null
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in the set
	 */
	private E floor(Node current, E e) throws NullPointerException, ClassCastException {
//		Node smaller = null;
		
		if (current == null) {
			try {
//				@SuppressWarnings("unchecked") 
//				E tinier = (E)smaller; //cast larger from node to E
//				return tinier;
				
				throw new NullPointerException("Element is null");
			}
			catch(ClassCastException ex){
				System.out.println("ClassCastException thrown");
			}
		}
		
		//if e is equal to current, value is found
		int comp = e.compareTo(current.data);
	   
		if (comp == 0) { //if e is equal to current, value is found
	    	return current.data;
	    }
	    
	    if (comp < 0) { //if e is less than current, go to left subtree
	        return floor(current.left, e); 
	    } 
	    
	    //else, go to right subtree
	    Node n = new Node(floor(current.right, e)); 
        if (n != null) {
        	return n.data;
        }	    
        
        return current.data;
	}
	
	/**
	 * Returns the first (lowest) element currently in this tree
	 * 
	 * @return the first (lowest) element currently in this tree
	 * @throws NoSuchElementException if this set is empty
	 */
	public E first() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException("The set is empty");
		}
		
		return first(root);
	}
	
	/**
	 * Actual recursive implementation of first method
	 * 
	 * @return the first (lowest) element currently in this tree
	 */
	private E first(Node current) {
		if (current == null) {
			return null;
		}
		
		Node n = current;
		
		//traverse through left subtree
		while(n.left != null) {
			n = n.left;
		}
		
		return n.data;
	}
	
	/**
	 * Returns the last (highest) element currently in this tree
	 * 
	 * @return the last (highest) element currently in this tree
	 * @throws NoSuchElementException if this set is empty
	 */
	public E last() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException("The set is empty");
		}
		
		return last(root);
	}
	
	/**
	 * Actual recursive implementation of first method
	 * 
	 * @return the first (lowest) element currently in this tree
	 */
	private E last(Node current) {
		if (current == null) {
			return null;
		}
		
		Node n = current;
		
		//traverse through right subtree
		while(n.right != null) {
			n = n.right;
		}
		
		return n.data;
	}
	
	/**
	 * Returns the greatest element in this set strictly less than the given element, or null if there is no such element
	 * 
	 * @param e is the value to match
	 * @return the greatest element less than e, or null if there is no such element
	 * @throws NullPointerException if the specified element is null
	 */
	public E lower(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		return lower(root, e);
	}
	
	/**
	 * Actual recursive implementation of lower method
	 * 
	 * @param current is the node at which the recursive call is made
	 * @param e is the value to match
	 * @return the greatest element less than e, or null if there is no such element
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in the set
	 */
	private E lower(Node current, E e) throws ClassCastException {
		if (current == null) {
			return null;
		}
		try { 
			
			int comp = current.data.compareTo(e);
			
			//if e is equal to current, return e
			if (comp == 0) {
				return e;
			}
			
			//if current is less than e
			else if (comp < 0) {
				if (current.right != null) {
					return lower(current.right, e);
				}
				else {
					return current.data;
				}
			}
			
			//if current is greater than e
			else if (comp > 0) {
				return lower(current.right, e); //traverse through left subtree
			}
			
		}
		catch(ClassCastException ex){
			System.out.println("ClassCastException thrown");
		}
		
		return null;		
			
		
	}
	
	/**
	 * Returns the least element in this tree strictly greater than the given element
	 * or null if there is no such element
	 * 
	 * @param e is the value to match
	 * @return the least element greater than e, or null if there is no such element
	 * @throws NullPointerException if the specified element is null
	 */
	public E higher(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException("Specified element is null.");
		}
		
		return higher(root, e);
	}
	
	/**
	 * Actual recursive implementation of higher method
	 * 
	 * @param current is the node at which the recursive call is made
	 * @param e is the value to match
	 * @return the greatest element less than e, or null if there is no such element
	 * @throws NullPointerException if the specified node is null
	 * @throws ClassCastException if the specified object cannot be compared with the elements currently in the set
	 */
	private E higher(Node current, E e) throws NullPointerException, ClassCastException {
		if (current == null) {
			return null;
		}
		
		try { 
			
			int comp = current.data.compareTo(e);
			
			int compLeft = current.left.data.compareTo(e);
			
			//if left and right of current are null and current is less than e, return null
			if (current.left == null && current.right == null && comp < 0) {
				return null;
			}
			
			//if current is greater than or equal to e, left of current is null
			//if current is greater than or equal to e, left of current is less than e
			if ((comp > 0 && current.left == null) || comp > 0 && compLeft < 0) {
				return current.data; //return current 
			}
			
			//if current is less than or equal to e
			if (comp < 0) {
				return higher(current.right, e); //traverse right subtree
			}
			
			else {
				return higher(current.left, e); //traverse left subtree
			}
		}
		catch(ClassCastException ex){
			System.out.println("ClassCastException thrown");
		}
		
		return null;
		
	}
	
	/**
	 * Compares the specified object with this tree for equality
	 * Returns true if the given object is also a tree, the two trees have the same size
	 * and every member of the given tree is contained in this tree
	 * 
	 * @param obj is object to be compared for equality with this tree
	 * @return true if the specified object is equal to this tree
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		return equals(root);
	}
	
	/**
	 * Actual recursive implementation of equals method
	 * 
	 * @param other is the node at which the recursive call is made
	 * @return true if the specified object is equal to this tree
	 */
	private boolean equals(Node other) {
		if (other == null) {
			return false;
		}
		
		//if they are not equal, return false
		if (this != other.data) {
			return false;
		}
		
		//if left of this reference is null, return false
		if (this.left == null && other.left != null) {
		    return false;
		}
		
		//if left of this reference exists but it is not equal to left of other, return false
		if (this.left != null && !this.left.equals(other.left)) {
		    return false;
		}
		
		//if right of this reference is null, return false
		if (this.right == null && other.right != null) {
		    return false;
		}
		
		//if right of this reference exists but it is not equal to right of other, return false
		if (this.right != null && !this.right.equals(other.right)) {
		    return false;
		}
		
		//else, return true
		return true;
	}
	
	/**
	 * Returns a string representation of this tree
	 * The string representation consists of a list of the tree's elements in the order 
	 * they are returned by its iterator (inorder traversal), enclosed in square brackets ("[]") 
	 * Adjacent elements are separated by the characters ", " (comma and space)
	 * Elements are converted to strings as by String.valueOf(Object)
	 * 
	 * @return a string representation of this collection.
	 */	
	@Override
	public String toString() {
		return "[" + toString(root) + "]";
	}
	
	/**
	 * Actual recursive implementation of toString method
	 * 
	 * @param current is the node at which the recursive call is made
	 * @return a string representation of this collection
	 */	
	private String toString(Node current) {
		String treeString = ""; //create empty string
		
		if (current == null) {
			return "";
		}
		
		treeString += toString(current.left); //append left subtree to string 
		treeString += current.toString(); //append current to string
		treeString += toString(current.right); //append right subtree to string
		return treeString + ", "; //return string representation
	}
	
	/**
	 * Produces tree like string representation of this tree. 
	 * Returns a string representation of this tree in a tree-like format. The string representation consists 
	 * of a tree-like representation of this tree. 
	 * Each node is shown in its own line with the indentation showing the depth of the node in this tree. 
	 * The root is printed on the first line, followed by its left subtree, followed by its right subtree. 
	 * 
	 * @return string containing tree-like representation of this tree.
	 * 
	 * From BST class in Ed workspace
	 */
	public String toStringTreeFormat() {
		StringBuffer sb = new StringBuffer(); 
        toStringTreeFormat(sb, root, 0);
        return sb.toString();
	}
	
	/**
	 * Actual recursive implementation of toStringTreeFormat method
	 * 
	 * @param sb is the stringbuffer used in toStringTreeFormat()
	 * @param n is the node at which the recursive call is made
	 * @param level represents the levels of the tree
	 * @return a string representation of this collection
	 * 
	 * From BST class in Ed workspace
	 */	
	public void toStringTreeFormat(StringBuffer sb, Node n, int level) {
		if (level > 0) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (n == null) {
            sb.append( "->\n"); 
            return;
        }
        else {
            sb.append(n.data + "\n"); 
        }

        //display the left subtree 
        toStringTreeFormat(sb, n.left, level+1); 
        //display the right subtree 
        toStringTreeFormat(sb, n.right, level+1);
	}
	
	/**
	 * Node class for this BST
	 * It implements the comparable interface
	 * 
	 * From BST class in Ed workspace
	 */
	private class Node implements Comparable<Node> {
	    E data;    
	    Node left, right;  
	    int key;

	    public Node(E data) {
	        this.data = data;
	    }
	    
	    public Node(int dat) {
	    	key = dat;
	    }
	    
	    public Node(E data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo (Node other) {
            if (BST.this.comparator == null) {
                return this.data.compareTo(other.data);
            
            }
            else {
                return comparator.compare(this.data, other.data); 
            }
        }
	}

	
}
