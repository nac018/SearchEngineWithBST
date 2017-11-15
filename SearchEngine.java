/*
 * Name: Nang Chen
 * Login: cs12saw
 * PID: A14205066
 * Date: 05/21/2017
 * File: SearchEngine.java
 * 
 * This is a class that implements a simple search engine
 * with the methods we implement in BSTree.java.
 */

package hw7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchEngine {

	/*
	 * Populate a BST from a file
	 * 
	 * @param searchTree - BST to be populated
	 * 
	 * @param fileName - name of the input file
	 * 
	 * @returns false if file not found, true otherwise
	 */
	public static boolean populateSearchTree(BSTree<String> searchTree, 
			String fileName) {
		File file = new File(fileName);
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				// read two lines - one for document and the next line for the
				// list of keywords
				String document = scanner.nextLine().trim();
				String keywords[] = scanner.nextLine().split(" ");
				//populate the search tree with relative information
				for (int i = 0; i < keywords.length; i++) {
					searchTree.insert(keywords[i].toLowerCase());
					searchTree.insertInformation(keywords[i].toLowerCase()
							, document.toLowerCase());
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\nFile not found!!");
			return false;
		}
		return true;
	}

	/*
	 * Search a query in a BST. Prints ArrayList of documents in which the query
	 * string is found(using print method).
	 * 
	 * @param searchTree - BST to be searched
	 * 
	 * @param fileName - query string
	 */
	public static void searchMyQuery(BSTree<String> searchTree, String query) {
		ArrayList<String> list = new ArrayList<String>();
		String key[] = query.split(" ");
		//create another arraylist to store all the keys and their information
		@SuppressWarnings("unchecked")
		ArrayList<String> keyList[] = new ArrayList[key.length];
		//if length of the input key is 0
		if(key.length <= 0){
			return;
		}
		//if length of the input key is 1
		else if(key.length == 1){
			if(searchTree.findKey(key[0].toLowerCase())){
			//add the only element to the tree
			list.addAll(searchTree.findMoreInformation
					(key[0].toLowerCase()));
			}
			print(query,list);
		}
		//if there are multiple input keys
		else if(key.length > 1){
			//if the key is found at the tree, add them to the arraylist
			if(searchTree.findKey(key[0].toLowerCase())){
				list.addAll(searchTree.findMoreInformation
						(key[0].toLowerCase()));
				}
			//if same key is found in tree, intersections need to be retained
			for(int i = 0;i<key.length;i++){
				if(searchTree.findKey(key[i].toLowerCase())){
					list.retainAll(searchTree.findMoreInformation
							(key[i].toLowerCase()));
				}
			}
			print(query,list);
			//populate the new arraylist with the target keys' information
			for(int i = 0;i<key.length;i++){
				if(searchTree.findKey(key[i].toLowerCase())){
				keyList[i] = searchTree.findMoreInformation
						(key[i].toLowerCase());
				}
			}
			for(int i = 0;i<key.length;i++){
				if(searchTree.findKey(key[i].toLowerCase())){
					//remove all the duplicates if possible
					keyList[i].removeAll(list);
					//add the non-duplicates to the list
					list.addAll(keyList[i]);
					//make sure the print is towards a non-empty list index
					if(!keyList[i].isEmpty()){
					print(key[i],keyList[i]);
					}
				}
				else{
					//print failing results
					print(key[i],null);
				}
			}
		}
	}

	/*
	 * Print method
	 * 
	 * @param query input
	 * 
	 * @param documents - result of SearchMyQuery
	 */

	public static void print(String query, ArrayList<String> documents) {
		if (documents == null || documents.isEmpty())
			System.out.println("The search yielded no results for " + query);
		else {
			Object[] converted = documents.toArray();
			Arrays.sort(converted);
			System.out.println("Documents related to " + query + " are: " + 
			Arrays.toString(converted));
		}
	}

	public static void main(String[] args) {
		args = new String[]{"input","fish blue"}; //extra
		if (args.length < 2) {
			System.err.println("Invalid number of arguments passed");
			return;
		}
		BSTree<String> searchTree = new BSTree<>(); 
		String fileName = args[0];
		String query = args[1];
		// Create my BST from file
		boolean check = populateSearchTree(searchTree, fileName);
		if (check == false) {  
			System.out.println("\nUnable to create search tree");
		}
		searchMyQuery(searchTree, query);
	}
}
