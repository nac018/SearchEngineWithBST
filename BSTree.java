/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/21/2017
 * File: BSTree.java
 * 
 * This is a class that implements the BSTree structure with
 * BSTNode, BSTree and Iterator classes. The height, specific key 
 * and count of leaves will change after insertion. Information 
 * can also be read inside the nodes of the trees.
 */

package hw7;

import java.util.*;

/**
 * BSTree class
 * 
 * @author Nang Chen
 *
 * @param <T>
 */
public class BSTree<T extends Comparable<? super T>> {

	private int nelems;
	private BSTNode root;

	/**
	 * BSTNode inner class
	 * 
	 * @author Nang Chen
	 *
	 */
	protected class BSTNode {

		T key;
		ArrayList<T> relatedInfo;
		BSTNode left;
		BSTNode right;

		/**
		 * A constructor that initializes the BSTNode instance variables.
		 * 
		 * @param left
		 * @param right
		 * @param relatedInfo
		 * @param key
		 */
		public BSTNode(BSTNode left, BSTNode right, ArrayList<T> relatedInfo, T key) {
			this.left = left;
			this.right = right;
			this.relatedInfo = relatedInfo;
			this.key = key;
		}

		/**
		 * A constructor that initializes the BSTNode instance variables.
		 * 
		 * @param left
		 * @param right
		 * @param key
		 */
		public BSTNode(BSTNode left, BSTNode right, T key) {
			this.left = left;
			this.right = right;
			this.key = key;
			this.relatedInfo = new ArrayList<T>();
		}

		/**
		 * Return the key
		 * 
		 * @return key
		 */
		public T getKey() {
			return key;
		}

		/**
		 * Returns the left child of the node
		 * 
		 * @return left
		 */
		public BSTNode getLeft() {
			return left;
		}

		/**
		 * Returns the right child of the node
		 * 
		 * @return right
		 */
		public BSTNode getRight() {
			return right;
		}

		/**
		 * Returns the ArrayList of the node
		 * 
		 * @return relatedInfo
		 */
		public ArrayList<T> getRelatedInfo() {
			return relatedInfo;
		}

		/**
		 * Setter for left pointer of the node
		 * 
		 * @param newLeft
		 */
		public void setLeft(BSTNode newLeft) {
			this.left = newLeft;
		}

		/**
		 * Setter for right pointer of the node
		 * 
		 * @param newRight
		 */
		public void setRight(BSTNode newRight) {
			this.right = newRight;
		}

		/**
		 * Setter for the ArrayList of the node
		 * 
		 * @param newInfo
		 */
		public void setRelatedInfo(ArrayList<T> newInfo) {
			this.relatedInfo = newInfo;
		}

		/**
		 * Append new info to the end of the existing ArrayList of the node
		 * 
		 * @param info
		 */
		public void addNewInfo(T info) {
			relatedInfo.add(info);
		}

		/**
		 * Remove info from the ArrayList of the node
		 * 
		 * @param info
		 * @return true if info exists to be removed, else false
		 */
		public boolean removeInfo(T info) {
			if (relatedInfo.contains(info)) {
				relatedInfo.remove(info);
				return true;
			}
			return false;
		}
		
		private BSTNode insertHelper(BSTNode current, T key) {
			int value = key.compareTo(current.getKey());

			if (value == 0) {
				return null;
			}
			if (value < 0) {
				if (current.getLeft() == null) {
					current.setLeft(new BSTNode(null, null, key));
				} else {
					insertHelper(current.getLeft(), key);
				}
				return current;
			} else {
				if (current.getRight() == null) {
					current.setRight(new BSTNode(null, null, key));
				} else {
					insertHelper(current.getRight(), key);
				}
				return current;
			}
		}
	}

	/**
	 * A 0-arg constructor that initializes root to null and nelems to 0.
	 */
	public BSTree() {
		this.root = null;
		this.nelems = 0;
	}

	/**
	 * Returns the root of BSTree.
	 * 
	 * @return root, else null if size is 0
	 */
	public BSTNode getRoot() {
		if (nelems == 0) {
			return null;
		} else {
			return root;
		}
	}

	/**
	 * Return size.
	 * 
	 * @return nelems
	 */
	public int getSize() {
		return nelems;
	}

