/** 
 * CS 146 Section 6 
 * Data Structures and Algorithms
 * Project 3
 * 
 * @author Michelle Lai, Michelle Song
 *
 * AvlTree is a balanced BST where the maximum height difference between the left and right subtrees for each node is 1
 *	AvlTree extends BinarySearchTree and implements the DataCounter to store data counts into the tree
 */

@SuppressWarnings("unchecked")
public class AvlTree extends BinarySearchTree implements DataCounter
{
	private BSTNode root;
	private static final int ALLOWED_IMBALANCE = 1;

	/**
	 * Constructs an empty tree
	 */
	public AvlTree ()
	{
		root = null;
	}

	/**
	 * Returns the height of the node
	 * @param t the node of interest
	 * @return the height of the node
	 */
	public int height( BSTNode t )
	{
		if (t == null) return -1;
		return t.height(t);
	}
	
    /**
     * Get an array of all of the data counts in the DataCounter structure.
     * Each element is an unique element with a string data and count
     * 
     * @return an array of the data counts.
     */
    public DataCount<String>[] getCounts() {
    	@SuppressWarnings("unchecked")
        DataCount<String>[] counts = new DataCount[size];
        if (root != null)
        {
            traverse(root, counts, 0);
        }
        return counts;
    }

    /**
     * Increment the count for a particular data element.
     * Adds the element into the data structure if not already present.
     * 
     * @param data data element whose count to increment.
     */
	public void incCount(String data)
	{
		insert(data, root);
	}
	
	/**
	 * Inserts the given element x into the tree, re-balancing when necessary
	 * @param x the element to be inserted
	 * @param t the node that roots the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode insert( String x, BSTNode t )
	{
		if ( root == null ) 
		{
			root = new BSTNode(x);
		}
		if( t == null )
		{
			return new BSTNode( x );
		}
		if ( x.compareTo((String) t.data) < 0)
			t.left = insert( x, t.left );
		else if ( x.compareTo((String) t.data) > 0)
			t.right = insert( x, t.right );
		else
		{
			t.count++;
		}
		return balance( t );
	}

	/**
	 * Balances the tree following the insertion or removal of a node in the tree and adjusts the height of all affected nodes
	 * @param t the node that roots the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode balance( BSTNode t )
	{
		if( t == null )
			return t;
		if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
			if( height( t.left.left ) >= height( t.left.right ) )
			{
				System.out.println("Single left-left rotation: " + t.data);
				if (root == t)
				{
					t = rotateWithLeftChild( t );
					root = t;
				}
				else t = rotateWithLeftChild( t );
			}
			else
			{
				System.out.println("Double left-right rotation: " + t.data);
				if (root == t)
				{
					t = doubleWithLeftChild( t );
					root = t;
				}
				else t = doubleWithLeftChild( t );
			}
		else
			if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
				if( height( t.right.right ) >= height( t.right.left ) )
				{
					System.out.println("Single right-right rotation: " + t.data);
					if (root == t)
					{
						t = rotateWithRightChild( t );
						root = t;
					}
					else t = rotateWithRightChild( t );
				}
				else
				{
					System.out.println("Double right-left rotation: " + t.data);
					if (root == t)
					{
						t = doubleWithRightChild( t );
						root = t;
					}
					else t = doubleWithRightChild( t );
				}
		t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
		return t;
	}


	/**
	 * CASE 1: Single left-left rotation
	 * Rotate binary tree node with left child and updates heights of nodes affected
	 * @param k2 the current root of the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode rotateWithLeftChild( BSTNode k2 )
	{
		BSTNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.left ), k2.height ) + 1;
		return k1;
	}

	/**
	 * CASE 4: Single right-right rotation
	 * Rotate binary tree node with right child and updates heights of nodes affected
	 * @param k2 the current root of the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode rotateWithRightChild( BSTNode k2 )
	{
		BSTNode k1 = k2.right;
		k2.right = k1.left;
		k1.left = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.right ), k2.height ) + 1;
		return k1;
	}

	/**
	 * CASE 2 : Double left-right rotation
	 * Double rotate binary tree node: first left child with its right child; then node k3 with new left child and updates heights of nodes affected
	 * @param k3 the current root of the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode doubleWithLeftChild( BSTNode k3 )
	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}

	/**
	 * CASE 3: Double right-left rotation
	 * Double rotate binary tree node: right left child with its left child; then node k3 with new right child and updates heights of nodes affected
	 * @param k3 the current root of the subtree
	 * @return the new root of the subtree
	 */
	public BSTNode doubleWithRightChild( BSTNode k3 )
	{
		k3.right = rotateWithLeftChild( k3.right );
		return rotateWithRightChild( k3 );
	}

	/**
	 * Helper method to find the leftmost node of a given node
	 * @param right2 the node of interest
	 * @return the leftmost node of right2
	 */
	public BSTNode findMin( BSTNode right2 ) 
	{
		if (right2.left == null) return right2;
		BSTNode current = right2;
		while (current.left != null)
		{
			current = current.left;
		}
		return current;
	}

	/**
	 * Searches the tree for a target node
	 * @param x the element to search for
	 * @param t the starting node of the search
	 * @return boolean result of the search
	 */
	public boolean search (String x, BSTNode t)
	{
		BSTNode current = t;
		while (current != null)
		{
			if (x.compareTo((String) t.data) < 0) current = current.left;
			else if (x.compareTo((String) t.data) > 0) current = current.right;
			else if (x.equals(current.data)) return true;
		}
		return false;
	}

	/**
	 * Returns the root of the tree
	 * @return the root of the tree
	 */
	public BSTNode getRoot()
	{
		return root;
	}
}
