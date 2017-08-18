
public class insertion_sort {

	private boolean less(Integer v, Integer w) {
		return v.compareTo(w) < 0;
	}
	
	private void exch(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	private void sort(int[] arr) {
		int len = arr.length;
		for ( int i = 0; i < len; ++i ) {
			//swap if a[i] > a[j]
			for ( int j = i; j > 0; --j) {
				if ( less(arr[j], arr[j-1]) ) exch(arr, j, j-1);
				else break;
			}
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] ip = new int[] {1, 5, -1, 0, 2};
		insertion_sort res = new insertion_sort();
		res.sort(ip);
		for (int o: ip) {
			System.out.println(o);
		}
	}

}
