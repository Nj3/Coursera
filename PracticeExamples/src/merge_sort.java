import edu.princeton.cs.algs4.Insertion;

public class merge_sort {
	
	/* helper functions:
	 * 1. less - compares v and w and returns true if v<w
	 * 2. exch - exchanges a[i],a[j]
	 * 3. isSorted - checks whether array is sorted
	 */
	
	private boolean less(Integer v, Integer w) {
		return v.compareTo(w) < 0;
	}
	
	/* not needed
	private void exch(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	*/
	
	private boolean isSorted(Integer[] a, int low, int hi) {
		for (int i=low; i<=hi; i++) {
			if ( !less(a[i], a[i+1]) ) return false;
		}
		return true;
	}
	
	public void merge(Integer[] a, Integer[] aux, int low, int mid, int hi ) {
		// check whether subarrays are sorted before merging
		assert isSorted(a, low, mid);
		assert isSorted(a, mid+1, hi);
		
		// create a copy
		for (int i=low; i<=hi; ++i) {
			aux[i] = a[i];
		}
		
		// actual sorting, i - starting index of left subarray; j - starting index of right subarray
		int i = low;
		int j = mid+1;
		for(int k=low; k<=hi; ++k) {
			if ( i>mid ) a[k] = aux[j++];
			else if ( j>hi ) a[k] = aux[i++];
			else if ( less(aux[i], aux[j]) ) a[k] = aux[i++];
			else a[k] = aux[j++];
		}
		
		//post condition
		assert isSorted(a, low, hi);
		
	}
	
	private void sort(Integer[] a, Integer[] aux, int low, int hi) {
		// do insertion sort for length < 7
		if ( hi <= low + 6 ) {
			Insertion.sort(a, low, hi+1);
			return;
		}
		
		int mid = low + (hi - low)/2;
		sort(a, aux, low, mid);
		sort(a, aux, mid+1, hi);
		if ( less(a[mid], a[mid+1]) ) return;
		merge(a, aux, low, mid, hi);
	}
	
	private void sort_merge(Integer[] a) {
		Integer[] aux = new Integer[a.length];
		sort(a, aux, 0, a.length-1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] ip = new Integer[] {1, 5, -1, 0, 2, 22, 18, 1, -500, 30, 102, 15002, 3, -11};
		merge_sort res = new merge_sort();
		res.sort_merge(ip);
		for (int o: ip) {
			System.out.println(o);
		}
	}

}
