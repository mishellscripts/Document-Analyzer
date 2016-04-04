/**
 * CS 146 Section 6 
 * Data Structures and Algorithms
 * Project 3
 * 
 * @author Michelle Lai, Michelle Song
 *
 * An executable that determines the difference metric of two files given by the user
 * Determines this by calculating the frequency, removing outliers, and comparing a total sum
 * Uses the data structure specified by the user
 */

import java.io.File;
import java.io.IOException;
@SuppressWarnings("unchecked")

public class Correlator {
	private static DataCounter<String> counter;
	private static String sort;

	/**
	 * Constructs a correlator with the data structure specified by the user
	 * 
	 * @param a the string key specified by the user that represents a data structure
	 */
	public Correlator(String dataStructure, String sortingMethod)
	{
		// Passing first parameter input to create the corresponding data structure

		// Selected binary search tree
		if(dataStructure.compareTo("-b")==0)
		{
			BinarySearchTree bst = new BinarySearchTree();
			counter = (DataCounter<String>)(bst);
			System.out.println("BST");

		}
		// Selected AVL tree
		else if(dataStructure.compareTo("-a")==0)
		{
			AvlTree avl = new AvlTree();
			counter = (DataCounter<String>)(avl);
			System.out.println("AVL");
		}
		// Selected hashtable
		else if(dataStructure.compareTo("-h")==0)
		{
			counter = new HashTable(100);
			System.out.println("HashTable");
		}
		else {
			System.err.println("\tSaw "+ dataStructure +" instead of -b -a -h as first argument");
			System.exit(1);
		}


		// Passing second parameter input to use the corresponding sorting method

		if (sort.compareTo("-is") == 0 || sort.compareTo("-qs") == 0 || sort.compareTo("-ms") == 0)
		{
			sort = sortingMethod;
		}
		else 
		{
			System.err.println("\tSaw "+ sortingMethod +" instead of -is -qs -ms as first argument");
			System.exit(1);	
		}
	}

	/**
	 * Computes the total words in a given file
	 * 
	 * @param file the file to be examined
	 * @return the total number of words in the file
	 */
	public static int totalWords(DataCount<String>[] file)
	{
		int words = 0;
		for (int x = 0; x < file.length; x++)
		{
			if (file[x] != null)
			{
				words = words + file[x].count;
			}
		}
		return words;
	}

	/**
	 * Calculates the difference metric of two different files
	 * Normalizes the arrays so that considerably low and high values are not added to the total
	 * The lowest value is 0, with the files being identical
	 * Values such as 30, can relate to the two files being very similar
	 * 
	 * @param f1 the DataCount sorted array for file 1
	 * @param f2 the DataCount sorted array for file 2
	 * @return the total difference metric between the two files
	 */
	public static double differenceMetric(DataCount<String>[] f1, DataCount<String>[] f2)
	{
		double sum = 0;
		for (int i = 0; i < f1.length; i++)
		{
			for (int j = 0; j < f2.length; j++)
			{		
				if (f1[i] != null && f2[j] != null)
				{
					if (f1[i].data.equals(f2[j].data))
					{
						double normalize1 = totalWords(f1)/100;
						double normalize2 = totalWords(f2)/100;
						double percent1 = f1[i].count/normalize1;
						double percent2 = f2[j].count/normalize2;
						double difference = Math.abs(percent1-percent2);
					}
				}
			}
		}
		return sum;
	}

	/**
	 * Calculates the frequences for all the words in a given file
	 * Uses file reader to cycle through all the words in the file and updates them into
	 * the specified data structure by simply adding them or incrementing the count if already present
	 * Sorts the array by descending count 
	 * 
	 * @param file the file which its frequencies need to be calculated
	 * @return the array of DataCount elements for all the words in the file
	 */
	public static DataCount<String>[] calculateFrequencies(String file)
	{
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + e);
			System.exit(1);
		}
		DataCount<String>[] counts = counter.getCounts();
		sortByDescendingCount(counts);
		return counts;
	}

	/**
	 * Sorts the given array in descending order by its count element
	 * 
	 * @param counts the array to be sorted
	 */
	private static <E extends Comparable<? super E>> void sortByDescendingCount(
			DataCount<E>[] counts) {

		if (sort.compareTo("-is") == 0)
		{
			for (int i = 1; i < counts.length; i++) {
				DataCount<E> x = counts[i];
				int j;
				for (j = i - 1; j >= 0; j--) {
					if (counts[j] != null && x != null)
					{
						if (counts[j].count >= x.count) {
							break;
						}
					}
					counts[j + 1] = counts[j];
				}
				counts[j + 1] = x;
			}
		}
		else if (sort.compareTo("-qs") == 0)
		{
			quickSort(counts);
		}
		else if (sort.compareTo("-ms") == 0)
		{
			mergeSort(counts);
		}
	}

	private static <E extends Comparable<? super E>> void quickSort(DataCount<E>[] counts)
	{

	}
	private static <E extends Comparable<? super E>> void mergeSort(DataCount<E>[] counts) 
	{

	}


/**
 * The main class that takes in 3 user arguments, a specific data structure, file 1, and file 2
 * The possible data structures are Binary Search Tree, -b, AVL tree, -a, and
 * Hash Table, -h
 * Prints the total words of each of the files and the difference metric
 * 
 * For the difference metric calculation, 
 * the lowest value is 0, with the files being identical
 * Values such as 30, can relate to the two files being very similar
 */
@SuppressWarnings("unused")
public static void main(String[] args) {

	Correlator corr = new Correlator(args[0], args[1]);
	String fileName1 = args[2];
	DataCount<String>[] file1 = corr.calculateFrequencies(fileName1);
	System.out.println("The total words: " + corr.totalWords(file1));

	Correlator corr2 = new Correlator(args[0], args[1]);
	String fileName2 = args[3];
	DataCount<String>[] file2 = corr2.calculateFrequencies(fileName2);
	System.out.println("The total words: " + corr2.totalWords(file2));

	System.out.println("The difference metric is " + corr.differenceMetric(file1, file2));
}

}
