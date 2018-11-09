package com.soracasus.scrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 	A simple structure to represent a node within the binary tree
 * @author Sora Casus
 *	
 * @field data - The data to be stored within the node
 * @field left - The node to the left of the node
 * @field right - The node to the right of the node
 * @field parent - The parent node of the current node
 */
class Node {
	public String data;
	public Node left;
	public Node right;
	public Node parent;

	public Node(String data) {
		this.data = data;
		this.parent = null;
		this.left = null;
		this.right = null;
	}

}

class BinaryTree {
	private static int index;
	private Node diaryNode;

	/**
	 * Builds a binary tree based off the given in order and post order output
	 * @param in		- The tree in order
	 * @param post		- The tree in post order
	 * @param start		- The start of the segment
	 * @param end		- The end of the segment
	 * @param parent	- The parent node, null if the root of the tree
	 * @return			- The root node of the tree
	 */
	public Node BuildTreeFromPostIN(ArrayList<String> in, ArrayList<String> post, int start, int end, Node parent) {
		if (start > end) {
			// Error checking
			return null;
		}

		String name = post.get(index).toLowerCase();

		Node node = new Node(post.get(index));
		node.parent = parent;
		index--;

		if (name.equals("diary")) {
			diaryNode = node;
		}

		// If the node has no children
		if (start == end) {
			return node;
		}

		int _index = in.indexOf(node.data);

		node.right = BuildTreeFromPostIN(in, post, _index + 1, end, node);
		node.left = BuildTreeFromPostIN(in, post, start, _index - 1, node);

		return node;
	}

	/**
	 * 	Construct the binary tree from its in order form and post order form
	 * @param in	- The tree in in order form
	 * @param post	- The tree in post order form
	 * @param n		- The number of nodes in the tree
	 * @return		- The root node of the tree
	 */
	public Node BuildTree(ArrayList<String> in, ArrayList<String> post, int n) {
		index = n - 1;
		return BuildTreeFromPostIN(in, post, 0, n - 1, null);
	}

	/*
	 * Prints the path from the root to the diary node of the tree
	 */
	public void FindDiary() {

		Node node = diaryNode;
		Stack<String> path = new Stack<>();
		
		while (node != null) {
			path.push(node.data);
			node = node.parent;
		}
		
		while(!path.isEmpty()) {
			System.out.println(path.pop());
		}

	}

}

public class Main {

	public static void main(String[] args) throws IOException {
		// Creates a BufferedReader to read input from the console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// Read the first line of the input, in this case, the number of nodes within the binary tree
		int n = Integer.parseInt(reader.readLine());
		// Read the next line of the input, in this case, the binary tree in post order
		String postorder = reader.readLine();
		// Read the next line of the input, in this case, the binary tree in in order
		String inorder = reader.readLine();
		// Split the post order string into tokens (Chunks) separated by a space character ' '
		StringTokenizer stPostorder = new StringTokenizer(postorder);
		// Split the in order string into tokens (Chunks) separated by a space character ' '
		StringTokenizer stInorder = new StringTokenizer(inorder);
		// Create the binary tree object to work with
		BinaryTree tree = new BinaryTree();

		// Create Array lists to parse the tokens to read
		ArrayList<String> inOrder = new ArrayList<>();
		ArrayList<String> postOrder = new ArrayList<>();

		// Place each of the tokens into the respective ArrayList
		while (stPostorder.hasMoreTokens()) {
			postOrder.add(stPostorder.nextToken());
		}

		while (stInorder.hasMoreTokens()) {
			inOrder.add(stInorder.nextToken());
		}

		// Create the tree from the given data
		tree.BuildTree(inOrder, postOrder, n);

		// Locate and print the path to the diary
		tree.FindDiary();
	}

}
