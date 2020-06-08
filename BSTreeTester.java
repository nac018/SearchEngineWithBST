package hw7;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class BSTreeTester {
	private BSTree<String> BST;
	private BSTree<String> emptyBST;

	// set up a bst with 7 characters and a empty bst also
	@Before
	public void setUp() {
		emptyBST = new BSTree<String>();
		BST = new BSTree<String>();
		BST.insert("P");
		BST.insert("C");
		BST.insert("E");
		BST.insert("Y");
		BST.insert("U");
		BST.insert("K");
		BST.insert("A");
	}

	// test constructor
	@Test
	public void testCtor() {
		assertEquals(0, emptyBST.getSize());
		assertEquals(null, emptyBST.getRoot());
	}

	// test getRoot
	@Test
	public void testGetRoot() {
		assertEquals(null, emptyBST.getRoot());
		assertEquals("P", BST.getRoot().getKey());
	}

	// test getSize
	@Test
	public void testGetSize() {
		assertEquals(0, emptyBST.getSize());
		assertEquals(7, BST.getSize());
	}

	// test insert
	@Test
	public void testInsert() {
		BST.insert("Z");
		BST.insert("L");
		assertEquals("L", BST.getRoot().getLeft().
				getRight().getRight().getRight().getKey());
		assertEquals("Z", BST.getRoot().getRight().getRight().getKey());
	}

	// test insert's null pointer exception
	@Test
	public void testInsertException() {
		try {
			BST.insert(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}
	}

	// test findKey
	@Test
	public void testFindKey() {
		assertTrue(BST.findKey("Y"));
		assertTrue(BST.findKey("E"));
	}

	// test findKey's null pointer exception
	@Test
	public void testFindKeyException() {
		try {
			BST.findKey(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}
	}

	// test insertInformation
	@Test
	public void testInsertInformation() {
		BST.insertInformation("P", "dayum");
		BST.insertInformation("P", "ayo");
		assertEquals("dayum", BST.getRoot().getRelatedInfo().get(0));
		assertEquals("ayo", BST.getRoot().getRelatedInfo().get(1));
	}

	// test insertInformation's null pointer exception
	@Test
	public void testInsertInformationException() {
		try {
			BST.insertInformation("A", null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}

		try {
			BST.insertInformation(null, "A");
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}

		try {
			BST.insertInformation("B", "A");
			fail("Should have generated an exception");
		} catch (IllegalArgumentException e) {
		}
	}

	// test findMoreInformation
	@Test
	public void testFindMoreInformation() {
		BST.insertInformation("P", "dayum");
		BST.insertInformation("P", "ayo");
		assertEquals("dayum", BST.findMoreInformation("P").get(0));
		assertEquals("ayo", BST.findMoreInformation("P").get(1));
	}

	// test findMoreInformation's null pointer exception
	@Test
	public void testFindMoreInformationException() {
		try {
			BST.findMoreInformation(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
		}

		try {
			BST.findMoreInformation("B");
			fail("Should have generated an exception");
		} catch (IllegalArgumentException e) {
		}
	}

	// test findHeight
	@Test
	public void testFindHeight() {
		assertEquals(-1, emptyBST.findHeight());
		assertEquals(3, BST.findHeight());
	}

	// test leafCount
	@Test
	public void testLeafCount() {
		assertEquals(0, emptyBST.leafCount());
		assertEquals(3, BST.leafCount());
	}

	// test iterator
	@Test
	public void testIterator() {
		Iterator<String> iter = emptyBST.iterator();
		try {
			iter.next();
			fail("Should have generated an exception");
		} catch (NoSuchElementException e) {
		}
		Iterator<String> iter1 = BST.iterator();
		assertEquals("A", iter1.next());
	}

	// test hasNext
	@Test
	public void testHasNext() {
		Iterator<String> iter = BST.iterator();
		iter.next();
		iter.next();
		iter.next();
		iter.next();
		assertTrue(iter.hasNext());
		iter.next();
		iter.next();
		iter.next();
		assertFalse(iter.hasNext());
	}

	// test next
	@Test
	public void testNext() {
		Iterator<String> iter = BST.iterator();
		assertEquals("A", iter.next());
		assertEquals("C", iter.next());
		assertEquals("E", iter.next());
		assertEquals("K", iter.next());
	}

	// test next's exception
	@Test
	public void testNextException() {
		Iterator<String> iter = BST.iterator();
		try {
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			fail("Should have generated an exception");
		} catch (NoSuchElementException e) {
		}
	}
	
	//test intersection
	@Test
	public void testIntersection(){
		BSTree<String> bst = new BSTree<String>();
		bst.insert("P");
		bst.insert("O");
		bst.insert("U");
		bst.insert("C");
		bst.insert("D");
		bst.insert("W");
		bst.insert("A");
		Iterator<String> iter = BST.iterator();
		Iterator<String> iter1 = bst.iterator();
		System.out.println(Arrays.toString(BST.intersection(iter,iter1).toArray()));
	}
	
	//test levelCount
	@Test
	public void testLevelCount(){
		assertEquals(3,BST.levelCount(2));
		assertEquals(1,BST.levelCount(3));
	}
	
	//test isCompleteBST
	@Test
	public void testIsCompleteBST(){
		BSTree<String> bst = new BSTree<String>();
		bst.insert("P");
		bst.insert("C");
		bst.insert("Y");
		bst.insert("A");
		bst.insert("E");
		bst.insert("U");
		bst.insert("Z");
		assertTrue(bst.isCompleteBST());
	}
}