	/**
	 * Inserts a key into the BST
	 * 
	 * @param key
	 */
	public void insert(T key) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			root = new BSTNode(null, null, key);
		} else {
			root.insertHelper(root, key);
		}
		nelems++;
	}

	/**
	 * Return true if the key is found in the tree
	 * 
	 * @param key
	 * @return true if key is found, else false
	 */
	public boolean findKey(T key) {
		return findKeyHelper(root, key);
	}

	/**
	 * Inserts info into the ArrayList of the node whose key is key.
	 * 
	 * @param key
	 * @param info
	 */
	public void insertInformation(T key, T info) {
		if (key == null || info == null) {
			throw new NullPointerException();
		}
		if (!findKey(key)) {
			throw new IllegalArgumentException();
		} else {
			findInfoHelper(root, key).getRelatedInfo().add(info);
		}
	}

	/**
	 * Return the ArrayList of the node with key value key.
	 * 
	 * @param key
	 * @return arraylist of the node with "key".
	 */
	public ArrayList<T> findMoreInformation(T key) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (!findKey(key)) {
			throw new IllegalArgumentException();
		} else {
			return findInfoHelper(root, key).getRelatedInfo();
		}
	}

	/**
	 * Returns the height of the tree
	 * 
	 * @return height of the tree
	 */
	public int findHeight() {
		return findHeightHelper(root);
	}

	/**
	 * Returns the number of leaf nodes in the tree.
	 * 
	 * @return leaf counts
	 */
	public int leafCount() {
		return leafCountHelper(root);
	}

	

	private boolean findKeyHelper(BSTNode current, T key) {
		if (key == null) {
			throw new NullPointerException();
		}
		if (current == null) {
			return false;
		}
		if (key.compareTo(current.getKey()) < 0) {
			return findKeyHelper(current.getLeft(), key);
		} else if (key.compareTo(current.getKey()) > 0) {
			return findKeyHelper(current.getRight(), key);
		} else {
			return true;
		}
	}

	private BSTNode findInfoHelper(BSTNode current, T key) {
		if (key.compareTo(current.getKey()) < 0) {
			return findInfoHelper(current.getLeft(), key);
		} else if (key.compareTo(current.getKey()) > 0) {
			return findInfoHelper(current.getRight(), key);
		} else {
			return current;
		}
	}

	private int findHeightHelper(BSTNode current) {
		if (current == null) {
			return -1;
		} else {
			int leftHeight = findHeightHelper(current.getLeft());
			int rightHeight = findHeightHelper(current.getRight());
			if (leftHeight > rightHeight) {
				return leftHeight + 1;
			} else {
				return rightHeight + 1;
			}
		}
	}

	private int leafCountHelper(BSTNode current) {
		if (current == null) {
			return 0;
		}
		if (current.getLeft() == null && current.getRight() == null) {
			return 1;
		} else {
			int leftLeafCount = leafCountHelper(current.getLeft());
			int rightLeafCount = leafCountHelper(current.getRight());
			return leftLeafCount + rightLeafCount;
		}
	}

	/**
	 * BSTree Iterator inner class
	 * 
	 * @author Nang Chen
	 *
	 */
	public class BSTree_Iterator implements Iterator<T> {
		Stack<BSTNode> stack;

		/**
		 * Constructor that initializes the Stack with the leftPath of the root.
		 */
		public BSTree_Iterator() {
			stack = new Stack<BSTNode>();
			while (root != null) {
				stack.push(root);
				root = root.getLeft();
			}
		}

		/**
		 * Returns false if the Stack is empty, else true.
		 */
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * Returns the next item in the BST.
		 */
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			} else {
				BSTNode node = stack.pop();
				T value = node.getKey();
				if (node.getRight() != null) {
					node = node.getRight();
					while (node != null) {
						stack.push(node);
						node = node.getLeft();
					}
				}
				return value;
			}
		}
	}

	public Iterator<T> iterator() {
		return new BSTree_Iterator();
	}

	/**
	 * Find the intersecting elements between two lists.
	 * 
	 * @param iter1
	 * @param iter2
	 * @return listForInterSection
	 */
	public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
		//arraylist for the first tree
		ArrayList<T> listForIter1 = new ArrayList<T>();
		while (iter1.hasNext()) {
			listForIter1.add(iter1.next());
		}
		//arraylist for the second tree
		ArrayList<T> listForIter2 = new ArrayList<T>();
		while (iter2.hasNext()) {
			listForIter2.add(iter2.next());
		}
		//if first tree is larger than second tree
		if(listForIter1.size() >= listForIter2.size()){
		ArrayList<T> listForIntersection = new ArrayList<T>();
		for(int i = 0;i<listForIter1.size();i++){
			for(int j = 0;j<listForIter2.size();j++){
				if(listForIter1.get(i).equals((listForIter2).get(j))){
					listForIntersection.add(listForIter1.get(i));
				}
			}
		}
		return listForIntersection;
		}
		else{
			ArrayList<T> listForIntersection = new ArrayList<T>();
			for(int i = 0;i<listForIter2.size();i++){
				for(int j = 0;j<listForIter1.size();j++){
					if(listForIter1.get(j).equals((listForIter2).get(i))){
						listForIntersection.add(listForIter2.get(i));
					}
				}
		}
		return listForIntersection;
		}
	}

	/**
	 * This method is to count the number of tree nodes at a specific level.
	 * 
	 * @param level
	 * @return count of the specific level
	 */
	public int levelCount(int level){
		return levelCountHelper(root,level);
	}
	
	private int levelCountHelper(BSTNode current,int level){
		if(current == null){
			return 0;
		}
		if(level == 0){
			return 1;
		}
		if(level > findHeight()){
			return -1;
		}
		return levelCountHelper(current.getLeft(),level-1) + levelCountHelper(current.getRight(),level-1);
	}
	
	/**
	 * Check if the BST is complete
	 * 
	 * @param current
	 * @return true if the tree is full,else false
	 */
	public boolean isCompleteBST(){
		return isCompleteBSTHelper(root);
	}
	
	private boolean isCompleteBSTHelper(BSTNode current){
		if(current == null){
			return true;
		}
		else{
			if(current.getLeft() == null && current.getRight() == null){
				return true;
			}
			if(current.getLeft() == null && current.getRight() != null){
				return false;
			}
			if(current.getLeft() != null && current.getRight() == null){
				return false;
			}
			if(current.getLeft() != null && current.getRight() != null){
				return isCompleteBSTHelper(current.getLeft()) && isCompleteBSTHelper(current.getRight());
			}
		}
		return false;
	}
}
