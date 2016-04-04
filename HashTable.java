/**
 * CS 146 Section 6 
 * Data Structures and Algorithms
 * Project 3
 * 
 * @author Michelle Lai, Michelle Song
 *
 * HashTable implements the DataCounter interface in order to store data count elements into the buckets
 * The HashTable contains buckets which each use a chaining linked list
 * The buckets are determined depending on a specified hash code
 */

@SuppressWarnings("unchecked")
public class HashTable implements DataCounter<String> {

	private Node[] buckets;
	private int numBuckets;
	private int numWords;
	private int bucketSize;

	/**
	 * Constructs an empty hash table with the given size
	 * @param size the default size of the hash table array
	 */
	public HashTable(int size)
	{
		bucketSize = size;
		numWords = 0;
		numBuckets = 0;
		buckets = new Node[bucketSize];
	}

	/**
	 * Grows the hash table by making a new hash table double the length
	 * and copying over the old values into the new hash table
	 */
	public void grow()
	{
		int newSize = bucketSize*2;
		HashTable newBucket = new HashTable(newSize);

		for (int i = 0; i < buckets.length; i++)
		{
			newBucket.buckets[i] = buckets[i];
		}
		newBucket.numBuckets = numBuckets;
		newBucket.numWords = numWords;
		buckets = newBucket.buckets;
	}

	/**
	 * Returns the DataCount of the specified word in the hash table
	 * 
	 * @param word the data to be found
	 * @return the correct DataCount for the specified word 
	 * 			or null if the word doesn't exist in the hash table
	 */
	public DataCount find(String word) 
	{
		int index = word.length() - 1;
		Node current = buckets[index];
		while (current != null)
		{
			if (current.dataCount.data.equals(word)) 
			{
				return current.dataCount;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Checks if the specified word is in the hash table
	 * 
	 * @param word the word to be checked
	 * @return boolean result of the search
	 */
	public boolean contains(String word)
	{
		int index = hashIndex(word);
		Node current = buckets[index];
		while (current != null)
		{
			if (current.dataCount.data.equals(word)) return true;
			current = current.next;
		}
		return false;
	}

	/**
	 * Computes the custom hash code for the given word
	 * 
	 * @param word the word to which a hash code is desired
	 * @return the hash code of the word
	 */
	public int hashIndex(String word)
	{
		return word.length() - 1;
	}

	/**
	 * Get an array of all of the data counts in the DataCounter structure.
	 * Each element is an unique element with a string data and count
	 * 
	 * @return an array of the data counts.
	 */
	public DataCount<String>[] getCounts() {
		DataCount<String>[] counts = new DataCount[numWords];
		int loc = 0;
		for (int x = 0; x < bucketSize; x++)
		{
			if (buckets[x] != null)
			{
				Node current = buckets[x];
				while (current != null)
				{
					counts[loc] = current.dataCount;
					loc++;
					current = current.next;
				}
			}
		}
		return counts;
	}

	/**
	 * The number of unique data elements in the structure.
	 * 
	 * @return the number of unique data elements in the structure.
	 */
	public int getSize() {
		return numWords;
	}

	/**
	 * Increment the count for a particular data element.
	 * Adds the element into the data structure if not already present.
	 * 
	 * @param data data element whose count to increment.
	 */
	public void incCount(String word) {

		int index = word.length() - 1;
		Node current = buckets[index];
		Node previous = current;
		boolean found = false;
		while (current != null)
		{
			if (current.dataCount.data.equals(word)) 
			{
				current.dataCount.count++;
				found = true;
			}
			previous = current;
			current = current.next;
		}
		if (!found)
		{
			Node newNode = new Node();
			DataCount newData = new DataCount(word, 1);
			newNode.dataCount = newData;
			if (buckets[index] == null) 
			{
				buckets[index] = newNode;
				numBuckets++;
			}
			else 
			{
				previous.next = newNode;
			}
			numWords++;
		}
		if (numBuckets == bucketSize/2) grow();
	}

	/**
	 * Node of the LinkedList in the hash table buckets
	 * Stores the DataCount element and a reference to the next node
	 * 
	 * @author Michelle Lai, Michelle Song
	 */
	class Node {
		public DataCount dataCount;
		public Node next;
	}

	/**
	 * LinkedList for the chained buckets
	 * Stores the reference to the first element for each bucket
	 * 
	 * @author Michelle Lai, Michelle Song
	 */
	class LinkedList {
		public Node first;
	}
}
