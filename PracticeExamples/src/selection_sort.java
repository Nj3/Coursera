public class selection_sort {
	
	private boolean less(Integer v, Integer w) {
		return v.compareTo(w) < 0;
	}
	
	private void exch(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public void sort(int[] arr) {
		int len = arr.length;
		for ( int i = 0; i < len; ++i ) {
			int min = i;
			for ( int j = i+1; j < len; ++j) {
				if (less(arr[j], arr[min])) min = j;
			}
			exch(arr, i, min);
		}
	}
	
	public static void main(String[] args) {
		int[] ip = new int[] {10, 5, -1, 0, 2};
		selection_sort res = new selection_sort();
		res.sort(ip);
		for (int o: ip) {
			System.out.println(o);
		}
	}

}