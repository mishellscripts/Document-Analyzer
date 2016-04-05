
public class Tester {
	public static void main(String[] args)
	{
		DataCount[] counts = new DataCount[10];
		counts[0] = new DataCount("hi", 3);
		counts[1] = new DataCount("test",3);
		counts[2] = new DataCount("hello", 3);
		counts[3] = new DataCount("this", 3);
		counts[4] = new DataCount("that", 3);
		counts[5] = new DataCount("please", 3);
		counts[6] = new DataCount("work", 3);
		counts[7] = new DataCount("piece", 3);
		counts[8] = new DataCount("of", 3);
		counts[9] = new DataCount("shit", 3);


		
		System.out.println("Before sort:");
		for (DataCount c : counts)
		{
			System.out.print(c.count + " ");
		}
		System.out.println();
		
		quickSort(counts,0,counts.length-1);
		System.out.println("After sort:");
		for (DataCount c : counts)
		{
			System.out.print(c.count + " ");
		}
	}
	
	
	
	
	private static <E extends Comparable<? super E>> void quickSort(DataCount<E>[] counts, int low, int high) 
	{
		int left = low;
		int right = high;

		if (high-low == 1)
		{
			if (counts[low].count < counts[high].count)
			{
				swap(counts, low, high);
			}
		}

		// If there are 3 or more elements
		// Find median of low, mid, high
		// Move pointers

		else if ( left != right )
		{
			int mid = low + (high-low)/2;
			DataCount[] getMedian = new DataCount[3];
			getMedian[0] = counts[low];
			getMedian[1] = counts[mid];
			getMedian[2] = counts[high];
			for (int i = 1; i < getMedian.length; i++) 
			{
				DataCount x = getMedian[i];
				int j;
				for (j = i - 1; j >= 0; j--) {
					if (getMedian[j] != null && x != null)
					{
						if (getMedian[j].count >= x.count) {
							break;
						}
						getMedian[j + 1] = getMedian[j];
					}
				}
				getMedian[j + 1] = x;
			}
			// Pivot is the median
			DataCount pivot = getMedian[1];
			int pivotIndex = 0;
			if (pivot == counts[mid]) pivotIndex = mid;
			else if (pivot == counts[low]) pivotIndex = low;
			else if (pivot == counts[high]) pivotIndex = high;
			swap(counts, pivotIndex, low);
			left++;
			while (left < right)
			{
				while ( counts[left].count >= pivot.count && left < high)
				{
				left++;	
				}
				while (counts[right].count < pivot.count && right > low)
				{
					right--;
				}
				if (left < right) swap(counts, right, left);
			}
			swap(counts, low, left-1);
			
			pivotIndex = left-1;
			if (low < pivotIndex-1)
			{
			quickSort(counts, low, pivotIndex-1);
			}
			if (high > pivotIndex+1)
			{
			quickSort(counts, pivotIndex+1, high);
			}
		}


	}

	public static <E> void swap (DataCount<E>[] counts, int a, int b)
	{
		DataCount temp = counts[a];
		counts[a] = counts[b];
		counts[b] = temp;
		
	}
}
